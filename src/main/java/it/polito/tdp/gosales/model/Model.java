package it.polito.tdp.gosales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.gosales.dao.GOsalesDAO;

public class Model {
	
	private GOsalesDAO dao;
	private Graph<Retailers, DefaultWeightedEdge> grafo;
	private Map<Integer, Retailers> retailersIdMap;
	private int nConnessi;
	
	
	public Model() {
		this.dao = new GOsalesDAO();
		
		this.retailersIdMap = new HashMap<Integer, Retailers>();
		
		//Popoliamo l'identity map, in caso ci servisse dopo
		List<Retailers> retailers = this.dao.getAllRetailers();
		for (Retailers r : retailers) {
			this.retailersIdMap.put(r.getCode(), r);
		}
		
		this.nConnessi = 0;
		
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
		List<Retailers> vertici = this.dao.getVertici(nazione);
		Graphs.addAllVertices(this.grafo, vertici);
		
		//assegnazione degli archi
		// VERSIONE 1) calcoliamo gli archi da query
		List<Arco> archi = this.dao.getArchi(nazione, anno, nMin);
		for (Arco a : archi) {
			Retailers r1 = this.retailersIdMap.get(a.getrCode1());
			Retailers r2 = this.retailersIdMap.get(a.getrCode2());
			int peso = a.getNComune();
			Graphs.addEdgeWithVertices(this.grafo, r1, r2, peso);
		}
		
		
		
		// VERSIONE 2) aggreghiamo gli archi lato codice
		//Per ogni vertice del grafo, leggo tutti i suoi prodotti nell'anno
//		List<RetailerProducts> rp = new ArrayList<RetailerProducts>();
//		for (Retailers r : vertici) {
//			rp.add(new RetailerProducts(r, this.dao.getRetailerProducts(r.getCode(), anno)) );
//		}
//		// Doppio ciclo for, per verificare le coppie di retailers
//		for (int i = 0; i<rp.size(); i++) {
//			for (int j = i+1; j < rp.size(); j ++) {
//				Retailers r1 = rp.get(i).getRetailer();
//				Retailers r2 = rp.get(j).getRetailer();
//				Set<Integer> products1 = new HashSet<Integer>(rp.get(i).getSoldProducts());
//				Set<Integer> products2 = rp.get(j).getSoldProducts();
//				products1.retainAll(products2);
//				//se i due venditori hanno almeno Nmin prodotti in comune, 
//				//aggiungo l'arco
//				if (products1.size()>= nMin) {
//					Graphs.addEdgeWithVertices(this.grafo, r1, r2, products1.size());
//				}
//			}
//		}
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
	public List<ArcoExt> getArchi(){
		//creare lista risultato dell'oggetto ArcoExt, che contiene i vertici dell'arco ed il peso
		List<ArcoExt> result = new ArrayList<ArcoExt>();
		
		//per ogni arco del grafo, aggiungere un oggetto arcoExt alla lista
		for (DefaultWeightedEdge e : this.grafo.edgeSet()) {
			result.add(new ArcoExt(this.grafo.getEdgeSource(e),
					this.grafo.getEdgeTarget(e),
					(int)(this.grafo.getEdgeWeight(e))) );
			
		}
		Collections.sort(result);
		
		//restituire lista
		return result;
	}
	
	
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
	
	
	
	/**
	 * Metodo che calcola ed analizza la componente connessa per il prodotto p selezionato
	 * @param r
	 * @return
	 */
	public StatsConnessa analizzaComponente(Retailers r) {
		// Trova componente connessa  (Connectivity Inspector)
		ConnectivityInspector<Retailers, DefaultWeightedEdge> inspector =
				new ConnectivityInspector<Retailers, DefaultWeightedEdge>(this.grafo);
		Set<Retailers> connessi = inspector.connectedSetOf(r);
		
		//Salvo la dimensione della componente connessa perché serve per la simulazoine
		this.nConnessi = connessi.size();
		
		//calcola il peso totale degli archi nella componente connessa
		// Possiamo prendere gli archi del grafo uno a uno, e verificare se i suoi 
		// vertici sono nella componente connessa. In caso affermativo, possiamo aggiungere
		//il suo peso al totale.
		int peso = 0;
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if (connessi.contains(this.grafo.getEdgeSource(e)) &&
					connessi.contains(this.grafo.getEdgeTarget(e))) {
				peso += (int)this.grafo.getEdgeWeight(e);
			}
		}
		
		//restituiamo il risultato (dimensione componente connessa + peso totale)
		StatsConnessa result = new StatsConnessa(connessi, peso);
		return result;
	}
	
	
	
	public List<Products> getProductsRetailerYear(Retailers r, int anno){
		//leggere dal dao i prodotti venduti dal retailer nell'anno selezionato
		return this.dao.getProductsRetailerYear(r, anno);
	}
	
	
	
	/**
	 * Metodo che crea un simulatore ed esegue la simulazione
	 * @param prodotto
	 * @param q
	 * @param n
	 * @param r
	 * @param anno
	 * @return
	 */
	public SimulationResult eseguiSimulazione(Products p, int q, int n, Retailers r, int  anno) {
		// Creare simulatore e coda degli eventi
		Simulatore sim = new Simulatore(r, anno, p, n, q, this.nConnessi);
		sim.popolaCoda();
	
		// Eseguire simulazione
		sim.processaEventi();
	
		// Leggere e restituire il risultato della simulazione
		return sim.getSimulationResult();
	}
	
	
	
	/*
	 * Metodo usato per passare al controller le nazioni lette dal dao
	 */
	public List<String> getNazioni(){
		return this.dao.getNazioni();
	}
	
}
