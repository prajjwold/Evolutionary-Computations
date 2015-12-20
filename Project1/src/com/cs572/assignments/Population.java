/**
 * 
 */
package com.cs572.assignments;

/**
 * @author prajjwol
 *
 */
public class Population {
	private float avgFitness;
	private float bestFitness;
	private int best;
	private Individual individuals[];

	public static int POPULATION_SIZE = 100;

	public Population(Function func) {
		super();
		this.individuals = new Individual[POPULATION_SIZE];
		generate(func);

	}

	public void generate(Function func) {
		for (int i = 0; i < POPULATION_SIZE; i++) {
			Individual indv = new Individual();
			indv.generate(func);
			individuals[i] = indv;
		}
		this.calAvgFitness();
		this.calBestFitness();
	}

	public void print() {
		System.out.println("Population:");
		System.out.println("------------------");
		for (Individual indv : individuals) {
			indv.print();
		}
		printPopnSummary();
	}

	public String getPopulationSummary() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n\nPopulation Summary");
		buffer.append("\n-------------------------");
		buffer.append("\nAverage Fitness:" + getAvgFitness());
		buffer.append("\nBest Individual:");
		buffer.append("\n--------------");
		buffer.append("\nFitness: " + individuals[best].getFitness() + "\n[ ");
		for (float i : individuals[best].getGenome()) {
			buffer.append((i) + ",");
		}
		buffer.append("]\n");
		return buffer.toString();
	}

	public void printPopnSummary() {
		System.out.println(getPopulationSummary());
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

	public int getBest() {
		return best;
	}

	public float getBestFitness() {
		return bestFitness;
	}

	public float getAvgFitness() {
		return avgFitness;
	}

	public Individual getIndividual(int index) {
		return individuals[index];
	}

	public void evaluate() {
		calAvgFitness();
		calBestFitness();
	}
}
