package Ristorante.ElementiRistorante;

import java.util.HashSet;

import Giorno.Periodo;

public abstract class Menu {

	private HashSet<Piatto> elencoMenu;
	private Periodo validitaMenu;


	public Menu(Periodo validitaMenu) {
		this.elencoMenu =  new HashSet<>();
		this.validitaMenu = validitaMenu;
	}

	public Menu() {
		this.elencoMenu =  new HashSet<>();
		this.validitaMenu = new Periodo();
	}

	public HashSet<Piatto> getElenco() { 
		return elencoMenu;
	}

	public void setElenco(HashSet<Piatto> elenco) {
		this.elencoMenu = elenco;
	}


	public Periodo getValidita() {
		return validitaMenu;
	}

	public void setValidita(Periodo validitaMenu) {
		this.validitaMenu = validitaMenu;
	}

	public boolean aggiungiPiatto(Piatto piatto) {
		if (piatto.getNome()!="" && piatto.getCaricoLavoro()>0) {
			return this.elencoMenu.add(piatto);
		} else {
			return false;
		}
	}
}
