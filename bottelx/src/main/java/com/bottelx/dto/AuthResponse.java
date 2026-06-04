package com.bottelx.dto;


public class AuthResponse {

	private String accessToken;
	private UserDto user;
	private String refreshToken;

	public AuthResponse(String accessToken, String refreshToken, UserDto user) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.user = user;

	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public AuthResponse(String accessToken, UserDto user) {
		super();
		this.accessToken = accessToken;
		this.user = user;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}

