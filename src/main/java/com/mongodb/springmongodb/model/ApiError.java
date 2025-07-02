package com.mongodb.springmongodb.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

	private int status;
    private String error;
    private String errorCode;
    private String message;
    private String path;
    private LocalDateTime timestamp;
}
