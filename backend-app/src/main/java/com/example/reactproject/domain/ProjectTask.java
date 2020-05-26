package com.example.reactproject.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false, unique = true)
	private String projectSequence;

	@NotBlank(message = "Please include a project summary")
	private String summary;

	private String acceptanceCriteria;

	@NotBlank
	private String status;


	@Min(value = 1)
	@Max(value = 3)
	private Integer priority;

	private Date dueDate;

	private Date create_At;

	private Date update_At;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "backlog_id", updatable = false, nullable = false)
	@JsonIgnore
	private Backlog backlog;

	@Column(updatable = false)
	private String projectIdentifier;

	@PrePersist
	protected void onCreate() {
		this.create_At = new Date();

	}

	@PreUpdate
	protected void onUpdate() {
		this.update_At = new Date();

	}
}
