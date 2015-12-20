package com.cs572.assignments.Project3.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cs572.assignments.Project3.gui.NQueensFrame;

public class StopSimulationActionListener implements ActionListener {

	private NQueensFrame frame;

	private StartSimulationActionListener listener;

	public StopSimulationActionListener(NQueensFrame frame) {
		this.frame = frame;
	}

	public void setListener(StartSimulationActionListener listener) {
		this.listener = listener;
	}

	public void actionPerformed(ActionEvent event) {
		listener.stopSimulation();

		frame.getControlPanel().getStartButton().setEnabled(true);
		frame.getControlPanel().getStopButton().setEnabled(false);
		frame.getControlPanel().getClearButton().setEnabled(true);
	}

}