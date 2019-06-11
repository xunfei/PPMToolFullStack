package io.HCL.ppmtool.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = "Username cannot be blank")
	private String username;
	@NotBlank(message = "Password cannot be blank")
	private String password;

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