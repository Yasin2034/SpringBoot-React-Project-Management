package com.example.reactproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.reactproject.domain.Backlog;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {
	
	Backlog findByProjectIdentifier(String projectIdentifier);

}
