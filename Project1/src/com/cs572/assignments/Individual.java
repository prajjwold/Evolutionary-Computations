/**
 * 
 */
package com.cs572.assignments;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * @author prajjwol
 *
 */
public class Individual {
	private float genome[];
	private float fitness;
	private NumberFormat formatter = new DecimalFormat("#0.00");

	public Individual() {
		super();
		this.genome = new float[Constants.DIMENSION];
	}

	float getFitness() {
		return fitness;
	}

	float[] getGenome() {
		return this.genome;
	}

	/**
	 * This method generate a random individual based on the type of Function by
	 * initialising the genome with random values within the function boundary
	 * 
	 * @param func
	 *            Function representing one of the benchmark optimisation
	 *            problems e.g. Spherical,Schwefel
	 */
	void generate(Function func) {
		Random rnd = new Random();
		for (int i = 0; i < Constants.DIMENSION; i++) {

			this.genome[i] = Float.valueOf(formatter
					.format((func.getMINRANGE() + rnd.nextFloat() * (func.getMAXRANGE() - func.getMINRANGE()))));
		}
		this.evaluate(func);
	};

	/**
	 * This method evaluates the fitness of the individual based on the function
	 * 
	 * @param func
	 *            Function representing one of the benchmark optimisation
	 *            problems e.g. Spherical,Schwefel
	 * 
	 */
	public void evaluate(Function func) {
		this.fitness = func.calcFitness(this.getGenome());
	};

	/**
	 * @param indv
	 *            Source Individual that is copied to this object
	 */
	void copy(Individual indv) {
		System.arraycopy(indv.getGenome(), 0, this.getGenome(), 0, indv.getGenome().length);
		this.fitness = indv.getFitness();
	}

	void print() {
		System.out.print("Fitness: " + getFitness() + " [ ");
		for (float i : getGenome()) {
			System.out.print(formatter.format(i) + ",");
		}
		System.out.println();
	}

}
