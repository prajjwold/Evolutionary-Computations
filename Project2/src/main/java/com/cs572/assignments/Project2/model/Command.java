/**
 * 
 */
package com.cs572.assignments.Project2.model;

/**
 * @author prajjwol
 *
 */
public enum Command {
	ADD(0), SUBSTRACT(1), DIVIDE(2), MULTIPLY(3), INPUTX(4), CONSTANT(5);

	private int commandCode;

	Command(int i) {
		this.commandCode = i;
	}

	public int getCommandCode() {
		return this.commandCode;
	}
	
	public void setCommandCode(int i) {
		commandCode=i;
	}

}
