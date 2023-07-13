package Utenti.Gestore;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import Giorno.Giorno;
import Giorno.Periodo;
import Giorno.GiornoView.PeriodoView;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.InsiemeExtra;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;
import Ristorante.ElementiRistorante.ElementiRistorantiView.ElencoMenuTematiciView;
import Ristorante.ElementiRistorante.ElementiRistorantiView.MenuTematicoView;
import Ristorante.ElementiRistorante.ElementiRistorantiView.PiattoView;
import Ristorante.ElementiRistorante.ElementiRistorantiView.RicettaView;
import Util.InputDati;
import Util.GestioneFile.CreazioneDirectory;
import Util.GestioneFile.CreazioneFile;
import Util.GestioneFile.CreazioneOggetti;
import Util.GestioneFile.ServizioFile;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreExtra;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreManager;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreMenuTematico;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratorePiatto;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRicetta;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRistorante;

public class Gestore {

	static final String MSG_CARICO_RISTORANTE = "Inserisci il carico di lavoro per persona: ";
	static final String MSG_NUM_POSTI_RISTORANTE = "Inserisci il numero di posti a sedere disponibili del ristorante: ";

	static final String MSG_NOME_BEVANDA = "Inserisci il nome della bevanda da aggiungere: ";
	static final String MSG_CONSUMO_BEVANDA = "Inserisci il consumo pro capite della bevanda da aggiungere: ";

	static final String MSG_NOME_RIMOZIONE_BEVANDA = "Inserisci il nome della bevanda da rimuovere: ";
	static final String MSG_SI_RIMOZIONE_BEVANDA = "Bevanda rimossa con successo";
	static final String MSG_ERR_NO_BEVANDA = "La bevanda non e' presente nell'insieme";

	static final String MSG_NOME_GENERE_EXTRA = "Inserisci il nome del genere extra da aggiungere: ";
	static final String MSG_CONSUMO_GENERE_EXTRA = "Inserisci il consumo pro capite del genere extra da aggiungere: ";

	static final String MSG_NOME_RIMOZIONE_GENERE_EXTRA = "Inserisci il nome del genere extra da rimuovere: ";
	static final String MSG_SI_RIMOZIONE_GE = "Genere extra rimosso con successo";
	static final String MSG_ERR_NO_GE = "Il genere extra non e' presente nell'insieme";

	static final String MSG_NOME_T_AGGIUNTA_GIORNI = "Inserisci il nome del menu tematico a cui aggiungere giorni di validita': ";
	static final String MSG_ALTRI_GIORNI_PIATTO_E_MENU_T = "Vuoi aggiungere altri giorni validi? ";

	static final String MSG_NOME_RICERCA_PIATTO = "Inserisci il nome del piatto da cercare: ";
	static final String MSG_SI_CORRISPONDENZA = "Esiste una corrispondenza tra il piatto cercato e una ricetta.";
	static final String MSG_ERR_NO_CORRISPONDENZA = "ATTENZIONE! Non esiste una ricetta con questo nome.";

	private ConfiguratoreManager<Ristorante> confRistorante;
	private ConfiguratoreManager<InsiemeExtra> confInsEx;
	private ConfiguratoreManager<Ricetta> confRicetta;
	private ConfiguratoreManager<Piatto> confPiatto;
	private ConfiguratoreManager<MenuTematico> confMenuT;

	public Gestore() {
		this.confRistorante = new ConfiguratoreRistorante();
		this.confInsEx = new ConfiguratoreExtra();
		this.confRicetta = new ConfiguratoreRicetta();
		this.confPiatto = new ConfiguratorePiatto();
		this.confMenuT = new ConfiguratoreMenuTematico();
	}

