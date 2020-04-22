package com.example.reactproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.reactproject.domain.ProjectTask;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
	
	List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
	
	ProjectTask findByProjectSequence(String projectSequence);
	

}
