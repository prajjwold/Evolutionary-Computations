package com.cs572.assignments.Project2.utility;

import com.cs572.assignments.Project2.model.Node;

public class DotGraphCreator {
	private final String OPEN_GRAPH = "graph \"\"\n{\n";
	private final String CLOSE_GRAPH = "}";
	private final String OPEN_LABEL = "[label=\"";
	private final String CLOSE_LABEL = "\"];\n";
	private final String OPEN_COLOR = "color";
	private final String CLOSE_COLOR = "\"];\n";
	private final String DIRECTION_OPERATOR = "--";
	private final String NODE = "node";
	private static int counter = 0;

	public StringBuilder parseNode(Node node, StringBuilder expr) {
		if (node.isFunctionNode()) {
			String parent = NODE + counter;
			expr.append(parent + OPEN_LABEL + node.decode() + CLOSE_LABEL);

			String child1 = NODE + (++counter);
			expr.append(parent + DIRECTION_OPERATOR + child1 + ";\n");
			parseNode(node.getChildren(0), expr);

			String child2 = NODE + (++counter);
			expr.append(parent + DIRECTION_OPERATOR + child2 + ";\n");
			parseNode(node.getChildren(1), expr);
		} else {
			String node1 = NODE + counter;
			expr.append(node1 + OPEN_LABEL + node.decode() + CLOSE_LABEL);
			return expr;
		}
		return expr;

	}

	public String createGraph(Node node) {
		StringBuilder expr = new StringBuilder();
		counter = 0;
		expr.append(OPEN_GRAPH);
		parseNode(node, expr);
		expr.append(CLOSE_GRAPH);
		return expr.toString();

	}

}
