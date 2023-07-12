package Util.GestioneFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Prenotazioni.Prenotazione;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;
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

	public static String creaFileListaSpesa(String pathDirectoryDaComprare) {
		String nomeFileListaSpesa = "lista della spesa.txt";
		String pathFileListaSpesa = pathDirectoryDaComprare + "/" + nomeFileListaSpesa;

		// Controlla se il file esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileListaSpesa)) {
			ServizioFile.creaFile(pathFileListaSpesa);
		}
		return pathFileListaSpesa;
	}

	public static String creaFileInsiemeBevande(String pathDirectoryInsiemiExtra) {
		String nomeFileInsiemeBevande = "insieme bevande.txt";
		String pathFileInsiemeBevande = pathDirectoryInsiemiExtra + "/" + nomeFileInsiemeBevande;

		// Controlla se il file esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileInsiemeBevande)) {
			ServizioFile.creaFile(pathFileInsiemeBevande);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileInsiemeBevande));
				writer.write("Insieme Extra=");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				System.out.println("Errore inizializzazione file");
				e.printStackTrace();
			}
		}
		return pathFileInsiemeBevande;
	}

	public static String creaFileInsiemeGeneriExtra(String pathDirectoryInsiemiExtra) {
		String nomeFileInsiemeGeneriExtra = "insieme generi extra.txt";
		String pathFileInsiemeGeneriExtra = pathDirectoryInsiemiExtra + "/" + nomeFileInsiemeGeneriExtra;

		// Controlla se il file esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileInsiemeGeneriExtra)) {
			ServizioFile.creaFile(pathFileInsiemeGeneriExtra);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileInsiemeGeneriExtra));
				writer.write("Insieme Extra=");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				System.out.println("Errore inizializzazione file");
				e.printStackTrace();
			}
		}
		return pathFileInsiemeGeneriExtra;
	}

	public static String creaFileRicetta(String pathDirectoryRicettario, String nomeRicetta) {
		String nomeFileRicetta = nomeRicetta + ".txt";
		String pathFileRicetta = pathDirectoryRicettario + "/" + nomeFileRicetta;

		// Controlla se il file nomeRicetta + ".txt" esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileRicetta)) {
			ServizioFile.creaFile(pathFileRicetta);
		}
		return pathFileRicetta;
	}

	public static String trovaFilePiatto(String pathDirectoryPiatti, Ricetta ricetta) {
		String nomeFilePiatto = ricetta.getNome() + ".txt";
		String pathFilePiatto = pathDirectoryPiatti + "/" + nomeFilePiatto;

		return pathFilePiatto;
	}

	public static String creaFilePiatto(Piatto piatto, String nomeDirectoryMenuCarta) {
		String nomePiattoMenuCarta = piatto.getNome() + ".txt";
		String pathPiattoMenuCarta = nomeDirectoryMenuCarta + "/" + nomePiattoMenuCarta;
	
		// Controlla se il file esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathPiattoMenuCarta)) {
			ServizioFile.creaFile(pathPiattoMenuCarta);
		}
		return pathPiattoMenuCarta;
	}

	public static String creaFileMenuTematico(String pathDirectoryMenuTematici, String nomeMenuT) {
		String nomeFileMenuTematico = nomeMenuT + ".txt";
		String pathFileMenuTematico = pathDirectoryMenuTematici + "/" + nomeFileMenuTematico;
	
		// Controlla se il file nomeMenuT + ".txt" esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileMenuTematico)) {
			ServizioFile.creaFile(pathFileMenuTematico);
		}
		return pathFileMenuTematico;
	}

}
