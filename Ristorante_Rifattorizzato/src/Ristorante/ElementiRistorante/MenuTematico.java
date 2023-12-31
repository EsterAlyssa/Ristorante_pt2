package Ristorante.ElementiRistorante;

import java.util.HashSet;

import Giorno.Giorno;
import Giorno.Periodo;
import Prenotazioni.SceltaPrenotazione;

public class MenuTematico extends Menu implements SceltaPrenotazione{

	private String nomeMenuTematico;
	private double caricoLavoroMenuTematico;

	public MenuTematico(String nomeMenuTematico,Periodo validita) {
		super(validita);
		this.nomeMenuTematico = nomeMenuTematico;
		this.caricoLavoroMenuTematico = 0.0;
	}

	public MenuTematico(String nomeMenuTematico) {
		super();
		this.nomeMenuTematico = nomeMenuTematico;
		this.caricoLavoroMenuTematico = 0.0;
	}
	
	public String getNome() {
		return nomeMenuTematico;
	}

	public void setNome(String nome) {
		this.nomeMenuTematico = nome;
	}

	public double getCaricoLavoro() {
		return caricoLavoroMenuTematico;
	}
	
	public void setCaricoLavoro(double caricoLavoro) {
		this.caricoLavoroMenuTematico = caricoLavoro;
	}

	public boolean aggiungiPiatto (Piatto piatto) {
		if (super.aggiungiPiatto(piatto)) {
			this.caricoLavoroMenuTematico += piatto.getCaricoLavoro();
			return true;
		} else {
			return false;
		}
	}

	public static MenuTematico trovaMenuTDaNome(String menu, HashSet<MenuTematico> menuTematici) {
		for (MenuTematico m : menuTematici) {
			if (m.getNome().equals(menu)) {
				return m;
			}
		}
		// Se il menu non viene trovato si ritorna null
		return null;
	}

	@Override
	public HashSet<Piatto> getPiatti() {
		return this.getElenco();
	}

	public boolean aggiungiGiornoValido(Giorno giorno) {
		return this.getValidita().aggiungiGiorno(giorno);
	}
	
}
