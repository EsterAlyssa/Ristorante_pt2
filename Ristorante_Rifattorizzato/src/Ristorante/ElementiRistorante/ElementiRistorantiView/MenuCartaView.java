package Ristorante.ElementiRistorante.ElementiRistorantiView;

import Ristorante.ElementiRistorante.MenuCarta;

public class MenuCartaView extends MenuView {
	
	public MenuCartaView(MenuCarta menuCarta) {
		super(menuCarta);
	}
	
	public String descrizioneMenuCartaNomiPiatti() {
		String daTornare = "Menu alla carta:\n";
		daTornare += super.descrizioneNomiPiattiMenu();
		return daTornare;
	}
	
	public void mostraDescrizioneMenuCartaNomiPiatti() {
		System.out.println(descrizioneMenuCartaNomiPiatti());
	}

	
}
