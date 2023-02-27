package com.example.demo.common.response;

import lombok.Data;

@Data
public class Result<T> {

	private int code;
	private String message;
	private T data;

	public Result(int code) {
		this.code = code;
		this.message = Message.SUCCESS;
	}

	public Result(int code, T data) {
		this.code = code;
		this.message = Message.SUCCESS;
		this.data = data;
	}

	private Result(int code, String msg) {
		this.code = code;
		this.message = msg;
	}

	public Result(int code, String msg, T data) {
		this.code = code;
		this.message = msg;
		this.data = data;
	}

	public static <T> Result<T> success() {
		return new Result<>(Code.SUCCESS, null);
	}

	public static <T> Result<T> success(T data) {
		return new Result<>(Code.SUCCESS, data);
	}

	public static <T> Result<T> success(String msg, T data) {
		return new Result<T>(Code.SUCCESS, msg, data);
	}

	public static <T> Result<T> failure() {
		return new Result<>(Code.FAILURE, null);
	}

	public static <T> Result<T> failure(String msg) {
		return new Result<>(Code.FAILURE, msg);
	}

	public static <T> Result<T> failure(String msg, T data) {
		return new Result<>(Code.FAILURE, msg, data);
	}

	public static <T> Result<T> build(int code, String msg, T data) {
		return new Result<T>(code, msg, data);
	}

}
