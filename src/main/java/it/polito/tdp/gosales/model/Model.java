package it.polito.tdp.gosales.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.gosales.dao.GOsalesDAO;

public class Model {
	
	private GOsalesDAO dao;
	private Graph<Retailers, DefaultWeightedEdge> grafo;
	private Map<Integer, Retailers> retailersIdMap;
	
	
	
	public Model() {
		this.dao = new GOsalesDAO();
		
		this.retailersIdMap = new HashMap<Integer, Retailers>();
		
		//Popoliamo l'identity map, in caso ci servisse dopo
		List<Retailers> retailers = this.dao.getAllRetailers();
		for (Retailers r : retailers) {
			this.retailersIdMap.put(r.getCode(), r);
		}
		
	}
	
	
	
	/**
	 * Metodo che crea il grafo
	 * @param nazione
	 * @param anno
	 * @param nMin
	 */
	public void creaGrafo(String nazione, Integer anno, Integer nMin) {
		//costruzione di un nuovo grafo
		this.grafo = new SimpleWeightedGraph<Retailers, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//assegnazione dei vertici
		
		//assegnazione degli archi
	}
	
	
	
	/**
	 * Metodo che restituisce una lista di vertici dell'arco
	 * @return
	 */
	public List<Retailers> getVertici(){
		return new ArrayList<Retailers>(this.grafo.vertexSet());
	}
	
	
	/**
	 * Metodo che restituisce una lista di vertici dell'arco
	 * @return
	 */
//	public List<ArcoExt> getArchi(){
//		//creare lista risultato dell'oggetto ArcoExt, che contiene i vertici dell'arco ed il peso
//		
//		//per ogni arco del grafo, aggiungere un oggetto arcoExt alla lista
//		
//		//restituire lista
//	}
	
	
	/**
	 * Metodo che restituisce il numero di vertici del grafo
	 * @return
	 */
	public int getNVertici(){
		return this.grafo.vertexSet().size();
	}
	
	
	/**
	 * Metodo che restituisce il numero di archi del grafo
	 * @return
	 */
	public int getNArchi(){
		return this.grafo.edgeSet().size();
	}
	
	
	
	
//	public ComponenteStats analizzaComponente(Retailers r) {
//		// Trova componente connessa  (Connectivity Inspector)
//		
//		//calcola il peso totale degli archi nella componente connessa
//		// Possiamo prendere gli archi del grafo uno a uno, e verificare se i suoi 
//		// vertici sono nella componente connessa. In caso affermativo, possiamo aggiungere
//		//il suo peso al totale.
//		
//		//restituiamo il risultato (dimensione componente connessa + peso totale)
//	}
	
	
	
//	public List<Products> getProductsRetailerYear(Retailers r, int anno){
//		//leggere dal dao i prodotti venduti dal retailer nell'anno selezionato
//	}
	
	
//	public SimulationResult eseguiSimulazione(Products prodotto, int q, int n, Retailers r, int  anno) {
		// Creare simulatore e coda degli eventi
	
		// Eseguire simulazione
	
		// Leggere e restituire il risultato della simulazione
//	}
	
	
	/*
	 * Metodo usato per passare al controller le nazioni lette dal dao
	 */
	public List<String> getNazioni(){
		return this.dao.getNazioni();
	}
	
}
