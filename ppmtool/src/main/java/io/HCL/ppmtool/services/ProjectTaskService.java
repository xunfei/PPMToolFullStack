package io.HCL.ppmtool.services;

import io.HCL.ppmtool.domain.Backlog;
import io.HCL.ppmtool.domain.Project;
import io.HCL.ppmtool.domain.ProjectTask;
import io.HCL.ppmtool.exceptions.ProjectIdException;
import io.HCL.ppmtool.exceptions.ProjectNotFoundException;
import io.HCL.ppmtool.repositories.BacklogRepository;
import io.HCL.ppmtool.repositories.ProjectRepository;
import io.HCL.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired

	private ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		// PTs to be added to a specific project, project != null, BL exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

		// Exceptions: Project not found
		if (backlog == null) {
			throw new ProjectNotFoundException("Project ID '" + projectIdentifier + "' does not exist");

		}
		// set the bl to pt
		projectTask.setBacklog(backlog);
		// we want our project sequence to be like this: IDPRO-1 IDPRO-2 ...100 101
		Integer backlogSequence = backlog.getPTSequence();
		// Update the BL SEQUENCE
		backlogSequence++;

		backlog.setPTSequence(backlogSequence);

		// Add Sequence to Project Task
		projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);

		// INITIAL priority when priority null
		if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
			projectTask.setPriority(3);
		}
		// INITIAL status when status is null
		if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TO_DO");
		}

		return projectTaskRepository.save(projectTask);
	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		Project project = projectRepository.findByProjectIdentifier(backlog_id);
		if (project == null) {

			throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exist");

		}

		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {

		// make sure we are searching on an existing backlog
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if (backlog == null) {
			throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exist");
		}

		// make sure that our task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

		if (projectTask == null) {
			throw new ProjectNotFoundException("Project Task '" + pt_id + "' not found");
		}

		// make sure that the backlog/project id in the path corresponds to the right
		// project
		if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task '" + pt_id + "' does not exist in project '" + backlog_id);
		}

		return projectTask;
	}

	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

		projectTask = updatedTask;

		return projectTaskRepository.save(projectTask);
	}

	public void deletePTByProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

		projectTaskRepository.delete(projectTask);
	}
}