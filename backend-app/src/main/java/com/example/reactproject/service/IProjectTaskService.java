package com.example.reactproject.service;

import java.util.List;

import com.example.reactproject.domain.ProjectTask;

public interface IProjectTaskService {

	ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username);

	List<ProjectTask> findBacklogById(String backlog_id, String username);

	/**
	 * If the project task exists in the user account, find the user's project by ID and username otherwise throws an error
	 * @param backlog_id
	 * @param pt_id is project task id
	 * @param username
	 * @return
	 */
	ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username);

	ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String backlog_id, String pt_id,
			String username);
	
	void deletePTByProjectSequence(String backlog_id, String pt_id, String username);
	
	
}

