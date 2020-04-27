package com.example.reactproject.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reactproject.domain.Backlog;
import com.example.reactproject.domain.Project;
import com.example.reactproject.domain.ProjectTask;
import com.example.reactproject.exception.ProjectNotFoundException;
import com.example.reactproject.repository.BacklogRepository;
import com.example.reactproject.repository.ProjectRepository;
import com.example.reactproject.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectService projectService;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask,String username) {

		try {
			Backlog backlog = projectService.findByProjectIdentifier(projectIdentifier, username).getBacklog();
			projectTask.setBacklog(backlog);
			Integer backlogSequence = backlog.getPTSequence();
			backlogSequence++;
			backlog.setPTSequence(backlogSequence);
			projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + backlogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
				projectTask.setPriority(3);
			}

			if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
				projectTask.setStatus("TO_DO");
			}

			return projectTaskRepository.save(projectTask);

		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}

	}

	public List<ProjectTask> findBacklogById(String backlog_id,String username) {
		Project project = projectService.findByProjectIdentifier(backlog_id, username);
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}
	
	
	public ProjectTask findPTByProjectSequence(String backlog_id ,String pt_id, String username) {
		

		projectService.findByProjectIdentifier(backlog_id, username);

		
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project Task " + pt_id+" not found");
		}
		
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task " + pt_id+" does not exist in project: " + backlog_id);
		}
		
		return projectTaskRepository.findByProjectSequence(pt_id);
	}
	
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask,String backlog_id,String pt_id,String username) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);
		projectTask = updatedProjectTask;
		return projectTaskRepository.save(projectTask);
	}
	
	public void deletePTByProjectSequence(String backlog_id,String pt_id,String username) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);
		projectTaskRepository.delete(projectTask);
	}
	
	

}
