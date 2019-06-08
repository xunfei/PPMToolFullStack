package io.HCL.ppmtool.exceptions;

public class ProjectNotFoundExceptionResponse {
	private String projectNotFound;

	public ProjectNotFoundExceptionResponse(String projectNotFound) {
		this.projectNotFound = projectNotFound;
	}

	/**
	 * @return the projectNotFound
	 */
	public String getProjectNotFound() {
		return projectNotFound;
	}

	/**
	 * @param projectNotFound the projectNotFound to set
	 */
	public void setProjectNotFound(String projectNotFound) {
		this.projectNotFound = projectNotFound;
	}

}
