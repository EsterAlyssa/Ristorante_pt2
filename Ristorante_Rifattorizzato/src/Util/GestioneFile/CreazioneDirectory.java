package Util.GestioneFile;

import Giorno.*;
import Giorno.GiornoView.*;

public class CreazioneDirectory {
	
	public static String creaDirectoryCalendario(String pathCompletoFileRistorante) {
		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Calendario";
		String pathCalendario = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory "Calendario" esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathCalendario);
		return pathCalendario;
	}

	public static String creaDirectoryRegistroMagazzino(String pathCompletoFileRistorante) {
		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Registro Magazzino";
		String pathRegistroMagazzino = pathDirectory + "/" + nomeDirectory;
		// Controlla se la directory "Registro Magazzino" esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathRegistroMagazzino);
		
		return pathRegistroMagazzino;
	}
	
	public static String creaDirectoryGiornata(Giorno dataPrenotazione, String pathCalendario) {
		GiornoView giornoView = new GiornoView (dataPrenotazione.getGiorno());
		String nomeDirectoryGiornata = giornoView.descrizioneGiorno();
		String pathGiornata = pathCalendario + "/" + nomeDirectoryGiornata;
		ServizioFile.creaDirectory(pathGiornata);
		return pathGiornata;
	}

	public static String creaSubDirectoryMenuTematici(String pathGiornata) {
		String nomeDirectoryMenuTematici = "Menu Tematici";
		String pathDirectoryMenuTematici = pathGiornata + "/" + nomeDirectoryMenuTematici;
		ServizioFile.creaDirectory(pathDirectoryMenuTematici);
		return pathDirectoryMenuTematici;
	}

	public static String creaSubDirectoryMenuCarta(String pathGiornata) {
		String nomeDirectoryMenuCarta = "Menu alla carta";
		String pathDirectoryMenuCarta = pathGiornata + "/" + nomeDirectoryMenuCarta;
		ServizioFile.creaDirectory(pathDirectoryMenuCarta);
		return pathDirectoryMenuCarta;
	}

	public static String creaSubDirectoryPrenotazioni(String pathGiornata) {
		String nomeDirectoryPrenotazioni = "Prenotazioni";
		String pathDirectoryPrenotazioni = pathGiornata + "/" + nomeDirectoryPrenotazioni;
		ServizioFile.creaDirectory(pathDirectoryPrenotazioni);
		return pathDirectoryPrenotazioni;
	}

	
}
