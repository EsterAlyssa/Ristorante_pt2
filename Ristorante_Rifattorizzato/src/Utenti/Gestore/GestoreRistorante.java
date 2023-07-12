package Utenti.Gestore;

import Utenti.Utente;

public class GestoreRistorante extends Utente{

	private static String etichettaG = "gestore";
	private Gestore gestore;
	private VisualizzatoreGestione visualizzatoreGestione;
	private static String[] voci = {
			"Cambia i parametri del ristorante",
			"Visualizza i parametri del ristorante",
			"Aggiungi bevanda all'insieme delle bevande",
			"Rimuovi bevanda dall'insieme delle bevande", 
			"Visualizza l'insieme delle bevande", 
			"Aggiungi genere extra all'insieme dei generi extra",
			"Rimuovi genere extra dall'insieme dei generi extra", 
			"Visualizza l'insieme dei generi extra",
			"Crea una ricetta", 
			"Visualizza il ricettario (solo i nomi)", 
			"Visualizza una ricetta", 
			"Visualizza le informazioni di tutte le ricetta",
			"Crea corrispondenza Piatto - Ricetta",
			"Visualizza tutti i piatti", 
			"Verifica l'esistenza di una ricetta dato il piatto",
			"Crea un menu tematico", 
			"Visualizza tutti i menu tematici (solo i nomi)", 
			"Visualizza tutti i menu tematici",
			"Visualizza un menu tematico",
			"Aggiungi giorno di validita' a un menu tematico"
	};

	public GestoreRistorante(String nome) {
		super(nome, etichettaG, voci);
		this.gestore = new Gestore();
		this.visualizzatoreGestione = new VisualizzatoreGestione();
	}

	@Override
	public void eseguiMetodi(int scelta, String pathCompletoFileRistorante) {
		switch (scelta) {
		case 1:
			Gestore.inizializzaRistorante(pathCompletoFileRistorante);
			break;
		case 2:
			visualizzatoreGestione.visualizzaRistorante(pathCompletoFileRistorante);
			break;
		case 3:
			gestore.aggiungiBevanda(pathCompletoFileRistorante);
			break;
		case 4:
			gestore.rimuoviBevanda(pathCompletoFileRistorante);
			break;
		case 5:
			visualizzatoreGestione.visualizzaInsiemeBevande(pathCompletoFileRistorante);
			break;
		case 6: 
			gestore.aggiungiGenereExtra(pathCompletoFileRistorante);
			break;
		case 7: 
			gestore.rimuoviGenereExtra(pathCompletoFileRistorante);
			break;
		case 8: 
			visualizzatoreGestione.visualizzaInsiemeGeneriExtra(pathCompletoFileRistorante);
			break;
		case 9: 
			gestore.creaRicetta(pathCompletoFileRistorante);
			break;
		case 10:
			visualizzatoreGestione.visualizzaNomiRicettario(pathCompletoFileRistorante);
			break;
		case 11: 
			visualizzatoreGestione.visualizzaRicetta(pathCompletoFileRistorante);
			break;
		case 12:
			visualizzatoreGestione.visualizzaInfoRicette(pathCompletoFileRistorante);
			break;
		case 13:
			gestore.corrispondenzaPiattoRicetta(pathCompletoFileRistorante); //aggiungere piatto al menu alla carta di quel giorno
			break;
		case 14:
			visualizzatoreGestione.visualizzaPiatti(pathCompletoFileRistorante);
			break;
		case 15:
			gestore.verificaCorrispondenzaPiattoRicetta(pathCompletoFileRistorante);
			break;
		case 16:
			gestore.creaMenuTematico(visualizzatoreGestione, pathCompletoFileRistorante); 
			break;
		case 17:
			visualizzatoreGestione.visualizzaNomiMenuTematici(gestore, pathCompletoFileRistorante);
			break;
		case 18:
			visualizzatoreGestione.visualizzaInfoMenuTematici(gestore, pathCompletoFileRistorante);
			break;
		case 19:
			visualizzatoreGestione.visualizzaMenuTematico(gestore, pathCompletoFileRistorante); 
			break;
		case 20:
			gestore.aggiuntaPeriodoValiditaMenuTematico(pathCompletoFileRistorante);
			break;
		}
	}

}
