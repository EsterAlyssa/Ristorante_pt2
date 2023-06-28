package Utenti;

import Giorno.Periodo;
import Prenotazioni.Prenotazione;
import Prenotazioni.SceltaPrenotazione;
import Ristorante.Giornata;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.MenuCarta;
import Util.InputDati;
import Util.ServizioFile;
import Util.ConfigurazioneFile.ConfiguratoreRistorante;
import Util.ConfigurazioneFile.ConfiguratorePrenotazione;
import Util.ConfigurazioneFile.ConfiguratoreMenuCarta;
import Util.ConfigurazioneFile.ConfiguratoreMenuTematico;

import java.util.TreeSet;
import java.io.File;
import java.util.HashSet;
import java.util.List;

import Giorno.Giorno;


public class AddettoPrenotazioni extends Utente {

	private static String etichettaAP = "addetto alle prenotazioni";
	private static String[] voci = {"Accetta le prenotazioni", "Visualizza le prenotazioni dato il giorno"};

	public AddettoPrenotazioni(String nome) {
		super(nome, etichettaAP, voci);
	}

	public void accettazionePrenotazione(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Calendario";
		String pathCalendario = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory "Calendario" esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathCalendario);

		String messaggioSuccessoAccettazione = "La prenotazione è avvenuta con successo";
		String messaggioErrAccettazione = "La prenotazione non si può accettare";

		Prenotazione prenotazione = Prenotazione.creaPrenotazioneVuota(ristorante.getNumPosti());
		Giorno dataPrenotazione = prenotazione.getData();

		String nomeDirectoryGiornata = dataPrenotazione.toString();
		String pathGiornata = pathCalendario + "/" + nomeDirectoryGiornata;
		ServizioFile.creaDirectory(pathGiornata);

		String nomeDirectoryPrenotazioni = "Prenotazioni";
		String pathDirectoryPrenotazioni = pathGiornata + "/" + nomeDirectoryPrenotazioni;
		ServizioFile.creaDirectory(pathDirectoryPrenotazioni);

		String nomeDirectoryMenuCarta = "Menu alla carta";
		String pathDirectoryMenuCarta = pathGiornata + "/" + nomeDirectoryMenuCarta;
		ServizioFile.creaDirectory(pathDirectoryMenuCarta);

		String nomeDirectoryMenuTematici = "Menu Tematici";
		String pathDirectoryMenuTematici = pathGiornata + "/" + nomeDirectoryMenuTematici;
		ServizioFile.creaDirectory(pathDirectoryMenuTematici);

		aggiungiScelte(pathCompletoFileRistorante, prenotazione, pathDirectoryMenuCarta, pathDirectoryMenuTematici);

		List<File> elencoDirGiornate = ServizioFile.getElencoDirectory(pathCalendario);

		TreeSet<Giornata> calendarioNoParam = new TreeSet<>();
		for (File file : elencoDirGiornate) {
			Giornata giornata = new Giornata(file.getName());
			calendarioNoParam.add(giornata);
		}
		ristorante.setCalendario(calendarioNoParam);

		HashSet<Prenotazione> prenotazioni = new HashSet<>();
		HashSet<Piatto> menuCarta = new HashSet<>();
		HashSet<MenuTematico> menuTematici = new HashSet<>();

		for (File file : elencoDirGiornate) {
			List<File> elencoDir1Giornata = ServizioFile.getElencoDirectory(file.getAbsolutePath());
			for (File f : elencoDir1Giornata) {
				String nomeCartella = f.getName();
				switch (nomeCartella) {
				case "Prenotazioni":
					ConfiguratorePrenotazione confPren = new ConfiguratorePrenotazione();
					for (File filePren : ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella)) {
						Prenotazione pren = (Prenotazione) confPren.caricaIstanzaOggettoDaFile(filePren.getAbsolutePath());
						prenotazioni.add(pren);
					}
					Giornata giornataVecchiaP = ristorante.getGiornata(Giorno.parseGiorno(nomeCartella));
					ristorante.getCalendario().remove(giornataVecchiaP);
					giornataVecchiaP.setPrenotazioni(prenotazioni); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaP); //giornata vecchia che è diventata nuova

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

					Giornata giornataVecchiaMC = ristorante.getGiornata(Giorno.parseGiorno(nomeCartella));
					ristorante.getCalendario().remove(giornataVecchiaMC);
					giornataVecchiaMC.setMenuCarta(menu); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaMC); //giornata vecchia che è diventata nuova
					break;
				case "Menu Tematici":
					ConfiguratoreMenuTematico confMT = new ConfiguratoreMenuTematico();
					for (File fileMT : ServizioFile.getElencoFileTxt(f.getAbsolutePath()+"/"+nomeCartella)) {
						MenuTematico menuT = (MenuTematico) confMT.caricaIstanzaOggettoDaFile(fileMT.getAbsolutePath());
						menuTematici.add(menuT);
					}

