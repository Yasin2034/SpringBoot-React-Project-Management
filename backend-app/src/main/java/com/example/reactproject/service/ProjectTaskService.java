package com.example.reactproject.service;

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
	BacklogRepository backlogRepository;

	@Autowired
	ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		try {
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			projectTask.setBacklog(backlog);
			Integer backlogSequence = backlog.getPTSequence();
			backlogSequence++;
			backlog.setPTSequence(backlogSequence);
			projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + backlogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			if (projectTask.getPriority() == null) {
				projectTask.setPriority(3);
			}

			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}

			return projectTaskRepository.save(projectTask);

		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}

	}

	public List<ProjectTask> findBacklogById(String backlog_id) {
		Project project = projectRepository.findByProjectIdentifier(backlog_id);
		if(project == null) throw new ProjectNotFoundException("Project with ID: " +backlog_id+" does not exist");
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}
	
	
	public ProjectTask findPTByProjectSequence(String backlog_id ,String pt_id) {
		
		backlog_id =backlog_id.toUpperCase();
		pt_id=pt_id.toUpperCase();
		
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if(backlog == null) {
			throw new ProjectNotFoundException("Project wih ID: " + backlog_id+" does not exist");
		}
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project Task " + pt_id+" not found");
		}
		
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task " + pt_id+" does not exist in project: " + backlog_id);
		}
		
		return projectTaskRepository.findByProjectSequence(pt_id);
	}
	
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask,String backlog_id,String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id);
		projectTask = updatedProjectTask;
		return projectTaskRepository.save(projectTask);
	}
	
	public void deletePTByProjectSequence(String backlog_id,String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id);
		projectTaskRepository.delete(projectTask);
	}
	
	

}
