package com.cs572.assignments.Project2.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cs572.assignments.Project2.utility.InputFileLoader;
import com.cs572.assignments.Project2.utility.RandomUtils;

/**
 * @author prajjwol
 *
 */
public class Node implements Cloneable {

    public NumberFormat formatter = new DecimalFormat("#0.00");

    private Node parent;
    private Node[] children = new Node[2];
    /*
     * 0 represent Functions<br/> 1 represent Terminals<br/> 2 represent
     * Constants<br/>
     */
    private int type;
    /*
     * store the index of the variable if the node is Terminal
     */
    private int varIndex = -1;
    /*
     * store the index of the function if the node is Function
     */
    private int funIndex = -1;
    /*
     * store the constant value if the the node is Terminal with random constant
     * value
     */
    private float constVal;
    /*
     * num of terminals
     */
    private Integer terms = 0;
    /*
     * num of functions/non-terminals
     */
    private Integer nonTerms = 0;

    public void generate(int depth, int maxdepth, Node parentNode) {
        //genByGrowMethod(depth, maxdepth, parentNode);
        genByFullMethod(depth, maxdepth, parentNode);
    }

    /*
     * generate random full tree
     */
    public void genByFullMethod(int depth, int maxdepth, Node parentNode) {
        /* creates full trees for the initial population */
        this.parent = parentNode; // point to parent node
        if (depth >= maxdepth) {
            // add terminals or constants based on some probability
            int var = RandomUtils.getInt(InputFileLoader.getNumXVar() + 1);
            if (var >= InputFileLoader.getNumXVar()) {
                // add constants
                this.type = 2;
                this.constVal = Float.valueOf(formatter.format(GPConstants.CONST_LIMIT * RandomUtils.getFloat()));
                // System.out.print(decode() + " ");
            } else {
                // add variables
                this.type = 1;
                this.varIndex = var;
                // System.out.print(decode() + " ");
            }
        } else {
            // add function/non-terminals
            this.type = 0;
            this.funIndex = RandomUtils.getInt(GPConstants.functionSet.length);
            String function = decode();
            switch (function) {
                // for +,-,/,* only
                case "+":
                case "-":
                case "*":
                case "/":
                    // System.out.print(decode() + " ");
                    for (int i = 0; i < 2; i++) {
                        children[i] = new Node();
                        children[i].generate(depth + 1, maxdepth, this);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /*
     * generate random trees by grow method
     */
    public void genByGrowMethod(int depth, int maxdepth, Node parentNode) {
        /* creates full trees for the initial population */
        this.parent = parentNode; // point to parent node		
        if (depth == maxdepth || RandomUtils.getInt(10) > 8) {
            // add terminals or constants based on some probability
            int var = RandomUtils.getInt(InputFileLoader.getNumXVar() + 1);
            if (var >= InputFileLoader.getNumXVar()) {
                // add constants
                this.type = 2;
                this.constVal = Float.valueOf(formatter.format(GPConstants.CONST_LIMIT * RandomUtils.getFloat()));
                // System.out.print(decode() + " ");
            } else {
                // add variables
                this.type = 1;
                this.varIndex = var;
                // System.out.print(decode() + " ");
            }
        } else {
            // add function/non-terminals
            this.type = 0;
            this.funIndex = RandomUtils.getInt(GPConstants.functionSet.length);
            String function = decode();
            switch (function) {
                // for +,-,/,* only
                case "+":
                case "-":
                case "*":
                case "/":
                    // System.out.print(decode() + " ");
                    for (int i = 0; i < 2; i++) {
                        children[i] = new Node();
                        children[i].generate(depth + 1, maxdepth, this);
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /*
     * Evaluates a tree. Just a recursive function that evaluates branches and
     * adds, subtracts, etc. their values. For a more complex problem it may
     * need to manipulate some global values. E.g. for a 'forward' terminal the
     * position (stored as a global) of a robot is changed.
     */
    public float evaluate(float[] input) {
        /* evaluates the tree , for a given X. */
        float l, r;
        if (isFunctionNode()) {
            switch (decode()) {
                case "+": // +
                    l = getChildren(0).evaluate(input);
                    r = getChildren(1).evaluate(input);
                    return (l + r);
                case "-": // -
                    l = getChildren(0).evaluate(input);
                    r = getChildren(1).evaluate(input);
                    return (l - r);
                case "/": // protected division
                    l = getChildren(0).evaluate(input);
                    r = getChildren(1).evaluate(input);
                    if (Math.abs(r) <= 0.001) {
                        return l;
                    } else {
                        return (l / r);
                    }
                case "*":
                    l = getChildren(0).evaluate(input);
                    r = getChildren(1).evaluate(input);
                    return (l * r);
                default:
                    System.out.println("Error, unknown instruction ");
            }
        } else if (isTerminalNode()) {
            return input[varIndex];
        } else if (isConstantNode()) {
            return this.constVal;
        }
        return 0.0f;
    }

    /*
     * write the prefix expression
     */
    public Vector<String> getPreFixExpr(Vector<String> expr) {
        if (isFunctionNode()) {
            expr.addElement(decode());
            children[0].getPreFixExpr(expr);
            children[1].getPreFixExpr(expr);
        } else {
            expr.addElement(decode());
        }
        return expr;
    }

    /*
     * write the postfix expression
     */
    public Vector<String> getPostFixExpr(Vector<String> expr) {
        if (isFunctionNode()) {
            children[0].getPostFixExpr(expr);
            children[1].getPostFixExpr(expr);
            expr.addElement(decode());
        } else {
            expr.addElement(decode());
        }
        return expr;
    }

    /*
     * write the infix expression
     */
    public Vector<String> getInFixExpr(Vector<String> expr) {
        if (isFunctionNode()) {
            expr.addElement("(");
            children[0].getInFixExpr(expr);
            expr.addElement(decode());
            children[1].getInFixExpr(expr);
            expr.addElement(")");
        } else {
            expr.addElement(decode());
        }
        return expr;
    }

    public void printPreFixExpr() {
        Vector<String> expr = new Vector<>();
        for (String var : getPreFixExpr(expr)) {
            System.out.print(var + " ");
        }
        System.out.println();
    }

    public void printPostFixExpr() {
        Vector<String> expr = new Vector<>();
        for (String var : getPostFixExpr(expr)) {
            System.out.print(var + " ");
        }
        System.out.println();
    }

    public void printInFixExpr() {
        Vector<String> expr = new Vector<String>();
        for (String var : getInFixExpr(expr)) {
            System.out.print(var + " ");
        }
        System.out.println();
    }

    /*
     * copies trees
     */
    public Node getCopy() {
        try {
            return (Node) this.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void copy(Node srcNode) {
        this.type = srcNode.getType();
        this.funIndex = srcNode.getFunIndex();
        this.varIndex = srcNode.getVarIndex();
        this.constVal = srcNode.getConstVal();
        this.terms = srcNode.getTerms();
        this.nonTerms = srcNode.getNonTerms();
        //this.parent = srcNode.getParent();

        if (srcNode.isFunctionNode()) {
            for (int i = 0; i < 2; i++) {
                if (srcNode.getChildren(i) != null) {
                    children[i] = new Node();
                    children[i].copy(srcNode.getChildren(i));
                } else {
                    children[i] = null;
                }
            }
        }
    }

    public Node copy() {
        Node newNode = new Node();
        newNode.type = this.getType();
        newNode.funIndex = this.getFunIndex();
        newNode.varIndex = this.getVarIndex();
        newNode.constVal = this.getConstVal();
        newNode.terms = this.getTerms();
        newNode.nonTerms = this.getNonTerms();
        //this.parent = srcNode.getParent();

        if (isFunctionNode()) {
            for (int i = 0; i < 2; i++) {
                if (getChildren(i) != null) {
                    newNode.children[i] = new Node();
                    newNode.children[i] = children[i].copy();
                } else {
                    newNode.children[i] = null;
                }
            }
        }
        return newNode;
    }

    /*
     * recursively erases/frees trees
     */
    public void erase() {
        if (isTerminalNode()) {
            for (int i = 0; i < GPConstants.MAX_ARITY; i++) {
                if (children[i] != null) {
                    children[i] = null;
                }
            }
        }
        System.gc();
    }

    public String decode() {
        if (isFunctionNode()) {
            return GPConstants.functionSet[funIndex];
        } else if (isTerminalNode()) {
            return "X[" + (varIndex + 1) + "]";
        } else if (isConstantNode()) {
            return constVal + "";
        }
        return null;
    }

    public Node getChildren(int index) {
        return children[index];
    }

    public boolean isFunctionNode() {
        return type == 0 ? true : false;
    }

    public boolean isTerminalNode() {
        return type == 1 ? true : false;
    }

    public boolean isConstantNode() {
        return type == 2 ? true : false;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVarIndex() {
        return varIndex;
    }

    public void setVarIndex(int varIndex) {
        this.varIndex = varIndex;
    }

    public int getFunIndex() {
        return funIndex;
    }

    public void setFunIndex(int funIndex) {
        this.funIndex = funIndex;
    }

    public float getConstVal() {
        return constVal;
    }

    public void setConstVal(float constVal) {
        this.constVal = constVal;
    }

    public int getSize() {
        int size = 0;
        return getSize(size);
    }

    private int getSize(int size) {
        if (isFunctionNode()) {
            size += 1 + children[0].getSize(size) + children[1].getSize(size);
        } else {
            size++;
        }
        return size;
    }

    private int getTermSize(int size) {
        if (isFunctionNode()) {
            size += 1 + children[0].getTermSize(size) + children[1].getTermSize(size);
        }
        return size;
    }

    private int getNonTermSize(int size) {
        if (!isFunctionNode()) {
            size++;
        } else {
            size += children[0].getNonTermSize(size) + children[1].getNonTermSize(size);
        }
        return size;
    }

    public int getSize1() {
        return getTermSize(terms) + getNonTermSize(nonTerms);
    }

    public Integer getTerms() {
        terms = getTermSize(terms);
        return terms;
    }

    public Integer getNonTerms() {
        nonTerms = getNonTermSize(nonTerms);
        return nonTerms;
    }

    public String getExpr() {
        StringBuilder expr1 = new StringBuilder();
        Vector<String> expr = new Vector<>();
        for (String var : getInFixExpr(expr)) {
            expr1.append(var).append(" ");
        }
        return expr1.toString();
    }

    private Node getNode(int index, int stepSize) {
        if (stepSize == index) {
            return this;
        } else if (this.children[0] != null) {
            return this.children[0].getNode(index, stepSize + 1);
        } else if (this.children[1] != null) {
            return this.children[1].getNode(index, stepSize + 1);
        }
        return this;
    }

    public Node getNodeAtIndex(int index) {
        int counter = 0;
        return getNode(index, counter);
    }

//    public Node getNode1(int index) {
//        if (index > 0 && isFunctionNode()) {
//            System.out.println(decode());
//            this.getChildren(0).getNode1(--index);
//            this.getChildren(1).getNode1(--index);
//        } else {
//            //System.out.println(decode());
//            return this;
//        }
//        return null;
//
//    }
}
