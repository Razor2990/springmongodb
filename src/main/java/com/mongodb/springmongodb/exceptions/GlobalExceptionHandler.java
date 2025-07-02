package com.mongodb.springmongodb.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.mongodb.springmongodb.model.ApiError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 1. Recurso no encontrado (genérico)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
    	
    	log.warn("Recurso no encontrado: {}", ex.getMessage());
    	
        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .errorCode(ErrorCode.NOT_FOUND.getCode())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleResourceAlreadyExists(ResourceAlreadyExistsException ex, WebRequest request) {
    	
    	log.warn("Recurso encontrado: {}", ex.getMessage());
    	
        ApiError error = ApiError.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .errorCode(ErrorCode.RECORD_FOUND.getCode())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    
 // 2. Error genérico
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiError> handleGeneralError(Exception ex, WebRequest request) {
    	
    	log.warn("Error interno del servidor: {}", ex.getMessage());
    	
        return buildErrorResponse(
                "Error interno del servidor",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request,
                ex.getMessage(),
                ErrorCode.INTERNAL_SERVER_ERROR);
    }

    // 2. Error genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex, WebRequest request) {
    	
    	log.error("Excepción general atrapada: {}", ex.getMessage(), ex);
    	
        return buildErrorResponse(
                "Error interno del servidor",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request,
                ex.getMessage(),
                ErrorCode.INTERNAL_SERVER_ERROR);
    }

    // 3. Parámetro ausente en la solicitud (ej. /api?name= falta)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
    	
    	log.warn("Parámetro requerido ausente: {}", ex.getMessage());

        ApiError error = ApiError.builder()
                .status(status.value())
                .error("Parámetro requerido ausente")
                .errorCode(ErrorCode.PARAMETER_MISSING.getCode())
                .message(ex.getParameterName() + " es requerido")
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, status);
    }

    // 4. Error de tipo de argumento (ej. se esperaba un número, pero llegó una cadena)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
    	
    	log.warn("Error con tipo de argumento: {}", ex.getMessage());
    	
        String message = String.format("Parámetro '%s' debe ser de tipo '%s'",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido");

        return buildErrorResponse(message, HttpStatus.BAD_REQUEST, request, null, ErrorCode.TYPE_MISMATCH);
    }

    // 5. Validaciones con @Valid fallidas
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
    	
    	log.warn("@valid fallidas: {}", ex.getMessage());

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Error de validación")
                .errorCode(ErrorCode.VALIDATION_ERROR.getCode())
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Utilidad para construir la respuesta de error
    private ResponseEntity<ApiError> buildErrorResponse(String message, HttpStatus status, WebRequest request, String details, ErrorCode errorCode) {
        ApiError error = ApiError.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .errorCode(errorCode.getCode())
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, status);
    }
}