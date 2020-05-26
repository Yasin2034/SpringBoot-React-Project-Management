package com.example.reactproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reactproject.domain.Backlog;
import com.example.reactproject.domain.ProjectTask;
import com.example.reactproject.exception.ProjectNotFoundException;
import com.example.reactproject.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectService projectService;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

			Backlog backlog = projectService.findByProjectIdentifier(projectIdentifier, username).getBacklog();
			projectTask.setBacklog(backlog);
			Integer backlogSequence = backlog.getPTSequence();
			backlogSequence++;
			backlog.setPTSequence(backlogSequence);
			projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + backlogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			return projectTaskRepository.save(projectTask);

	}

	public List<ProjectTask> findBacklogById(String backlog_id, String username) {
		projectService.findByProjectIdentifier(backlog_id, username);
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}

	/**
	 * If the project task exists in the user account, find the user's project by ID and username otherwise throws an error
	 * @param backlog_id
	 * @param pt_id is project task id
	 * @param username
	 * @return
	 */
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {

		projectService.findByProjectIdentifier(backlog_id, username);

		ProjectTask projectTask = projectTaskRepository.findByProjectSequenceIgnoreCase(pt_id);
		if (projectTask == null || !projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task " + pt_id + " not found");
		}

		return projectTaskRepository.findByProjectSequenceIgnoreCase(pt_id);
	}

	public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String backlog_id, String pt_id,
			String username) {
		findPTByProjectSequence(backlog_id, pt_id, username);
		return projectTaskRepository.save(updatedProjectTask);
	}

	public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
		projectTaskRepository.delete(projectTask);
	}

}
