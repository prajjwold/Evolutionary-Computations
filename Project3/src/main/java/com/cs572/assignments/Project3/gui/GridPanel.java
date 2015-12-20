package com.cs572.assignments.Project3.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.cs572.assignments.Project3.model.NQueensModel;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = -8035892195317835189L;

	private NQueensModel model;

	public GridPanel(NQueensModel model) {
		this.model = model;
		this.setPreferredSize(model.getPreferredSize());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		model.draw(g);
	}
}