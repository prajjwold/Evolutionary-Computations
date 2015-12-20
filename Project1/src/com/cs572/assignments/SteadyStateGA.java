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
public class SteadyStateGA {
	private Random rnd = new Random();
	private NumberFormat formatter = new DecimalFormat("#0.00");

	public Population popn;
	public int iterations;
	public Function func;

	/**
	 * @param func
	 */
	public SteadyStateGA(Function func) {
		super();
		this.func = func;
	}

	public int select() {
		return selectUsingTournamentSelection();
	}

	/**
	 * The method returns the list of index for num worst individuals in the
	 * population
	 * 
	 * @param popn
	 *            Population
	 * @param num
	 *            The number of worst individual to select
	 * @return int[] representing the index for worst individuals in the
	 *         population popn
	 */

	public int[] selectWorst(Population popn, int num) {
		int[] worstIndvIndex = new int[num];
		int[] popnRank = new int[Population.POPULATION_SIZE];

		for (int i = 0; i < popnRank.length; i++) {
			popnRank[i] = i;
		}
		for (int j = 0; j < worstIndvIndex.length; j++) {
			for (int i = 0; i < Population.POPULATION_SIZE; i++) {
				if ((popn.getIndividual(popnRank[i]).getFitness()) > (popn.getIndividual(popnRank[j]).getFitness())) {
					worstIndvIndex[j] = popnRank[i];
					int temp = popnRank[i];
					popnRank[i] = popnRank[j];
					popnRank[j] = temp;
				}
			}
		}
		return worstIndvIndex;
	}

	/**
	 * 
	 * @param popn
	 *            The population
	 * @return int array with index of two worst individual in the population
	 */
	public int[] selectWorst(Population popn) {
		int[] worstIndvIndex = { 0, 1 };
		// Select the index of first worst individual in population
		for (int j = 0; j < Population.POPULATION_SIZE; j++) {
			if ((popn.getIndividual(j).getFitness()) > (popn.getIndividual(worstIndvIndex[0]).getFitness())) {
				worstIndvIndex[0] = j;
			}
		}
		// Select the index of second worst individual in population
		for (int j = 0; j < Population.POPULATION_SIZE; j++) {
			if (j != worstIndvIndex[0]) {
				if ((popn.getIndividual(j).getFitness()) > (popn.getIndividual(worstIndvIndex[1]).getFitness())) {
					worstIndvIndex[1] = j;
				}
			}
		}
		return worstIndvIndex;
	}

	public void crossOver(Individual indv1, Individual indv2) {
		// onePointCrossOver(indv1, indv2);
		twoPointCrossOver(indv1, indv2);
		// uniformCrossOver(indv1,indv2);
		// arithmeticCrossOver(indv1,indv2);

	}

	public void mutate(Individual indv) {
		for (int j = 0; j < Constants.DIMENSION; j++) {
			// only mutate if the random probability is < MUTATION_PROBABILITY
			if (rnd.nextFloat() < Constants.MUTATION_PROBABILITY) {
				float val = indv.getGenome()[j]
						+ RandomUtils.genRandomFloatBetnRange(-Constants.DELTA, Constants.DELTA);
				// only apply the mutation if the change is within the function
				// boundary
				if (func.checkBoundary(val)) {
					indv.getGenome()[j] = Float.valueOf(formatter.format(val));
				}
			}
		}
	}

	public void run() {
		int p1, p2; // parent
		int c1, c2; // offspring

		// Generate Initial Population
		Population popn = new Population(func);
		popn.generate(func);

		popn.printPopnSummary();

		int iteration = 0;
		do {
			// Selection of the parents for crossover and mutation
			p1 = select();
			do {
				p2 = select();
			} while (p1 == p2);
			// Selection of worst parents to be replaced with new offsprings
			int[] index = selectWorst(popn);
			c1 = index[0];
			c2 = index[1];
			popn.getIndividual(c1).copy(popn.getIndividual(p1));
			popn.getIndividual(c2).copy(popn.getIndividual(p2));
			// crossover two selected individuals
			crossOver(popn.getIndividual(c1), popn.getIndividual(c2));
			// Mutate the offspring
			mutate(popn.getIndividual(c1));
			mutate(popn.getIndividual(c2));
			// Re-evaluate the offsprings
			popn.getIndividual(c1).evaluate(func);
			popn.getIndividual(c2).evaluate(func);
			// Re-evaluate the population
			popn.evaluate();
			iteration++;
		} while (iteration < Constants.NUM_ITERATIONS && popn.getAvgFitness() > Constants.FITNESS_TOLERANCE);
		popn.printPopnSummary();
		System.out.println("\nIterations:" + iteration);
	}

