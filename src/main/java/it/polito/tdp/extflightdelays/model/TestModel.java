package it.polito.tdp.extflightdelays.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		List<Airport> aereoporti=model.getAereoporti();
		/*for(Airport a: aereoporti)
			System.out.println(a.getId());
		*/
	    //model.creaGrafo(30);
	    
	    //Map<Airport, Airport> mappaAereoporti=model.visitaGrafo(aereoporti.get(0));
	   // mappaAereoporti.toString();
	    
		List<CoppiaId> percorso = model.calcolaPercorso(30);
	    System.out.println("Esiste un percorso da "+aereoporti.get(20)+
	    		" a "+ aereoporti.get(0)+ " con più di 30 miglia? "+ percorso);

	}

}
