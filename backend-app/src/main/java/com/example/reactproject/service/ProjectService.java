package com.example.reactproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reactproject.domain.Backlog;
import com.example.reactproject.domain.Project;
import com.example.reactproject.domain.User;
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

	public Project save(Project project, String username) {

		try {
			User user = userRepository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			project.setProjectIdentifier(project.getProjectIdentifier());

			Backlog backlog = new Backlog();
			project.setBacklog(backlog);
			backlog.setProject(project);
			backlog.setProjectIdentifier(project.getProjectIdentifier());
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectNotFoundException(
					"Project Id " + project.getProjectIdentifier() + " already exist");
		}
	}

	public Project update(Project project, String username) {

		findByProjectIdentifier(project.getProjectIdentifier(),username);
		
		project.setBacklog(backlogRepository.findByProjectIdentifierIgnoreCase(project.getProjectIdentifier()));
		return projectRepository.save(project);

	}

	
    /**
     * If the project exists in the user account, find the user's project by ID and username otherwise throws an error
     * @param projectId
     * @param username
     * @return
     */
	public Project findByProjectIdentifier(String projectId, String username) {

		Project project = projectRepository.findByProjectIdentifierIgnoreCase(projectId);
		if (project == null || !project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project Id " + projectId + " does not exist");
		}

		return project;
	}

	public Iterable<Project> findAllProjects(String username) {

		return projectRepository.findAllByProjectLeader(username);
	}

	public void deleteByProjectIdentifier(String projectId, String username) {
		projectRepository.delete(findByProjectIdentifier(projectId, username));
	}

}
