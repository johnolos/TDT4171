package assignment4;

import java.util.ArrayList;

public class Node {
	
	Node parent;
	int importance;
	ArrayList<Integer> attributes;
	ArrayList<Node> children;
	
	public Node(Node parent, ArrayList<Integer> attributes) {
		this.parent = parent;
		this.attributes = attributes;
	}
	
	public Node(int pluralityValue) {
		
	}
	
	
	public void addChild(Node child) {
		this.children.add(child);
	}

}
