package io.HCL.ppmtool.exceptions;

public class InvalidLoginResponse {
	private String username;
	private String password;

	public InvalidLoginResponse() {
		this.username = "Invalid Username or Password";
		this.password = "Invalid Username or Password";
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

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
