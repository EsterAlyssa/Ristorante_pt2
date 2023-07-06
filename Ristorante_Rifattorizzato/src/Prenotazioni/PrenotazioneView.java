package Prenotazioni;

import Giorno.Giorno;
import Giorno.GiornoView.GiornoView;
import Util.InputDati;

public class PrenotazioneView {
	final static String messaggioNomeCliente = "Inserire il nome di chi prenota: ";
	final static String messaggioNumCoperti = "Inserire il numero di persone per cui si vuole prenotare: ";
	final static String messaggioGiornoPrenotazione = "Inserire il giorno per cui si vuole prenotare: ";
	
	public static Prenotazione creaPrenotazioneVuota(int maxCoperti) {
		
		String nomeCliente = InputDati.leggiStringaNonVuota(messaggioNomeCliente);
		int numCoperti = InputDati.leggiIntero(messaggioNumCoperti, 1, maxCoperti);
		System.out.println(messaggioGiornoPrenotazione);
		Giorno data = GiornoView.richiestaCreaGiorno();
		
		Prenotazione prenotazione = new Prenotazione(nomeCliente, numCoperti, data);
	
		return prenotazione;
	}

}
