package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private Graph<Airport, DefaultWeightedEdge>grafo;
	private ExtFlightDelaysDAO dao;
	private Map<Integer, Airport>aeroportiIdMap;
	private List<Airport>allAeroporti;
	
	public Model() {
		this.dao  = new ExtFlightDelaysDAO();
		this.aeroportiIdMap = new HashMap<>();
		this.allAeroporti = new ArrayList<>(dao.loadAllAirports());
	}
	
	public void creaGrafo(int nMinCompagnieAeree) {
		//grafo
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		//idMap
		for(Airport x : this.allAeroporti) {
			this.aeroportiIdMap.put(x.getId(), x);
		}
		
		//vertici (gia compresi i vincoli sul numero di compagnie aeree)
		Graphs.addAllVertices(grafo, this.dao.getVertici(nMinCompagnieAeree, aeroportiIdMap));
		
		//archi
		List<CoppiaA>edges = dao.getArchi(aeroportiIdMap);
		for(CoppiaA x : edges) {
			Airport origin = x.getPartenza();
			Airport destination = x.getArrivo();
			int peso = x.getN();
			
			//metto controllo del tipo: se esistono i vertici: se l'arco esiste gia ci incremento il peso, altrimenti lo creo nuovo
			if(grafo.vertexSet().contains(origin) && grafo.vertexSet().contains(destination)) {
				DefaultWeightedEdge edge = this.grafo.getEdge(origin, destination);
				if(edge!=null) {
					double weight = this.grafo.getEdgeWeight(edge);
					weight += peso;
					this.grafo.setEdgeWeight(origin, destination, weight);
				} else {
					this.grafo.addEdge(origin, destination);
					this.grafo.setEdgeWeight(origin, destination, peso);
				}
			}			
		}
		System.out.println("Grafo creato con "+grafo.vertexSet().size()+" vertici, "+grafo.edgeSet().size()+" archi. " );
	}
	
	
	public boolean aeroportiConnessi(Airport a, Airport b) {
		boolean c = false;
		//se gli aeroporti sono tra i vertici del grafo
		if(this.grafo.vertexSet().contains(a) && grafo.vertexSet().contains(b)) {
			
			List<Airport>confinanti = new ArrayList<Airport>();
			confinanti = Graphs.neighborListOf(grafo, a);	//prendo tutti i vicini di a	
			if(confinanti.contains(b)) {// se tra questi c'è b : sono connessi
				c = true;
			}
		}
		return c;
	}

	public ExtFlightDelaysDAO getDao() {
		return dao;
	}

	public List<Airport> getAllAeroporti() {
		return allAeroporti;
	}
	
	
}



















