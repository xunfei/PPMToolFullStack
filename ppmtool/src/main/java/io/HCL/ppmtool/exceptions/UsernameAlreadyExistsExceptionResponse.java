package io.HCL.ppmtool.exceptions;

public class UsernameAlreadyExistsExceptionResponse {
	private String username;

	public UsernameAlreadyExistsExceptionResponse(String username) {
		this.username = username;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
