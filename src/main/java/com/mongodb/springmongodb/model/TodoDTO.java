package com.mongodb.springmongodb.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Genera getters, setters, equals, hashCode y toString
@AllArgsConstructor // Genera un constructors con todos los argumentos
@NoArgsConstructor	// Genera un constructor sin argumentos
@Builder	// Genera el patrón builder
@Document(collection = "todos")	//general el documento de mongodb
public class TodoDTO {

	@Id
	private String id;
	
	@NotEmpty
	@NotNull
	private String todo;
	
	@NotEmpty
	@NotNull
	private String description;
	
	@NotNull
	private Boolean completed;
	
	private Date createdAt;
	private Date updatedAt;
}
