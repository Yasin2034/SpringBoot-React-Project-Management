package com.example.reactproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reactproject.domain.Backlog;
import com.example.reactproject.domain.Project;
import com.example.reactproject.exception.ProjectIdException;
import com.example.reactproject.repository.BacklogRepository;
import com.example.reactproject.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;

	public Project saveOrUpdate(Project project) {

		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if(project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier());
			}
			
			if(project.getId() != null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
			}
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
