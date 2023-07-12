package Ristorante.ElementiRistorante.ElementiRistorantiView;

import Ristorante.ElementiRistorante.MenuTematico;

public class MenuTematicoView extends MenuView{

	private MenuTematico menuTematico;
	
	public MenuTematicoView(MenuTematico menuTematico) {
		super(menuTematico);
		this.menuTematico = menuTematico;
	}
	
	public String descrizioneMenuTematico() {
	    String daTornare = "Menu Tematico: " + menuTematico.getNome() +
	            "\nCarico di lavoro del menu tematico: " + menuTematico.getCaricoLavoro();
	    daTornare += "\n" + super.descrizioneMenu();
	    return daTornare;
	}
	
	public void mostraDescrizioneMenuTematico() {
		System.out.println(descrizioneMenuTematico());
	}
	
	public String descrizioneMenuTematicoNomiPiatti() {
	    String daTornare = "Menu Tematico: " + menuTematico.getNome() +
	            "\nCarico di lavoro del menu tematico: " + menuTematico.getCaricoLavoro();
	    daTornare += "\n" + super.descrizioneNomiPiattiMenu();
	    return daTornare;
	}
	
	public void mostraDescrizioneMenuTematicoNomiPiatti() {
		System.out.println(descrizioneMenuTematico());
	}
	
	public String descrizioneNomeMenuTematico() {		
		return "Menu tematico: " + menuTematico.getNome();
	}
	
	public void mostraDescrizioneNomeMenuTematico() {
		System.out.println(descrizioneNomeMenuTematico());
	}
	
}
