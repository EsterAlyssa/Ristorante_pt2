package Util.GestioneFile;

import Giorno.Giorno;
import Giorno.GiornoView.GiornoView;

public class CreazioneDirectory {

	public static String creaDirectory(String pathCompletoFileRistorante, String nomeNuovaDirectory) {
		String pathDirectoryRistorante = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		return creaSubDirectory(pathDirectoryRistorante, nomeNuovaDirectory);
	}

	public static String creaDirectoryCalendario(String pathCompletoFileRistorante) {
		String nomeDirectory = "Calendario";
		return creaDirectory(pathCompletoFileRistorante, nomeDirectory);
	}

	public static String creaDirectoryInsiemiExtra(String pathCompletoFileRistorante) {
		String nomeDirectory = "Insiemi Extra";
		return creaDirectory(pathCompletoFileRistorante, nomeDirectory);
	}

	public static String creaDirectoryRegistroMagazzino(String pathCompletoFileRistorante) {
		String nomeDirectory = "Registro Magazzino";
		return creaDirectory(pathCompletoFileRistorante, nomeDirectory);
	}

	public static String creaDirectoryRicettario (String pathCompletoFileRistorante) {
		String nomeDirectory = "Ricettario";
		return creaDirectory(pathCompletoFileRistorante, nomeDirectory);
	}

	public static String creaDirectoryPiatti (String pathCompletoFileRistorante) {
		String nomeDirectory = "Piatti";
		return creaDirectory(pathCompletoFileRistorante, nomeDirectory);
	}

	public static String creaDirectoryMenuTematici (String pathCompletoFileRistorante) {
		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		return creaSubDirectoryMenuTematici(pathDirectory);
	}

	public static String creaSubDirectory(String pathDirectory, String nomeNuovaDirectory) {
		String pathNuovaDirectory = pathDirectory + "/" + nomeNuovaDirectory;
		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathNuovaDirectory);
		return pathNuovaDirectory;
	}

	public static String creaDirectoryGiornata(Giorno giorno, String pathDirectoryCalendario) {
		GiornoView giornoView = new GiornoView (giorno.getGiorno());
		String nomeDirectoryGiornata = giornoView.descrizioneGiorno();
		return creaSubDirectory(pathDirectoryCalendario, nomeDirectoryGiornata);
	}

	public static String creaSubDirectoryMenuTematici(String pathDirectoryGiornata) {
		String nomeDirectoryMenuTematici = "Menu tematici";
		return creaSubDirectory(pathDirectoryGiornata, nomeDirectoryMenuTematici);
	}

	public static String creaSubDirectoryMenuCarta(String pathDirectoryGiornata) {
		String nomeDirectoryMenuCarta = "Menu alla carta";
		return creaSubDirectory(pathDirectoryGiornata, nomeDirectoryMenuCarta);
	}

	public static String creaSubDirectoryPrenotazioni(String pathDirectoryGiornata) {
		String nomeDirectoryPrenotazioni = "Prenotazioni";
		return creaSubDirectory(pathDirectoryGiornata, nomeDirectoryPrenotazioni);
	}

	public static String creaSubDirectoryDaComprare(String pathDirectoryGiornata) {
		String nomeDirectoryDaComprare = "Da comprare";
		return creaSubDirectory(pathDirectoryGiornata, nomeDirectoryDaComprare);
	}


}
