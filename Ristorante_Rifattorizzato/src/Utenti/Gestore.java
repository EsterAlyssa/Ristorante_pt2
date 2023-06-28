package Utenti;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import Giorno.Giorno;
import Giorno.Periodo;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;
import Util.InputDati;
import Util.ServizioFile;
import Util.ConfigurazioneFile.*;

public class Gestore extends Utente{

	private static String etichettaG = "gestore";
	private static String[] voci = {"Cambia i parametri del ristorante","Visualizza i parametri del ristorante","Aggiungi bevanda all'insieme delle bevande",
			"Rimuovi bevanda dall'insieme delle bevande", "Visualizza l'insieme delle bevande", "Aggiungi genere extra all'insieme dei generi extra",
			"Rimuovi genere extra dall'insieme dei generi extra", "Visualizza l'insieme dei generi extra","Crea corrispondenza Piatto - Ricetta",
			"Modifica il periodo di validita' di tutti i piatti", "Visualizza tutti i piatti", "Verifica l'esistenza di una ricetta",
			"Crea una ricetta", "Visualizza il ricettario (solo i nomi)", "Visualizza una ricetta", "Visualizza le informazioni di tutte le ricetta",
			"Crea un menu tematico", "Visualizza tutti i menu tematici (solo i nomi)", "Visualizza un menu tematico"};

	public Gestore(String nome) {
		super(nome, etichettaG, voci);
	}

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

	public void visualizzaRistorante(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		System.out.printf("Nome del ristorante: %s\n", ristorante.getNome());
		System.out.printf("Numeri di posti a sedere nel ristorante: %d\n", ristorante.getNumPosti());
		System.out.printf("Carico di lavoro per persona: %d\n", ristorante.getCaricoLavoroPersona());
		System.out.printf("Carico di lavoro sostenibile dal ristorante: %.2f\n", ristorante.getCaricoLavoroRistorante());
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
		}

		String msgNome = "Inserisci il nome della bevanda da aggiungere: ";
		String msgConsumo = "Inserisci il consumo pro capite della bevanda da aggiungere: ";

		String nome = InputDati.leggiStringaNonVuota(msgNome);
		double consumoProCapite = InputDati.leggiDoubleConMinimo(msgConsumo, 0);

