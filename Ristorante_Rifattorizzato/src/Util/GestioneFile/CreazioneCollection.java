package Util.GestioneFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import Prenotazioni.Prenotazione;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreMenuTematico;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratorePiatto;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratorePrenotazione;

public class CreazioneCollection {

	public static HashSet<Piatto> creaMenuCarta(String pathDirectoryMenuCarta) {
		List<File> menuCarta = ServizioFile.getElencoFileTxt(pathDirectoryMenuCarta);
		HashSet<Piatto> piattiMenuCarta = new HashSet<>();
		ConfiguratorePiatto confPiatto = new ConfiguratorePiatto();
		for (File fileMenuCarta : menuCarta) {
			Piatto piatto = (Piatto) confPiatto.caricaIstanzaOggettoDaFile(fileMenuCarta.getPath());
			piattiMenuCarta.add(piatto);
		}
		return piattiMenuCarta;
	}

	public static HashSet<MenuTematico> creaMenuTematici(String pathDirectoryMenuTematici) {
		List<File> menuTematici = ServizioFile.getElencoFileTxt(pathDirectoryMenuTematici);
		HashSet<MenuTematico> elencoMenuTematici = new HashSet<>();
		ConfiguratoreMenuTematico confMT = new ConfiguratoreMenuTematico();
		for (File fileMenuTematici : menuTematici) {
			MenuTematico menuTematico = (MenuTematico) confMT.caricaIstanzaOggettoDaFile(fileMenuTematici.getAbsolutePath());
			elencoMenuTematici.add(menuTematico);
		}
		return elencoMenuTematici;
	}

	public static HashSet<Prenotazione> creaPrenotazioni(String pathDirectoryPrenotazioni){
		List<File> prenotazioni = ServizioFile.getElencoFileTxt(pathDirectoryPrenotazioni);
		HashSet<Prenotazione> elencoPrenotazioni = new HashSet<>();
		ConfiguratorePrenotazione confPren = new ConfiguratorePrenotazione();
		for (File filePren : prenotazioni) {
			Prenotazione pren = (Prenotazione) confPren.caricaIstanzaOggettoDaFile(filePren.getPath());
			elencoPrenotazioni.add(pren);
		}
		return elencoPrenotazioni;
	}
}
