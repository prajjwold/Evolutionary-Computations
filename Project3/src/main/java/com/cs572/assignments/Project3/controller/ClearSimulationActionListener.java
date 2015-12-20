package com.cs572.assignments.Project3.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cs572.assignments.Project3.gui.NQueensFrame;
import com.cs572.assignments.Project3.model.NQueensModel;



public class ClearSimulationActionListener implements ActionListener {

	private NQueensFrame frame;

	private NQueensModel model;

	public ClearSimulationActionListener(NQueensFrame frame, NQueensModel model) {
		this.frame = frame;
		this.model = model;
	}

	public void actionPerformed(ActionEvent event) {
		model.clearGrid();
		frame.setGenerationTextField();
		frame.repaintGridPanel();
		frame.getControlPanel().getStartButton().setEnabled(false);
		frame.getControlPanel().getClearButton().setEnabled(false);
	}

}