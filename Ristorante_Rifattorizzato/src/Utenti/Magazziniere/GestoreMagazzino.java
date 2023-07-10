package Utenti.Magazziniere;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import Giorno.Giorno;
import Giorno.Periodo;
import Giorno.GiornoView.GiornoView;
import Magazzino.ElementoMagazzino;
import Magazzino.ListaSpesa;
import Magazzino.RegistroMagazzino;
import Magazzino.MagazzinoView.RegistroMagazzinoView;
import Magazzino.Merce.Merce;
import Magazzino.Merce.MerceView.MerceView;
import Prenotazioni.Prenotazione;
import Ristorante.Giornata;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.InsiemeExtra;
import Ristorante.ElementiRistorante.MenuCarta;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;
import Util.InputDati;
import Util.GestioneFile.ServizioFile;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreExtra;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreListaSpesa;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreManager;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreMenuTematico;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratorePiatto;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratorePrenotazione;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRegistroMagazzino;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRicetta;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRistorante;

public class GestoreMagazzino {

	RegistroMagazzinoView registroMagazzinoView;
	ConfiguratoreManager confRist;
	ConfiguratoreManager confRegMag;

	public GestoreMagazzino() {
		this.registroMagazzinoView = new RegistroMagazzinoView();
		this.confRist = new ConfiguratoreRistorante(); 
		this.confRegMag = new ConfiguratoreRegistroMagazzino();
	}

	public void aggiuntaProdottiAcquistati(String pathCompletoFileRistorante, String pathFileRegistroMagazzino) {
		Ristorante ristorante = (Ristorante) confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		HashSet<ElementoMagazzino> comprati = registroMagazzinoView.elementiMagazzinoComprati();

		RegistroMagazzino registro = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		registro.acquistatiI(comprati);
		ristorante.setRegistroMagazzino(registro);

		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public void prelievoIngredientiPerCucina(Giorno giornoCorrente, String pathCompletoFileRistorante, String pathFileRegistroMagazzino) {

		Ristorante ristorante = (Ristorante) confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Calendario";
		String pathDirectoryCalendario = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryCalendario);

		GiornoView giornoView = new GiornoView (giornoCorrente.getGiorno());
		String nomeDirectoryGiornata = giornoView.descrizioneGiorno();
		String pathDirectoryGiornata = pathDirectoryCalendario + "/" + nomeDirectoryGiornata;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryGiornata);

