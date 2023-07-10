package Utenti.Gestore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import Giorno.Giorno;
import Giorno.Periodo;
import Giorno.GiornoView.GiornoView;
import Giorno.GiornoView.PeriodoView;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.InsiemeExtra;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;
import Util.InputDati;
import Util.GestioneFile.ServizioFile;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreExtra;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreManager;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreMenuTematico;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratorePiatto;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRicetta;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRistorante;

public class Gestore {

	public static void inizializzaRistorante(String pathCompletoFileRistorante) {
		String msgCarico = "Inserisci il carico di lavoro per persona: ";
		String msgNumPosti = "Inserisci il numero di posti a sedere disponibili del ristorante: ";

		int caricoLavoroPersona = InputDati.leggiInteroNonNegativo(msgCarico);
		int numPosti = InputDati.leggiInteroPositivo(msgNumPosti);

		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		ristorante.setCaricoLavoroPersona(caricoLavoroPersona);
		ristorante.setNumPosti(numPosti);
		ristorante.setCaricoLavoroRistorante(1.2 * (ristorante.getCaricoLavoroPersona() * ristorante.getNumPosti()));

		conf.salvaIstanzaOggetto(ristorante, pathCompletoFileRistorante);
	}

	public void aggiungiBevanda(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Insiemi extra";
		String pathInsiemiExtra = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory "Insiemi extra" esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathInsiemiExtra);

		String nomeFileBevande = "insieme bevande.txt";
		String pathFileBevande = pathInsiemiExtra + "/" + nomeFileBevande;

