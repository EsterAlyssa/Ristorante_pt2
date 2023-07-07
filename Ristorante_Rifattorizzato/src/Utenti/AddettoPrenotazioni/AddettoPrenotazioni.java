package Utenti.AddettoPrenotazioni;

import Utenti.Utente;

public class AddettoPrenotazioni extends Utente {

	private static String etichettaAP = "addetto alle prenotazioni";
	private static String[] voci = {"Accetta le prenotazioni", "Visualizza le prenotazioni dato il giorno"};
	private GestorePrenotazioni gestorePrenotazioni;
    private VisualizzatorePrenotazioni visualizzatorePrenotazioni;
   
	public AddettoPrenotazioni(String nome) {
		super(nome, etichettaAP, voci);
		this.gestorePrenotazioni = new GestorePrenotazioni();
		this.visualizzatorePrenotazioni = new VisualizzatorePrenotazioni();
	}

	@Override
	public void eseguiMetodi(int scelta, String pathCompletoFileRistorante) {
		switch (scelta) {
		case 1: 
			gestorePrenotazioni.accettazionePrenotazione(pathCompletoFileRistorante);
			break;
		case 2:
			visualizzatorePrenotazioni.visualizzaPrenotazioni(pathCompletoFileRistorante);
			break;
		}
	}

}