					Giornata giornataVecchiaMT = ristorante.getGiornata(Giorno.parseGiorno(nomeCartella));
					ristorante.getCalendario().remove(giornataVecchiaMT);
					giornataVecchiaMT.setMenuTematici(menuTematici); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaMT); //giornata vecchia che è diventata nuova
					break;
				}
			}
		} //fine aggiornamento calendario da file


		TreeSet<Giornata> calendario = ristorante.getCalendario();

		int postiRimasti = ristorante.getNumPosti();

		for (Giornata giornata : calendario) {
			if (giornata.getGiorno().equals(dataPrenotazione)) {
				if (controlloVincoli(giornata.numCopertiPrenotati(), postiRimasti, prenotazione, ristorante.getCaricoLavoroRistorante())) {
					giornata.getPrenotazioni().add(prenotazione);

					String nomePrenotazione = prenotazione.getCliente()+"_"+prenotazione.getNumCoperti() + ".txt";
					String pathPrenotazione = pathDirectoryPrenotazioni + "/" + nomePrenotazione;

					// Controlla se il file esiste, altrimenti lo crea
					if (!ServizioFile.controlloEsistenzaFile(pathPrenotazione)) {
						ServizioFile.creaFile(pathPrenotazione);
					}

					//salva il file della prenotazione nella cartella delle prenotazioni del giorno per cui si prenota
					ConfiguratorePrenotazione confPren = new ConfiguratorePrenotazione();
					confPren.salvaIstanzaOggetto(prenotazione, pathPrenotazione);

					postiRimasti-=prenotazione.getNumCoperti();
					System.out.println(messaggioSuccessoAccettazione);

				} else {
					System.out.println(messaggioErrAccettazione);
				}
			}
		}
	}

	public void aggiungiScelte(String pathCompletoFileRistorante, Prenotazione prenotazione, 
			String pathDirectoryMenuCarta, String pathDirectoryMenuTematici) {

		List<File> menuCarta = ServizioFile.getElencoFileTxt(pathDirectoryMenuCarta);
		HashSet<Piatto> piattiMenuCarta = new HashSet<>();
		ConfiguratoreMenuCarta confMC = new ConfiguratoreMenuCarta();
		for (File fileMenuCarta : menuCarta) {
			Piatto piatto = (Piatto) confMC.caricaIstanzaOggettoDaFile(fileMenuCarta.getAbsolutePath());
			piattiMenuCarta.add(piatto);
		}

		List<File> menuTematici = ServizioFile.getElencoFileTxt(pathDirectoryMenuTematici);
		HashSet<MenuTematico> elencoMenuTematici = new HashSet<>();
		ConfiguratoreMenuTematico confMT = new ConfiguratoreMenuTematico();
		for (File fileMenuTematici : menuTematici) {
			MenuTematico menuTematico = (MenuTematico) confMT.caricaIstanzaOggettoDaFile(fileMenuTematici.getAbsolutePath());
			elencoMenuTematici.add(menuTematico);
		}

		String messaggioNomeScelta = "Inserire il nome del menu tematico o del piatto scelto: ";
		String messaggioNumScelta = "Inserire per quante persone vale la scelta: ";
		String messaggioRichiestaAltreScelte = "Vuoi inserire altri elementi in questa prenotazione?";

		String messaggioErrAltriPiatti = "Vanno inseriti almeno altre %d scelte";

		boolean risposta = false;
		do {
			System.out.println("Menu alla carta:");
			for (Piatto piatto : piattiMenuCarta) {
				System.out.println(piatto.getNome());
			}

			System.out.println("Menu Tematici:");
			for (MenuTematico menu : elencoMenuTematici) {
				System.out.println(menu.toString()+'\n');
			}

			String nomeScelta = InputDati.leggiStringaNonVuota(messaggioNomeScelta);
			int numScelta = InputDati.leggiInteroConMinimo(messaggioNumScelta, 1);

			//	*aggiungi SceltaPrenotazione all'hashmap della prenotazione*
			HashSet<SceltaPrenotazione> insiemeTotale = new HashSet<>(elencoMenuTematici);
			insiemeTotale.addAll(piattiMenuCarta);

			SceltaPrenotazione scelta = SceltaPrenotazione.trovaDaNome(nomeScelta, insiemeTotale);
			if (scelta!=null) {
				prenotazione.addScelta(scelta, numScelta);	
			}

			risposta = InputDati.yesOrNo(messaggioRichiestaAltreScelte);
			int piattiMinDaInserire= numScelta-prenotazione.getNumCoperti();
			if (piattiMinDaInserire>0) {
				System.out.printf(messaggioErrAltriPiatti, piattiMinDaInserire);
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

		if(cond1 & cond2) {
			return true;
		} else		
			return false;
	}

	public void visualizzaPrenotazioni(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String messaggioGiornata = "Inserire la giornata di cui si vuole vedere le prenotazioni";

		System.out.println(messaggioGiornata);
		Giorno giornoScelto = Giorno.richiestaCreaGiorno();

		for (Giornata giornata : ristorante.getCalendario()) {
			if (giornoScelto.equals(giornata.getGiorno())) {
				System.out.println(giornata.stampaPrenotazioni());
			}
		}
	}

	@Override
	public void eseguiMetodi(int scelta, String pathCompletoFileRistorante) {
		switch (scelta) {
		case 1: 
			accettazionePrenotazione(pathCompletoFileRistorante);
			break;
		case 2:
			visualizzaPrenotazioni(pathCompletoFileRistorante);
			break;
		}
	}

}
