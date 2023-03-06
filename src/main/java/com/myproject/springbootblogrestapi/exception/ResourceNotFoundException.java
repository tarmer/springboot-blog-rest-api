package com.myproject.springbootblogrestapi.exception;

public class ResourceNotFoundException extends   RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fieldName;
	private long fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, long id) {
		super(String.format("%s not found with %s:%s", resourceName, fieldName, id));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = id;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName; 
	}

	public long getFieldValue() {
		return fieldValue;
	}

}
