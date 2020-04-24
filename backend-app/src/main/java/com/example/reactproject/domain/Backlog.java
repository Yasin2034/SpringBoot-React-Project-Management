package com.example.reactproject.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Backlog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer PTSequence = 0;
	
	private String projectIdentifier;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id", nullable = false)
	@JsonIgnore
	private Project project;
	
	@OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,mappedBy = "backlog",orphanRemoval = true)
	private List<ProjectTask> projectTasks = new ArrayList<>();

}
