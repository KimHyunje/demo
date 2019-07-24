package com.demo.booksearch.common.exception;

@SuppressWarnings("serial")
public class InternalServerErrorException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "일시적인 오류가 있습니다. 잠시 후에 다시 시도해 주세요.";

	public InternalServerErrorException() {
		super(DEFAULT_MESSAGE);
	}

	public InternalServerErrorException(String message) {
		super(message);
	}

}
