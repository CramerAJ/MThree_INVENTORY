package com.m3.training.inventoryProj;

public class NotInDBException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NotInDBException(String exceptionMsg) {
		super(exceptionMsg);
	}
}
