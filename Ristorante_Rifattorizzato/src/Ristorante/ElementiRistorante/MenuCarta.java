package Ristorante.ElementiRistorante;

import Giorno.Periodo;

public class MenuCarta extends Menu {

	public MenuCarta(Periodo validita) {
		super(validita);
	}
	
	public MenuCarta() {
		super();
	}
	
	public String descrizioneMenuCarta() {
		String stringa = "Menu alla carta:\nPiatti:\n";
		for (Piatto piatto : super.getElenco()) {
			stringa += piatto.getNome() + "\n";
		}
		return stringa;
	}

	
	
}
