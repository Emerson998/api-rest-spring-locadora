package br.com.magna.api.masterlocadora.exception;

public class ServiceExceptionLocadora extends Exception{

	private static final long serialVersionUID = 1L;

	public ServiceExceptionLocadora(String msg) {
		super(msg);
	}

	public ServiceExceptionLocadora(String message, Throwable cause) {
		super(message, cause);
	}
}


