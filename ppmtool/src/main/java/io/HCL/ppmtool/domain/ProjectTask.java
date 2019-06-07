package io.HCL.ppmtool.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

@Entity
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable = false)
	private String projectSequence;
	@NotBlank(message = "Please include a project summary")
	private String summary;
	private String acceptanceCriteria;
	private String status;
	private Integer priority;
	private Date dueDate;
	// ManyToOne with Backlog
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "backlog_id", updatable = false, nullable = false)
	@JsonIgnore
	private Backlog backlog;

	@Column(updatable = false)
	private String projectIdentifier;
	private Date create_At;
	private Date update_At;

	public ProjectTask() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the projectSequence
	 */
	public String getProjectSequence() {
		return projectSequence;
	}

	/**
	 * @param projectSequence the projectSequence to set
	 */
	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the acceptanceCriteria
	 */
	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	/**
	 * @param acceptanceCriteria the acceptanceCriteria to set
	 */
	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the projectIdentifer
	 */
	public String getProjectIdentifer() {
		return projectIdentifier;
	}

	/**
	 * @param projectIdentifer the projectIdentifer to set
	 */
	public void setProjectIdentifer(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	/**
	 * @return the create_At
	 */
	public Date getCreate_At() {
		return create_At;
	}

	/**
	 * @param create_At the create_At to set
	 */
	public void setCreate_At(Date create_At) {
		this.create_At = create_At;
	}

	/**
	 * @return the update_At
	 */
	public Date getUpdate_At() {
		return update_At;
	}

	/**
	 * @param update_At the update_At to set
	 */
	public void setUpdate_At(Date update_At) {
		this.update_At = update_At;
	}

	/**
	 * @return the backlog
	 */
	public Backlog getBacklog() {
		return backlog;
	}

	/**
	 * @param backlog the backlog to set
	 */
	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	@PrePersist
	protected void onCreate() {
		this.create_At = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.update_At = new Date();
	}

	@Override
	public String toString() {
		return "ProjectTask{" + "id=" + id + ", projectSequence='" + projectSequence + '\'' + ", summary='" + summary
				+ '\'' + ", acceptanceCriteria='" + acceptanceCriteria + '\'' + ", status='" + status + '\''
				+ ", priority=" + priority + ", dueDate=" + dueDate + ", projectIdentifier='" + projectIdentifier + '\''
				+ ", create_At=" + create_At + ", update_At=" + update_At + '}';
	}
}
