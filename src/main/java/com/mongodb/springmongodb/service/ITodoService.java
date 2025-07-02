package com.mongodb.springmongodb.service;

import java.util.List;
import java.util.Optional;

import com.mongodb.springmongodb.model.TodoDTO;

public interface ITodoService {
	
	/**
	 * retorna el listado de todos los registros
	 * @return List<TodoDTO>
	 */
	List<TodoDTO> getAllTodos();
	
	/**
	 * retorna la entidad encontrada
	 * @param id
	 * @return Optional<TodoDTO>
	 */
	Optional<TodoDTO> getSingleTodo(String id);
	
	/**
	 * retorna la entidad guardada
	 * @param todo
	 * @return
	 */
	TodoDTO createdOrUpdateTodo(TodoDTO todo);
		
	/**
	 * retorna la entidad como bandera para determinar la eliminación o mensaje
	 * @param id
	 * @return Optional<TodoDTO>
	 */
	TodoDTO deleteAndFindById(String id);
	
	

}
