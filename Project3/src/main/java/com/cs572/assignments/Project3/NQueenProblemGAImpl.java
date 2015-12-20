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
public class NQueenProblemGAImpl {
	public NumberFormat formatter = new DecimalFormat("#0.00");

	public int select(Population popn) {
		return selectUsingTournamentSelection(popn);
	}

	public void crossOver(Individual indv1, Individual indv2) {
		PartiallyMatchedCrossover(indv1, indv2);
	}

	public void mutate(Individual indv) {
		swapMutatation(indv);
	}

	public int selectUsingRouletteWheelSelection(Population popn) {
		int index = 0;
		return index;

	}

	public int selectUsingTournamentSelection(Population popn) {
		int winner = 0;
		for (int i = 0; i < Constants.TOURNAMENT_SIZE; i++) {
			int index = RandomUtils.getInt(Constants.POPSIZE);
			if ((popn.getIndividual(winner).getFitness()) > (popn.getIndividual(index).getFitness())) {
				winner = index;
			}
		}
		return winner;
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
		for (int i = 0; i < num; i++) {
			for (int j = i + 1; j < Constants.POPSIZE; j++) {

				if (popn.getIndividual(j).getFitness() > popn.getIndividual(i).getFitness()) {
					Individual temp = new Individual();
					temp.copy(popn.getIndividual(j));
					popn.getIndividual(j).copy(popn.getIndividual(i));
					popn.getIndividual(i).copy(temp);
				}
			}
		}
		// for (int i = 0; i < num; i++) {
		// System.out.println(i + "\t:" +
		// formatter.format(popn.getIndividual(i).getFitness()));
		// }
		int index[] = new int[num];
		for (int i = 0; i < num; i++) {
			index[i] = i;
		}
		return index;
	}

	public int[] selectBest(Population popn, int num) {
		for (int i = 0; i < num; i++) {
			for (int j = i + 1; j < Constants.POPSIZE; j++) {

				if (popn.getIndividual(j).getFitness() < popn.getIndividual(i).getFitness()) {
					Individual temp = new Individual();
					temp.copy(popn.getIndividual(j));
					popn.getIndividual(j).copy(popn.getIndividual(i));
					popn.getIndividual(i).copy(temp);
				}
			}
		}
		// for (int i = 0; i < num; i++) {
		// System.out.println(i + "\t:" +
		// formatter.format(popn.getIndividual(i).getFitness()));
		// }
		int index[] = new int[num];
		for (int i = 0; i < num; i++) {
			index[i] = i;
		}
		return index;
	}

	private void PartiallyMatchedCrossover(Individual parent1, Individual parent2) {
		int index1, index2;
		index1 = RandomUtils.getInt(Constants.DIMENSION / 2);
		do {
			index2 = RandomUtils.getInt(Constants.DIMENSION);
		} while (index1 >= index2);

		// System.out.println("Index1:" + index1 + " Index2:" + index2);

		Individual tempParent1 = new Individual();
		tempParent1.copy(parent1);

		Individual tempParent2 = new Individual();
		tempParent2.copy(parent2);

		for (int i = index1; i <= index2; i++) {
			tempParent1.getGenome()[i] = parent2.getGenome()[i];
			tempParent2.getGenome()[i] = parent1.getGenome()[i];
		}
		// System.out.println("Changing first parent:");
		for (int i = 0; i < Constants.DIMENSION; i++) {
			if (!(i >= index1 && i <= index2)) {
				do {
					int index = indexOf(tempParent1.getGenome()[i], parent2.getGenome(), index1, index2);
					if (index != -1) {
						int var = parent1.getGenome()[index];
						tempParent1.getGenome()[i] = var;
					} else {
						tempParent1.getGenome()[i] = parent1.getGenome()[i];
						break;
					}
				} while (indexOf(tempParent1.getGenome()[i], parent2.getGenome(), index1, index2) >= 0);
			}
		}
		// System.out.println("Changing second parent:");
		for (int i = 0; i < Constants.DIMENSION; i++) {
			if (!(i >= index1 && i <= index2)) {
				do {
					int index = indexOf(tempParent2.getGenome()[i], parent1.getGenome(), index1, index2);
					if (index != -1) {
						int var = parent2.getGenome()[index];
						tempParent2.getGenome()[i] = var;
					} else {
						tempParent2.getGenome()[i] = parent2.getGenome()[i];
						break;
					}
				} while (indexOf(tempParent2.getGenome()[i], parent1.getGenome(), index1, index2) >= 0);

			}
		}
		tempParent1.updateIndividual();
		tempParent2.updateIndividual();
		parent1.copy(tempParent1);
		parent2.copy(tempParent2);
	}

	private int indexOf(int a, int array[], int minRange, int maxRange) {
		for (int i = minRange; i <= maxRange; i++) {
			if (array[i] == a) {
				return i;
			}
		}
		return -1;
	}

	public void swapMutatation(Individual indv) {
		if (RandomUtils.getInt(10) < Constants.MUTATION_PROB * 10) {
			int a, b;
			a = RandomUtils.getInt(Constants.DIMENSION - 1);
			do {
				b = RandomUtils.getInt(Constants.DIMENSION - 1);
			} while (a == b);
			// swap a and b th element of genome
			int temp = indv.getGenome()[a];
			indv.getGenome()[a] = indv.getGenome()[b];
			indv.getGenome()[b] = temp;
			indv.updateIndividual();
		}
	}

