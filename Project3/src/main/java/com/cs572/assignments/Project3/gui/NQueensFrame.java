package com.cs572.assignments.Project3.gui;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.cs572.assignments.Project3.model.NQueensModel;

public class NQueensFrame {

	private ControlPanel controlPanel;

	private NQueensModel model;

	private GridPanel gridPanel;

	private JFrame frame;

	public NQueensFrame(NQueensModel model) {
		this.model = model;
		createPartControl();
	}

	private void createPartControl() {
		controlPanel = new ControlPanel(this, model);
		gridPanel = new GridPanel(model);

		frame = new JFrame();
		frame.setLocation(0, 0);

		frame.setTitle("N Queens Problem");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				exitProcedure();
			}
		});

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(gridPanel);
		mainPanel.add(controlPanel.getPanel());

		frame.setLayout(new FlowLayout());
		frame.add(mainPanel);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	public void exitProcedure() {
		frame.dispose();
		System.exit(0);
	}

	public void setGenerationTextField() {
		controlPanel.setGenerationTextField(model.getGenerationCount());
	}

	public void repaintGridPanel() {
		gridPanel.repaint();
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}
}