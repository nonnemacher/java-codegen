package com.github.nonnemacher.codegen.dto;

import java.util.List;

/**
 * @author Carlos Henrique Nonnemacher
 * @version 1.0.0.0
 */
public class ClassCode {

	private String className;
	private String serviceName;
	private String packageName;
	private String serviceNameSpace;
	private List<Operation> operations;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getServiceNameSpace() {
		return serviceNameSpace;
	}

	public void setServiceNameSpace(String serviceNameSpace) {
		this.serviceNameSpace = serviceNameSpace;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

}
