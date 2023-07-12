package Ristorante.ElementiRistorante.ElementiRistorantiView;

import Giorno.GiornoView.PeriodoView;
import Ristorante.ElementiRistorante.Menu;

public abstract class MenuView {
	
	private Menu menu;

	public MenuView(Menu menu) {
		this.menu = menu;
	}
	
	public String descrizioneMenu() {
		String daTornare = "";
		PiattiView piattiView = new PiattiView(menu.getElenco());
		daTornare += piattiView.descrizioneNomePiatti();
		daTornare += "Periodo di validita':\n";
		PeriodoView periodoView = new PeriodoView(menu.getValidita());
		daTornare += periodoView.descrizionePeriodo();

		return daTornare;
	}
	
	public void mostraDescrizioneMenu() {
		System.out.println(descrizioneMenu());
	}
	
	public String descrizioneNomiPiattiMenu() {
		String daTornare = "Piatti:\n";
		PiattiView piattiView = new PiattiView(menu.getElenco());
		daTornare += piattiView.descrizioneNomePiatti();
		
		return daTornare;
	}
	
	public void mostraDescrizioneNomiPiattiMenu() {
		System.out.println(descrizioneNomiPiattiMenu());
	}
}
