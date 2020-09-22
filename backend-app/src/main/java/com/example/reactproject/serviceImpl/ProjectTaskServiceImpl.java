package com.example.reactproject.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.reactproject.domain.Backlog;
import com.example.reactproject.domain.ProjectTask;
import com.example.reactproject.exception.ProjectNotFoundException;
import com.example.reactproject.repository.ProjectTaskRepository;
import com.example.reactproject.service.IProjectService;
import com.example.reactproject.service.IProjectTaskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectTaskServiceImpl implements IProjectTaskService {

	private final ProjectTaskRepository projectTaskRepository;
	private final IProjectService projectService;
	

	@Override
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

	@Override
	public List<ProjectTask> findBacklogById(String backlog_id, String username) {
		projectService.findByProjectIdentifier(backlog_id, username);
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}

	@Override
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {
		projectService.findByProjectIdentifier(backlog_id, username);

		ProjectTask projectTask = projectTaskRepository.findByProjectSequenceIgnoreCase(pt_id);
		if (projectTask == null || !projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task " + pt_id + " not found");
		}

		return projectTaskRepository.findByProjectSequenceIgnoreCase(pt_id);
	}

	@Override
	public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String backlog_id, String pt_id,
			String username) {
		findPTByProjectSequence(backlog_id, pt_id, username);
		return projectTaskRepository.save(updatedProjectTask);
	}

	@Override
	public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
		projectTaskRepository.delete(projectTask);
	}

}
