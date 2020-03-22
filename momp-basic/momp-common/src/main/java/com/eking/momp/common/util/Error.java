package com.eking.momp.common.util;

import lombok.Data;

@Data
public class Error {
	private String code;
	private String message;

	private Error(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static Error of(String code, String message) {
		return new Error(code, message);
	}

}
