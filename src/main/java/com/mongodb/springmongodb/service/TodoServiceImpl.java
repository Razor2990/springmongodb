package com.mongodb.springmongodb.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.springmongodb.exceptions.ErrorCode;
import com.mongodb.springmongodb.exceptions.ResourceAlreadyExistsException;
import com.mongodb.springmongodb.exceptions.ResourceNotFoundException;
import com.mongodb.springmongodb.model.TodoDTO;
import com.mongodb.springmongodb.repository.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoServiceImpl implements ITodoService {

	@Autowired
	private TodoRepository todoRepository;

	@Override
	public List<TodoDTO> getAllTodos() {

		log.info("Obteniendo todos los registros de tareas...");
		List<TodoDTO> todos = todoRepository.findAll();
		log.debug("Total de tareas encontradas: {}", todos.size());
		return todos;
	}

	@Override
	public Optional<TodoDTO> getSingleTodo(String id) {

		log.info("Buscando tarea con ID: {}", id);
		Optional<TodoDTO> todo = todoRepository.findById(id);
		if (todo.isPresent()) {
			log.debug("Tarea encontrada: {}", todo.get());
		} else {
			log.warn("No se encontró ninguna tarea con ID: {}", id);
		}
		return todo;
	}

	@Override
	public TodoDTO createdOrUpdateTodo(TodoDTO todo) {
		log.info("Iniciando operación de creación/actualización para: {}", todo);

		List<TodoDTO> todoOptional = todoRepository.findByTodo(todo.getTodo());

		if (todo.getId() == null) {
			if (todoOptional.size() < 1) {
				todo.setCreatedAt(new Date());
				TodoDTO saved = todoRepository.save(todo);
				log.info("Tarea creada exitosamente: {}", saved);
				return saved;
			} else {
				log.warn("Intento de crear tarea duplicada: {}", todo.getTodo());
				throw new ResourceAlreadyExistsException(ErrorCode.RECORD_FOUND,
						"Registro: " + todo.getTodo() + " encontrado.");
			}
		} else {
			log.info("Intentando actualizar tarea con ID: {}", todo.getId());
			return todoRepository.findById(todo.getId()).map(existing -> {
				boolean changed = false;

				if (todo.getTodo() != null && !todo.getTodo().equals(existing.getTodo())) {
					log.debug("Campo 'todo' actualizado de '{}' a '{}'", existing.getTodo(), todo.getTodo());
					existing.setTodo(todo.getTodo());
					changed = true;
				}
				if (todo.getDescription() != null && !todo.getDescription().equals(existing.getDescription())) {
					log.debug("Campo 'description' actualizado de '{}' a '{}'", existing.getDescription(),
							todo.getDescription());
					existing.setDescription(todo.getDescription());
					changed = true;
				}
				if (todo.getCompleted() != null && !todo.getCompleted().equals(existing.getCompleted())) {
					log.debug("Campo 'completed' actualizado de '{}' a '{}'", existing.getCompleted(),
							todo.getCompleted());
					existing.setCompleted(todo.getCompleted());
					changed = true;
				}

				if (changed) {
					existing.setUpdatedAt(new Date());
					TodoDTO updated = todoRepository.save(existing);
					log.info("Tarea actualizada exitosamente: {}", updated);
					return updated;
				} else {
					log.info("No se detectaron cambios en la tarea con ID: {}", todo.getId());
					return existing;
				}
			}).orElseThrow(() -> {
				log.error("No se encontró la tarea con ID: {}", todo.getId());
				return new ResourceNotFoundException(ErrorCode.NOT_FOUND,
						"Todo con ID " + todo.getId() + " no encontrado.");
			});

		}
	}

	@Override
	public TodoDTO deleteAndFindById(String id) {
		log.info("Intentando eliminar tarea con ID: {}", id);
		return todoRepository.findById(id).map(todo -> {
			log.info("Tarea eliminada exitosamente con ID: {}", id);
			todoRepository.deleteById(id);
			return todo;
		}).orElseThrow(() -> {
			log.error("No se encontró la tarea con ID para eliminar: {}", id);
			return new ResourceNotFoundException(ErrorCode.NOT_FOUND,
					"Todo con ID " + id + " no encontrado.");
		});

	}

}