		// Controlla se il file "insieme_bevande.txt" esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileBevande)) {
			ServizioFile.creaFile(pathFileBevande);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileBevande));
				writer.write("Insieme Extra=");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				System.out.println("Errore inizializzazione file");
				e.printStackTrace();
			}
		}

		String msgNome = "Inserisci il nome della bevanda da aggiungere: ";
		String msgConsumo = "Inserisci il consumo pro capite della bevanda da aggiungere: ";

		String nome = InputDati.leggiStringaNonVuota(msgNome);
		double consumoProCapite = InputDati.leggiDoubleConMinimo(msgConsumo, 0);

		ConfiguratoreExtra confIns = new ConfiguratoreExtra();
		InsiemeExtra insiemeB = (InsiemeExtra) confIns.caricaIstanzaOggettoDaFile(pathFileBevande);

		ristorante.setInsiemeB(insiemeB);
		ristorante.aggiungiBevanda(nome, consumoProCapite);
		insiemeB = ristorante.getInsiemeB();
		confIns.salvaIstanzaOggetto(insiemeB, pathFileBevande);
	}

	public void rimuoviBevanda(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String msgNome = "Inserisci il nome della bevanda da rimuovere: ";

		String nome = InputDati.leggiStringaNonVuota(msgNome);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Insiemi extra";
		String pathInsiemiExtra = pathDirectory + "/" + nomeDirectory;
		String nomeFileBevande = "insieme bevande.txt";
		String pathFileBevande = pathInsiemiExtra + "/" + nomeFileBevande;

		ConfiguratoreExtra confIns = new ConfiguratoreExtra();
		InsiemeExtra insiemeB = (InsiemeExtra) confIns.caricaIstanzaOggettoDaFile(pathFileBevande);

		ristorante.setInsiemeB(insiemeB);
		if (ristorante.rimuoviBevanda(nome))
			System.out.println("Bevanda rimossa con successo");
		else 
			System.out.println("La bevanda non e' presente nell'insieme");

		insiemeB = ristorante.getInsiemeB();
		confIns.salvaIstanzaOggetto(insiemeB, pathFileBevande);
	}


	public void aggiungiGenereExtra(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String msgNome = "Inserisci il nome del genere extra da aggiungere: ";
		String msgConsumo = "Inserisci il consumo pro capite del genere extra da aggiungere: ";

		String nome = InputDati.leggiStringaNonVuota(msgNome);
		double consumoProCapite = InputDati.leggiDoubleConMinimo(msgConsumo, 0);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Insiemi extra";
		String pathInsiemiExtra = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory "Insiemi extra" esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathInsiemiExtra);

		String nomeFileGeneriExtra = "insieme generi extra.txt";
		String pathFileGeneriExtra = pathInsiemiExtra + "/" + nomeFileGeneriExtra;

		// Controlla se il file "insieme_generi extra.txt" esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileGeneriExtra)) {
			ServizioFile.creaFile(pathFileGeneriExtra);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileGeneriExtra));
				writer.write("Insieme Extra=");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				System.out.println("Errore inizializzazione file");
				e.printStackTrace();
			}
		}

		ConfiguratoreExtra confIns = new ConfiguratoreExtra();
		InsiemeExtra insiemeGE = (InsiemeExtra) confIns.caricaIstanzaOggettoDaFile(pathFileGeneriExtra);

		ristorante.setInsiemeGE(insiemeGE);
		ristorante.aggiungiGenereExtra(nome, consumoProCapite);
		insiemeGE = ristorante.getInsiemeGE();
		confIns.salvaIstanzaOggetto(insiemeGE, pathFileGeneriExtra);
	}

	public void rimuoviGenereExtra(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Insiemi extra";
		String pathInsiemiExtra = pathDirectory + "/" + nomeDirectory;
		String nomeFileGeneriExtra = "insieme generi extra.txt";
		String pathFileGeneriExtra = pathInsiemiExtra + "/" + nomeFileGeneriExtra;

		String msgNome = "Inserisci il nome del genere extra da rimuovere: ";
		String nome = InputDati.leggiStringaNonVuota(msgNome);

		ConfiguratoreExtra confIns = new ConfiguratoreExtra();
		InsiemeExtra insiemeGE = (InsiemeExtra) confIns.caricaIstanzaOggettoDaFile(pathFileGeneriExtra);

		ristorante.setInsiemeGE(insiemeGE);
		if (ristorante.rimuoviGenereExtra(nome))
			System.out.println("Genere extra rimosso con successo");
		else 
			System.out.println("Il genere extra non e' presente nell'insieme");

		insiemeGE = ristorante.getInsiemeGE();
		confIns.salvaIstanzaOggetto(insiemeGE, pathFileGeneriExtra);
	}

	private void aggiungiIngredienti(Ricetta ricetta) {
		String msgNome = "Inserisci il nome dell'ingrediente da aggiungere: ";
		String msgDose = "Inserisci la dose dell'ingrediente da aggiungere: ";

		String nomeIngrediente = InputDati.leggiStringaNonVuota(msgNome);
		double doseIngrediente = InputDati.leggiDoubleConMinimo(msgDose, 0);

		ricetta.aggiungiIngrediente(nomeIngrediente, doseIngrediente);		
	}


	public void creaRicetta(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String msgNome = "Inserisci il nome della ricetta da creare: ";
		String msgNumPorzioni = "Inserisci il numero delle porzioni della ricetta da creare: ";
		String msgCaricoLavoro = "Inserisci il carico di lavoro per persona della ricetta da creare: ";

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Ricettario";
		String pathRicettario = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory "Ricettario" esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathRicettario);

		String msgScelta = "Vuoi inserire altri ingredienti? ";

		String nomeRicetta = InputDati.leggiStringaNonVuota(msgNome);
		int numPorzioni = InputDati.leggiInteroPositivo(msgNumPorzioni);
		double caricoLavoroPorzione = InputDati.leggiDoubleConMinimo(msgCaricoLavoro, 0);

		Ricetta nuova = new Ricetta(nomeRicetta, numPorzioni, caricoLavoroPorzione);
		boolean scelta = true;
		do {
			aggiungiIngredienti(nuova);
			scelta = InputDati.yesOrNo(msgScelta);
		} while (scelta);

		ristorante.aggiungiRicetta(nuova);

		String nomeFileRicetta = nomeRicetta + ".txt";
		String pathFileRicetta = pathRicettario + "/" + nomeFileRicetta;

		// Controlla se il file nomeRicetta + ".txt" esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileRicetta)) {
			ServizioFile.creaFile(pathFileRicetta);
		}

		ConfiguratoreRicetta confRic = new ConfiguratoreRicetta();
		confRic.salvaIstanzaOggetto(nuova, pathFileRicetta);
	}


	public void corrispondenzaPiattoRicetta(GestoreRistorante gestoreRistorante, String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectoryPiatti = "Piatti";
		String pathPiatti = pathDirectory + "/" + nomeDirectoryPiatti;

		// Controlla se la directory "Piatti" esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathPiatti);

		ConfiguratorePiatto confPiat = new ConfiguratorePiatto();

		String nomeDirectoryRicettario = "Ricettario";
		String pathRicettario = pathDirectory + "/" + nomeDirectoryRicettario;

		ConfiguratoreRicetta confRice = new ConfiguratoreRicetta();
		List<File> ricettario = ServizioFile.getElencoFileTxt(pathRicettario);

		for (File ricettaFile : ricettario) {
			Ricetta ricetta = (Ricetta) confRice.caricaIstanzaOggettoDaFile(ricettaFile.getPath());

			Piatto piatto = new Piatto(ricetta.getNome(), ricetta.getCaricoLavoroPorzione());

			String nomeFilePiatto = ricetta.getNome() + ".txt";
			String pathFilePiatto = pathPiatti + "/" + nomeFilePiatto;

			// Controlla se il file nomeFilePiatto + ".txt" esiste, altrimenti lo crea
			if (!ServizioFile.controlloEsistenzaFile(pathFilePiatto)) {
				ServizioFile.creaFile(pathFilePiatto);
				aggiungiValiditaPiatto(pathCompletoFileRistorante, piatto);
				confPiat.salvaIstanzaOggetto(piatto, pathFilePiatto);
			} else {
				piatto = (Piatto) confPiat.caricaIstanzaOggettoDaFile(pathFilePiatto);
				System.out.printf("Periodo di validità del piatto %s:\n", piatto.getNome());
				for (Giorno giorno : piatto.getValidita().getPeriodoValidita()) {
					GiornoView giornoView = new GiornoView (giorno.getGiorno());
					giornoView.mostraDescrizioneGiorno();
				}
				boolean scelta = InputDati.yesOrNo("Vuoi aggiungere altri giorni validi?");
				if (scelta) {
					aggiungiValiditaPiatto(pathCompletoFileRistorante, piatto);
					confPiat.salvaIstanzaOggetto(piatto, pathFilePiatto);
				}
			}

			ristorante.aggiungiPiatto(piatto);
		}
	}

	private void aggiungiValiditaPiatto (String pathCompletoFileRistorante, Piatto piatto) {
		String msgValidita = "Piatto: %s\n";
		System.out.printf(msgValidita, piatto.getNome());

		Periodo validita = PeriodoView.creaPeriodoValidita();
		Periodo newValidita = Periodo.unisciPeriodi(validita, piatto.getValidita());
		piatto.setValidita(newValidita);

		ConfiguratorePiatto confPiatto = new ConfiguratorePiatto();

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectoryCalenario = "Calendario";
		String pathCalendario = pathDirectory + "/" + nomeDirectoryCalenario;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathCalendario);

		for (Giorno giorno : piatto.getValidita().getPeriodoValidita()) {
			GiornoView giornoView = new GiornoView (giorno.getGiorno());
			String nomeDirectoryGiornata = giornoView.descrizioneGiorno();
			String pathGiornata = pathCalendario + "/" + nomeDirectoryGiornata;
			ServizioFile.creaDirectory(pathGiornata);

			String nomeDirectoryMenuCarta = "Menu alla carta";
			String pathMenuCarta = pathGiornata + "/" + nomeDirectoryMenuCarta;
			ServizioFile.creaDirectory(pathMenuCarta);

			String nomePiattoMenuCarta = piatto.getNome() + ".txt";
			String pathPiattoMenuCarta = pathMenuCarta + "/" + nomePiattoMenuCarta;

			// Controlla se il file esiste, altrimenti lo crea
			if (!ServizioFile.controlloEsistenzaFile(pathPiattoMenuCarta)) {
				ServizioFile.creaFile(pathPiattoMenuCarta);
			}

			//salva il file del piatto nella cartella del menu alla carta di ogni giorno in cui è valio
			confPiatto.salvaIstanzaOggetto(piatto, pathPiattoMenuCarta);
		}
	}


	public void verificaCorrispondenzaPiattoRicetta(String pathCompletoFileRistorante){
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectoryRicettario = "Ricettario";
		String pathRicettario = pathDirectory + "/" + nomeDirectoryRicettario;

		ConfiguratoreRicetta confRic = new ConfiguratoreRicetta();

		List<File> elencoRicette = ServizioFile.getElencoFileTxt(pathRicettario);
		for (File file : elencoRicette) {
			Ricetta ricetta = (Ricetta) confRic.caricaIstanzaOggettoDaFile(file.getAbsolutePath());
			ristorante.aggiungiRicetta(ricetta);
		}

		String msgNome = "Inserisci il nome del piatto da cercare: ";
		String msgSiRicetta = "Esiste una corrispondenza tra il piatto cercato e una ricetta";
		String msgNoRicetta = "Non esiste una ricetta con questo nome";

		String nome = InputDati.leggiStringaNonVuota(msgNome);


		if (Ricetta.trovaRicetta(nome, ristorante.getRicettario())!=null) {
			System.out.println(msgSiRicetta);
		} else {
			System.out.println(msgNoRicetta);
		}
	}

	public void creaMenuTematico(VisualizzatoreGestione visualizzatoreGestione, String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectoryMenuT = "Menu Tematici";
		String pathDirectoryMenuTematici = pathDirectory + "/" + nomeDirectoryMenuT;

		// Controlla se la directory "Menu Tematici" esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathDirectoryMenuTematici);

		String nomeDirectoryPiatti = "Piatti";
		String pathDirectoryPiatti = pathDirectory + "/" + nomeDirectoryPiatti;

		ConfiguratoreManager confPiat = new ConfiguratorePiatto();
		List<File> elencoPiatti = ServizioFile.getElencoFileTxt(pathDirectoryPiatti);
		HashSet<Piatto> piatti = new HashSet<>();
		for (File file : elencoPiatti) {
			Piatto piatto = (Piatto) confPiat.caricaIstanzaOggettoDaFile(file.getPath());
			piatti.add(piatto);
		}
		ristorante.setPiatti(piatti);

		String msgNome = "Inserisci il nome del menu tematico da creare: ";
		String msgPiatto = "Inserisci il nome del piatto da aggiungere al menu tematico: ";
		String msgErrValidita = "ATTENZIONE! Il piatto scelto non è valido per i giorni selezionati.";
		String msgErrPiatto = "ATTENZIONE! Con questo piatto il carico di lavoro è troppo alto.";
		String msgScelta = "Vuoi inserire altri piatti? ";

		String nomeMenuT = InputDati.leggiStringaNonVuota(msgNome);
		Periodo validitaMenuT = PeriodoView.creaPeriodoValidita();
		MenuTematico nuovo = new MenuTematico(nomeMenuT, validitaMenuT);
		boolean scelta = true;
		do {
			visualizzatoreGestione.visualizzaRicettario(pathCompletoFileRistorante);
			String nomePiatto = InputDati.leggiStringaNonVuota(msgPiatto);
			Piatto piattoTrovato = Piatto.trovaPiattoDaNome(nomePiatto, ristorante.getPiatti());
			if (piattoTrovato != null) {
				double CLP = piattoTrovato.getCaricoLavoro();
				double CLM = nuovo.getCaricoLavoro();
				double CLPersona = ristorante.getCaricoLavoroPersona();
				if ((CLP+CLM) <= (4/3)*CLPersona) {
					if (piattoTrovato.getValidita().getPeriodoValidita().containsAll(nuovo.getValidita().getPeriodoValidita())) {
						nuovo.aggiungiPiatto(piattoTrovato);
						scelta = InputDati.yesOrNo(msgScelta);
					} else {
						System.out.println(msgErrValidita);
					}
				} else {
					System.out.println(msgErrPiatto);
				}
			} else {
				System.out.println("Non è stato trovato nessun piatto con questo nome");
			}
		} while (scelta);
		ristorante.aggiungiMenuTematico(nuovo);

		String nomeFileMenuTematico = nomeMenuT + ".txt";
		String pathFileMenuTematico = pathDirectoryMenuTematici + "/" + nomeFileMenuTematico;

		// Controlla se il file nomeMenuT + ".txt" esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileMenuTematico)) {
			ServizioFile.creaFile(pathFileMenuTematico);
		}

		ConfiguratoreMenuTematico confMenuT = new ConfiguratoreMenuTematico();
		//salviamo il file nella cartella contenente tutti i menu tematici
		confMenuT.salvaIstanzaOggetto(nuovo, pathFileMenuTematico);

		//salviamo il file nella cartella della giornata contenente tutti i menu tematici
		String nomeDirectoryCalenario = "Calendario";
		String pathCalendario = pathDirectory + "/" + nomeDirectoryCalenario;

		for (Giorno giorno : nuovo.getValidita().getPeriodoValidita()) {
			GiornoView giornoView = new GiornoView (giorno.getGiorno());
			String nomeDirectoryGiornata = giornoView.descrizioneGiorno();
			String pathGiornata = pathCalendario + "/" + nomeDirectoryGiornata;
			ServizioFile.creaDirectory(pathGiornata);

			String nomeDirectoryMenuTematico = "Menu tematici";
			String pathDirectoryMenuTematico = pathGiornata + "/" + nomeDirectoryMenuTematico;
			ServizioFile.creaDirectory(pathDirectoryMenuTematico);

			String nomeMenuTematico = nuovo.getNome() + ".txt";
			String pathMenuTematico = pathDirectoryMenuTematico + "/" + nomeMenuTematico;

			// Controlla se il file esiste, altrimenti lo crea
			if (!ServizioFile.controlloEsistenzaFile(pathMenuTematico)) {
				ServizioFile.creaFile(pathMenuTematico);
			}

			//salva il file del piatto nella cartella del menu tematico di ogni giorno in cui è valido
			confMenuT.salvaIstanzaOggetto(nuovo, pathMenuTematico);
		}
	}

	public HashSet<MenuTematico> ottieniMenuTematici(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Menu Tematici";
		String pathMenuTematici = pathDirectory + "/" + nomeDirectory;

		ConfiguratoreMenuTematico confMenuT = new ConfiguratoreMenuTematico();

		List<File> elencoMenuT = ServizioFile.getElencoFileTxt(pathMenuTematici);
		HashSet<MenuTematico> menuT = new HashSet<>();
		for (File file : elencoMenuT) {
			MenuTematico menu = (MenuTematico) confMenuT.caricaIstanzaOggettoDaFile(file.getPath());
			menuT.add(menu);
		}
		ristorante.setMenuTematici(menuT);
		return menuT;
	}

}