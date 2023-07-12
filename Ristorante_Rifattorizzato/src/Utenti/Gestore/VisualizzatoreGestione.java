package Utenti.Gestore;

import java.util.HashSet;

import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.InsiemeExtra;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;
import Ristorante.ElementiRistorante.ElementiRistorantiView.ElencoMenuTematiciView;
import Ristorante.ElementiRistorante.ElementiRistorantiView.InsiemeExtraView;
import Ristorante.ElementiRistorante.ElementiRistorantiView.MenuTematicoView;
import Ristorante.ElementiRistorante.ElementiRistorantiView.PiattiView;
import Ristorante.ElementiRistorante.ElementiRistorantiView.RicettaView;
import Ristorante.ElementiRistorante.ElementiRistorantiView.RicettarioView;
import Ristorante.RistoranteView.RistoranteView;
import Util.InputDati;
import Util.GestioneFile.CreazioneDirectory;
import Util.GestioneFile.CreazioneFile;
import Util.GestioneFile.CreazioneOggetti;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreManager;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRistorante;

public class VisualizzatoreGestione {
	static final String MSG_NOME_RICETTA = "Inserisci il nome della ricetta da visualizzare: ";
	static final String MSG_ERR_RICETTA = "Non esiste una ricetta con questo nome.";
	static final String MSG_NOME_NUOVA_RICETTA = "Inserisci di nuovo il nome della ricetta: ";

	static final String MSG_NOME_MENU_T = "Inserisci il nome del menu tematico da visualizzare: ";
	static final String MSG_ERR_NOME_MENU_T= "ATTENZIONE! Il menu inserito non esiste";

	private ConfiguratoreManager<Ristorante> confRistorante;

	public VisualizzatoreGestione() {
		this.confRistorante = new ConfiguratoreRistorante();
	}

