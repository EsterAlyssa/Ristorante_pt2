package Ristorante.ElementiRistorante.ElementiRistorantiView;

import Ristorante.ElementiRistorante.Menu;
import Ristorante.ElementiRistorante.MenuCarta;

public class MenuCartaView extends MenuView {
	
	public MenuCartaView(MenuCarta menuCarta) {
		super(menuCarta);
	}

	@Override
	public Menu getMenu() {
		if (getMenu() instanceof MenuCarta) 
			return (MenuCarta) getMenu();
		else 
			return null;
	}
	
	public String descrizioneMenuCarta() {
		String daTornare = "Menu alla carta:\n";
		daTornare += super.descrizioneNomiPiattiMenu();
		return daTornare;
	}
	
	public void mostraDescrizioneMenuCarta() {
		System.out.println(descrizioneMenuCarta());
	}

	
}
