/**
 * 
 */
package com.cs572.assignments.Project3;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author prajjwol
 *
 */
public class Population {
	private float avgFitness;
	private float bestFitness;
	private int best;
	private Individual individuals[];
	public NumberFormat formatter = new DecimalFormat("#0.00");

	public void generate() {
		individuals = new Individual[Constants.POPSIZE];
		for (int i = 0; i < Constants.POPSIZE; i++) {
			individuals[i] = new Individual();
		}
		calAvgFitness();
		calBestFitness();
	}

	public void calAvgFitness() {
		avgFitness = 0.0f;
		for (int i = 0; i < Constants.POPSIZE; i++) {
			avgFitness += individuals[i].getFitness();
		}
		avgFitness /= Constants.POPSIZE;
	}

	public void calBestFitness() {
		this.best = 0;
		for (int i = 1; i < Constants.POPSIZE - 1; i++) {
			if (individuals[i].getFitness() < individuals[best].getFitness()) {
				this.best = i;
			}
			this.bestFitness = individuals[this.best].getFitness();
		}
	}

	public void printSummary() {
		System.out.println("Generation Status");
		System.out.println("------------------------------------");
		System.out.println("Average Fitness: " + formatter.format(this.avgFitness));
		System.out.println("Best Fitness: " + formatter.format(this.bestFitness));
		System.out.println("Best Individual: ");
		individuals[best].printIndv1D();
		individuals[best].printIndv2D();
	}

	public String getSummary() {
		StringBuilder summary = new StringBuilder();
		summary.append("Generation Status");
		summary.append("------------------------------------");
		summary.append("Average Fitness: " + this.avgFitness);
		summary.append("Best Fitness: " + this.bestFitness);
		return summary.toString();
	}

	public double getAvgFitness() {
		return avgFitness;
	}

	public double getBestFitness() {
		return bestFitness;
	}

	public Individual getIndividual(int index) {
		return individuals[index];
	}

	public int[] getBestIndividual() {
		return individuals[best].getGenome();
	}

	public int[][] get2DBestIndividual() {

		int[][] board = new int[Constants.DIMENSION][Constants.DIMENSION];
		for (int i = 0; i < Constants.DIMENSION; i++) {
			for (int j = 0; j < Constants.DIMENSION; j++) {
				board[i][j] = 0;
			}
		}
		for (int i = 0; i < Constants.DIMENSION; i++) {
			int j = individuals[best].getGenome()[i];
			board[i][j - 1] = 1;
		}
		return board;

	}

	public void updatePopulation() {
		calAvgFitness();
		calBestFitness();
	}
}
