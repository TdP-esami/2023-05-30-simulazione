package it.polito.tdp.gosales.model;

import java.util.Set;

public class StatsConnessa {

	private Set<Retailers> retailers;
	private int peso;
	
	public StatsConnessa(Set<Retailers> retailers, int peso) {
		super();
		this.retailers = retailers;
		this.peso = peso;
	}
	public Set<Retailers> getRetailers() {
		return retailers;
	}
	public void setRetailers(Set<Retailers> retailers) {
		this.retailers = retailers;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
	
}
