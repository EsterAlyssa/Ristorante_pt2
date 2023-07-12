package Ristorante.ElementiRistorante.ElementiRistorantiView;

import Ristorante.ElementiRistorante.Menu;
import Ristorante.ElementiRistorante.MenuTematico;

public class MenuTematicoView extends MenuView{

	public MenuTematicoView(MenuTematico menuTematico) {
		super(menuTematico);
	}
	
	@Override
	public Menu getMenu() {
		if (getMenu() instanceof MenuTematico) 
			return (MenuTematico) getMenu();
		else 
			return null;
	}

	public String descrizioneMenuTematico() {
	    MenuTematico menuTematico = (MenuTematico) getMenu();
	    String daTornare = "Menu Tematico: " + menuTematico.getNome() +
	            "\nCarico di lavoro del menu tematico: " + menuTematico.getCaricoLavoro();
	    daTornare += super.descrizioneMenu();
	    return daTornare;
	}
	
	public void mostraDescrizioneMenuTematico() {
		System.out.println(descrizioneMenuTematico());
	}
	
	public String descrizioneNomeMenuTematico() {		
		MenuTematico menuTematico = (MenuTematico) getMenu();
		return "Menu tematico: " + menuTematico.getNome();
	}
	
	public void mostraDescrizioneNomeMenuTematico() {
		System.out.println(descrizioneNomeMenuTematico());
	}
	
}
