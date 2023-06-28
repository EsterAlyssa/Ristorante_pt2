package Prenotazioni;
import java.util.HashMap;

import Giorno.Giorno;
import Ristorante.ElementiRistorante.Piatto;
import Util.InputDati;

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

	public void addScelta (SceltaPrenotazione scelta, int numPersone) {
		elenco.put(scelta, numPersone);
	}

	//ritorna il numero di Persone (= valore) dato il primo elemento della coppia = quante persone hanno ordinato un determinato piatto o menu tematico
	public int getNumeroPersone(SceltaPrenotazione ordine) {
		return elenco.get(ordine);
	}

	//ritorna la somma del numero dei piatti totali ordinati in una prenotazione 
	public int ritornaNumPiattiOrdinati () {
		int temp = 0;
		for (Integer ordine : elenco.values()) {
			temp += ordine;
		}
		return temp;
	}

	//metodo che servira' per la lista della spesa relativa alla singola prenotazione
	public HashMap <Piatto, Integer> elencoPiatti () {
		HashMap<Piatto, Integer> conteggio = new HashMap<>();
		for (SceltaPrenotazione scelta : elenco.keySet()) {
			for (Piatto piatto : scelta.getPiatti()) {
				conteggio.put(piatto, conteggio.getOrDefault(piatto, 0) + elenco.get(scelta));
			}
		}
		return conteggio;
	}

	public double calcoloCaricoLavoro() {
		double caricoLavoroTotale=0.0;
		
		for (SceltaPrenotazione scelta : elenco.keySet()) {
			caricoLavoroTotale += scelta.getCaricoLavoro();
		}
		
		return caricoLavoroTotale;
	}
	
	public static Prenotazione creaPrenotazioneVuota(int maxCoperti) {
		String messaggioNomeCliente = "Inserire il nome di chi prenota: ";
		String messaggioNumCoperti = "Inserire il numero di persone per cui si vuole prenotare: ";
		String messaggioGiornoPrenotazione = "Inserire il giorno per cui si vuole prenotare: ";
		
		String nomeCliente = InputDati.leggiStringaNonVuota(messaggioNomeCliente);
		int numCoperti = InputDati.leggiIntero(messaggioNumCoperti, 1, maxCoperti);
		System.out.println(messaggioGiornoPrenotazione);
		Giorno data = Giorno.richiestaCreaGiorno();
		
		Prenotazione prenotazione = new Prenotazione(nomeCliente, numCoperti, data);

		return prenotazione;
	}

	@Override
	public String toString() {
		return "Prenotazione di " + cliente + ", per " + numCoperti + "persone";
	}
	
}


