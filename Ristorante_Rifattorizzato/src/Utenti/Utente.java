package Utenti;

import Util.*;

public abstract class Utente implements MenuUtente {

	private String nome;
	private String etichetta;

	//ci serve per il metodo del menu
	private String[] azioni;
	private MyMenu menu;

	private static final String[] MENU_RUOLI = {"Gestore", "Addetto alle prenotazioni", "Magazziniere"};

	public Utente(String nome, String etichetta, String[] azioni) {
		this.nome = nome;
		this.etichetta = etichetta;
		this.azioni = azioni;
		this.menu = new MyMenu("menu "+ etichetta, azioni);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEtichetta() {
		return etichetta;
	}

	public void setEtichetta(String etichetta) {
		this.etichetta = etichetta;
	}

	public String[] getAzioni() {
		return azioni;
	}

	public void setAzioni(String[] azioni) {
		this.azioni = azioni;
	}

	public void menu(String pathCompletoFile) {
		System.out.printf("Ciao %s!\n", this.nome);
		int scelta = menu.scegli();
		eseguiMetodi(scelta, pathCompletoFile);

	}


	public static void mostraMenuRuoli(String pathCompletoFile) {	
		Utente utente = null;
		String nome = InputDati.leggiStringaNonVuota("Inserisci il tuo nome: ");

		MyMenu menuRuoli = new MyMenu("Seleziona il tuo ruolo:", MENU_RUOLI);
		int scelta = menuRuoli.scegli();
		switch (scelta) {
		case 1:
			utente = new Gestore(nome);
			break;
		case 2:
			utente = new AddettoPrenotazioni(nome);
			break;
		case 3:
			utente = new Magazziniere(nome);
			break;
		}

		if (utente != null) {
			boolean sceltaAltreAzioni = false;
			do {
				utente.menu(pathCompletoFile);
				sceltaAltreAzioni = InputDati.yesOrNo("Vuoi fare altro?");
			} while (sceltaAltreAzioni);
		}
		System.out.println("Grazie per aver usato l'applicazione. Arrivederci!");
	}

	public abstract void eseguiMetodi(int scelta, String pathCompletoFile);
}