	public static void inizializzaRistorante(String pathCompletoFileRistorante) {
		int caricoLavoroPersona = InputDati.leggiInteroNonNegativo(MSG_CARICO_RISTORANTE);
		int numPosti = InputDati.leggiInteroPositivo(MSG_NUM_POSTI_RISTORANTE);

		ConfiguratoreManager<Ristorante> confRistorante = new ConfiguratoreRistorante();
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		ristorante.setCaricoLavoroPersona(caricoLavoroPersona);
		ristorante.setNumPosti(numPosti);
		ristorante.setCaricoLavoroRistorante(1.2 * (ristorante.getCaricoLavoroPersona() * ristorante.getNumPosti()));

		confRistorante.salvaIstanzaOggetto(ristorante, pathCompletoFileRistorante);
	}

	public void aggiungiBevanda(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryInsiemiExtra = CreazioneDirectory.creaDirectoryInsiemiExtra(pathCompletoFileRistorante);
		String pathFileInsiemeBevande = CreazioneFile.creaFileInsiemeBevande(pathDirectoryInsiemiExtra);

		String nome = InputDati.leggiStringaNonVuota(MSG_NOME_BEVANDA);
		double consumoProCapite = InputDati.leggiDoubleConMinimo(MSG_CONSUMO_BEVANDA, 0);

		InsiemeExtra insiemeB = CreazioneOggetti.creaInsiemeExtra(pathFileInsiemeBevande);

		ristorante.setInsiemeB(insiemeB);
		ristorante.aggiungiBevanda(nome, consumoProCapite);
		insiemeB = ristorante.getInsiemeB();
		confInsEx.salvaIstanzaOggetto(insiemeB, pathFileInsiemeBevande);
	}


	public void rimuoviBevanda(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String nome = InputDati.leggiStringaNonVuota(MSG_NOME_RIMOZIONE_BEVANDA);

		String pathDirectoryInsiemiExtra = CreazioneDirectory.creaDirectoryInsiemiExtra(pathCompletoFileRistorante);
		String pathFileInsiemeBevande = CreazioneFile.creaFileInsiemeBevande(pathDirectoryInsiemiExtra);

		InsiemeExtra insiemeB = CreazioneOggetti.creaInsiemeExtra(pathFileInsiemeBevande);

		ristorante.setInsiemeB(insiemeB);
		if (ristorante.rimuoviBevanda(nome)) {
			System.out.println(MSG_SI_RIMOZIONE_BEVANDA);
		} else {
			System.out.println(MSG_ERR_NO_BEVANDA);
		}

		insiemeB = ristorante.getInsiemeB();
		confInsEx.salvaIstanzaOggetto(insiemeB, pathFileInsiemeBevande);
	}

	public void aggiungiGenereExtra(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String nome = InputDati.leggiStringaNonVuota(MSG_NOME_GENERE_EXTRA);
		double consumoProCapite = InputDati.leggiDoubleConMinimo(MSG_CONSUMO_GENERE_EXTRA, 0);

		String pathDirectoryInsiemiExtra = CreazioneDirectory.creaDirectoryInsiemiExtra(pathCompletoFileRistorante);
		String pathFileInsiemeGeneriExtra = CreazioneFile.creaFileInsiemeGeneriExtra(pathDirectoryInsiemiExtra);

		InsiemeExtra insiemeGE = CreazioneOggetti.creaInsiemeExtra(pathFileInsiemeGeneriExtra);

		ristorante.setInsiemeGE(insiemeGE);
		ristorante.aggiungiGenereExtra(nome, consumoProCapite);
		insiemeGE = ristorante.getInsiemeGE();
		confInsEx.salvaIstanzaOggetto(insiemeGE, pathFileInsiemeGeneriExtra);
	}

