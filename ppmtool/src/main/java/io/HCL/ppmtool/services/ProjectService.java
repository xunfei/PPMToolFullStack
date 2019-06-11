package io.HCL.ppmtool.services;

import io.HCL.ppmtool.domain.Backlog;
import io.HCL.ppmtool.domain.Project;
import io.HCL.ppmtool.domain.User;
import io.HCL.ppmtool.exceptions.ProjectIdException;
import io.HCL.ppmtool.exceptions.ProjectNotFoundException;
import io.HCL.ppmtool.repositories.BacklogRepository;
import io.HCL.ppmtool.repositories.ProjectRepository;
import io.HCL.ppmtool.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private UserRepository userRepository;

	public Project saveOrUpdateProject(Project project, String username) {

		if (project.getId() != null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found in your account");
			} else if (existingProject == null) {
				throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier()
						+ "' cannot be updated because it doesn't exist");
			}
		}

		try {
			User user = userRepository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());

			String projectIdentifier = project.getProjectIdentifier().toUpperCase();
			project.setProjectIdentifier(projectIdentifier);
			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(projectIdentifier);
			}
			if (project.getId() != null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
			}
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
		}
	}

	public Project findProjectByIdentifier(String projectId, String username) {

		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if (project == null) {
			throw new ProjectIdException("Project ID '" + projectId + "' does not exist");

		}
		if (!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project not found in your account");
		}

		return project;
	}

	public Iterable<Project> findAllProjects(String username) {
		return projectRepository.findAllByProjectLeader(username);
	}

	public void deleteProjectByIdentifier(String projectid, String username) {
		projectRepository.delete(findProjectByIdentifier(projectid, username));
	}

}
