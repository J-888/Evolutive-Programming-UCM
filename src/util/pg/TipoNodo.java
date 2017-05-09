package util.pg;

import java.util.ArrayList;

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
	}, AND {
		public boolean resolve(ArrayList<Node> children) {
			boolean ret = true;
			for(int i = 0; i < children.size() && ret; i++){
				if(!children.get(i).resolve())
					ret = false;
			}
			return ret;
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
	}, XOR {
		public boolean resolve(ArrayList<Node> children) {
			boolean ret = false;
			for(int i = 0; i < children.size(); i++){
				if(children.get(i).resolve())
					ret = !ret;
			}
			return ret;
		}
	}, ENTRADA {
		//Shouldn't ever be called
		public boolean resolve(ArrayList<Node> children) {
			return (Boolean) null;
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
	};	
	
	public abstract boolean resolve(ArrayList<Node> children);

}
