package com.cs572.assignments.Project3.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import com.cs572.assignments.Project3.gui.NQueensFrame;
import com.cs572.assignments.Project3.model.NQueensModel;

public class StartSimulationActionListener implements ActionListener {

	private NQueensFrame frame;

	private NQueensModel model;

	private RunSimulation runSimulation;

	public StartSimulationActionListener(NQueensFrame frame, NQueensModel model) {
		this.frame = frame;
		this.model = model;
	}

	public void actionPerformed(ActionEvent event) {
		runSimulation = new RunSimulation();
		new Thread(runSimulation).start();
		frame.getControlPanel().getStartButton().setEnabled(false);
		frame.getControlPanel().getStopButton().setEnabled(true);
		frame.getControlPanel().getClearButton().setEnabled(false);
	}

	public void stopSimulation() {
		runSimulation.stopRunning();
		runSimulation = null;
	}

	class RunSimulation implements Runnable {

		private volatile boolean running;

		public void run() {
			this.running = true;

			model.executeGA(model);
			while (running) {
				sleep();
				repaint();
			}
		}

		private void repaint() {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.setGenerationTextField();
					frame.repaintGridPanel();
				}
			});
		}

		private void sleep() {
			try {
				Thread.sleep(model.getGenerationDelay());
			} catch (InterruptedException e) {
			}
		}

		public synchronized void stopRunning() {
			this.running = false;
		}

	}

}