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
			daTornare += menuTematicoView.descrizioneNomeMenuTematico() + "\n";
			i++;
		}
		return daTornare;
	}
	
	public void mostraDescrizioneNomeMenuTematici() {
		System.out.println(descrizioneNomeMenuTematici());
	}
	
	public String descrizioneMenuTematici() {
		String daTornare = "Elenco Menu tematici:\n";
		int i=1;
		for(MenuTematico menuTematico : elencoMenuTematici) {
			daTornare += i+")";
			MenuTematicoView menuTematicoView = new MenuTematicoView(menuTematico);
			daTornare += menuTematicoView.descrizioneMenuTematico() + "\n";
			i++;
		}
		return daTornare;
	}
	
	public void mostraDescrizioneMenuTematici() {
		System.out.println(descrizioneMenuTematici());
	}
	
	public String descrizioneNome_PeriodoMenuTematici() {
		String daTornare = "";
		int i=1;
		for(MenuTematico menuTematico : elencoMenuTematici) {
			daTornare += i+")";
			MenuTematicoView menuTematicoView = new MenuTematicoView(menuTematico);
			daTornare += menuTematicoView.descrizioneNome_PeriodoMenuTematico() + "\n";
			i++;
		}
		return daTornare;
	}
	
	public void mostraDescrizioneNome_PeriodMenuTematici() {
		System.out.println(descrizioneNome_PeriodoMenuTematici());
	}
	
	public String descrizioneNomeMenuTematiciNomePiatti() {
		String daTornare = "";
		int i=1;
		for(MenuTematico menuTematico : elencoMenuTematici) {
			daTornare += i+")";
			MenuTematicoView menuTematicoView = new MenuTematicoView(menuTematico);
			daTornare += menuTematicoView.descrizioneMenuTematicoNomiPiatti()+ "\n";
			i++;
		}
		return daTornare;
	}
	
	public void mostraDescrizioneNomeMenuTematiciNomePiatti() {
		System.out.println(descrizioneNomeMenuTematiciNomePiatti());
	}
}
