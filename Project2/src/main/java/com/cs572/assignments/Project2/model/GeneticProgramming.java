/**
 *
 */
package com.cs572.assignments.Project2.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.cs572.assignments.Project2.utility.DataRecorder;
import com.cs572.assignments.Project2.utility.InputFileLoader;
import com.cs572.assignments.Project2.utility.RandomUtils;

/**
 * @author prajjwol
 *
 */
public class GeneticProgramming {
	public Population popn;
	public int iterations;
	public NumberFormat formatter = new DecimalFormat("#0.00");

	public int select(Population popn) {
		return selectUsingTournamentSelection(popn);
	}

	public void crossOver(Individual indv1, Individual indv2) {
		subTreeCrossover(indv1, indv2);
	}

	public void mutate(Individual indv) {
		nodeMutatation(indv);
	}

	public int selectUsingRouletteWheelSelection(Population popn) {
		int index = 0;
		return index;

	}

	public int selectUsingTournamentSelection(Population popn) {
		int winner = 0;
		for (int i = 0; i < GPConstants.TOURNAMENT_SIZE; i++) {
			int index = RandomUtils.getInt(GPConstants.POPSIZE);
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
			for (int j = i + 1; j < GPConstants.POPSIZE; j++) {

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
			for (int j = i + 1; j < GPConstants.POPSIZE; j++) {

				if (popn.getIndividual(j).getFitness() < popn.getIndividual(i).getFitness()) {
					Individual temp = new Individual();
					temp.copy(popn.getIndividual(j));
					popn.getIndividual(j).copy(popn.getIndividual(i));
					popn.getIndividual(i).copy(temp);
				}
			}
		}
		int index[] = new int[num];
		for (int i = 0; i < num; i++) {
			index[i] = i;
		}
		return index;
	}

	private void subTreeCrossover(Individual indv1, Individual indv2) {
		int index1 = RandomUtils.getInt(indv1.getSize());
		int index2 = RandomUtils.getInt(indv2.getSize());

		Node temp1 = indv1.getNodeAtIndex(index1).copy();
		Node temp2 = indv2.getNodeAtIndex(index2).copy();

		indv1.getNodeAtIndex(index1).copy(temp2);
		indv2.getNodeAtIndex(index2).copy(temp1);
		indv1.updateIndividual();
		indv2.updateIndividual();
	}

	public void nodeMutatation(Individual indv) {
		if (RandomUtils.getInt(10) < GPConstants.MUTATION_PROB * 10) {
			mutateNode(indv.getRootNode());
			indv.updateIndividual();
		}
	}

	public void mutateNode(Node node) {
		// if the root is null no need for mutation
		if (node == null) {
			return;
		}
		if (RandomUtils.getInt(10) < GPConstants.MUTATION_PER_NODE_PROB * 10) {
			if (node.isFunctionNode()) {
				// Change Node Type
				int funIndex = RandomUtils.getInt(GPConstants.functionSet.length);
				node.setFunIndex(funIndex);
				// Move to the next branches
				mutateNode(node.getChildren(0));
				mutateNode(node.getChildren(1));
			} else if (node.isTerminalNode()) {
				int varIndex = RandomUtils.getInt(InputFileLoader.getNumXVar());
				node.setVarIndex(varIndex);
			} else if (node.isConstantNode()) {
				float constVal = Float.valueOf(formatter.format(GPConstants.CONST_LIMIT * RandomUtils.getFloat()));
				node.setConstVal(constVal);
			}
		}
	}

	public void subTreeMutatation(Individual indv) {

	}

	public void run() {
		DataRecorder.open();
		popn = new Population();
		popn.generate();
		int iterations = 0;
		int count = 2;
		// int count = (int) (GPConstants.CROSSOVER_PROB * GPConstants.POPSIZE);
		// To make count even for generation of pairs
		// if (count % 2 != 0) {
		// count++;
		// }
		int[] selectedIndvIndx = new int[count];
		do {
			DataRecorder.write1(GPConstants.TOURNAMENT_SIZE, iterations, popn.getAvgFitness(), popn.getBestFitness());
			// DataRecorder.write2(iterations, popn.getAvgSize());
			// selection of the individuals
			for (int i = 0; i < count; i++) {
				selectedIndvIndx[i] = select(popn);
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
			// replace the worst with the copy of selected indiviudals after
			// variation operator
			for (int k = 0; k < count; k++) {
				popn.getIndividual(worstIndvIndex[k]).copy(copyOfSelectedIndividual[k]);
			}
			iterations++;
			popn.updatePopulation();
		} while (iterations < GPConstants.GENERATIONS);
		// for (int i = 0; i < InputFileLoader.getNumDataRows(); i++) {
		// DataRecorder.write3(i, popn.getActualOutput()[i],
		// popn.getExpectedOutput()[i]);
		// }
		DataRecorder.close();
		// popn.writeGraphFile();
		popn.printSummary(iterations);
	}

	public void iterativeRun() {
		DataRecorder.open();
		Population popn = new Population();
		popn.generate();
		int iterations = 0;
		int count = (int) (GPConstants.CROSSOVER_PROB * GPConstants.POPSIZE);
		// To make count even for generation of pairs
		if (count % 2 != 0) {
			count++;
		}
		int[] selectedIndvIndx = new int[count];
		do {
			DataRecorder.write1(GPConstants.TOURNAMENT_SIZE, iterations, popn.getAvgFitness(), popn.getBestFitness());
			DataRecorder.write2(iterations, popn.getAvgSize());
			// selection of the individuals
			for (int i = 0; i < count; i++) {
				selectedIndvIndx[i] = select(popn);
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
			// replace the worst with the copy of selected indiviudals after
			// variation operator
			for (int k = 0; k < count; k++) {
				popn.getIndividual(worstIndvIndex[k]).copy(copyOfSelectedIndividual[k]);
			}

			iterations++;
			popn.updatePopulation();
		} while (iterations < GPConstants.GENERATIONS);
		for (int i = 0; i < InputFileLoader.getNumDataRows(); i++) {
			DataRecorder.write3(i, popn.getActualOutput()[i], popn.getExpectedOutput()[i]);
		}
		DataRecorder.close();
		popn.writeGraphFile();
		popn.printSummary(iterations);
	}

	public void execute() {
		// popn = new Population();
		// popn.generate();
		iterations = 0;
		// int count = (int) (GPConstants.CROSSOVER_PROB * GPConstants.POPSIZE);
		int count = 2;
		// To make count even for generation of pairs
		// if (count % 2 != 0) {
		// count++;
		// }
		int[] selectedIndvIndx = new int[count];
		do {
			// selection of the individuals
			for (int i = 0; i < count; i++) {
				selectedIndvIndx[i] = select(popn);
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
			// replace the worst with the copy of selected indiviudals after
			// variation operator
			for (int k = 0; k < count; k++) {
				popn.getIndividual(worstIndvIndex[k]).copy(copyOfSelectedIndividual[k]);
			}
			iterations++;
			popn.updatePopulation();
		} while (iterations < GPConstants.GENERATIONS);
	}

	public void setPopulation(Population popn) {
		this.popn = popn;

	}
}
