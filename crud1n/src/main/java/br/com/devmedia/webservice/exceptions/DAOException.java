package br.com.devmedia.webservice.exceptions;

public class DAOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3965087475900464946L;
	
	private int code;
	
	public DAOException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
}
