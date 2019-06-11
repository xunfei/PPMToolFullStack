package io.HCL.ppmtool.exceptions;

public class UsernameAlreadyExistsExceptionResponse {
	private String usernameAlreadyExists;

	public UsernameAlreadyExistsExceptionResponse(String usernameAlreadyExists) {
		this.usernameAlreadyExists = usernameAlreadyExists;
	}

	/**
	 * @return the usernameAlreadyExists
	 */
	public String getUsernameAlreadyExists() {
		return usernameAlreadyExists;
	}

	/**
	 * @param usernameAlreadyExists the usernameAlreadyExists to set
	 */
	public void setUsernameAlreadyExists(String usernameAlreadyExists) {
		this.usernameAlreadyExists = usernameAlreadyExists;
	}
	
	

}
