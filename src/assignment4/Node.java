package assignment4;

import java.util.ArrayList;

public class Node {
	
	private int importance;
	private ArrayList<Node> children;
	private int branch;
	
	public Node(int pluralityValue) {
		children = new ArrayList<Node>();
		this.importance = pluralityValue;
		
	}
	public void printTree(int depth){
		String line = "|";
		for(int i = 0; i < depth; i++)
			line += "_ ";
		if(this.children.isEmpty()){
			System.out.println(line + "Leaf: " + this.importance);
		}
		else{
			System.out.println(line + "Internal: "+ this.importance);
			for (Node child :  this.children) {
				int depthCopy = depth + 1;
				child.printTree(depthCopy);
			}
		}
		
	}
	
	public int getImportance() {
		return this.importance;
	}
	
	public void setBranch(int branch) {
		this.branch = branch;
	}
	
	public int getBranch() {
		return this.branch;
	}
	
	public void addChild(Node child) {
		this.children.add(child);
	}
	
	public ArrayList<Node> getChildren() {
		return this.children;
	}
	public boolean classify(ArrayList<Integer> transaction) {
		if(this.children.isEmpty()){
			if(transaction.get(7)==this.importance){
				return true;
			}
			return false;
		}
		else{
				if(this.children.get(0).branch == transaction.get(importance)){
					return this.children.get(0).classify(transaction);
				}
				else{
					return this.children.get(1).classify(transaction);
				}
		}
	}

}
