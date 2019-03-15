package com.personal.stakeservice.server;

import java.lang.reflect.Method;

public class Handler {
	
	private Object instance;
	private Method method;
	private String httpOperation;

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getHttpOperation() {
		return httpOperation;
	}

	public void setHttpOperation(String httpOperation) {
		this.httpOperation = httpOperation;
	}
}