		String nomeDirectoryDaComprare = "Da comprare";
		String pathDirectoryDaComprare = pathDirectoryGiornata + "/" + nomeDirectoryDaComprare;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryDaComprare);

		String nomeFileListaSpesa = "lista della spesa.txt";
		String pathFileListaSpesa = pathDirectoryDaComprare + "/" + nomeFileListaSpesa;

		// Controlla se il file esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileListaSpesa)) {
			ServizioFile.creaFile(pathFileListaSpesa);
		}

		//Giornata corrente con inizializzato solo il giorno
		Giornata giornataCorrente = new Giornata(giornoView.descrizioneGiorno());

		HashSet<Prenotazione> prenotazioni = new HashSet<>();
		HashSet<Piatto> menuCarta = new HashSet<>();
		HashSet<MenuTematico> menuTematici = new HashSet<>();
		ListaSpesa listaSpesa = new ListaSpesa();

		//inizializzazione giornata da file
		List<File> elencoDir1Giornata = ServizioFile.getElencoDirectory(pathDirectoryGiornata);
		for (File f : elencoDir1Giornata) {
			String nomeCartella = f.getName();
			switch (nomeCartella) {
			case "Prenotazioni":
				ConfiguratorePrenotazione confPren = new ConfiguratorePrenotazione();
				for (File filePren : ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella)) {
					Prenotazione pren = (Prenotazione) confPren.caricaIstanzaOggettoDaFile(filePren.getAbsolutePath());
					prenotazioni.add(pren);
				}
				giornataCorrente.setPrenotazioni(prenotazioni); 

				break;
			case "Menu alla carta":
				ConfiguratorePiatto confPiatto = new ConfiguratorePiatto();
				for (File fileMC : ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella)) {
					Piatto piatto = (Piatto) confPiatto.caricaIstanzaOggettoDaFile(fileMC.getAbsolutePath());
					menuCarta.add(piatto);
				}

				Periodo periodoMenuCarta = new Periodo(Giorno.parseGiorno(nomeCartella));
				MenuCarta menu = new MenuCarta(periodoMenuCarta);
				menu.setElenco(menuCarta);

				giornataCorrente.setMenuCarta(menu); 
				break;
			case "Menu Tematici":
				ConfiguratoreMenuTematico confMT = new ConfiguratoreMenuTematico();
				for (File fileMT : ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella)) {
					MenuTematico menuT = (MenuTematico) confMT.caricaIstanzaOggettoDaFile(fileMT.getAbsolutePath());
					menuTematici.add(menuT);
				}

				giornataCorrente.setMenuTematici(menuTematici); 
				break;
			case "Da comprare":
				ConfiguratoreListaSpesa confLS = new ConfiguratoreListaSpesa();
				File fileLS = (File) ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella);
				ListaSpesa listaS = (ListaSpesa) confLS.caricaIstanzaOggettoDaFile(fileLS.getAbsolutePath());
				listaSpesa = listaS;

				giornataCorrente.setDaComprare(listaSpesa);
			}
		} //fine inizializzazione Giornata da file

		RegistroMagazzino registro = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		ristorante.getRegistroMagazzino().inCucinaO(giornataCorrente);

		ristorante.setRegistroMagazzino(registro);

		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public void prelievoExtraPerTavoli(Giorno giornoCorrente, String pathCompletoFileRistorante, String pathFileRegistroMagazzino) {

		Ristorante ristorante = (Ristorante) confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Calendario";
		String pathDirectoryCalendario = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryCalendario);

		GiornoView giornoView = new GiornoView (giornoCorrente.getGiorno());
		String nomeDirectoryGiornata = giornoView.descrizioneGiorno();
		String pathDirectoryGiornata = pathDirectoryCalendario + "/" + nomeDirectoryGiornata;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryGiornata);

		//Giornata corrente con inizializzato solo il giorno
		Giornata giornataCorrente = new Giornata(giornoView.descrizioneGiorno());

		HashSet<Prenotazione> prenotazioni = new HashSet<>();

		//inizializzazione giornata da file
		List<File> elencoDir1Giornata = ServizioFile.getElencoDirectory(pathDirectoryGiornata);
		for (File f : elencoDir1Giornata) {
			String nomeCartella = f.getName();
			switch (nomeCartella) {
			case "Prenotazioni":
				ConfiguratorePrenotazione confPren = new ConfiguratorePrenotazione();
				for (File filePren : ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella)) {
					Prenotazione pren = (Prenotazione) confPren.caricaIstanzaOggettoDaFile(filePren.getAbsolutePath());
					prenotazioni.add(pren);
				}
				giornataCorrente.setPrenotazioni(prenotazioni); 

				break;
			}
		} //fine inizializzazione Giornata (solo prenotazioni) da file

		String nomeDirectoryInsiemiExtra = "Insiemi extra";
		String pathDirectoryInsiemiExtra = pathDirectory + "/" + nomeDirectoryInsiemiExtra;
		String nomeFileBevande = "insieme bevande.txt";
		String pathFileBevande = pathDirectoryInsiemiExtra + "/" + nomeFileBevande;
		String nomeFileGeneriExtra = "insieme generi extra.txt";
		String pathFileGeneriExtra = pathDirectoryInsiemiExtra + "/" + nomeFileGeneriExtra;

		ConfiguratoreExtra confIns = new ConfiguratoreExtra();
		InsiemeExtra insiemeB = (InsiemeExtra) confIns.caricaIstanzaOggettoDaFile(pathFileBevande);
		ristorante.getInsiemeB().setInsiemeExtra(insiemeB.getInsiemeExtra());

		InsiemeExtra insiemeGE = (InsiemeExtra) confIns.caricaIstanzaOggettoDaFile(pathFileGeneriExtra);
		ristorante.getInsiemeGE().setInsiemeExtra(insiemeGE.getInsiemeExtra());

		RegistroMagazzino registro = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		registro.extraO(ristorante, giornataCorrente);

		ristorante.setRegistroMagazzino(registro);
		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public void aggiuntaMerciInutilizzati(String pathCompletoFileRistorante, String pathFileRegistroMagazzino) {
		Ristorante ristorante = (Ristorante) confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		HashMap<Merce, Double> avanzi = new HashMap<>();

		String messaggioQuantita = "Inserisci quante merci hanno queste caratteristiche: ";
		String messaggioAltreMerci = "Vuoi aggiungere altre merci? ";

		boolean scelta = false;
		do {
			Merce merce = MerceView.creaMerce();
			double quantita = InputDati.leggiDoubleConMinimo(messaggioQuantita, 0.0);

			avanzi.put(merce, quantita);

			scelta = InputDati.yesOrNo(messaggioAltreMerci);
		} while (scelta);

		RegistroMagazzino registro = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		registro.avanziI(avanzi);
		ristorante.setRegistroMagazzino(registro);
		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public void eliminazioneScarti(Giorno giornoCorrente, String pathCompletoFileRistorante, String pathFileRegistroMagazzino) {

		Ristorante ristorante = (Ristorante) confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		GiornoView giornoView = new GiornoView (giornoCorrente.getGiorno());
		Giornata giornataCorrente = new Giornata(giornoView.descrizioneGiorno());

		Merce merceNonDiQualita = dichiarazioneMerceDeteriorata();

		RegistroMagazzino registro = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		registro.setFalseQualitaMerce(merceNonDiQualita);
		registro.scartiO(giornataCorrente);
		ristorante.setRegistroMagazzino(registro);
		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	private Merce dichiarazioneMerceDeteriorata() {
		String messaggioDichiarazione = "Definisci la merce deteriorata: ";

		System.out.println(messaggioDichiarazione);
		return MerceView.creaMerce();
	}

	public void generaListaSpesa(Giorno giornoCorrente, String pathCompletoFileRistorante, String pathFileRegistroMagazzino) {

		Ristorante ristorante = (Ristorante) confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		RegistroMagazzino registroMagazzino = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Calendario";
		String pathDirectoryCalendario = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryCalendario);

		GiornoView giornoView = new GiornoView (giornoCorrente.getGiorno());
		String nomeDirectoryGiornata = giornoView.descrizioneGiorno();
		String pathDirectoryGiornata = pathDirectoryCalendario + "/" + nomeDirectoryGiornata;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryGiornata);

		String nomeDirectoryDaComprare = "Da comprare";
		String pathDirectoryDaComprare = pathDirectoryGiornata + "/" + nomeDirectoryDaComprare;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryDaComprare);

		String nomeFileListaSpesa = "lista della spesa.txt";
		String pathFileListaSpesa = pathDirectoryDaComprare + "/" + nomeFileListaSpesa;

		// Controlla se il file esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileListaSpesa)) {
			ServizioFile.creaFile(pathFileListaSpesa);
		}

		//Giornata corrente con inizializzato solo il giorno
		Giornata giornataCorrente = new Giornata(giornoView.descrizioneGiorno());

		HashSet<Prenotazione> prenotazioni = new HashSet<>();
		HashSet<Piatto> menuCarta = new HashSet<>();
		HashSet<MenuTematico> menuTematici = new HashSet<>();
		ListaSpesa listaSpesa = new ListaSpesa();

		//inizializzazione giornata da file
		List<File> elencoDir1Giornata = ServizioFile.getElencoDirectory(pathDirectoryGiornata);
		for (File f : elencoDir1Giornata) {
			String nomeCartella = f.getName();
			switch (nomeCartella) {
			case "Prenotazioni":
				ConfiguratorePrenotazione confPren = new ConfiguratorePrenotazione();
				for (File filePren : ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella)) {
					Prenotazione pren = (Prenotazione) confPren.caricaIstanzaOggettoDaFile(filePren.getAbsolutePath());
					prenotazioni.add(pren);
				}
				giornataCorrente.setPrenotazioni(prenotazioni); 

				break;
			case "Menu alla carta":
				ConfiguratorePiatto confPiatto = new ConfiguratorePiatto();
				for (File fileMC : ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella)) {
					Piatto piatto = (Piatto) confPiatto.caricaIstanzaOggettoDaFile(fileMC.getAbsolutePath());
					menuCarta.add(piatto);
				}

				Periodo periodoMenuCarta = new Periodo(Giorno.parseGiorno(nomeCartella));
				MenuCarta menu = new MenuCarta(periodoMenuCarta);
				menu.setElenco(menuCarta);

				giornataCorrente.setMenuCarta(menu); 
				break;
			case "Menu Tematici":
				ConfiguratoreMenuTematico confMT = new ConfiguratoreMenuTematico();
				for (File fileMT : ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella)) {
					MenuTematico menuT = (MenuTematico) confMT.caricaIstanzaOggettoDaFile(fileMT.getAbsolutePath());
					menuTematici.add(menuT);
				}

				giornataCorrente.setMenuTematici(menuTematici); 
				break;
			case "Da comprare":
				ConfiguratoreListaSpesa confLS = new ConfiguratoreListaSpesa();
				File fileLS = ServizioFile.trovaPrimoFileTxt(f.getAbsolutePath()+"/"+nomeCartella);
				try {
					ListaSpesa listaS = (ListaSpesa) confLS.caricaIstanzaOggettoDaFile(fileLS.getAbsolutePath());
					listaSpesa = listaS;
					giornataCorrente.setDaComprare(listaSpesa);
				} catch (NullPointerException e) {
					System.out.println("Non ci sono prenotazioni per il giorno seguente");
				}
			}
		} //fine inizializzazione Giornata da file

		//settaggio insiemi bevande, generi extra, ricettario di ristorante
		String nomeDirectoryInsiemiExtra = "Insiemi extra";
		String pathDirectoryInsiemiExtra = pathDirectory + "/" + nomeDirectoryInsiemiExtra;
		String nomeFileBevande = "insieme bevande.txt";
		String pathFileBevande = pathDirectoryInsiemiExtra + "/" + nomeFileBevande;
		String nomeFileGeneriExtra = "insieme generi extra.txt";
		String pathFileGeneriExtra = pathDirectoryInsiemiExtra + "/" + nomeFileGeneriExtra;

		ConfiguratoreExtra confIns = new ConfiguratoreExtra();
		InsiemeExtra insiemeB = (InsiemeExtra) confIns.caricaIstanzaOggettoDaFile(pathFileBevande);
		ristorante.getInsiemeB().setInsiemeExtra(insiemeB.getInsiemeExtra());

		InsiemeExtra insiemeGE = ((InsiemeExtra) confIns.caricaIstanzaOggettoDaFile(pathFileGeneriExtra));
		ristorante.getInsiemeGE().setInsiemeExtra(insiemeGE.getInsiemeExtra());

		String nomeDirectoryRicettario = "Ricettario";
		String pathRicettario = pathDirectory + "/" + nomeDirectoryRicettario;

		ConfiguratoreRicetta confRic = new ConfiguratoreRicetta();

		List<File> elencoRicette = ServizioFile.getElencoFileTxt(pathRicettario);
		for (File file : elencoRicette) {
			Ricetta ricetta = (Ricetta) confRic.caricaIstanzaOggettoDaFile(file.getAbsolutePath());
			ristorante.aggiungiRicetta(ricetta);
		}

		giornataCorrente.creaListaSpesaIniziale(ristorante);

		ListaSpesa lista = giornataCorrente.getDaComprare(); 
		for (String nome : lista.getLista().keySet()) {
			if (registroMagazzino.getRegistro().containsKey(nome)) {
				double effettivoDaComprare = (lista.getLista().get(nome)*1.1) - (registroMagazzino.ritornaQuantitaDatoNome(nome));
				lista.getLista().put(nome, effettivoDaComprare);
			} else {
				lista.getLista().put(nome, lista.getLista().get(nome) * 1.1);
			}
		}
	}

}
