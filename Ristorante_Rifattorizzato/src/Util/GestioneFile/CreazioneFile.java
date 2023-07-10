package Util.GestioneFile;

import Prenotazioni.Prenotazione;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratorePrenotazione;

public class CreazioneFile {
	
	public static String creaFilePrenotazione(Prenotazione prenotazione, String pathDirectoryPrenotazioni) {
		String nomePrenotazione = prenotazione.getCliente()+"_"+prenotazione.getNumCoperti() + ".txt";
		String pathPrenotazione = pathDirectoryPrenotazioni + "/" + nomePrenotazione;

		// Controlla se il file esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathPrenotazione)) {
			ServizioFile.creaFile(pathPrenotazione);
		}

		//salva il file della prenotazione nella cartella delle prenotazioni del giorno per cui si prenota
		ConfiguratorePrenotazione confPren = new ConfiguratorePrenotazione();
		confPren.salvaIstanzaOggetto(prenotazione, pathPrenotazione);
		
		return pathPrenotazione;
	}
	
	public static String creaFileRegistroMagazzino(String pathRegistroMagazzino) {
		String nomeFileRegistroMagazzino = "registro magazzino.txt";
		String pathFileRegistroMagazzino = pathRegistroMagazzino + "/" + nomeFileRegistroMagazzino;

		// Controlla se il file esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileRegistroMagazzino)) {
			ServizioFile.creaFile(pathFileRegistroMagazzino);
		}
		return pathFileRegistroMagazzino;
	}
}
