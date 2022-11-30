package it.polito.tdp.extflightdelays.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;


public class RegistraAlberoDiVisita implements TraversalListener<Airport, DefaultEdge> {

	private Map<Airport, Airport> alberoInverso;
	private Graph <Airport, DefaultEdge> grafo;
	
	public RegistraAlberoDiVisita(Map<Airport, Airport> alberoInverso, Graph <Airport, DefaultEdge> grafo) {
		this.alberoInverso=alberoInverso;
		this.grafo= grafo;
	}
	
	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
		//System.out.println(e.getEdge());
				Airport source=this.grafo.getEdgeSource(e.getEdge());
				Airport target=this.grafo.getEdgeTarget(e.getEdge());
				
				if(!alberoInverso.containsKey(target)) {
					alberoInverso.put(target, source);
					System.out.println(target+" si raggiunge da "+source);
				}
				else if(!alberoInverso.containsKey(source)) {
					alberoInverso.put(source, target);
					System.out.println(source+" si raggiunge da "+target);
				}
	}

	@Override
	public void vertexTraversed(VertexTraversalEvent<Airport> e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void vertexFinished(VertexTraversalEvent<Airport> e) {
		// TODO Auto-generated method stub

	}

}
