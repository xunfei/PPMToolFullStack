package io.HCL.ppmtool.services;

import io.HCL.ppmtool.domain.Project;
import io.HCL.ppmtool.exceptions.ProjectIdException;
import io.HCL.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdateProject(Project project) {

		// Logic
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
		}
	}

	public Project findProjectByIdentifier(String projectId) {

		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if (project == null) {
			throw new ProjectIdException("Project ID '" + projectId + "' does not exist");

		}

		return project;
	}

	public Iterable<Project> findAllProjects() {
		return projectRepository.findAll();
	}

	public void deleteProjectByIdentifier(String projectid) {
		Project project = projectRepository.findByProjectIdentifier(projectid.toUpperCase());

		if (project == null) {
			throw new ProjectIdException(
					"Cannot delete project with ID '" + projectid + "'. This project does not exist");
		}

		projectRepository.delete(project);
	}

	/* Update - 1st try
	 * public void updateProject(Project p) { String projectid =
	 * p.getProjectIdentifier(); Project project =
	 * projectRepository.findByProjectIdentifier(projectid.toUpperCase());
	 * 
	 * if (project == null) { throw new ProjectIdException(
	 * "Cannot update project with ID '" + projectid +
	 * "'. This project does not exist"); }
	 * 
	 * p.setProjectIdentifier(p.getProjectIdentifier().toUpperCase());
	 * projectRepository.save(p); }
	 */
}
