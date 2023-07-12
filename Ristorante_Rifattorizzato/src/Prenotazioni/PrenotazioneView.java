package Prenotazioni;

import Giorno.Giorno;
import Giorno.GiornoView.GiornoView;
import Util.InputDati;

public class PrenotazioneView {
	static final String MSG_NOME = "Inserire il nome di chi prenota: ";
	static final String MSG_NUM = "Inserire il numero di persone per cui si vuole prenotare: ";
	static final String MSG_GIORNO = "Inserire il giorno per cui si vuole prenotare: ";
	
	private Prenotazione prenotazione;
	
	public PrenotazioneView(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}

	public static Prenotazione creaPrenotazioneVuota(int maxCoperti) {
		String nomeCliente = InputDati.leggiStringaNonVuota(MSG_NOME);
		int numCoperti = InputDati.leggiIntero(MSG_NUM, 1, maxCoperti);
		System.out.println(MSG_GIORNO);
		Giorno data = GiornoView.richiestaCreaGiorno();
		
		Prenotazione prenotazione = new Prenotazione(nomeCliente, numCoperti, data);
	
		return prenotazione;
	}
	
	public String descrizionePrenotazione() {
		return "Prenotazione di " + prenotazione.getCliente() + 
				", per " + prenotazione.getNumCoperti() + " persone";
	}
	
	public void mostraDescrizionePrenotazione() {
		System.out.println(descrizionePrenotazione());
	}

}
