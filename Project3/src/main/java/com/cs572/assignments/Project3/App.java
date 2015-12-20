package com.cs572.assignments.Project3;

import javax.swing.SwingUtilities;

import com.cs572.assignments.Project3.gui.NQueensFrame;
import com.cs572.assignments.Project3.model.NQueensModel;

/**
 * Hello world!
 *
 */
public class App implements Runnable {
	// public static void main(String[] args) throws InterruptedException {
	// // int genome[]=Permutation.getSingleInstance(8);
	// // for (int i = 0; i < genome.length; i++) {
	// // System.out.print(genome[i]+"\t");
	// // }
	// // Permutation.printAllInstance();
	//
	// // Individual indv = new Individual();
	// // indv.printIndvSummary();
	// // Population popn=new Population();
	// // popn.generate();
	//// NQueenProblemGAImpl GA = new NQueenProblemGAImpl();
	//// GA.run();
	//
	// SwingUtilities.invokeLater(new NQueens());
	// }

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new App());
		// NQueenProblemGAImpl GA = new NQueenProblemGAImpl();
		// GA.runGA();
		//GA.iterativeRun();
		
	}

	public void run() {
		new NQueensFrame(new NQueensModel());
	}
}