	public void execute(Function func) {
		popn = new Population(func);
		popn.generate(func);
		iterations = 0;
		// int count = (int) (Constants.CROSSOVER_PROB * Constants.POPSIZE);
		int count = 2;
		// To make count even for generation of pairs
		if (count % 2 != 0) {
			count++;
		}
		int[] selectedIndvIndx = new int[count];
		do {
			// selection of the individuals
			for (int i = 0; i < count; i++) {
				selectedIndvIndx[i] = select();
			}
			// make copy of selected individuals
			Individual[] copyOfSelectedIndividual = new Individual[count];
			for (int i = 0; i < count; i++) {
				copyOfSelectedIndividual[i] = new Individual();
				copyOfSelectedIndividual[i].copy(popn.getIndividual(selectedIndvIndx[i]));
			}
			// crossover of the individuals
			int i, j;
			for (i = 0, j = count / 2; i < count / 2; i++, j++) {
				crossOver(copyOfSelectedIndividual[i], copyOfSelectedIndividual[j]);
			}
			// mutate the selected individuals
			for (int k = 0; k < count; k++) {
				mutate(copyOfSelectedIndividual[k]);
			}
			int worstIndvIndex[] = selectWorst(popn, count);
			// replace the worst with the copy of selected individuals after
			// variation operator
			for (int k = 0; k < count; k++) {
				popn.getIndividual(worstIndvIndex[k]).copy(copyOfSelectedIndividual[k]);
			}
			iterations++;
			popn.evaluate();
		} while (iterations < Constants.NUM_ITERATIONS && popn.getAvgFitness() > Constants.FITNESS_TOLERANCE);
	}

	/*
	 * for all members of population sum += fitness of this individual end for
	 * 
	 * for all members of population probability = sum of probabilities +
	 * (fitness / sum) sum of probabilities += probability end for
	 * 
	 * loop until new population is full do this twice number = Random between 0
	 * and 1 for all members of population if number > probability but less than
	 * next probability then you have been selected end for end create offspring
	 * end loop
	 */
	public int selectUsingRouletteWheelSelection(Population popn) {
		int index = 0;
		float cumulativeProbability[] = new float[Population.POPULATION_SIZE];
		float sumFitness = 0.0f;
		float probSelection = 0.0f;
		for (int i = 0; i < Population.POPULATION_SIZE; i++) {
			sumFitness += popn.getIndividual(i).getFitness();
		}
		for (int i = 0; i < Population.POPULATION_SIZE; i++) {
			probSelection += (popn.getIndividual(i).getFitness()) / sumFitness;
			cumulativeProbability[i] = probSelection;
		}
		float probability = rnd.nextInt(1);
		for (int i = 0; i < cumulativeProbability.length; i++) {
			if (probability >= cumulativeProbability[i] && probability < cumulativeProbability[i + 1]) {
				index = i;
			}
		}
		return index;
	}

	public int selectUsingTournamentSelection() {
		int winner;
		final int tournamentSize = 5;
		float winnerFitness;
		Random rnd = new Random();

		winner = rnd.nextInt(Population.POPULATION_SIZE);
		winnerFitness = popn.getIndividual(winner).getFitness();
		for (int i = 0; i < tournamentSize; i++) {
			int temp = rnd.nextInt(Population.POPULATION_SIZE);
			float tempFitness = popn.getIndividual(temp).getFitness();
			if (tempFitness < winnerFitness) {
				winner = temp;
				winnerFitness = tempFitness;
			}
		}
		return winner;
	}

	// private void arithmeticCrossOver(Individual indv1, Individual indv2) {
	// // alpha = [-0.5,1.5]
	// float alpha = RandomUtils.genRandomFloatBetnRange(-0.5f, 1.5f);
	// int index = RandomUtils.rnd.nextInt();
	//
	// indv1.getGenome()[index] = alpha * indv1.getGenome()[index] + (1 - alpha)
	// * indv2.getGenome()[index];
	// indv2.getGenome()[index] = (1 - alpha) * indv1.getGenome()[index] + alpha
	// * indv2.getGenome()[index];
	// }

	// private void uniformCrossOver(Individual indv1, Individual indv2) {
	// int index;
	// float temp;
	// // calculate the random number of the times of crossover
	// int numCrossoverPoint = RandomUtils.genRandomIntBetnRange(1, 29);
	// for (int i = 0; i < numCrossoverPoint; i++) {
	// index = RandomUtils.rnd.nextInt(30);
	// temp = indv1.getGenome()[index];
	// indv1.getGenome()[index] = indv2.getGenome()[index];
	// indv2.getGenome()[index] = temp;
	// }
	// }

	private void twoPointCrossOver(Individual indv1, Individual indv2) {
		// choose random point for first CrossOver Point from 0 to 28
		int crossoverPoint1 = RandomUtils.genRandomIntBetnRange(1, 28);
		// choose random point for second CrossOver which is greater than first
		// CrossOver Point
		int crossoverPoint2;
		do {
			crossoverPoint2 = RandomUtils.genRandomIntBetnRange(crossoverPoint1, 29);
		} while (crossoverPoint2 > crossoverPoint1);
		float temp;
		for (int i = crossoverPoint1; i < crossoverPoint2; i++) {
			temp = indv1.getGenome()[i];
			indv1.getGenome()[i] = indv2.getGenome()[i];
			indv2.getGenome()[i] = temp;
		}
	}

	// private void onePointCrossOver(Individual indv1, Individual indv2) {
	// // choose random point from 0 to 29
	// int crossoverPoint = RandomUtils.genRandomIntBetnRange(1, 29);
	// float temp;
	// for (int i = crossoverPoint; i < Constants.DIMENSION; i++) {
	// temp = indv1.getGenome()[i];
	// indv1.getGenome()[i] = indv2.getGenome()[i];
	// indv2.getGenome()[i] = temp;
	// }
	// }

}
