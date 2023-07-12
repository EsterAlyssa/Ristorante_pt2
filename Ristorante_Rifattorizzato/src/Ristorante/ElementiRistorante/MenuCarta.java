package Ristorante.ElementiRistorante;

import java.util.HashSet;

import Giorno.Periodo;

public class MenuCarta extends Menu {

	public MenuCarta(Periodo validita) {
		super(validita);
	}
	
	public MenuCarta() {
		super();
	}
	
	public MenuCarta(HashSet<Piatto> elencoPiatti) {
		super();
		this.setElenco(elencoPiatti);
	}
	
}