	public void run() {
		DataRecorder.open();
		popn.generate();
		int iterations = 0;
		int count = (int) (Constants.CROSSOVER_PROB * Constants.POPSIZE);
		// To make count even for generation of pairs
		if (count % 2 != 0) {
			count++;
		}
		// count = 2;
		int[] selectedIndvIndx = new int[count];
		do {
			// System.out.println("Start Iteration:");

			// System.out.println("Start Selection:");
			// selection of the individuals
			for (int i = 0; i < count; i++) {
				selectedIndvIndx[i] = select(popn);
			}
			// make copy of selected individuals
			// System.out.println("Start CopySelected Individual:");
			Individual[] copyOfSelectedIndividual = new Individual[count];
			for (int i = 0; i < count; i++) {
				copyOfSelectedIndividual[i] = new Individual();
				copyOfSelectedIndividual[i].copy(popn.getIndividual(selectedIndvIndx[i]));
			}
			// crossover of the individuals
			// System.out.println("Start Crossover");
			int i, j;
			for (i = 0, j = count / 2; i < count / 2; i++, j++) {
				crossOver(copyOfSelectedIndividual[i], copyOfSelectedIndividual[j]);
			}
			// mutate the selected individuals
			// System.out.println("Start Mutation");
			for (int k = 0; k < count; k++) {
				mutate(copyOfSelectedIndividual[k]);
			}

			int worstIndvIndex[] = selectWorst(popn, count);
			// replace the worst with the copy of selected indiviudals after
			// variation operator
			// System.out.println("Start Eliminationg Worst Individual:");
			for (int k = 0; k < count; k++) {
				popn.getIndividual(worstIndvIndex[k]).copy(copyOfSelectedIndividual[k]);
			}

			iterations++;
			// System.out.println("Iterations: " + iterations);
			popn.updatePopulation();
			DataRecorder.write1(Constants.DIMENSION, iterations, popn.getAvgFitness(), popn.getBestFitness());
		} // while (popn.getBestFitness() != 0 && iterations <
			// Constants.GENERATIONS);
		while (popn.getBestFitness() != 0);
		DataRecorder.close();
		System.out.println("Iterations: " + iterations);
		popn.printSummary();
	}

	public void runGA() {
		//DataRecorder.open();
		popn.generate();
		int iterations = 0;
		int count = (int) (Constants.CROSSOVER_PROB * Constants.POPSIZE);
		if (count % 2 != 0) {
			count++;
		}
		int[] selectedIndvIndx = new int[count];
		do
		{
			for (int i = 0; i < count; i++) {
				selectedIndvIndx[i] = select(popn);
			}
			Individual[] copyOfSelectedIndividual = new Individual[count];
			for (int i = 0; i < count; i++) {
				copyOfSelectedIndividual[i] = new Individual();
				copyOfSelectedIndividual[i].copy(popn.getIndividual(selectedIndvIndx[i]));
			}
			int i, j;
			for (i = 0, j = count / 2; i < count / 2; i++, j++) {
				crossOver(copyOfSelectedIndividual[i], copyOfSelectedIndividual[j]);
			}
			for (int k = 0; k < count; k++) {
				mutate(copyOfSelectedIndividual[k]);
			}
			int worstIndvIndex[] = selectWorst(popn, count);
			for (int k = 0; k < count; k++) {
				popn.getIndividual(worstIndvIndex[k]).copy(copyOfSelectedIndividual[k]);
			}
			iterations++;
			popn.updatePopulation();
			//DataRecorder.write1(Constants.DIMENSION, iterations, popn.getAvgFitness(), popn.getBestFitness());
		}
		while (popn.getBestFitness() != 0 && iterations < Constants.GENERATIONS);
		// popn.printSummary();
		//DataRecorder.close();
	}

	public void iterativeRun() {
		DataRecorder.open();
		long startTime, endTime, timeTaken;
		double avgTimeTaken = 0.0f;
		int iterations = 10;
		NQueenProblemGAImpl GA = new NQueenProblemGAImpl();
		int dimensions[] = { 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 150, 200 };
		// int dimensions[] = { 8, 10, 20, 30 };
		for (int dimension : dimensions) {
			Constants.DIMENSION = dimension;
			System.out.println("Dimension: " + dimension);
			System.out.println("Iterations\tTimeTaken(nanoSeconds)");
			for (int i = 0; i <= iterations;) {
				startTime = System.nanoTime();
				GA.runGA();
				endTime = System.nanoTime();
				if (GA.getPopn().getBestFitness() == 0) {
					timeTaken = (endTime - startTime);
					avgTimeTaken += timeTaken;
					System.out.println(i + "\t" + timeTaken);
					i++;
				}
			}
			avgTimeTaken /= iterations;
			DataRecorder.write2(dimension, avgTimeTaken);
			System.out.println("AverageTimeTaken(MilliSeconds): " + avgTimeTaken / 1000000);
		}
		DataRecorder.close();
	}

	private Population popn = new Population();

	public Population getPopn() {
		return popn;
	}

	public void setPopn(Population popn) {
		this.popn = popn;
	}

	public int[][] getData() {
		return this.popn.get2DBestIndividual();
	}

}