	public void rimuoviGenereExtra(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryInsiemiExtra = CreazioneDirectory.creaDirectoryInsiemiExtra(pathCompletoFileRistorante);
		String pathFileInsiemeGeneriExtra = CreazioneFile.creaFileInsiemeGeneriExtra(pathDirectoryInsiemiExtra);

		String nome = InputDati.leggiStringaNonVuota(MSG_NOME_RIMOZIONE_GENERE_EXTRA);

		InsiemeExtra insiemeGE = CreazioneOggetti.creaInsiemeExtra(pathFileInsiemeGeneriExtra);

		ristorante.setInsiemeGE(insiemeGE);
		if (ristorante.rimuoviGenereExtra(nome)) {
			System.out.println(MSG_SI_RIMOZIONE_GE);
		} else {
			System.out.println(MSG_ERR_NO_GE);
		}

		insiemeGE = ristorante.getInsiemeGE();
		confInsEx.salvaIstanzaOggetto(insiemeGE, pathFileInsiemeGeneriExtra);
	}

	public void creaRicetta(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);
		String pathDirectoryRicettario = CreazioneDirectory.creaDirectoryRicettario(pathCompletoFileRistorante);

		HashSet<Ricetta> ricettario = CreazioneOggetti.creaRicettario(pathDirectoryRicettario);
		ristorante.setRicettario(ricettario);


