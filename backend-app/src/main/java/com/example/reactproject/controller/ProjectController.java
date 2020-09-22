package com.example.reactproject.controller;

import java.security.Principal;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactproject.domain.Project;
import com.example.reactproject.service.IMapValidationService;
import com.example.reactproject.service.IProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IMapValidationService mapValidationService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result,Principal principal) {

		ResponseEntity<?> errorMap = mapValidationService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Project project1 = projectService.save(project,principal.getName());

		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}
	
	@PutMapping("")
	public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, BindingResult result,Principal principal) {

		ResponseEntity<?> errorMap = mapValidationService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Project project1 = projectService.update(project,principal.getName());

		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<Project> getProjectById(@PathVariable String projectId,Principal principal) {

		Project project = projectService.findByProjectIdentifier(projectId,principal.getName());

		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Project> getAllProjects(Principal principal) {

		return projectService.findAllProjects(principal.getName());
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId,Principal principal){
		projectService.deleteByProjectIdentifier(projectId,principal.getName());
		
		return new ResponseEntity<String>("Project with Id: " +projectId+" was deleted.",HttpStatus.OK);
	}

}
