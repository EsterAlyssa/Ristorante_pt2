package Utenti;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import Giorno.Giorno;
import Giorno.Periodo;
import Magazzino.ElementoMagazzino;
import Magazzino.ListaSpesa;
import Magazzino.RegistroMagazzino;
import Magazzino.Merce.Merce;
import Prenotazioni.Prenotazione;
import Ristorante.Giornata;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.MenuCarta;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;
import Util.InputDati;
import Util.ServizioFile;
import Util.ConfigurazioneFile.ConfiguratoreRistorante;
import Util.ConfigurazioneFile.ConfiguratoreMenuCarta;
import Util.ConfigurazioneFile.ConfiguratoreMenuTematico;
import Util.ConfigurazioneFile.ConfiguratorePrenotazione;
import Util.ConfigurazioneFile.ConfiguratoreRegistroMagazzino;
import Util.ConfigurazioneFile.ConfiguratoreRicetta;
import Util.ConfigurazioneFile.ConfiguratoreExtra;
import Util.ConfigurazioneFile.ConfiguratoreListaSpesa;

public class Magazziniere extends Utente {

	private static String etichettaM = "magazziniere";
	private static String[] voci = {"Aggiungi al magazzino i prodotti acquistati", 
			"Preleva dal magazzino gli ingredienti da portare in cucina",
			"Preleva bevande e generi extra da portare in sala", 
			"Aggiungi al magazzino le merci inutilizzate", 
			"Elimina dal magazzino gli scarti",
			"Genera lista della spesa per il prossimo giorno"};

	public Magazziniere(String nome) {
		super(nome, etichettaM, voci);
	}

	@Override
	public void eseguiMetodi(int scelta, String pathCompletoFileRistorante) {
		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Registro Magazzino";
		String pathRegistroMagazzino = pathDirectory + "/" + nomeDirectory;
		// Controlla se la directory "Registro Magazzino" esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathRegistroMagazzino);

		String nomeFileRegistroMagazzino = "registro magazzino.txt";
		String pathFileRegistroMagazzino = pathRegistroMagazzino + "/" + nomeFileRegistroMagazzino;

		// Controlla se il file esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileRegistroMagazzino)) {
			ServizioFile.creaFile(pathFileRegistroMagazzino);
		}

		Giorno giornoCorrente = Giorno.ritornaGiornoCorrente();

