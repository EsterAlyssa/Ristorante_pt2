package Ristorante.ElementiRistorante.ElementiRistorantiView;

import Giorno.Giorno;
import Giorno.Periodo;
import Giorno.GiornoView.GiornoView;
import Giorno.GiornoView.PeriodoView;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Utenti.Gestore.VisualizzatoreGestione;
import Util.InputDati;

public class MenuTematicoView extends MenuView{

	static final String MSG_NOME_MENU_T = "Inserisci il nome del menu tematico da creare: ";
	static final String MSG_NOME_PIATTO_MENU_T = "Inserisci il nome del piatto da aggiungere al menu tematico: ";
	static final String MSG_ERR_VALIDITA_PIATTO_MENU_T = "ATTENZIONE! Il piatto scelto non è valido per i giorni selezionati.";
	static final String MSG_ERR_CARICO_LAVORO_PIATTO_MENU_T = "ATTENZIONE! Con questo piatto il carico di lavoro è troppo alto.";
	static final String MSG_ERR_NO_PIATTO = "Non è stato trovato nessun piatto con questo nome";
	static final String MSG_ALTRI_PIATTI_MENU_T = "Vuoi inserire altri piatti? ";

	static final String MSG_AGGIUNGI_GIORNO_VALIDO = "Inserisci il giorno di validita' da aggiungere:\n";
	static final String MSG_ALTRI_GIORNI_PERIODO_PIATTI = "Vuoi aggiungere il giorno ai periodi di validita' dei piatti?";

	private MenuTematico menuTematico;

	public MenuTematicoView(MenuTematico menuTematico) {
		super(menuTematico);
		this.menuTematico = menuTematico;
	}

	public MenuTematico getMenuTematico() {
		return menuTematico;
	}

	public static MenuTematico creaMenuTematico(VisualizzatoreGestione visualizzatoreGestione,
			String pathCompletoFileRistorante, Ristorante ristorante) {

		String nomeMenuT = InputDati.leggiStringaNonVuota(MSG_NOME_MENU_T);
		Periodo validitaMenuT = PeriodoView.creaPeriodoValidita();
		MenuTematico nuovo = new MenuTematico(nomeMenuT, validitaMenuT);

		boolean scelta = true;
		do {
			visualizzatoreGestione.visualizzaNomiRicettario(pathCompletoFileRistorante);
			String nomePiatto = InputDati.leggiStringaNonVuota(MSG_NOME_PIATTO_MENU_T);
			Piatto piattoTrovato = Piatto.trovaPiattoDaNome(nomePiatto, ristorante.getPiatti());
			if (piattoTrovato != null) {
				double CLP = piattoTrovato.getCaricoLavoro();
				double CLM = nuovo.getCaricoLavoro();
				double CLPersona = ristorante.getCaricoLavoroPersona();
				if ((CLP+CLM) <= (4/3)*CLPersona) {
					if (piattoTrovato.getValidita().getPeriodoValidita().containsAll(nuovo.getValidita().getPeriodoValidita())) {
						nuovo.aggiungiPiatto(piattoTrovato);
						scelta = InputDati.yesOrNo(MSG_ALTRI_PIATTI_MENU_T);
					} else {
						System.out.println(MSG_ERR_VALIDITA_PIATTO_MENU_T);
					}
				} else {
					System.out.println(MSG_ERR_CARICO_LAVORO_PIATTO_MENU_T);
				}
			} else {
				System.out.println(MSG_ERR_NO_PIATTO);
			}
		} while (scelta);
		ristorante.aggiungiMenuTematico(nuovo);
		return nuovo;
	}

	public String descrizioneMenuTematico() {
		String daTornare = "Menu Tematico: " + menuTematico.getNome() +
				"\nCarico di lavoro del menu tematico: " + menuTematico.getCaricoLavoro();
		daTornare += "\n" + super.descrizioneMenu();
		return daTornare;
	}

	public void mostraDescrizioneMenuTematico() {
		System.out.println(descrizioneMenuTematico());
	}

	public String descrizioneMenuTematicoNomiPiatti() {
		String daTornare = "Menu Tematico: " + menuTematico.getNome() + "\n" + 
				super.descrizioneNomiPiattiMenu();
		return daTornare;
	}

	public void mostraDescrizioneMenuTematicoNomiPiatti() {
		System.out.println(descrizioneMenuTematico());
	}

	public String descrizioneNomeMenuTematico() {		
		return "Menu tematico: " + menuTematico.getNome();
	}

	public void mostraDescrizioneNomeMenuTematico() {
		System.out.println(descrizioneNomeMenuTematico());
	}

	public String descrizioneNome_PeriodoMenuTematico() {		
		PeriodoView periodoView = new PeriodoView(menuTematico.getValidita());
		return descrizioneNomeMenuTematico() + ", periodo validita':\n" + periodoView.descrizionePeriodo();
	}

	public void mostraDescrizioneNome_PeriodoMenuTematico() {
		System.out.println(descrizioneNome_PeriodoMenuTematico());
	}

	public void aggiungiValiditaMenuTematico () {
		System.out.println( MSG_AGGIUNGI_GIORNO_VALIDO);
		Giorno giorno = GiornoView.richiestaCreaGiorno() ;
		boolean validitaTrovata = false;
		boolean scelta = true;
		for (Piatto piatto : menuTematico.getElenco()) {
			for (Giorno giornoP : piatto.getValidita().periodoValidita) {
				if (giornoP.compareTo(giorno) == 0) {
					validitaTrovata = true;
					break;
				} else {
					validitaTrovata = false;
				}
			}
		}
		if (!validitaTrovata) {
			scelta = InputDati.yesOrNo(MSG_ALTRI_GIORNI_PERIODO_PIATTI);
			if (scelta) {
				for (Piatto piatto : menuTematico.getElenco()) {
					piatto.getValidita().periodoValidita.add(giorno);
				}
			}
		}
		if (validitaTrovata && scelta) {
			menuTematico.aggiungiGiornoValido(giorno);
		}
	}
}
