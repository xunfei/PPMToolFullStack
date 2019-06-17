package io.HCL.ppmtool.services;

import io.HCL.ppmtool.domain.Backlog;
import io.HCL.ppmtool.domain.ProjectTask;
import io.HCL.ppmtool.exceptions.ProjectNotFoundException;
import io.HCL.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectService projectService;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

		// PTs to be added to a specific project, project != null, BL exists
		Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();

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
		if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
			projectTask.setPriority(3);
		}
		// INITIAL status when status is null
		if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
			projectTask.setStatus("TO_DO");
		}

		return projectTaskRepository.save(projectTask);
	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id, String sort, String username) {
		// check for exception
		projectService.findProjectByIdentifier(backlog_id, username);
		
		//sort by due date and put nulls at the end.--------the nullsLast does not work. 
		Sort nullsLast = Sort.by(Sort.Order.asc("dueDate").nullsLast());

		// sort: 0=Priority (default), 1=DueDate, 2=ProjectSequence
		if (sort.equals("1")) {
			return projectTaskRepository.findByProjectIdentifier(backlog_id, nullsLast);
		} else if (sort.equals("2")) {
			return projectTaskRepository.findByProjectIdentifierOrderByProjectSequence(backlog_id);
		} else {
			return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
		}

	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {

		// make sure we are searching on an existing backlog
		projectService.findProjectByIdentifier(backlog_id, username);

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

	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id,
			String username) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

		projectTask = updatedTask;

		return projectTaskRepository.save(projectTask);
	}

	public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

		projectTaskRepository.delete(projectTask);
	}
}