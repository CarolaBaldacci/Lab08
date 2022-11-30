package it.polito.tdp.extflightdelays.model;

public class CoppiaId {

	private int idPartenza;
	private int idArrivo;
	private double peso;
	private int flightId;
	
	
	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public CoppiaId(int idPartenza, int idArrivo, double peso ) {
		super();
		this.idPartenza = idPartenza;
		this.idArrivo = idArrivo;
		this.peso=peso;
	}
	
	
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	public int getIdPartenza() {
		return idPartenza;
	}
	public void setIdPartenza(int idPartenza) {
		this.idPartenza = idPartenza;
	}
	public int getIdArrivo() {
		return idArrivo;
	}
	public void setIdArrivo(int idArrivo) {
		this.idArrivo = idArrivo;
	}

	@Override
	public String toString() {
		return "CoppiaId: idPartenza=" + idPartenza + ", idArrivo=" + idArrivo + ", peso=" + peso + "\n";
	}
	
}
