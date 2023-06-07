package it.polito.tdp.gosales.model;



/**
 * Helper class per restituire e stampare i risultati della simulazione
 * @author carlo
 *
 */
public class SimulationResult {
	
	private double soddisfazione;
	private double costo;
	private double ricavo;
	private double profitto;
	
	
	public SimulationResult(double soddisfazione, double costo, double ricavo, double profitto) {
		super();
		this.soddisfazione = soddisfazione;
		this.costo = costo;
		this.ricavo = ricavo;
		this.profitto = profitto;
	}


	public double getSoddisfazione() {
		return soddisfazione;
	}


	public void setSoddisfazione(double soddisfazione) {
		this.soddisfazione = soddisfazione;
	}


	public double getCosto() {
		return costo;
	}


	public void setCosto(double costo) {
		this.costo = costo;
	}


	public double getRicavo() {
		return ricavo;
	}


	public void setRicavo(double ricavo) {
		this.ricavo = ricavo;
	}


	public double getProfitto() {
		return profitto;
	}


	public void setProfitto(double profitto) {
		this.profitto = profitto;
	}


	@Override
	public String toString() {
		String s = String.format("Soddisfazione: %3.2f\n", this.soddisfazione*100);
		s+=String.format("Costo: %10.3f k$\n", this.costo/1000);
		s+=String.format("Ricavo: %10.3f k$\n", this.ricavo/1000);
		s+=String.format("Profitto: %10.3f k$\n", this.profitto/1000);
		return s;
	}
	
	
	

}
