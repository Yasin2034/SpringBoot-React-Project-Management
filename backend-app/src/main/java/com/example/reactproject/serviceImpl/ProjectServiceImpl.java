package com.example.reactproject.serviceImpl;

import org.springframework.stereotype.Service;

import com.example.reactproject.domain.Backlog;
import com.example.reactproject.domain.Project;
import com.example.reactproject.domain.User;
import com.example.reactproject.exception.ProjectNotFoundException;
import com.example.reactproject.repository.BacklogRepository;
import com.example.reactproject.repository.ProjectRepository;
import com.example.reactproject.repository.UserRepository;
import com.example.reactproject.service.IProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {
	
	private final ProjectRepository projectRepository;
	private final BacklogRepository backlogRepository;
	private final UserRepository userRepository;

	@Override
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

	@Override
	public Project update(Project project, String username) {

		findByProjectIdentifier(project.getProjectIdentifier(),username);
		
		project.setBacklog(backlogRepository.findByProjectIdentifierIgnoreCase(project.getProjectIdentifier()));
		return projectRepository.save(project);
	}

	@Override
	public Project findByProjectIdentifier(String projectId, String username) {

		Project project = projectRepository.findByProjectIdentifierIgnoreCase(projectId);
		if (project == null || !project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project Id " + projectId + " does not exist");
		}

		return project;
	}


	@Override
	public Iterable<Project> findAllProjects(String username) {
		return projectRepository.findAllByProjectLeader(username);
	}

	@Override
	public void deleteByProjectIdentifier(String projectId, String username) {
		projectRepository.delete(findByProjectIdentifier(projectId, username));
	}

}
