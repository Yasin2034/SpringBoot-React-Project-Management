package com.example.reactproject.service;

import com.example.reactproject.domain.Project;

public interface IProjectService {
	

	Project save(Project project, String username);
	
	Project update(Project project, String username);

	
    /**
     * If the project exists in the user account, find the user's project by ID and username otherwise throws an error
     * @param projectId
     * @param username
     * @return
     */
	Project findByProjectIdentifier(String projectId, String username);

	Iterable<Project> findAllProjects(String username);

	void deleteByProjectIdentifier(String projectId, String username);

}
