package Utenti.AddettoPrenotazioni;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import Giorno.Giorno;
import Prenotazioni.Prenotazione;
import Prenotazioni.PrenotazioneView;
import Prenotazioni.SceltaPrenotazione;
import Ristorante.Giornata;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Util.InputDati;
import Util.GestioneFile.AggiornamentoCalendario;
import Util.GestioneFile.CreazioneDirectory;
import Util.GestioneFile.CreazioneFile;
import Util.GestioneFile.CreazioneInsiemi;
import Util.GestioneFile.ServizioFile;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRistorante;

public class GestorePrenotazioni {

	private static final String MSG_ERR_MIN_SCELTE = "Vanno inseriti almeno altre %d scelte";
	private static final String MSG_RICHIESTA_ALTRE_SCELTE = "Vuoi inserire altri elementi in questa prenotazione?";
	private static final String MSG_NUM_SCELTA = "Inserire per quante persone vale la scelta: ";
	private static final String MSG_NOME_SCELTA = "Inserire il nome del menu tematico o del piatto scelto: ";
	private static final String MSG_ERRORE_ACCETTAZIONE = "La prenotazione non si può accettare";
	private static final String MSG_SUCCESSO_ACCETTAZIONE = "La prenotazione è avvenuta con successo";

	private ConfiguratoreRistorante configuratoreRistorante;

	public GestorePrenotazioni() {
		this.configuratoreRistorante = new ConfiguratoreRistorante();
	}

	public void accettazionePrenotazione(String pathCompletoFileRistorante) {
		Ristorante ristorante = (Ristorante) configuratoreRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		Prenotazione prenotazione = PrenotazioneView.creaPrenotazioneVuota(ristorante.getNumPosti());
		Giorno dataPrenotazione = prenotazione.getData();

		String pathCalendario = CreazioneDirectory.creaDirectoryCalendario(pathCompletoFileRistorante);
		String pathGiornata = CreazioneDirectory.creaDirectoryGiornata(dataPrenotazione, pathCalendario);
		String pathDirectoryPrenotazioni = CreazioneDirectory.creaSubDirectoryPrenotazioni(pathGiornata);
		String pathDirectoryMenuCarta = CreazioneDirectory.creaSubDirectoryMenuCarta(pathGiornata);
		String pathDirectoryMenuTematici = CreazioneDirectory.creaSubDirectoryMenuTematici(pathGiornata);

		aggiungiScelte(pathCompletoFileRistorante, prenotazione, pathDirectoryMenuCarta, pathDirectoryMenuTematici);

		List<File> elencoDirGiornate = ServizioFile.getElencoDirectory(pathCalendario);

		AggiornamentoCalendario.aggiornamentoCalendario(ristorante, elencoDirGiornate);

		TreeSet<Giornata> calendario = ristorante.getCalendario();

		int postiRimasti = ristorante.getNumPosti();

		for (Giornata giornata : calendario) {
			if (giornata.getGiorno().compareTo(dataPrenotazione)==0) {
				if (controlloVincoli(giornata.numCopertiPrenotati(), postiRimasti, prenotazione, ristorante.getCaricoLavoroRistorante())) {
					giornata.getPrenotazioni().add(prenotazione);

					CreazioneFile.creaFilePrenotazione(prenotazione, pathDirectoryPrenotazioni);

					postiRimasti-=prenotazione.getNumCoperti();
					System.out.println(MSG_SUCCESSO_ACCETTAZIONE);

				} else {
					System.out.println(MSG_ERRORE_ACCETTAZIONE);
				}
			}
		}
	}

	

	public void aggiungiScelte(String pathCompletoFileRistorante, Prenotazione prenotazione, 
			String pathDirectoryMenuCarta, String pathDirectoryMenuTematici) {

		HashSet<Piatto> piattiMenuCarta = CreazioneInsiemi.creaMenuCarta(pathDirectoryMenuCarta);

		HashSet<MenuTematico> elencoMenuTematici = CreazioneInsiemi.creaMenuTematici(pathDirectoryMenuTematici);

		boolean risposta = false;
		do {
			System.out.println("Menu alla carta:");
			for (Piatto piatto : piattiMenuCarta) {
				System.out.println(piatto.getNome());
			}

			System.out.println("Menu Tematici:");
			for (MenuTematico menu : elencoMenuTematici) {
				System.out.println(menu.descrizioneMenuTematico()+'\n');
			}

			String nomeScelta = InputDati.leggiStringaNonVuota(MSG_NOME_SCELTA);
			int numScelta = InputDati.leggiInteroConMinimo(MSG_NUM_SCELTA, 1);

			//	*aggiungi SceltaPrenotazione all'hashmap della prenotazione*
			HashSet<SceltaPrenotazione> insiemeTotale = new HashSet<>(elencoMenuTematici);
			insiemeTotale.addAll(piattiMenuCarta);

			SceltaPrenotazione scelta = SceltaPrenotazione.trovaDaNome(nomeScelta, insiemeTotale);
			if (scelta!=null) {
				prenotazione.aggiungiScelta(scelta, numScelta);	
			}

			risposta = InputDati.yesOrNo(MSG_RICHIESTA_ALTRE_SCELTE);
			int piattiMinDaInserire= numScelta-prenotazione.getNumCoperti();
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
