package util;

import view.GUI;

public class FakeInterface extends GUI {
	
	private boolean escalado;
	private String contractividad;
	private boolean invEspecial;
	private boolean irradiate;
	
	public FakeInterface(boolean escalado, String contractividad, boolean invEspecial, boolean irradiate) {
		super();
		this.escalado = escalado;
		this.contractividad = contractividad;
		this.invEspecial = invEspecial;
		this.irradiate = irradiate;
	}

	@Override
	public boolean getEscalado() {
		return escalado;
	}

	@Override
	public String getContractividad() {
		return contractividad;
	}

	@Override
	public boolean getInvEspecial() {
		return invEspecial;
	}

	@Override
	public boolean getIrradiate() {
		return irradiate;
	}

}
