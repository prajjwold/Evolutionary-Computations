/**
 *
 */
package com.cs572.assignments.Project2.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.cs572.assignments.Project2.utility.DotGraphCreator;
import com.cs572.assignments.Project2.utility.InputFileLoader;
import com.cs572.assignments.Project2.utility.RandomUtils;

/**
 * @author prajjwol
 *
 */
public class Individual {

    private float fitness; // fitness of the individual
    private int size; // total number of nodes in the tree
    private Node rootNode; // pointer to the root of the 'program' tree
    
    public NumberFormat formatter = new DecimalFormat("#0.00");

    public Individual() {
        generate(GPConstants.MAXDEPTH);
        evaluate();
        calcSize();
    }
    
    public void updateIndividual(){
        evaluate();
        calcSize();
    }

    private void generate(int maxdepth) {
        rootNode = new Node();
        rootNode.generate(0, maxdepth, null);
    }

    private void evaluate() {
        this.fitness = 0.0f;
        float output;
        for (int j = 0; j < InputFileLoader.getNumDataRows(); j++) {
            // evaluate function on each input point
            output = rootNode.evaluate(InputFileLoader.inputdata[j]);
            fitness += (Math.pow((output - InputFileLoader.outputdata[j]), 2));
            // outputs array holds correct values
        }
        //fitness = Float.valueOf(formatter.format(Math.sqrt(fitness)));
        fitness =(float) Math.sqrt(fitness);
    }

    private void calcSize() {
        this.size = rootNode.getSize();
    }

    public void copy(Individual indv) {
        this.rootNode.copy(indv.getRootNode());
        this.size = indv.getSize();
        this.fitness = indv.getFitness();
        updateIndividual();
    }

    public void printSummary() {
        System.out.println("\nExpression:");
        rootNode.printInFixExpr();
        System.out.println("Fitness: " + getFitness());
        System.out.println("Size: " + getSize());
        float output;
        System.out.println("Input\tActual Output\tExpected Output");
        for (int i = 0; i < InputFileLoader.getNumDataRows(); i++) {
            output = rootNode.evaluate(InputFileLoader.inputdata[i]);
            System.out.println(i + "\t" + output + "\t\t" + InputFileLoader.outputdata[i]);
        }
        System.out.println();
    }

    public void erase() {
        rootNode.erase();
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    public float getFitness() {
        return fitness;
    }

    public int getSize() {
        return this.size;
    }

    public void printDotExpression() {
        DotGraphCreator dotcreater = new DotGraphCreator();
        System.out.println(dotcreater.createGraph(getRootNode()));
    }

    public String getExprGraph() {
        DotGraphCreator dotcreater = new DotGraphCreator();
        return dotcreater.createGraph(getRootNode());
    }

    public Node getRandomNode() {
        int rnd = RandomUtils.getInt(getSize());
//            System.out.println("Size:"+getSize());
//            System.out.println("Random:"+rnd);
        if (rnd == 0) {
            return this.getRootNode();
        }
        return getRootNode().getNodeAtIndex(rnd);
    }

    public Node getNodeAtIndex(int rnd) {
        if (rnd == 0) {
            return this.getRootNode();
        }
        return getRootNode().getNodeAtIndex(rnd);
    }
}
