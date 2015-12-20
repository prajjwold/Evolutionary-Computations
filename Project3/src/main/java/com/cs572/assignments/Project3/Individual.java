/**
 * 
 */
package com.cs572.assignments.Project3;

/**
 * @author prajjwol
 *
 */
public class Individual {
	private int genome[];
	private float fitness;

	public Individual() {
		initialize();
		calFitness();
	}

	public void updateIndividual() {
		calFitness();
	}

	private void initialize() {
		genome = Permutation.getSingleInstance(Constants.DIMENSION);
	}

	public void calFitness() {
		int conflicts = 0;
		for (int i = 0; i < genome.length - 1; i++) {
			int a = genome[i];
			for (int j = i + 1; j < genome.length; j++) {
				int b = genome[j];
				if (Math.abs(a - b) == Math.abs(i - j)) {
					conflicts++;
				}
			}
		}
		this.fitness = conflicts;
	}

	public float getFitness() {
		return fitness;
	}

	public void copy(Individual indv) {
		this.genome = indv.genome.clone();
		this.fitness = indv.fitness;
	}

	private char[][] map1Dto2D() {
		char[][] board = new char[Constants.DIMENSION][Constants.DIMENSION];
		for (int i = 0; i < Constants.DIMENSION; i++) {
			for (int j = 0; j < Constants.DIMENSION; j++) {
				board[i][j] = ' ';
			}
		}
		for (int i = 0; i < Constants.DIMENSION; i++) {
			int j = genome[i];
			board[i][j - 1] = 'Q';
		}
		return board;
	}

	public void printIndv1D() {
		for (int i : genome) {
			System.out.print(i + "\t");
		}
		System.out.println();
	}

	public void printIndv2D() {
		char board[][] = map1Dto2D();
		System.out.println();
		System.out.print("_");
		for (int i = 0; i < Constants.DIMENSION * 2; i++) {
			System.out.print("_");
		}
		System.out.println();
		/*
		 * for (char[] cs : board) { System.out.print("|"); for (char c : cs) {
		 * System.out.print(c + "|"); } System.out.println(); }
		 */
		for (int i = 0; i < board.length; i++) {
			System.out.print("|");
			for (int j = 0; j < board.length; j++) {
				System.out.print(board[j][i] + "|");
			}
			System.out.println();
		}

		System.out.print("-");
		for (int i = 0; i < Constants.DIMENSION * 2; i++) {
			System.out.print("-");
		}
	}

	public void printIndvSummary() {
		System.out.println("Individual Summary");
		System.out.println("-------------------");
		System.out.println("Fitness: " + this.fitness);
		printIndv1D();
		printIndv2D();
	}

	public int[] getGenome() {
		return genome;
	}
}
