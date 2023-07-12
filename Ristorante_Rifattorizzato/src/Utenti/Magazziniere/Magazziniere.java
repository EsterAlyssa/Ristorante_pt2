package Utenti.Magazziniere;

import Giorno.Giorno;
import Utenti.Utente;
import Util.GestioneFile.CreazioneDirectory;
import Util.GestioneFile.CreazioneFile;

public class Magazziniere extends Utente {

	private static String etichettaM = "magazziniere";
	private GestoreMagazzino gestoreMagazzino;
	private VisualizzatoreMagazzino visualizzatoreMagazzino;
	private static String[] voci = {
			"Aggiungi al magazzino i prodotti acquistati", 
			"Preleva dal magazzino gli ingredienti da portare in cucina",
			"Preleva bevande e generi extra da portare in sala", 
			"Aggiungi al magazzino le merci inutilizzate", 
			"Elimina dal magazzino gli scarti",
			"Visualizza registro magazzino",
			"Genera lista della spesa per il giorno corrente",
			"Visualizza la lista della spesa per il giorno corrente"
	};

	public Magazziniere(String nome) {
		super(nome, etichettaM, voci);
		this.gestoreMagazzino = new GestoreMagazzino();
		this.visualizzatoreMagazzino = new VisualizzatoreMagazzino();
	}

	@Override
	public void eseguiMetodi(int scelta, String pathCompletoFileRistorante) {
		String pathDirectoryRegistroMagazzino = CreazioneDirectory.creaDirectoryRegistroMagazzino(pathCompletoFileRistorante);
		String pathFileRegistroMagazzino = CreazioneFile.creaFileRegistroMagazzino(pathDirectoryRegistroMagazzino);

		Giorno giornoCorrente = Giorno.ritornaGiornoCorrente();

		switch(scelta) {
		case 1: 
			gestoreMagazzino.aggiuntaProdottiAcquistati(pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		case 2:
			gestoreMagazzino.prelievoIngredientiPerCucina(giornoCorrente, pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		case 3:
			gestoreMagazzino.prelievoExtraPerTavoli(giornoCorrente, pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		case 4:
			gestoreMagazzino.aggiuntaMerciInutilizzati(pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		case 5:
			gestoreMagazzino.eliminazioneScarti(giornoCorrente, pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		case 6:
			visualizzatoreMagazzino.visualizzaRegistroMagazzino(pathFileRegistroMagazzino);
			break;
		case 7:
			gestoreMagazzino.generaListaSpesa(giornoCorrente, pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		case 8: 
			visualizzatoreMagazzino.visualizzaListaSpesa(pathCompletoFileRistorante, giornoCorrente);
			break;
		}
	}




}
