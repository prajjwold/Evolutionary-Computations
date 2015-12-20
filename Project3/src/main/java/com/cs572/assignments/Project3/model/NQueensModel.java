package com.cs572.assignments.Project3.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cs572.assignments.Project3.Constants;
import com.cs572.assignments.Project3.NQueenProblemGAImpl;
import com.cs572.assignments.Project3.Population;

public class NQueensModel {
	private int GRID_WIDTH = 700;
	private int noOfCells;
	private int CELL_WIDTH;

	public int[][] board;
	private BufferedImage blackqueen, whitequeen;

	private volatile long generationCount;
	private volatile long generationDelay;
	private volatile long numOfQueens;

	// public NQueensModel() {
	// this.board = new int[GRID_WIDTH + 2][GRID_WIDTH + 2];
	// this.generationDelay = 100L;
	// clearGrid();
	// }

	public NQueensModel() {
		this.board = new int[Constants.DIMENSION][Constants.DIMENSION];
		for (int i = 0; i < Constants.DIMENSION; i++) {
			for (int j = 0; j < Constants.DIMENSION; j++) {
				this.board[i][j] = 0;
			}
		}
		this.noOfCells = board.length;
		this.CELL_WIDTH = (int) (GRID_WIDTH / noOfCells);
		this.generationDelay = 100L;
		this.numOfQueens = 8;
		try {
			// image = ImageIO.read(new
			// File("C:\\Users\\prajjwol\\workspace\\Chessboard\\queenblack.jpg"));
			blackqueen = ImageIO.read(new File("C:\\Users\\prajjwol\\workspace\\Chessboard\\blackqueen.png"));
			whitequeen = ImageIO.read(new File("C:\\Users\\prajjwol\\workspace\\Chessboard\\whitequeen.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearGrid() {
		this.generationCount = 0;
		for (int i = 0; i < GRID_WIDTH + 2; i++) {
			for (int j = 0; j < GRID_WIDTH + 2; j++) {
				resetCell(i, j);
			}
		}
	}

	public void setCell(int i, int j) {
		board[i][j] = 1;
	}

	public void resetCell(int i, int j) {
		board[i][j] = 0;
	}

	public Dimension getPreferredSize() {
		// int x = (GRID_WIDTH * (CELL_WIDTH + 1)) + 1;
		int x = GRID_WIDTH;
		return new Dimension(x, x);
	}

	public int getGridWidth() {
		return GRID_WIDTH;
	}

	public long getGenerationCount() {
		return generationCount;
	}

	public long getGenerationDelay() {
		return generationDelay;
	}

	public long getNoOfQueens() {
		return noOfCells;
	}

	public synchronized void setGenerationDelay(long generationDelay) {
		this.generationDelay = generationDelay;
	}

	public synchronized void setNoOfQueens(int queen) {
		this.numOfQueens = queen;
	}

	public void draw(Graphics g) {
		for (int i = 0; i < noOfCells; i++) {
			for (int j = 0; j < noOfCells; j++) {
				g.setColor(Color.BLACK);
				drawCell(g, i, j);
			}
		}
	}

	private void drawCell(Graphics g, int x, int y) {
		if ((x + y) % 2 == 0) {
			g.setColor(Color.WHITE);
		}
		g.fillRect(x * CELL_WIDTH, y * CELL_WIDTH, CELL_WIDTH, CELL_WIDTH);
		if (board[x][y] == 1) {
			if ((x + y) % 2 == 0) {
				g.drawImage(blackqueen, x * CELL_WIDTH, y * CELL_WIDTH, CELL_WIDTH, CELL_WIDTH, new ImageObserver() {

					public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
						// TODO Auto-generated method stub
						return false;
					}
				});
			} else {
				g.drawImage(whitequeen, x * CELL_WIDTH, y * CELL_WIDTH, CELL_WIDTH, CELL_WIDTH, new ImageObserver() {

					public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
						// TODO Auto-generated method stub
						return false;
					}
				});
			}
		}
	}

	public void update(int board[][]) {
		this.board = board;
	}

	public void executeGA(NQueensModel model) {
		System.out.println("Executing GA");
		NQueenProblemGAImpl gaImpl = new NQueenProblemGAImpl();
		gaImpl.run();
		int newBoard[][] = gaImpl.getData();
		for (int i = 0; i < Constants.DIMENSION; i++) {
			for (int j = 0; j < Constants.DIMENSION; j++) {
				model.board[i][j] = newBoard[i][j];
			}
		}
	}
}