		Ricetta nuovaRicetta = RicettaView.creaRicetta(ristorante);
		String pathFileRicetta = CreazioneFile.creaFileRicetta(pathDirectoryRicettario, nuovaRicetta.getNome());
		confRicetta.salvaIstanzaOggetto(nuovaRicetta, pathFileRicetta);

	}

	public void corrispondenzaPiattoRicetta(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryPiatti = CreazioneDirectory.creaDirectoryPiatti(pathCompletoFileRistorante);
		String pathDirectoryRicettario = CreazioneDirectory.creaDirectoryRicettario(pathCompletoFileRistorante);

		List<File> ricettario = ServizioFile.getElencoFileTxt(pathDirectoryRicettario);

		for (File ricettaFile : ricettario) {
			Ricetta ricetta = confRicetta.caricaIstanzaOggettoDaFile(ricettaFile.getPath());

			Piatto piatto = new Piatto(ricetta.getNome(), ricetta.getCaricoLavoroPorzione());
			String pathFilePiatto = CreazioneFile.trovaFilePiatto(pathDirectoryPiatti, ricetta);

			// Controlla se il file nomeFilePiatto + ".txt" esiste, altrimenti lo crea
			if (!ServizioFile.controlloEsistenzaFile(pathFilePiatto)) {
				ServizioFile.creaFile(pathFilePiatto);
				aggiungiValiditaPiatto(pathCompletoFileRistorante, piatto);
				confPiatto.salvaIstanzaOggetto(piatto, pathFilePiatto);
			} else {
				piatto = confPiatto.caricaIstanzaOggettoDaFile(pathFilePiatto);
				System.out.printf("Periodo di validità del piatto %s:\n", piatto.getNome());
				PeriodoView periodoView = new PeriodoView(piatto.getValidita());
				periodoView.mostraDescrizionePeriodo();

				boolean scelta = InputDati.yesOrNo(MSG_ALTRI_GIORNI_PIATTO_E_MENU_T);
				while (scelta) {
					aggiungiValiditaPiatto(pathCompletoFileRistorante, piatto);
					scelta = InputDati.yesOrNo(MSG_ALTRI_GIORNI_PIATTO_E_MENU_T);
				}
				confPiatto.salvaIstanzaOggetto(piatto, pathFilePiatto);
			}
			ristorante.aggiungiPiatto(piatto);
		}
	}

	private void aggiungiValiditaPiatto (String pathCompletoFileRistorante, Piatto piatto) {
		PiattoView piattoView = new PiattoView (piatto);
		piattoView.mostraDescrizioneNomePiatto();

		Periodo validita = PeriodoView.creaPeriodoValidita();
		Periodo newValidita = Periodo.unisciPeriodi(validita, piatto.getValidita());
		piatto.setValidita(newValidita);

		String pathDirectoryCalendario = CreazioneDirectory.creaDirectoryCalendario(pathCompletoFileRistorante);

		for (Giorno giorno : piatto.getValidita().getPeriodoValidita()) {
			String pathDirectoryGiornata = CreazioneDirectory.creaDirectoryGiornata(giorno, pathDirectoryCalendario);
			String nomeDirectoryMenuCarta = CreazioneDirectory.creaSubDirectoryMenuCarta(pathDirectoryGiornata);

			//crea il file del piatto nella cartella dei menu alla carta
			String pathPiattoMenuCarta = CreazioneFile.creaFilePiatto(piatto, nomeDirectoryMenuCarta);

			//salva il file del piatto nella cartella del menu alla carta di ogni giorno in cui è valido
			confPiatto.salvaIstanzaOggetto(piatto, pathPiattoMenuCarta);
		}
	}


	public void verificaCorrispondenzaPiattoRicetta(String pathCompletoFileRistorante){
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryRicettario = CreazioneDirectory.creaDirectoryRicettario(pathCompletoFileRistorante);
		HashSet<Ricetta> ricettario = CreazioneOggetti.creaRicettario(pathDirectoryRicettario);
		ristorante.setRicettario(ricettario);

		String nome = InputDati.leggiStringaNonVuota(MSG_NOME_RICERCA_PIATTO);

		if (Ricetta.trovaRicetta(nome, ristorante.getRicettario())!=null) {
			System.out.println(MSG_SI_CORRISPONDENZA);
		} else {
			System.out.println(MSG_ERR_NO_CORRISPONDENZA);
		}
	}

	public void creaMenuTematico(VisualizzatoreGestione visualizzatoreGestione, String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryMenuTematici = CreazioneDirectory.creaDirectoryMenuTematici(pathCompletoFileRistorante);
		String pathDirectoryPiatti = CreazioneDirectory.creaDirectoryPiatti(pathCompletoFileRistorante);

		HashSet<Piatto> piatti = CreazioneOggetti.creaPiatti(pathDirectoryPiatti);
		ristorante.setPiatti(piatti);

		MenuTematico nuovo = MenuTematicoView.creaMenuTematico(visualizzatoreGestione, pathCompletoFileRistorante, 
				ristorante);

		String pathFileMenuTematico = CreazioneFile.creaFileMenuTematico(pathDirectoryMenuTematici, nuovo.getNome());
		//salviamo il file nella cartella contenente tutti i menu tematici
		confMenuT.salvaIstanzaOggetto(nuovo, pathFileMenuTematico);

		//salviamo il file nella cartella della giornata contenente tutti i menu tematici
		String pathDirectoryCalendario = CreazioneDirectory.creaDirectoryCalendario(pathCompletoFileRistorante);

		for (Giorno giorno : nuovo.getValidita().getPeriodoValidita()) {
			String pathDirectoryGiornata = CreazioneDirectory.creaDirectoryGiornata(giorno, pathDirectoryCalendario);
			String pathSubDirectoryMenuTematici = CreazioneDirectory.creaSubDirectoryMenuTematici(pathDirectoryGiornata);
			String pathFileMenuT = CreazioneFile.creaFileMenuTematico(pathSubDirectoryMenuTematici, nuovo.getNome());
			//salva il file del piatto nella cartella del menu tematico di ogni giorno in cui è valido
			confMenuT.salvaIstanzaOggetto(nuovo, pathFileMenuT);
		}
	}

	public HashSet<MenuTematico> ottieniMenuTematici(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryMenuTematici = CreazioneDirectory.creaDirectoryMenuTematici(pathCompletoFileRistorante);
		HashSet<MenuTematico> menuT = CreazioneOggetti.creaMenuTematici(pathDirectoryMenuTematici);
		ristorante.setMenuTematici(menuT);
		return menuT;
	}

	public void aggiuntaPeriodoValiditaMenuTematico(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryMenuTematici = CreazioneDirectory.creaDirectoryMenuTematici(pathCompletoFileRistorante);
		String pathDirectoryPiatti = CreazioneDirectory.creaDirectoryPiatti(pathCompletoFileRistorante);

		HashSet<MenuTematico> menuTRistorante = ottieniMenuTematici(pathCompletoFileRistorante);
		ristorante.setMenuTematici(menuTRistorante);

		ElencoMenuTematiciView elencoMenuTematiciView = new ElencoMenuTematiciView(menuTRistorante);
		elencoMenuTematiciView.mostraDescrizioneNome_PeriodMenuTematici();

		boolean scelta = false;

		String nomeMenuT = InputDati.leggiStringaNonVuota(MSG_NOME_T_AGGIUNTA_GIORNI);
		MenuTematico menuTematicoScelto = MenuTematico.trovaMenuTDaNome(nomeMenuT, ristorante.getMenuTematici());
		MenuTematicoView menuTematicoSceltoView = new MenuTematicoView (menuTematicoScelto);
		do {			
			menuTematicoSceltoView.aggiungiValiditaMenuTematico();
			menuTematicoScelto = menuTematicoSceltoView.getMenuTematico();
			scelta = InputDati.yesOrNo(MSG_ALTRI_GIORNI_PIATTO_E_MENU_T);
		} while(scelta);

		//salvataggio menu tematici
		String pathFileMenuTematico = CreazioneFile.creaFileMenuTematico(pathDirectoryMenuTematici, menuTematicoScelto.getNome());
		//salviamo il file nella cartella contenente tutti i menu tematici
		confMenuT.salvaIstanzaOggetto(menuTematicoScelto, pathFileMenuTematico);

		//salviamo il file nella cartella della giornata contenente tutti i menu tematici
		String pathDirectoryCalendario = CreazioneDirectory.creaDirectoryCalendario(pathCompletoFileRistorante);

		for (Giorno giorno : menuTematicoScelto.getValidita().getPeriodoValidita()) {
			String pathDirectoryGiornata = CreazioneDirectory.creaDirectoryGiornata(giorno, pathDirectoryCalendario);
			String pathSubDirectoryMenuTematici = CreazioneDirectory.creaSubDirectoryMenuTematici(pathDirectoryGiornata);
			String pathFileMenuT = CreazioneFile.creaFileMenuTematico(pathSubDirectoryMenuTematici, menuTematicoScelto.getNome());
			//salva il file del piatto nella cartella del menu tematico di ogni giorno in cui è valido
			confMenuT.salvaIstanzaOggetto(menuTematicoScelto, pathFileMenuT);
		}

		//salvataggio file piatti
		//salvataggio nella cartella Piatti 
		for (Piatto piatto : menuTematicoScelto.getElenco()) {
			String pathFilePiattoMenuTematico = CreazioneFile.creaFilePiatto(piatto, pathDirectoryPiatti);
			confPiatto.salvaIstanzaOggetto(piatto, pathFilePiattoMenuTematico);
		}

		//salvataggio dei file piatti nelle cartelle dei menu alla carta per ogni giorno del calendario in cui è valido un menuTematico
		for (Giorno giorno : menuTematicoScelto.getValidita().getPeriodoValidita()) {
			String pathDirectoryGiornata = CreazioneDirectory.creaDirectoryGiornata(giorno, pathDirectoryCalendario);
			String pathSubDirectoryMenuCarta = CreazioneDirectory.creaSubDirectoryMenuCarta(pathDirectoryGiornata);
			for (Piatto piatto : menuTematicoScelto.getElenco()) {
				String pathFilePiattoMenuT = CreazioneFile.creaFilePiatto(piatto, pathSubDirectoryMenuCarta);
				//salva il file del piatto nella cartella del menu alla carta di ogni giorno in cui è valido
				confPiatto.salvaIstanzaOggetto(piatto, pathFilePiattoMenuT);
			}
		}
	}

}
