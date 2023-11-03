package com.my.exception;
//Exception 예외전용클래스
public class AddException extends Exception {

	public AddException() {
		super();
	}

	public AddException(String message) {
		super(message); //여기서의 super = 부모인 Exception
	}

}
