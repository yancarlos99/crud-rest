package br.com.devmedia.webservice.exceptions;

public enum ErrorCode {
	
	BAD_REQUEST(400),
	NOT_FOUND(404),
	SERVER_ERROR(500);

	private int code;
	
	ErrorCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
}
