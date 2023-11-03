package com.my.exception;
//Exception 예외전용클래스
public class FindException extends Exception {

	public FindException() {
		super();
	}

	public FindException(String message) {
		super(message); //여기서의 super = 부모인 Exception
	}

}
