package com.floyd.http;

import java.util.Map;

public class BaseRequest extends AbstractRequest {

	private static final String USER_AGENT = "android";
	
	public BaseRequest(String url, Map<String, String> parameters, HttpMethod httpMethod) {
        super(url, null, parameters, null,httpMethod, null, null);
    }

	@Override
	protected String getUserAgent() {
		return USER_AGENT;
	}

}
