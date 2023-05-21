package it.polito.tdp.extflightdelays.model;

public class CoppiaA {
	private Airport partenza;
	private Airport arrivo;
	private int N;
	
	public CoppiaA(Airport partenza, Airport arrivo, int n) {
		super();
		this.partenza = partenza;
		this.arrivo = arrivo;
		N = n;
	}
	
	public Airport getPartenza() {
		return partenza;
	}
	public void setPartenza(Airport partenza) {
		this.partenza = partenza;
	}
	public Airport getArrivo() {
		return arrivo;
	}
	public void setArrivo(Airport arrivo) {
		this.arrivo = arrivo;
	}
	public int getN() {
		return N;
	}
	public void setN(int n) {
		N = n;
	}
}