	public void visualizzaRistorante(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);
		RistoranteView ristoranteView = new RistoranteView(ristorante.getNome());
		ristoranteView.mostraDescrizioneRistorante();
	}

	public void visualizzaInsiemeBevande(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryInsiemiExtra = CreazioneDirectory.creaDirectoryInsiemiExtra(pathCompletoFileRistorante);
		String pathFileBevande = CreazioneFile.creaFileInsiemeBevande(pathDirectoryInsiemiExtra);

		InsiemeExtra insiemeB = CreazioneOggetti.creaInsiemeExtra(pathFileBevande);
		ristorante.setInsiemeB(insiemeB);

		InsiemeExtraView insiemeExtraView = new InsiemeExtraView(insiemeB);
		insiemeExtraView.mostraDescrizioneInsiemeExtra();
	}

	public void visualizzaInsiemeGeneriExtra(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryInsiemiExtra = CreazioneDirectory.creaDirectoryInsiemiExtra(pathCompletoFileRistorante);
		String pathFileGeneriExtra = CreazioneFile.creaFileInsiemeGeneriExtra(pathDirectoryInsiemiExtra);

		InsiemeExtra insiemeGE = CreazioneOggetti.creaInsiemeExtra(pathFileGeneriExtra);
		ristorante.setInsiemeGE(insiemeGE);

		InsiemeExtraView insiemeExtraView = new InsiemeExtraView(insiemeGE);
		insiemeExtraView.mostraDescrizioneInsiemeExtra();
	}

	public void visualizzaRicetta(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryRicettario = CreazioneDirectory.creaDirectoryRicettario(pathCompletoFileRistorante);
		HashSet<Ricetta> ricettario = CreazioneOggetti.creaRicettario(pathDirectoryRicettario);
		ristorante.setRicettario(ricettario);

		visualizzaNomiRicettario(pathCompletoFileRistorante);

		String ricettaScelta = InputDati.leggiStringaNonVuota(MSG_NOME_RICETTA);
		boolean trovata = true;
		do {
			Ricetta ricettaTrovata = Ricetta.trovaRicetta(ricettaScelta, ristorante.getRicettario());
			RicettaView ricettaTrovataView = new RicettaView (ricettaTrovata);
			if (ricettaTrovata != null) {
				//ritornare la ricetta
				ricettaTrovataView.mostraDescrizioneRicetta();
				trovata = false;
			} else {
				//la ricetta non è nel ricettario
				System.out.println(MSG_ERR_RICETTA);
				ricettaScelta = InputDati.leggiStringaNonVuota(MSG_NOME_NUOVA_RICETTA);
			}
		} while (trovata);
	}

	public void visualizzaNomiRicettario(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryRicettario = CreazioneDirectory.creaDirectoryRicettario(pathCompletoFileRistorante);
		HashSet<Ricetta> ricettario = CreazioneOggetti.creaRicettario(pathDirectoryRicettario);

		RicettarioView ricettarioView = new RicettarioView(ricettario);
		ricettarioView.mostraDescrizioneNomiRicettario();

		ristorante.setRicettario(ricettario);
	}

	public void visualizzaInfoRicette (String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryRicettario = CreazioneDirectory.creaDirectoryRicettario(pathCompletoFileRistorante);
		HashSet<Ricetta> ricettario = CreazioneOggetti.creaRicettario(pathDirectoryRicettario);

		RicettarioView ricettarioView = new RicettarioView (ricettario);
		ricettarioView.mostraDescrizioneRicettario();

		ristorante.setRicettario(ricettario);
	}

	public void visualizzaNomiMenuTematici(Gestore gestore, String pathCompletoFileRistorante) {
		HashSet<MenuTematico> menuTRistorante = gestore.ottieniMenuTematici(pathCompletoFileRistorante);
		ElencoMenuTematiciView elencoMenuTematiciView = new ElencoMenuTematiciView(menuTRistorante);
		elencoMenuTematiciView.mostraDescrizioneNomeMenuTematici();
	}
	
	public void visualizzaNomi_PeriodoMenuTematici(Gestore gestore, String pathCompletoFileRistorante) {
		HashSet<MenuTematico> menuTRistorante = gestore.ottieniMenuTematici(pathCompletoFileRistorante);
		ElencoMenuTematiciView elencoMenuTematiciView = new ElencoMenuTematiciView(menuTRistorante);
		elencoMenuTematiciView.mostraDescrizioneNome_PeriodMenuTematici();
	}

	public void visualizzaPiatti(String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryPiatti = CreazioneDirectory.creaDirectoryPiatti(pathCompletoFileRistorante);
		HashSet<Piatto> piatti = CreazioneOggetti.creaPiatti(pathDirectoryPiatti);

		ristorante.setPiatti(piatti);

		PiattiView piattiView = new PiattiView (piatti);
		piattiView.mostraDescrizionePiatti();
	}

	public void visualizzaInfoMenuTematici(Gestore gestore, String pathCompletoFileRistorante) {
		HashSet<MenuTematico> menuTRistorante = gestore.ottieniMenuTematici(pathCompletoFileRistorante);
		ElencoMenuTematiciView elencoMenuTematiciView = new ElencoMenuTematiciView(menuTRistorante);
		elencoMenuTematiciView.mostraDescrizioneMenuTematici();
	}

	public void visualizzaMenuTematico(Gestore gestore, String pathCompletoFileRistorante) {
		Ristorante ristorante = confRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		HashSet<MenuTematico> menuTRistorante = gestore.ottieniMenuTematici(pathCompletoFileRistorante);
		ristorante.setMenuTematici(menuTRistorante);

		visualizzaNomiMenuTematici(gestore, pathCompletoFileRistorante);

		boolean trovato = true;
		do {
			String ricerca = InputDati.leggiStringaNonVuota(MSG_NOME_MENU_T);	
			MenuTematico menuTematico = MenuTematico.trovaMenuTDaNome(ricerca, ristorante.getMenuTematici());
			if (menuTematico != null) {
				MenuTematicoView menuTematicoView = new MenuTematicoView(menuTematico);
				menuTematicoView.mostraDescrizioneMenuTematico();
				trovato = false;
			} else {
				System.out.println(MSG_ERR_NOME_MENU_T);
			}
		} while (trovato);		
	}

}
