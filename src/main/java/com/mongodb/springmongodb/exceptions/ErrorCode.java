package com.mongodb.springmongodb.exceptions;

public enum ErrorCode {

	NOT_FOUND("NOT_FOUND"),
	RECORD_FOUND("RECORD_FOUND"),
	VALIDATION_ERROR("VALIDATION_ERROR"), 
	PARAMETER_MISSING("PARAMETER_MISSING"),
	TYPE_MISMATCH("TYPE_MISMATCH"), 
	INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR");
	

	private final String code;

	ErrorCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
