package com.example.reactproject.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactproject.domain.ProjectTask;
import com.example.reactproject.repository.BacklogRepository;
import com.example.reactproject.service.MapValidationService;
import com.example.reactproject.service.ProjectTaskService;


@RestController
@RequestMapping("/api/backlog")
public class BacklogController {
	
	@Autowired
	ProjectTaskService projectTaskService;
	
	@Autowired
	MapValidationService mapValidationService;
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask,BindingResult result,
			@PathVariable String backlog_id,Principal principal){
		
		ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask,principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
	}
	
	@GetMapping("/{backlog_id}")
	public ResponseEntity<List<ProjectTask>> getProjectBacklog(@PathVariable String backlog_id,Principal principal){
		

		return new ResponseEntity<List<ProjectTask>>(projectTaskService.findBacklogById(backlog_id,principal.getName()), HttpStatus.OK);
	}
	
	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id,Principal principal){
		
		
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id,pt_id,principal.getName());
		
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}
	
	@PatchMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,BindingResult result,
			@PathVariable String backlog_id,@PathVariable String pt_id,Principal principal){
		ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		
		ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id,principal.getName());
		
	    return new ResponseEntity<ProjectTask>(updatedTask,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id,Principal principal){
		projectTaskService.deletePTByProjectSequence(backlog_id, pt_id,principal.getName());
		
		return new ResponseEntity<String>("Project Task " +pt_id+"was deleted successfully",HttpStatus.OK);
	}
	
	
	
	
	
	

}
