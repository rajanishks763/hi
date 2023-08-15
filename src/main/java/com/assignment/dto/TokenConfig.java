package com.assignment.dto;

public class TokenConfig {
	
	private String tokenUrl;

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public TokenConfig(String tokenUrl) {
		super();
		this.tokenUrl = tokenUrl;
	}

	public TokenConfig() {
		super();
	}

	@Override
	public String toString() {
		return "TokenConfig [tokenUrl=" + tokenUrl + "]";
	}
	
	

}