		ConfiguratoreExtra confIns = new ConfiguratoreExtra();
		HashMap<String, Double> insiemeB = (HashMap<String, Double>) confIns.caricaIstanzaOggettoDaFile(pathFileBevande);
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
		HashMap<String, Double> insiemeB = (HashMap<String, Double>) confIns.caricaIstanzaOggettoDaFile(pathFileBevande);
		ristorante.setInsiemeB(insiemeB);
		ristorante.rimuoviBevanda(nome);
		insiemeB = ristorante.getInsiemeB();
		confIns.salvaIstanzaOggetto(insiemeB, pathFileBevande);
	}

	public void visualizzaInsiemeBevande(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Insiemi extra";
		String pathInsiemiExtra = pathDirectory + "/" + nomeDirectory;
		String nomeFileBevande = "insieme bevande.txt";
		String pathFileBevande = pathInsiemiExtra + "/" + nomeFileBevande;

		ConfiguratoreExtra confIns = new ConfiguratoreExtra();
		HashMap<String, Double> insiemeB = ((HashMap<String, Double>) confIns.caricaIstanzaOggettoDaFile(pathFileBevande));
		ristorante.setInsiemeB(insiemeB);

		for (String elemento : insiemeB.keySet()) {
			System.out.printf("bevanda: %s\tconsumo pro capite: %f.2\n", elemento, insiemeB.get(elemento));
		}
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
		String nomeFileGeneriExtra = "insieme generi extra.txt";
		String pathFileGeneriExtra = pathInsiemiExtra + "/" + nomeFileGeneriExtra;

		// Controlla se il file "insieme_generi extra.txt" esiste, altrimenti lo crea
		if (!ServizioFile.controlloEsistenzaFile(pathFileGeneriExtra)) {
			ServizioFile.creaFile(pathFileGeneriExtra);
		}

		ConfiguratoreExtra confIns = new ConfiguratoreExtra();
		HashMap<String, Double> insiemeGE = (HashMap<String, Double>) confIns.caricaIstanzaOggettoDaFile(pathFileGeneriExtra);
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
		HashMap<String, Double> insiemeGE = (HashMap<String, Double>) confIns.caricaIstanzaOggettoDaFile(pathFileGeneriExtra);
		ristorante.setInsiemeGE(insiemeGE);
		ristorante.rimuoviGenereExtra(nome);
		insiemeGE = ristorante.getInsiemeGE();
		confIns.salvaIstanzaOggetto(insiemeGE, pathFileGeneriExtra);
	}

	public void visualizzaInsiemeGeneriExtra(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Insiemi extra";
		String pathInsiemiExtra = pathDirectory + "/" + nomeDirectory;
		String nomeFileGeneriExtra = "insieme generi extra.txt";
		String pathFileGeneriExtra = pathInsiemiExtra + "/" + nomeFileGeneriExtra;

		ConfiguratoreExtra confIns = new ConfiguratoreExtra();
		HashMap<String, Double> insiemeGE = (HashMap<String, Double>) confIns.caricaIstanzaOggettoDaFile(pathFileGeneriExtra);
		ristorante.setInsiemeGE(insiemeGE);

		for (String elemento : ristorante.getInsiemeGE().keySet()) {
			System.out.printf("genere extra: %s\tconsumo pro capite: %f.2\n", elemento, ristorante.getInsiemeGE().get(elemento));
		}
	}

	public void corrispondenzaPiattoRicetta (String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Piatti";
		String pathPiatti = pathDirectory + "/" + nomeDirectory;

		ConfiguratorePiatto confPiat = new ConfiguratorePiatto();

		ConfiguratoreRicetta confRice = new ConfiguratoreRicetta();
		List<File> ricettario = ServizioFile.getElencoFileTxt(pathPiatti);

		for (File ricettaFile : ricettario) {
			Ricetta ricetta = (Ricetta) confRice.caricaIstanzaOggettoDaFile(ricettaFile.getAbsolutePath());

			String nomeFilePiatto = ricetta.getNome()+".txt";
			String pathFilePiatto = pathPiatti + "/" + nomeFilePiatto;

			Piatto piatto = new Piatto (ricetta.getNome(), ricetta.getCaricoLavoroPorzione());
			aggiungiValiditaPiatto(pathCompletoFileRistorante, piatto);

			confPiat.salvaIstanzaOggetto(piatto, pathFilePiatto);
			ristorante.aggiungiPiatto(piatto);
		}
	}

	private void aggiungiValiditaPiatto (String pathCompletoFileRistorante, Piatto piatto) {
		String msgValidita = "Inserisci il periodo di validita' del piatto: ";
		System.out.println(msgValidita);

		Periodo validita = new Periodo();
		validita.creaPeriodoValidita();
		piatto.setValidita(validita);

		ConfiguratorePiatto confPiatto = new ConfiguratorePiatto();

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectoryCalenario = "Calendario";
		String pathCalendario = pathDirectory + "/" + nomeDirectoryCalenario;

		// Controlla se la directory esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathCalendario);

		for (Giorno giorno : piatto.getValidita().getPeriodoValidita()) {
			String nomeDirectoryGiornata = giorno.toString();
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

		try {
			Ricetta.trovaRicetta(nome, ristorante.getRicettario());
			System.out.println(msgSiRicetta);
		} catch (NullPointerException e) {
			System.out.println(msgNoRicetta);
		}
	}

	public void visualizzaPiatti(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectoryPiatti = "Piatti";
		String pathPiatti = pathDirectory + "/" + nomeDirectoryPiatti;

		ConfiguratorePiatto confPiat = new ConfiguratorePiatto();

		List<File> elencoPiatti = ServizioFile.getElencoFileTxt(pathPiatti);
		for (File file : elencoPiatti) {
			Piatto piatto = (Piatto) confPiat.caricaIstanzaOggettoDaFile(file.getAbsolutePath());
			ristorante.aggiungiPiatto(piatto);
		}

		HashSet<Piatto> piatti = ristorante.getPiatti();

		for (Piatto piatto : piatti) {
			System.out.printf("nome piatto: %s\tperiodo di validita': %s\n", piatto.getNome(), piatto.getValidita().toString());
		}
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

	private void aggiungiIngredienti(Ricetta ricetta) {
		String msgNome = "Inserisci il nome dell'ingrediente da aggiungere: ";
		String msgDose = "Inserisci la dose dell'ingrediente da aggiungere: ";

		String nomeIngrediente = InputDati.leggiStringaNonVuota(msgNome);
		double doseIngrediente = InputDati.leggiDoubleConMinimo(msgDose, 0);

		ricetta.aggiungiIngrediente(nomeIngrediente, doseIngrediente);		
	}

	public void visualizzaRicettario(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Ricettario";
		String pathRicettario = pathDirectory + "/" + nomeDirectory;

		ConfiguratoreRicetta confRic = new ConfiguratoreRicetta();

		List<File> elencoRicette = ServizioFile.getElencoFileTxt(pathRicettario);
		for (File file : elencoRicette) {
			Ricetta ricetta = (Ricetta) confRic.caricaIstanzaOggettoDaFile(file.getAbsolutePath());
			ristorante.aggiungiRicetta(ricetta);
		}

		int i = 1;
		for (Ricetta ric : ristorante.getRicettario()) {
			System.out.printf("%d: %s\n", i, ric.getNome());
			i++;
		}
	}

	public void visualizzaRicetta(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String msgRichiestaRicetta = "Inserisci il nome della ricetta da visualizzare: ";
		String msgNoRicetta = "Non esiste una ricetta con questo nome. Inseriscilo di nuovo: ";

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Ricettario";
		String pathRicettario = pathDirectory + "/" + nomeDirectory;

		ConfiguratoreRicetta confRic = new ConfiguratoreRicetta();

		List<File> elencoRicette = ServizioFile.getElencoFileTxt(pathRicettario);
		for (File file : elencoRicette) {
			Ricetta ricetta = (Ricetta) confRic.caricaIstanzaOggettoDaFile(file.getAbsolutePath());
			ristorante.aggiungiRicetta(ricetta);
		}

		visualizzaRicettario(pathCompletoFileRistorante);

		String ricettaScelta = InputDati.leggiStringaNonVuota(msgRichiestaRicetta);
		boolean trovata = true;
		do {
			Ricetta ricettaTrovata = Ricetta.trovaRicetta(ricettaScelta, ristorante.getRicettario());
			if (ricettaTrovata != null) {
				//ritornare la ricetta
				ricettaTrovata.toString();
				trovata = false;
			} else {
				//la ricetta non è nel ricettario
				System.out.println(msgNoRicetta);
			}
		} while (trovata);
	}

	public void visualizzaInfoRicette (String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Ricettario";
		String pathRicettario = pathDirectory + "/" + nomeDirectory;

		ConfiguratoreRicetta confRic = new ConfiguratoreRicetta();

		List<File> elencoRicette = ServizioFile.getElencoFileTxt(pathRicettario);
		for (File file : elencoRicette) {
			Ricetta ricetta = (Ricetta) confRic.caricaIstanzaOggettoDaFile(file.getAbsolutePath());
			ristorante.aggiungiRicetta(ricetta);
		}

		for (Ricetta ric : ristorante.getRicettario()) {
			System.out.println(ric.toString());
		}
	}

	//in ristorante abbiamo calendario, dove abbiamo giornate, aggiungiamo i menu alle rispettive giornate
	public void creaMenuTematico(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Menu Tematici";
		String pathMenuTematici = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory "Menu Tematici" esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathMenuTematici);

		String msgNome = "Inserisci il nome del menu tematico da creare: ";
		String msgPiatto = "Inserisci il nome del piatto da aggiungere al menu tematico: ";
		String msgErrValidita = "ATTENZIONE! Il piatto scelto non è valido per i giorni selezionati.";
		String msgErrPiatto = "ATTENZIONE! Con questo piatto il carico di lavoro è troppo alto.";
		String msgScelta = "Vuoi inserire altri piatti? ";

		String nomeMenuT = InputDati.leggiStringaNonVuota(msgNome);
		Periodo validitaMenuT = new Periodo();
		validitaMenuT.creaPeriodoValidita();
		MenuTematico nuovo = new MenuTematico(nomeMenuT, validitaMenuT);
		boolean scelta = true;
		do {
			String nomePiatto = InputDati.leggiStringaNonVuota(msgPiatto);
			Piatto piattoTrovato = Piatto.trovaPiattoDaNome(nomePiatto, ristorante.getPiatti());
			if (piattoTrovato != null) {
				double CLP = piattoTrovato.getCaricoLavoro();
				double CLM = nuovo.getCaricoLavoro();
				double CLPersona = ristorante.getCaricoLavoroPersona();
				if ((CLP+CLM) <= (4/3)*CLPersona) {
					if (validitaMenuT.getPeriodoValidita().containsAll(piattoTrovato.getValidita().getPeriodoValidita())) {
						nuovo.aggiungiPiatto(piattoTrovato);
						scelta = InputDati.yesOrNo(msgScelta);
					} else {
						System.out.println(msgErrValidita);
					}
				} else {
					System.out.println(msgErrPiatto);
				}
			}
		} while (scelta);
		ristorante.aggiungiMenuTematico(nuovo);

		String nomeFileMenuTematico = nomeMenuT + ".txt";
		String pathFileMenuTematico = pathMenuTematici + "/" + nomeFileMenuTematico;

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
			String nomeDirectoryGiornata = giorno.toString();
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

	public void visualizzaMenuTematici(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Menu Tematici";
		String pathMenuTematici = pathDirectory + "/" + nomeDirectory;

		ConfiguratoreMenuTematico confMenuT = new ConfiguratoreMenuTematico();

		List<File> elencoMenuT = ServizioFile.getElencoFileTxt(pathMenuTematici);
		for (File file : elencoMenuT) {
			MenuTematico menu = (MenuTematico) confMenuT.caricaIstanzaOggettoDaFile(file.getAbsolutePath());
			ristorante.aggiungiMenuTematico(menu);
		}

		for (MenuTematico menu : ristorante.getMenuTematici()) {
			System.out.println(menu.toString());
		}
	}	

	private void visualizzaNomiMenuTematici(Ristorante ristorante) {
		int i = 1;
		for (MenuTematico menu : ristorante.getMenuTematici()) {
			System.out.printf("%d) %s", i, menu.getNome());
			i++;
		}
	}

	//dato un giorno chiesto nel metodo stesso
	public void visualizzaMenuTematico(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String msgRichiesta = "Inserisci il nome del menu tematico da visualizzare: ";
		String msgErrMenu= "ATTENZIONE! Il menu inserito non esiste";

		ConfiguratoreMenuTematico confMenuT = new ConfiguratoreMenuTematico();

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Menu Tematici";
		String pathMenuTematici = pathDirectory + "/" + nomeDirectory;

		List<File> elencoMenuT = ServizioFile.getElencoFileTxt(pathMenuTematici);
		for (File file : elencoMenuT) {
			MenuTematico menu = (MenuTematico) confMenuT.caricaIstanzaOggettoDaFile(file.getAbsolutePath());
			ristorante.aggiungiMenuTematico(menu);
		}

		visualizzaNomiMenuTematici(ristorante);

		boolean trovato = true;
		do {
			String ricerca = InputDati.leggiStringaNonVuota(msgRichiesta);	
			MenuTematico menu = MenuTematico.trovaMenuTDaNome(ricerca, ristorante.getMenuTematici());
			if (menu != null) {
				System.out.println(menu.toString());
				trovato = false;
			} else {
				System.out.println(msgErrMenu);
			}
		} while (trovato);
	}

	@Override
	public void eseguiMetodi(int scelta, String pathCompletoFileRistorante) {
		switch (scelta) {
		case 1:
			inizializzaRistorante(pathCompletoFileRistorante);
			break;
		case 2:
			visualizzaRistorante(pathCompletoFileRistorante);
			break;
		case 3:
			aggiungiBevanda(pathCompletoFileRistorante);
			break;
		case 4:
			rimuoviBevanda(pathCompletoFileRistorante);
			break;
		case 5:
			visualizzaInsiemeBevande(pathCompletoFileRistorante);
			break;
		case 6: 
			aggiungiGenereExtra(pathCompletoFileRistorante);
			break;
		case 7: 
			rimuoviGenereExtra(pathCompletoFileRistorante);
			break;
		case 8: 
			visualizzaInsiemeGeneriExtra(pathCompletoFileRistorante);
			break;
		case 9: 
			corrispondenzaPiattoRicetta(pathCompletoFileRistorante); //aggiungere piatto al menu alla carta di quel giorno
			break;
		case 10:
			visualizzaPiatti(pathCompletoFileRistorante);
			break;
		case 11:
			verificaCorrispondenzaPiattoRicetta(pathCompletoFileRistorante);
			break;
		case 12: 
			creaRicetta(pathCompletoFileRistorante);
			break;
		case 13:
			visualizzaRicettario(pathCompletoFileRistorante);
			break;
		case 14: 
			visualizzaRicetta(pathCompletoFileRistorante);
			break;
		case 15:
			visualizzaInfoRicette(pathCompletoFileRistorante);
			break;
		case 16:
			creaMenuTematico(pathCompletoFileRistorante); 
			break;
		case 17:
			visualizzaMenuTematici(pathCompletoFileRistorante);
			break;
		case 18:
			visualizzaMenuTematico(pathCompletoFileRistorante); 
			break;
		}
	}

}
