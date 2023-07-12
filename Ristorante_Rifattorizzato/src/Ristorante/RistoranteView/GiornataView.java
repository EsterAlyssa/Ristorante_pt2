package Ristorante.RistoranteView;

import Prenotazioni.Prenotazione;
import Prenotazioni.PrenotazioneView;
import Ristorante.Giornata;

public class GiornataView {
	
	private Giornata giornata;

	public GiornataView(Giornata giornata) {
		this.giornata = giornata;
	}
	
	public String descrizionePrenotazioni() {
		String daStampare="Prenotazioni:\n";
		for (Prenotazione pren : giornata.getPrenotazioni()) {
			PrenotazioneView prenotazioneView = new PrenotazioneView(pren);
			daStampare += prenotazioneView.descrizionePrenotazione() + "\n"; 
		}
		return daStampare;
	}
	
	public void mostraDescrizionePrenotazioni() {
		System.out.println(descrizionePrenotazioni());
	}
}
