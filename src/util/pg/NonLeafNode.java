package util.pg;

import java.util.ArrayList;

public class NonLeafNode extends Node {

	protected ArrayList<Node> children;
	
	public NonLeafNode(){
		super();
		children = new ArrayList<>();
	}
	
	public void addChildren(Node c){
		this.children.add(c);
		c.setParent(this);
	}

	public ArrayList<Node> getChildren(){
		return this.children;
	}
	
	public boolean resolve() {
		return tipo.resolve(children);
	}
}