		switch(scelta) {
		case 1: 
			aggiuntaProdottiAcquistati(pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		case 2:
			prelievoIngredientiPerCucina(giornoCorrente, pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		case 3:
			prelievoExtraPerTavoli(giornoCorrente, pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		case 4:
			aggiuntaMerciInutilizzati(pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		case 5:
			eliminazioneScarti(giornoCorrente, pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		case 6:
			generaListaSpesa(giornoCorrente, pathCompletoFileRistorante, pathFileRegistroMagazzino);
			break;
		}
	}


	public void aggiuntaProdottiAcquistati(String pathCompletoFileRistorante, String pathFileRegistroMagazzino) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		HashSet<ElementoMagazzino> comprati = elementiMagazzinoComprati();

		ConfiguratoreRegistroMagazzino confRegMag = new ConfiguratoreRegistroMagazzino();
		RegistroMagazzino registro = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		registro.acquistatiI(comprati);
		ristorante.setRegistroMagazzino(registro);

		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public HashSet<ElementoMagazzino> elementiMagazzinoComprati(){
		HashSet<ElementoMagazzino> comprati = new HashSet<>();

		String messaggioQuantita = "Inserisci quante merci hanno queste caratteristiche: ";
		String messaggioAltreMerci = "Vuoi aggiungere altre merci? ";

		boolean scelta = false;
		do {
			Merce merce = creaMerce();
			double quantita = InputDati.leggiDoubleConMinimo(messaggioQuantita, 0.0);

			ElementoMagazzino elemento = new ElementoMagazzino(merce, quantita);
			comprati.add(elemento);

			scelta = InputDati.yesOrNo(messaggioAltreMerci);
		} while (scelta);

		return comprati;
	}

	public void prelievoIngredientiPerCucina(Giorno giornoCorrente, String pathCompletoFileRistorante, 
			String pathFileRegistroMagazzino) {

		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Calendario";
		String pathDirectoryCalendario = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryCalendario);

		String nomeDirectoryGiornata = giornoCorrente.toString();
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
		Giornata giornataCorrente = new Giornata(giornoCorrente.toString());

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
				ConfiguratoreMenuCarta confMC = new ConfiguratoreMenuCarta();
				for (File fileMC : ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella)) {
					Piatto piatto = (Piatto) confMC.caricaIstanzaOggettoDaFile(fileMC.getAbsolutePath());
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

		ConfiguratoreRegistroMagazzino confRegMag = new ConfiguratoreRegistroMagazzino();
		RegistroMagazzino registro = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		ristorante.getRegistroMagazzino().inCucinaO(giornataCorrente);

		ristorante.setRegistroMagazzino(registro);

		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public void prelievoExtraPerTavoli(Giorno giornoCorrente, String pathCompletoFileRistorante, 
			String pathFileRegistroMagazzino) {

		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Calendario";
		String pathDirectoryCalendario = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryCalendario);

		String nomeDirectoryGiornata = giornoCorrente.toString();
		String pathDirectoryGiornata = pathDirectoryCalendario + "/" + nomeDirectoryGiornata;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryGiornata);

		//Giornata corrente con inizializzato solo il giorno
		Giornata giornataCorrente = new Giornata(giornoCorrente.toString());

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
		HashMap<String, Double> insiemeB = ((HashMap<String, Double>) confIns.caricaIstanzaOggettoDaFile(pathFileBevande));
		ristorante.getInsiemeB().setInsiemeExtra(insiemeB);
		
		HashMap<String, Double> insiemeGE = ((HashMap<String, Double>) confIns.caricaIstanzaOggettoDaFile(pathFileGeneriExtra));
		ristorante.getInsiemeGE().setInsiemeExtra(insiemeGE);
		
		ConfiguratoreRegistroMagazzino confRegMag = new ConfiguratoreRegistroMagazzino();
		RegistroMagazzino registro = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		registro.extraO(ristorante, giornataCorrente);

		ristorante.setRegistroMagazzino(registro);
		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public void aggiuntaMerciInutilizzati(String pathCompletoFileRistorante, String pathFileRegistroMagazzino) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		HashMap<Merce, Double> avanzi = new HashMap<>();

		String messaggioQuantita = "Inserisci quante merci hanno queste caratteristiche: ";
		String messaggioAltreMerci = "Vuoi aggiungere altre merci? ";

		boolean scelta = false;
		do {
			Merce merce = creaMerce();
			double quantita = InputDati.leggiDoubleConMinimo(messaggioQuantita, 0.0);

			avanzi.put(merce, quantita);

			scelta = InputDati.yesOrNo(messaggioAltreMerci);
		} while (scelta);

		ConfiguratoreRegistroMagazzino confRegMag = new ConfiguratoreRegistroMagazzino();
		RegistroMagazzino registro = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		registro.avanziI(avanzi);
		ristorante.setRegistroMagazzino(registro);
		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	private Merce creaMerce() {
		String messaggioNome = "Inserisci il nome della merce: ";
		String messaggioUnitaMisura = "Inserisci l'unita' di misura della merce: ";
		String messaggioScadenza = "Inserisci la scadenza della merce: ";
		String messaggioTipo = "Inserire il tipo della merce [ingrediente/bevanda/genere extra]: ";
		String messaggioConsumoProCapite = "Inserire il consumo pro capite: ";
		String messaggioErrTipo = "ATTENZIONE! Il tipo inserito non è valido. Riprovare";

		String nomeMerce = InputDati.leggiStringaNonVuota(messaggioNome);
		String unitaMisura = InputDati.leggiStringaNonVuota(messaggioUnitaMisura);
		System.out.println(messaggioScadenza);
		Giorno scadenza = Giorno.richiestaCreaGiorno();

		String tipo = "";
		boolean controllo = false;
		do {
			tipo = InputDati.leggiStringaNonVuota(messaggioTipo);

			if (tipo != "ingrediente" || tipo != "bevanda" || tipo != "genere extra") {
				controllo = true;
				System.out.println(messaggioErrTipo);
			}
		} while (controllo);

		double consumoProCapite = 0.0;
		if (tipo == "bevanda" || tipo == "genere extra") {
			consumoProCapite = InputDati.leggiDoubleConMinimo(messaggioConsumoProCapite, 0.0);
		}

		return Merce.creaMerceDaTipo(nomeMerce, tipo, unitaMisura, scadenza, consumoProCapite);
	}

	public void eliminazioneScarti(Giorno giornoCorrente, String pathCompletoFileRistorante, 
			String pathFileRegistroMagazzino) {

		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		Giornata giornataCorrente = new Giornata(giornoCorrente.toString());

		Merce merceNonDiQualita = dichiarazioneMerceDeteriorata();

		ConfiguratoreRegistroMagazzino confRegMag = new ConfiguratoreRegistroMagazzino();
		RegistroMagazzino registro = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		registro.setFalseQualitaMerce(merceNonDiQualita);
		registro.scartiO(giornataCorrente);
		ristorante.setRegistroMagazzino(registro);
		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	private Merce dichiarazioneMerceDeteriorata() {
		String messaggioDichiarazione = "Definisci la merce deteriorata: ";

		System.out.println(messaggioDichiarazione);
		return creaMerce();
	}

	public void generaListaSpesa(Giorno giornoCorrente, String pathCompletoFileRistorante,
			String pathFileRegistroMagazzino) {

		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		ConfiguratoreRegistroMagazzino confRegMag = new ConfiguratoreRegistroMagazzino();
		RegistroMagazzino registroMagazzino = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Calendario";
		String pathDirectoryCalendario = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryCalendario);

		String nomeDirectoryGiornata = giornoCorrente.toString();
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
		Giornata giornataCorrente = new Giornata(giornoCorrente.toString());

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
				ConfiguratoreMenuCarta confMC = new ConfiguratoreMenuCarta();
				for (File fileMC : ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella)) {
					Piatto piatto = (Piatto) confMC.caricaIstanzaOggettoDaFile(fileMC.getAbsolutePath());
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

		//settaggio insiemi bevande, generi extra, ricettario di ristorante
		String nomeDirectoryInsiemiExtra = "Insiemi extra";
		String pathDirectoryInsiemiExtra = pathDirectory + "/" + nomeDirectoryInsiemiExtra;
		String nomeFileBevande = "insieme bevande.txt";
		String pathFileBevande = pathDirectoryInsiemiExtra + "/" + nomeFileBevande;
		String nomeFileGeneriExtra = "insieme generi extra.txt";
		String pathFileGeneriExtra = pathDirectoryInsiemiExtra + "/" + nomeFileGeneriExtra;

		ConfiguratoreExtra confIns = new ConfiguratoreExtra();
		HashMap<String, Double> insiemeB = ((HashMap<String, Double>) confIns.caricaIstanzaOggettoDaFile(pathFileBevande));
		ristorante.getInsiemeB().setInsiemeExtra(insiemeB);		
		
		HashMap<String, Double> insiemeGE = ((HashMap<String, Double>) confIns.caricaIstanzaOggettoDaFile(pathFileGeneriExtra));
		ristorante.getInsiemeGE().setInsiemeExtra(insiemeGE);

		String nomeDirectoryRicettario = "Ricettario";
		String pathRicettario = pathDirectory + "/" + nomeDirectoryRicettario;

		ConfiguratoreRicetta confRic = new ConfiguratoreRicetta();

		List<File> elencoRicette = ServizioFile.getElencoFileTxt(pathRicettario);
		for (File file : elencoRicette) {
			Ricetta ricetta = (Ricetta) confRic.caricaIstanzaOggettoDaFile(file.getAbsolutePath());
			ristorante.aggiungiRicetta(ricetta);
		}

		giornataCorrente.creaListaSpesa(ristorante);

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
