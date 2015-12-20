/**
 * 
 */
package com.cs572.assignments.Project3.controller;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.cs572.assignments.Project3.model.NQueensModel;

/**
 * @author prajjwol
 *
 */
public class NumberOfQueensChangeListener implements ChangeListener {

	private NQueensModel model;

	public NumberOfQueensChangeListener(NQueensModel model) {
		this.model = model;
	}

	public void stateChanged(ChangeEvent event) {
		JTextField source = (JTextField) event.getSource();
		model.setNoOfQueens(Integer.valueOf(source.getText()));
	}

}