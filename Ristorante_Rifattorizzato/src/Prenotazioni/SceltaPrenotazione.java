package Prenotazioni;

import java.util.HashSet;

import Ristorante.ElementiRistorante.Piatto;

public interface SceltaPrenotazione {

	public static SceltaPrenotazione trovaDaNome(String nome, HashSet<SceltaPrenotazione> insieme) {
		for (SceltaPrenotazione menu : insieme) {
			if (menu.getNome().equalsIgnoreCase(nome)) {
				return menu;
			}
		}
		return null;
	}
	
	public String getNome();
	public HashSet<Piatto> getPiatti();
	public double getCaricoLavoro();
}
