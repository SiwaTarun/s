package com.hcl.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationErrorResponse extends ErrorResponse {

	private Map<String, String> errorsMap = new HashMap<>();

	public Map<String, String> getErrorsMap() {
		return errorsMap;
	}

	public void setErrorsMap(Map<String, String> errorsMap) {
		this.errorsMap = errorsMap;
	}

}
