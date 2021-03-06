package com.example.reactproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.reactproject.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	@Override
	Iterable<Project> findAllById(Iterable<Long> iterable);

	Project findByProjectIdentifierIgnoreCase(String projectId);
	

	Iterable<Project> findAll();
	
	Iterable<Project> findAllByProjectLeader(String username);
}
