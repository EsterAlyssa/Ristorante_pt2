package Utenti.AddettoPrenotazioni;

import java.util.HashSet;
import java.util.TreeSet;

import Giorno.Giorno;
import Prenotazioni.Prenotazione;
import Prenotazioni.PrenotazioneView;
import Prenotazioni.SceltaPrenotazione;
import Ristorante.Giornata;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.MenuCarta;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.ElementiRistorantiView.ElencoMenuTematiciView;
import Ristorante.ElementiRistorante.ElementiRistorantiView.MenuCartaView;
import Util.InputDati;
import Util.GestioneFile.Aggiornamento;
import Util.GestioneFile.CreazioneDirectory;
import Util.GestioneFile.CreazioneFile;
import Util.GestioneFile.CreazioneOggetti;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreManager;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRistorante;

public class GestorePrenotazioni {

	static final String MSG_ERR_MIN_SCELTE = "Vanno inserite almeno altre %d scelte.\n";
	static final String MSG_RICHIESTA_ALTRE_SCELTE = "Vuoi inserire altri elementi in questa prenotazione?";
	static final String MSG_NUM_SCELTA = "Inserire per quante persone vale la scelta: ";
	static final String MSG_NOME_SCELTA = "Inserire il nome del menu tematico o del piatto scelto: ";
	static final String MSG_ERRORE_ACCETTAZIONE = "La prenotazione non si può accettare";
	static final String MSG_SUCCESSO_ACCETTAZIONE = "La prenotazione è avvenuta con successo";

	private ConfiguratoreManager<Ristorante> configuratoreRistorante;

	public GestorePrenotazioni() {
		this.configuratoreRistorante = new ConfiguratoreRistorante();
	}

	public void accettazionePrenotazione(String pathCompletoFileRistorante) {
		Ristorante ristorante = configuratoreRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		Prenotazione prenotazione = PrenotazioneView.creaPrenotazioneVuota(ristorante.getNumPosti());
		Giorno dataPrenotazione = prenotazione.getData();

		String pathCalendario = CreazioneDirectory.creaDirectoryCalendario(pathCompletoFileRistorante);
		String pathGiornata = CreazioneDirectory.creaDirectoryGiornata(dataPrenotazione, pathCalendario);
		String pathDirectoryPrenotazioni = CreazioneDirectory.creaSubDirectoryPrenotazioni(pathGiornata);
		String pathDirectoryMenuCarta = CreazioneDirectory.creaSubDirectoryMenuCarta(pathGiornata);
		String pathDirectoryMenuTematici = CreazioneDirectory.creaSubDirectoryMenuTematici(pathGiornata);

		aggiungiScelte(pathCompletoFileRistorante, prenotazione, pathDirectoryMenuCarta, pathDirectoryMenuTematici);

		Aggiornamento.aggiornamentoCalendario(ristorante, pathCalendario);

		TreeSet<Giornata> calendario = ristorante.getCalendario();

		int postiRimasti = ristorante.getNumPosti();

		for (Giornata giornata : calendario) {
			if (giornata.getGiorno().compareTo(dataPrenotazione)==0) {
				if (controlloVincoli(giornata.numCopertiPrenotati(), postiRimasti, prenotazione, 
						ristorante.getCaricoLavoroRistorante())) {
					giornata.getPrenotazioni().add(prenotazione);

					CreazioneFile.creaFilePrenotazione(prenotazione, pathDirectoryPrenotazioni);

					postiRimasti -= prenotazione.getNumCoperti();
					System.out.println(MSG_SUCCESSO_ACCETTAZIONE);

				} else {
					System.out.println(MSG_ERRORE_ACCETTAZIONE);
				}
			}
		}
	}

	public void aggiungiScelte(String pathCompletoFileRistorante, Prenotazione prenotazione, 
			String pathDirectoryMenuCarta, String pathDirectoryMenuTematici) {

		MenuCarta menuCarta = CreazioneOggetti.creaMenuCarta(pathDirectoryMenuCarta);
		MenuCartaView menuCartaView = new MenuCartaView(menuCarta);
		HashSet<MenuTematico> elencoMenuTematici = CreazioneOggetti.creaMenuTematici(pathDirectoryMenuTematici);
		ElencoMenuTematiciView menuTematiciView = new ElencoMenuTematiciView(elencoMenuTematici);
		int piattiMinDaInserire = prenotazione.getNumCoperti();
		boolean risposta = false;
		do {
			menuCartaView.mostraDescrizioneNomiPiattiMenu();
			menuTematiciView.mostraDescrizioneNomeMenuTematiciNomePiatti();;

			String nomeScelta = InputDati.leggiStringaNonVuota(MSG_NOME_SCELTA);
			int numScelta = InputDati.leggiInteroConMinimo(MSG_NUM_SCELTA, 1);

			HashSet<SceltaPrenotazione> insiemeTotale = new HashSet<>(elencoMenuTematici);
			insiemeTotale.addAll(menuCarta.getElenco());

			SceltaPrenotazione scelta = SceltaPrenotazione.trovaDaNome(nomeScelta, insiemeTotale);
			prenotazione.aggiungiScelta(scelta, numScelta);	

			risposta = InputDati.yesOrNo(MSG_RICHIESTA_ALTRE_SCELTE);
			piattiMinDaInserire -= numScelta;
			if (piattiMinDaInserire>0) {
				System.out.printf(MSG_ERR_MIN_SCELTE, piattiMinDaInserire);
				risposta = true;
			}
		} while (risposta);
	}

	public boolean controlloVincoli(int copertiGiornata, int postiRistorante, Prenotazione prenotazione, double caricoLavoroRistorante) {
		boolean cond1 = (copertiGiornata <= postiRistorante);
		double caricoLavoroPrenotazione = 0.0;
		for (SceltaPrenotazione scelta : prenotazione.getElenco().keySet()) {
			caricoLavoroPrenotazione = scelta.getCaricoLavoro()*prenotazione.getElenco().get(scelta);
		}
		boolean cond2 = caricoLavoroPrenotazione < caricoLavoroRistorante;
		return cond1 && cond2;
	}

}
