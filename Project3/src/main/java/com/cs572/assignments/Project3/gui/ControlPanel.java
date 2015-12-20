package com.cs572.assignments.Project3.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.cs572.assignments.Project3.controller.ClearSimulationActionListener;
import com.cs572.assignments.Project3.controller.GenerationDelayChangeListener;
import com.cs572.assignments.Project3.controller.StartSimulationActionListener;
import com.cs572.assignments.Project3.controller.StopSimulationActionListener;
import com.cs572.assignments.Project3.model.NQueensModel;

public class ControlPanel {

	private static final Insets buttonInsets = new Insets(10, 10, 0, 10);

	private NQueensFrame frame;

	private NQueensModel model;

	private JPanel panel;

	private JTextField generationTextField;

	public ControlPanel(NQueensFrame frame, NQueensModel model) {
		this.frame = frame;
		this.model = model;
		createPartControl();
	}

	private void createPartControl() {
		StartSimulationActionListener startListener = new StartSimulationActionListener(frame, model);
		StopSimulationActionListener stopListener = new StopSimulationActionListener(frame);
		stopListener.setListener(startListener);

		panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		int gridy = 0;

		JLabel noOfQueensLabel = new JLabel("Number of Queens:");
		addComponent(panel, noOfQueensLabel, 0, gridy, 1, 1, buttonInsets, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL);

		JTextField noOfQueensTextField = new JTextField(10);
		noOfQueensTextField.setHorizontalAlignment(JTextField.RIGHT);
		noOfQueensTextField.setEditable(true);
		addComponent(panel, noOfQueensTextField, 1, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL);

		startButton = new JButton("Start Simulation");
		startButton.addActionListener(startListener);
		addComponent(panel, startButton, 0, gridy++, 2, 1, buttonInsets, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL);

		stopButton = new JButton("Stop Simulation");
		stopButton.addActionListener(stopListener);
		addComponent(panel, stopButton, 0, gridy++, 2, 1, buttonInsets, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL);

		clearButton = new JButton("Clear Simulation");
		clearButton.addActionListener(new ClearSimulationActionListener(frame, model));
		addComponent(panel, clearButton, 0, gridy++, 2, 1, buttonInsets, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL);

		JLabel sliderLabel = new JLabel("Generation Delay (Faster to Slower)", JLabel.CENTER);
		sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		addComponent(panel, sliderLabel, 0, gridy++, 2, 1, buttonInsets, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL);

		int defaultDelay = (int) model.getGenerationDelay() / 100;
		JSlider generationDelaySlider = new JSlider(JSlider.HORIZONTAL, 1, 6, defaultDelay);
		generationDelaySlider.addChangeListener(new GenerationDelayChangeListener(model));
		generationDelaySlider.setMajorTickSpacing(1);
		generationDelaySlider.setPaintLabels(false);
		generationDelaySlider.setPaintTicks(true);
		addComponent(panel, generationDelaySlider, 0, gridy++, 2, 1, buttonInsets, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL);

		JLabel generationLabel = new JLabel("Generation:");
		addComponent(panel, generationLabel, 0, gridy, 1, 1, buttonInsets, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL);

		generationTextField = new JTextField(10);
		generationTextField.setHorizontalAlignment(JTextField.RIGHT);
		generationTextField.setEditable(false);
		addComponent(panel, generationTextField, 1, gridy++, 1, 1, buttonInsets, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL);

		startButton.setEnabled(true);
		stopButton.setEnabled(true);
		clearButton.setEnabled(true);
	}

	private void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth,
			int gridheight, Insets insets, int anchor, int fill) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0D, 1.0D, anchor, fill,
				insets, 0, 0);
		container.add(component, gbc);
	}

	public void setGenerationTextField(long generationCount) {
		NumberFormat nf = NumberFormat.getInstance();
		generationTextField.setText(nf.format(generationCount));
	}

	public JPanel getPanel() {
		return panel;
	}

	private JButton stopButton;
	private JButton startButton;
	private JButton clearButton;
	public JButton getStopButton() {
		return stopButton;
	}

	public void setStopButton(JButton stopButton) {
		this.stopButton = stopButton;
	}

	public JButton getStartButton() {
		return startButton;
	}

	public void setStartButton(JButton startButton) {
		this.startButton = startButton;
	}

	public JButton getClearButton() {
		return clearButton;
	}

	public void setClearButton(JButton clearButton) {
		this.clearButton = clearButton;
	}
}