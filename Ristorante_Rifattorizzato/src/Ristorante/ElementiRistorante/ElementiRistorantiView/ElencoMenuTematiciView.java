package Ristorante.ElementiRistorante.ElementiRistorantiView;

import java.util.HashSet;

import Ristorante.ElementiRistorante.MenuTematico;

public class ElencoMenuTematiciView {
	
	private HashSet<MenuTematico> elencoMenuTematici;

	public ElencoMenuTematiciView(HashSet<MenuTematico> elencoMenuTematici) {
		this.elencoMenuTematici = elencoMenuTematici;
	}

	public String descrizioneNomeMenuTematici() {
		String daTornare = "";
		int i=1;
		for(MenuTematico menuTematico : elencoMenuTematici) {
			daTornare += i+")";
			MenuTematicoView menuTematicoView = new MenuTematicoView(menuTematico);
			daTornare += menuTematicoView.descrizioneNomeMenuTematico();
			i++;
		}
		return daTornare;
	}
	
	public void mostraDescrizioneNomeMenuTematici() {
		System.out.println(descrizioneNomeMenuTematici());
	}
	
	public String descrizioneMenuTematici() {
		String daTornare = "Elenco Menu Tematici:\n";
		int i=1;
		for(MenuTematico menuTematico : elencoMenuTematici) {
			daTornare += i+")";
			MenuTematicoView menuTematicoView = new MenuTematicoView(menuTematico);
			daTornare += menuTematicoView.descrizioneMenuTematico();
			i++;
		}
		return daTornare;
	}
	
	public void mostraDescrizioneMenuTematici() {
		System.out.println(descrizioneMenuTematici());
	}
	
}
