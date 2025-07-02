package com.mongodb.springmongodb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.springmongodb.model.TodoDTO;
import com.mongodb.springmongodb.service.ITodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/todos")
@Tag(name = "Todo API", description = "Operaciones CRUD para gestión de tareas")
public class TodoController {

	@Autowired
	private ITodoService iTodoService;
	
	@Operation(summary = "Obtener todas las tareas", description = "Retorna una lista de todas las tareas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tareas encontradas"),
        @ApiResponse(responseCode = "404", description = "No hay tareas disponibles")
    })
	@GetMapping
	public ResponseEntity<?> getAllTodos() {
		log.info("Solicitud para obtener todas las tareas");
		List<TodoDTO> todos = iTodoService.getAllTodos();
		if (todos.size() > 0) {
			log.debug("Se encontraron {} tareas", todos.size());
			return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
		} else {
			log.warn("No hay tareas disponibles");
			return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
		}
	}
	
    @Operation(summary = "Obtener una tarea por ID", description = "Retorna una tarea específica según el ID proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea encontrada"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
	@GetMapping("/{id}")
	public ResponseEntity<?> getSingleTodo(@PathVariable String id) {
		log.info("Solicitud para obtener tarea con ID: {}", id);
		Optional<TodoDTO> todoOptional = iTodoService.getSingleTodo(id);
		
		if(todoOptional.isPresent()) {
			log.debug("Tarea encontrada: {}", todoOptional.get());
			return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
		} else {
			log.warn("Tarea no encontrada con ID: {}", id);
			return new ResponseEntity<>("Todo not found with id: " + id, HttpStatus.NOT_FOUND);
		}
	}
	
    @Operation(summary = "Crear una nueva tarea")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea creada exitosamente")
    })
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
	    log.info("Solicitud para crear tarea: {}", todo);
	    TodoDTO result = iTodoService.createdOrUpdateTodo(todo);
	    log.info("Tarea creada con ID: {}", result.getId());
	    return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
    @Operation(summary = "Actualizar una tarea existente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
	@PutMapping("/{id}")
	public ResponseEntity<?> updateById(@PathVariable String id, @RequestBody TodoDTO todo) {
		log.info("Solicitud para actualizar tarea con ID: {}", id);
		todo.setId(id);
		todo = iTodoService.createdOrUpdateTodo(todo);
		
		if(todo != null) {
			log.info("Tarea actualizada: {}", todo);
			return new ResponseEntity<>(todo, HttpStatus.OK);
		} else {
			log.warn("No se encontró tarea con ID para actualizar: {}", id);
			return new ResponseEntity<>("Todo not found with id: " + id, HttpStatus.NOT_FOUND);
		}
	}
	
    @Operation(summary = "Eliminar una tarea por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea eliminada exitosamente")
    })
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		log.info("Solicitud para eliminar tarea con ID: {}", id);
		TodoDTO deleted = iTodoService.deleteAndFindById(id);
	    log.info("Tarea eliminada con ID: {}", id);
	    return ResponseEntity.ok(deleted);
	}
	
}
