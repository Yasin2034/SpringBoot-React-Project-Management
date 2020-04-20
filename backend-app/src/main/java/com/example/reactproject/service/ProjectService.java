package com.example.reactproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reactproject.domain.Project;
import com.example.reactproject.exception.ProjectIdException;
import com.example.reactproject.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	ProjectRepository projectRepository;

	public Project saveOrUpdate(Project project) {

		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		}
		catch (Exception e) {
			throw new ProjectIdException("Project Id " + project.getProjectIdentifier().toUpperCase() +" already exist");
		}
	}
	
	public Project findByProjectIdentifier(String projectId) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Project Id "+projectId.toUpperCase()+" does not exist");
		}
		return project;
	}
	
	public Iterable<Project> findAllProjects(){
		
		return projectRepository.findAll();
	}
	
	public void deleteByProjectIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Can not Project with Id "+projectId+". This project does not exist");
		}
		projectRepository.delete(project);
	}
	
}
