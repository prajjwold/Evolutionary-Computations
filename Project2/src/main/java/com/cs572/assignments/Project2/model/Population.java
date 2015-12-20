/**
 *
 */
package com.cs572.assignments.Project2.model;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.cs572.assignments.Project2.utility.GraphWriter;
import com.cs572.assignments.Project2.utility.InputFileLoader;

/**
 * @author prajjwol
 *
 */
public class Population {

	private float avgFitness;
	private float bestFitness;
	private int best;
	private float avgSize;
	private Individual individuals[];

	public NumberFormat formatter = new DecimalFormat("#0.00");

	public void generate() {
		individuals = new Individual[GPConstants.POPSIZE];
		for (int i = 0; i < GPConstants.POPSIZE; i++) {
			individuals[i] = new Individual();
		}
		calAvgFitness();
		calBestFitness();
		calAvgSize();
	}

	public void copy(Population popn) {
		individuals = new Individual[GPConstants.POPSIZE];
		for (int i = 0; i < individuals.length; i++) {
			individuals[i] = new Individual();
			individuals[i].copy(popn.getIndividual(i));
		}
		best = popn.best;
		avgSize = popn.avgSize;
		avgFitness = popn.avgFitness;
		bestFitness = popn.bestFitness;
	}

	public void updatePopulation() {
		calAvgFitness();
		calBestFitness();
		calAvgSize();
	}

	public void calAvgFitness() {
		avgFitness = 0.0f;
		for (int i = 0; i < GPConstants.POPSIZE; i++) {
			avgFitness += individuals[i].getFitness();
		}
		avgFitness /= GPConstants.POPSIZE;
	}

	public void calBestFitness() {
		double temp = individuals[0].getFitness();
		for (int i = 1; i < GPConstants.POPSIZE - 1; i++) {
			if (individuals[i].getFitness() < temp) {
				this.best = i;
				this.bestFitness = individuals[i].getFitness();
			}
		}
	}

	public float[] getActualOutput() {
		float[] actualOutput = new float[InputFileLoader.getNumDataRows()];
		for (int j = 0; j < InputFileLoader.getNumDataRows(); j++) {
			// evaluate function on each input point
			actualOutput[j] = getIndividual(best).getRootNode().evaluate(InputFileLoader.inputdata[j]);
		}
		return actualOutput;
	}

	public float[] getExpectedOutput() {
		return InputFileLoader.outputdata;
	}

	public void calAvgSize() {
		avgSize = 0.0f;
		for (Individual individual : individuals) {
			avgSize += individual.getSize();
		}
		avgSize /= GPConstants.POPSIZE;
	}

	public void printSummary(int i) {
		System.out.println("Generation : " + i);
		System.out.println("------------------------------------");
		System.out.println("Average Fitness: " + formatter.format(this.avgFitness));
		System.out.println("Best Fitness: " + formatter.format(this.bestFitness));
		System.out.println("Average Size of Popn: " + avgSize);
		System.out.println("Best Individual: ");
		individuals[best].getRootNode().printInFixExpr();
	}

	public void printSummary() {
		System.out.println("Generation Status ");
		System.out.println("------------------------------------");
		System.out.println("Average Fitness: " + formatter.format(this.avgFitness));
		System.out.println("Best Fitness: " + formatter.format(this.bestFitness));
		System.out.println("Average Size of Popn: " + avgSize);
		System.out.println("Best Individual: ");
		individuals[best].getRootNode().printInFixExpr();
	}

	public String getSummary() {
		StringBuilder summary = new StringBuilder();
		summary.append("Average Fitness: ").append(formatter.format(this.avgFitness));
		summary.append("\nBest Fitness: ").append(formatter.format(this.bestFitness));
		summary.append("\nAverage Size of Popn: ").append(avgSize);
		summary.append("\nBest Individual: \n");
		// summary.append(getBestExprGraph());
		summary.append(getIndividual(best).getRootNode().getExpr());
		return summary.toString();
	}

	public float getAvgFitness() {
		return avgFitness;
	}

	public double getBestFitness() {
		return bestFitness;
	}

	public float getAvgSize() {
		return avgSize;
	}

	public Individual getIndividual(int index) {
		return individuals[index];
	}

	public String getBestExprGraph() {
		return individuals[best].getExprGraph();
	}

	public void writeGraphFile() {
		GraphWriter.writeContent(getBestExprGraph());
		String command = "dot -Tpng input.gv -o output.png";
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
