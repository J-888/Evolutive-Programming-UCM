package util;

public class Par<E> {
	private E n1, n2;

	public Par(E n1, E n2){
		this.n1 = n1;
		this.n2 = n2;
	}
	
	public E getN1() {
		return n1;
	}

	public void setN1(E n1) {
		this.n1 = n1;
	}

	public E getN2() {
		return n2;
	}

	public void setN2(E n2) {
		this.n2 = n2;
	}
	
	
}
