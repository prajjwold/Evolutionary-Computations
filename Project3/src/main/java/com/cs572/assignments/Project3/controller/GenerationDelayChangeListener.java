package com.cs572.assignments.Project3.controller;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.cs572.assignments.Project3.model.NQueensModel;

public class GenerationDelayChangeListener implements ChangeListener {

	private NQueensModel model;

	public GenerationDelayChangeListener(NQueensModel model) {
		this.model = model;
	}

	public void stateChanged(ChangeEvent event) {
		JSlider source = (JSlider) event.getSource();
		if (!source.getValueIsAdjusting()) {
			model.setGenerationDelay(100L * source.getValue());
		}
	}

}