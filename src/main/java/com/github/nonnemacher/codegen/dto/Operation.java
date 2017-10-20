package com.github.nonnemacher.codegen.dto;

import com.sun.xml.internal.ws.util.StringUtils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Carlos Henrique Nonnemacher
 * @version 1.0.0.0
 */
public class Operation {

	private StringProperty name;

	private StringProperty in;

	private StringProperty out;

	public Operation() {

	}

	public Operation(String name) {
		this.name = new SimpleStringProperty(name);
		this.in = new SimpleStringProperty("In" + StringUtils.capitalize(name));
		this.out = new SimpleStringProperty("Out" + StringUtils.capitalize(name));
	}

	public StringProperty getName() {
		return name;
	}

	public void setName(StringProperty name) {
		this.name = name;
	}

	public StringProperty getIn() {
		return in;
	}

	public void setIn(StringProperty in) {
		this.in = in;
	}

	public StringProperty getOut() {
		return out;
	}

	public void setOut(StringProperty out) {
		this.out = out;
	}

}
