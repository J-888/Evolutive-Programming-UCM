package util.pg;

import java.util.ArrayList;

import problemas.Pr3MuxN;

public enum TipoNodo {
	IF{
		public boolean resolve(ArrayList<Node> children) {
			if(children.size() != 3){
				System.err.println("IF DE != 3 ENTRADAS");
				return (Boolean) null;
			}
			else{
				if(children.get(0).resolve() == true)
					return children.get(1).resolve();
				else
					return children.get(2).resolve();
			}
		}

		public int getMinEnts() {
			return 3;
		}

		public int getMaxEnts() {
			return 3;
		}

		public String toString() {
			return "IF";
		}
		
	}, DC3e {
		public boolean resolve(ArrayList<Node> children) { //Los dos primeros conds, los 3 siguientes entradas
			if(children.get(0).resolve() == true){
				if(children.get(1).resolve() == true){
					return children.get(2).resolve();
				}
				else{
					return children.get(4).resolve();
				}
			}
			else{
				if(children.get(1).resolve() == true){
					return children.get(3).resolve();
				}
				else{
					return children.get(4).resolve();
				}
			}
		}

		public int getMinEnts() {
			return 5;
		}

		public int getMaxEnts() {
			return 5;
		}

		public String toString() {
			return "DC3e";
		}
	
	}, DC4e {
		public boolean resolve(ArrayList<Node> children) { //Los dos primeros conds, los 3 siguientes entradas
			if(children.get(0).resolve() == true){
				if(children.get(1).resolve() == true){
					return children.get(2).resolve();
				}
				else{
					return children.get(4).resolve();
				}
			}
			else{
				if(children.get(1).resolve() == true){
					return children.get(3).resolve();
				}
				else{
					return children.get(5).resolve();
				}
			}
		}

		public int getMinEnts() {
			return 6;
		}

		public int getMaxEnts() {
			return 6;
		}

		public String toString() {
			return "DC4e";
		}
	
	}, AND {
		public boolean resolve(ArrayList<Node> children) {
			boolean ret = true;
			for(int i = 0; i < children.size() && ret; i++){
				if(!children.get(i).resolve())
					ret = false;
			}
			return ret;
		}

		public int getMinEnts() {
			return 2;
		}

		public int getMaxEnts() {
			return Pr3MuxN.maxEntsPorNodo;
		}

		public String toString() {
			return "AND";
		}
		
	}, OR {
		public boolean resolve(ArrayList<Node> children) {
			boolean ret = false;
			for(int i = 0; i < children.size() && !ret; i++){
				if(children.get(i).resolve())
					ret = true;
			}
			return ret;
		}

		public int getMinEnts() {
			return 2;
		}

		public int getMaxEnts() {
			return Pr3MuxN.maxEntsPorNodo;
		}

		public String toString() {
			return "OR";
		}
		
	}, XOR {
		public boolean resolve(ArrayList<Node> children) {
			boolean ret = false;
			for(int i = 0; i < children.size(); i++){
				if(children.get(i).resolve())
					ret = !ret;
			}
			return ret;
		}

		public int getMinEnts() {
			return 2;
		}

		public int getMaxEnts() {
			return Pr3MuxN.maxEntsPorNodo;
		}

		public String toString() {
			return "XOR";
		}
		
	}, ENTRADA {
		//Shouldn't ever be called
		public boolean resolve(ArrayList<Node> children) {
			return (Boolean) null;
		}

		public int getMinEnts() {
			return 0;
		}

		public int getMaxEnts() {
			return 0;
		}

		public String toString() {
			return "";
		}
		
	}, NOT {
		public boolean resolve(ArrayList<Node> children) {
			if(children.size() != 1){
				System.err.println("NOT DE != 1 ENTRADA");
				return (Boolean) null;
			}
			else{
				return !children.get(0).resolve();
			}
		}

		public int getMinEnts() {
			return 1;
		}

		public int getMaxEnts() {
			return 1;
		}

		public String toString() {
			return "NOT";
		}
		
	};	
	
	public abstract boolean resolve(ArrayList<Node> children);
	public abstract int getMinEnts();
	public abstract int getMaxEnts();
	public abstract String toString();

}
