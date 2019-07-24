package com.demo.booksearch.common.exception;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "입력 값에 오류가 있습니다..";


	public BadRequestException() {
		super(DEFAULT_MESSAGE);
	}

	public BadRequestException(String message) {
		super(message);
	}

}
