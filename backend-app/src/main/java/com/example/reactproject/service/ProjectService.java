package com.example.reactproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reactproject.domain.Backlog;
import com.example.reactproject.domain.Project;
import com.example.reactproject.domain.User;
import com.example.reactproject.exception.ProjectIdException;
import com.example.reactproject.exception.ProjectNotFoundException;
import com.example.reactproject.repository.BacklogRepository;
import com.example.reactproject.repository.ProjectRepository;
import com.example.reactproject.repository.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	UserRepository userRepository;

	public Project saveOrUpdate(Project project,String username) {

		if(project.getId() != null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			if(existingProject != null && !existingProject.getProjectLeader().equals(username)) {
				throw new ProjectNotFoundException("Project not found in your account");
			}
			else if (existingProject == null) {
				throw new ProjectNotFoundException("Project with ID: "+ project.getProjectIdentifier()+"cannot be updated because it doesn't exist");
			}
		}

		try {
			User user = userRepository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
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
	
	public Project findByProjectIdentifier(String projectId,String username) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Project Id "+projectId.toUpperCase()+" does not exist");
		}
		if(!project.getProjectLeader().equals(username)) {
			throw new ProjectIdException("Project not found in your account");
		}
		return project;
	}
	
	public Iterable<Project> findAllProjects(String username){
		
		return projectRepository.findAllByProjectLeader(username);
	}
	
	public void deleteByProjectIdentifier(String projectId,String username) {
		projectRepository.delete(findByProjectIdentifier(projectId,username));
	}
	
}
