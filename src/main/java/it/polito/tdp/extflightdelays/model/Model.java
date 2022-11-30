package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	private ExtFlightDelaysDAO dao;
	private List<Airport> aereoportiList;
	private SimpleDirectedWeightedGraph<Airport, DefaultEdge> grafo;
	private Map<Integer,Airport> aereoportiIdMap;
	
	public Model() {
		super();
		this.dao= new ExtFlightDelaysDAO();
	}
	
	
	public List <Airport> getAereoporti(){
		if(this.aereoportiList==null)
			this.aereoportiList = dao.loadAllAirports();
		
		this.aereoportiIdMap = new HashMap<Integer,Airport>();
		for(Airport a: this.aereoportiList) {
			this.aereoportiIdMap.put(a.getId(), a);
		}
		return this.aereoportiList;
	}
	
//CREA GRAFO: semlice , non orientato, pesato
	//VERTICI==aereoporti ARCHI==voli PESO==distanza media percorsa
	public void creaGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<Airport, DefaultEdge>(DefaultEdge.class);
		ExtFlightDelaysDAO dao= new ExtFlightDelaysDAO();
		
		Graphs.addAllVertices(this.grafo, this.getAereoporti());
		
		List<CoppiaId> aereoportiDaCollegare=dao.getCoppiaAereoportiConnessi();
		for(CoppiaId coppia: aereoportiDaCollegare) {
			DefaultEdge e=this.grafo.getEdge(aereoportiIdMap.get(coppia.getIdPartenza()), aereoportiIdMap.get(coppia.getIdArrivo()));
			if(e==null) {
				Graphs.addEdge(grafo, aereoportiIdMap.get(coppia.getIdPartenza()), aereoportiIdMap.get(coppia.getIdArrivo()),coppia.getPeso());
			}else {
				double peso= grafo.getEdgeWeight(e);
				double newPeso=(peso + coppia.getPeso())/2;
				grafo.setEdgeWeight(e, newPeso);
			}
		}
	}


//VISITA GRAFO
	public Map<Airport, Airport> visitaGrafo(Airport partenza) {
		GraphIterator<Airport, DefaultEdge> visita= new BreadthFirstIterator<>(this.grafo, partenza);
		//registro il percorso
		
		Map<Airport, Airport> alberoInverso = new HashMap<>();
		alberoInverso.put(partenza, null);
		
		visita.addTraversalListener(new RegistraAlberoDiVisita(alberoInverso, this.grafo));
		while(visita.hasNext()) {
			visita.next();
		}
		
		return alberoInverso;
	}

//CALCOLO CAMMINO: se esiste almeno un vol che collega i due aereoporti restituisce vero
	public List<CoppiaId> calcolaPercorso(int x){
		creaGrafo();
		List<CoppiaId> risultato= new ArrayList<>();
		for(Airport partenza : aereoportiList) {
    		for(Airport arrivo : aereoportiList) {
	          if(!partenza.equals(arrivo)) {
				Map<Airport, Airport> alberoInverso = visitaGrafo(partenza);
	        	double distanza=0;
	        	Airport corrente=arrivo;
	        	List<Airport> percorso= new ArrayList<>();
	        	
	        	while(corrente!=null) {
	        		percorso.add(0,corrente);
		        	corrente=alberoInverso.get(corrente);
	         		if(percorso.size()>1) {
		         		DefaultEdge e = this.grafo.getEdge( percorso.get(0), percorso.get(1));
		            	distanza += this.grafo.getEdgeWeight(e);
			        }
		        }
		        if(distanza >=x) {
		        	for(DefaultEdge e : this.grafo.edgeSet()) {
		        		CoppiaId c =new CoppiaId(this.grafo.getEdgeSource(e).getId(), this.grafo.getEdgeTarget(e).getId(), this.grafo.getEdgeWeight(e));
			        	if(!risultato.contains(c))
			        		risultato.add(c);
		        	}
	         	}
		        return risultato;
		      }
            }
    	  }
		
		return risultato;
	}

//nVertici
	public int numeroVertici() {
		return this.grafo.vertexSet().size();
	}
//nArchi
		public int numeroArchi() {
			return this.grafo.edgeSet().size();
		}
	
}
