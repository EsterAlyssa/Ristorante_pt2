package Util.GestioneFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import Prenotazioni.Prenotazione;
import Ristorante.Giornata;
import Ristorante.ElementiRistorante.InsiemeExtra;
import Ristorante.ElementiRistorante.MenuCarta;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;
import Magazzino.ListaSpesa;
import Magazzino.RegistroMagazzino;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreMenuTematico;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratorePiatto;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratorePrenotazione;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRicetta;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreExtra;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreListaSpesa;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRegistroMagazzino;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreManager;

public class CreazioneOggetti {

	public static <T> HashSet<T> creaCollection(String pathDirectory, ConfiguratoreManager<T> conf) {
		HashSet<T> elencoOggetti = new HashSet<>();
		List<File> elencoFileOggetti = ServizioFile.getElencoFileTxt(pathDirectory);
		for (File fileOggetto : elencoFileOggetti) {
			T oggetto = conf.caricaIstanzaOggettoDaFile(fileOggetto.getPath());
			elencoOggetti.add(oggetto);
		}
		return elencoOggetti;
	}

	public static HashSet<Ricetta> creaRicettario(String pathDirectoryRicettario){
		ConfiguratoreManager<Ricetta> confRic = new ConfiguratoreRicetta();
		return creaCollection(pathDirectoryRicettario, confRic);
	}

	public static HashSet<MenuTematico> creaMenuTematici(String pathDirectoryMenuTematici) {
		ConfiguratoreManager<MenuTematico> confMT = new ConfiguratoreMenuTematico();
		return creaCollection(pathDirectoryMenuTematici, confMT);
	}

	public static HashSet<Prenotazione> creaPrenotazioni(String pathDirectoryPrenotazioni){
		ConfiguratoreManager<Prenotazione> confPren = new ConfiguratorePrenotazione();
		return creaCollection(pathDirectoryPrenotazioni, confPren);
	}
	
	public static HashSet<Piatto> creaPiatti(String pathDirectoryPiatti){
		ConfiguratoreManager<Piatto> confPiatto = new ConfiguratorePiatto();
		return creaCollection(pathDirectoryPiatti, confPiatto);
	}

	public static MenuCarta creaMenuCarta(String pathDirectoryMenuCarta) {
		HashSet<Piatto> elencoPiatti = creaPiatti(pathDirectoryMenuCarta);
		return new MenuCarta(elencoPiatti);
	}
	
	public static ListaSpesa creaListaSpesa(String pathDirectoryDaComprare) {
		File fileLS = ServizioFile.trovaPrimoFileTxt(pathDirectoryDaComprare);
		ListaSpesa listaSpesa = new ListaSpesa();
		ConfiguratoreManager<ListaSpesa> confLS = new ConfiguratoreListaSpesa();
		listaSpesa = confLS.caricaIstanzaOggettoDaFile(fileLS.getPath());

		return listaSpesa;
	}
	
	public static InsiemeExtra creaInsiemeExtra(String pathFileInsiemeExtra) {
		File fileInsiemeExtra = ServizioFile.creaFile(pathFileInsiemeExtra);
		InsiemeExtra insiemeExtra = new InsiemeExtra();
		ConfiguratoreManager<InsiemeExtra> confInsEx = new ConfiguratoreExtra();
		insiemeExtra = confInsEx.caricaIstanzaOggettoDaFile(fileInsiemeExtra.getPath());
		
		return insiemeExtra;
	}

	public static TreeSet<Giornata> creaCalendarioVuoto(List<File> elencoDirGiornate) {
		TreeSet<Giornata> calendarioNoParam = new TreeSet<>();
		for (File file : elencoDirGiornate) {
			Giornata giornata = new Giornata(file.getName());
			calendarioNoParam.add(giornata);
		}
		return calendarioNoParam;
	}
	
	public static RegistroMagazzino creaRegistroMagazzino(String pathFileRegistroMagazzino) {
		File fileRegMag = ServizioFile.trovaPrimoFileTxt(pathFileRegistroMagazzino);
		RegistroMagazzino registroMagazzino = new RegistroMagazzino();
		ConfiguratoreManager<RegistroMagazzino> confRegMag = new ConfiguratoreRegistroMagazzino();
		registroMagazzino = confRegMag.caricaIstanzaOggettoDaFile(fileRegMag.getPath());
		
		return registroMagazzino;	
	}
	
}
