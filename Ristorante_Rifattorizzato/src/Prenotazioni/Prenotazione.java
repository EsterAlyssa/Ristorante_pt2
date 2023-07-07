package Prenotazioni;
import java.util.HashMap;

import Giorno.Giorno;
import Ristorante.ElementiRistorante.Piatto;

public class Prenotazione {

	private String cliente;
	private int numCoperti;
	private Giorno data;
	private HashMap<SceltaPrenotazione, Integer> elenco; 

	public Prenotazione(String cliente, int numCoperti, Giorno data) {
		this.cliente = cliente;
		this.numCoperti = numCoperti;
		this.data = data;
		this.elenco = new HashMap<>();
	}
	
	public Prenotazione(String cliente) {
		this.cliente = cliente;
		this.numCoperti = 0;
		this.data = null;
		this.elenco = new HashMap<>();
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public int getNumCoperti() {
		return numCoperti;
	}

	public void setNumCoperti(int numCoperti) {
		this.numCoperti = numCoperti;
	}

	public Giorno getData() {
		return data;
	}

	public void setData(Giorno data) {
		this.data = data;
	}

	public HashMap<SceltaPrenotazione, Integer> getElenco() {
		return elenco;
	}

	public void setElenco(HashMap<SceltaPrenotazione, Integer> elenco) {
		this.elenco = elenco;
	}

	public void aggiungiScelta (SceltaPrenotazione scelta, int numPersone) {
		elenco.put(scelta, numPersone);
	}

	//metodo che servira' per la lista della spesa relativa alla singola prenotazione
	public HashMap <Piatto, Integer> elencoPiattiDaScelte () {
		HashMap<Piatto, Integer> mapPiatti = new HashMap<>();
		for (SceltaPrenotazione scelta : elenco.keySet()) {
			for (Piatto piatto : scelta.getPiatti()) {
				mapPiatti.put(piatto, mapPiatti.getOrDefault(piatto, 0) + elenco.get(scelta));
			}
		}
		return mapPiatti;
	}
	
	public String descrizionePrenotazione() {
		return "Prenotazione di " + cliente + ", per " + numCoperti + " persone";
	}
	
}


