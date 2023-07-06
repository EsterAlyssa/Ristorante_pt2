package Utenti;

import Giorno.Periodo;
import Giorno.GiornoView.GiornoView;
import Prenotazioni.Prenotazione;
import Prenotazioni.PrenotazioneView;
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
import Util.ConfigurazioneFile.ConfiguratorePiatto;
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
		
		String messaggioSuccessoAccettazione = "La prenotazione è avvenuta con successo";
		String messaggioErrAccettazione = "La prenotazione non si può accettare";

		Prenotazione prenotazione = PrenotazioneView.creaPrenotazioneVuota(ristorante.getNumPosti());
		Giorno dataPrenotazione = prenotazione.getData();
		
		String pathCalendario = creaDirectoryCalendario(pathCompletoFileRistorante);

		String pathGiornata = creaDirectoryGiornata(dataPrenotazione, pathCalendario);

		String pathDirectoryPrenotazioni = creaSubDirectoryPrenotazioni(pathGiornata);

		String pathDirectoryMenuCarta = creaSubDirectoryMenuCarta(pathGiornata);

		String pathDirectoryMenuTematici = creaSubDirectoryMenuTematici(pathGiornata);

		aggiungiScelte(pathCompletoFileRistorante, prenotazione, pathDirectoryMenuCarta, pathDirectoryMenuTematici);

		List<File> elencoDirGiornate = ServizioFile.getElencoDirectory(pathCalendario);
		
		aggiornamentoCalendario(ristorante, elencoDirGiornate);

		TreeSet<Giornata> calendario = ristorante.getCalendario();

		int postiRimasti = ristorante.getNumPosti();

		for (Giornata giornata : calendario) {
			if (giornata.getGiorno().compareTo(dataPrenotazione)==0) {
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

	public String creaDirectoryGiornata(Giorno dataPrenotazione, String pathCalendario) {
		String nomeDirectoryGiornata = dataPrenotazione.descrizioneGiorno();
		String pathGiornata = pathCalendario + "/" + nomeDirectoryGiornata;
		ServizioFile.creaDirectory(pathGiornata);
		return pathGiornata;
	}

	public String creaSubDirectoryMenuTematici(String pathGiornata) {
		String nomeDirectoryMenuTematici = "Menu Tematici";
		String pathDirectoryMenuTematici = pathGiornata + "/" + nomeDirectoryMenuTematici;
		ServizioFile.creaDirectory(pathDirectoryMenuTematici);
		return pathDirectoryMenuTematici;
	}

	public String creaSubDirectoryMenuCarta(String pathGiornata) {
		String nomeDirectoryMenuCarta = "Menu alla carta";
		String pathDirectoryMenuCarta = pathGiornata + "/" + nomeDirectoryMenuCarta;
		ServizioFile.creaDirectory(pathDirectoryMenuCarta);
		return pathDirectoryMenuCarta;
	}

	public String creaSubDirectoryPrenotazioni(String pathGiornata) {
		String nomeDirectoryPrenotazioni = "Prenotazioni";
		String pathDirectoryPrenotazioni = pathGiornata + "/" + nomeDirectoryPrenotazioni;
		ServizioFile.creaDirectory(pathDirectoryPrenotazioni);
		return pathDirectoryPrenotazioni;
	}

	public String creaDirectoryCalendario(String pathCompletoFileRistorante) {
		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Calendario";
		String pathCalendario = pathDirectory + "/" + nomeDirectory;

		// Controlla se la directory "Calendario" esiste, altrimenti la crea
		ServizioFile.creaDirectory(pathCalendario);
		return pathCalendario;
	}

	public void aggiornamentoCalendario(Ristorante ristorante, List<File> elencoDirGiornate) {
		TreeSet<Giornata> calendarioNoParam = new TreeSet<>();
		for (File file : elencoDirGiornate) {
			Giornata giornata = new Giornata(file.getName());
			calendarioNoParam.add(giornata);
		}
		ristorante.setCalendario(calendarioNoParam); //il ristorante così ha le giornate vuote (solo con il giorno)

		HashSet<Prenotazione> prenotazioni = new HashSet<>();
		HashSet<Piatto> menuCarta = new HashSet<>();
		HashSet<MenuTematico> menuTematici = new HashSet<>();

		for (File file : elencoDirGiornate) { //per ogni cartella del calendario
			List<File> elencoDir1Giornata = ServizioFile.getElencoDirectory(file.getAbsolutePath());
			for (File f : elencoDir1Giornata) { //per ogni cartella in ogni cartella del calendario, quindi per ogni cartella giornata
				String nomeCartella = f.getName();
				switch (nomeCartella) {
				case "Prenotazioni":
					ConfiguratorePrenotazione confPren = new ConfiguratorePrenotazione();
					for (File filePren : ServizioFile.getElencoFileTxt(f.getPath())) {
						Prenotazione pren = (Prenotazione) confPren.caricaIstanzaOggettoDaFile(filePren.getPath());
						prenotazioni.add(pren);
					}
					Giornata giornataVecchiaP = ristorante.getGiornata(Giorno.parseGiorno(file.getName()));
					ristorante.getCalendario().remove(giornataVecchiaP);
					giornataVecchiaP.setPrenotazioni(prenotazioni); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaP); //giornata vecchia che è diventata nuova

					break;
				case "Menu alla carta":
					ConfiguratorePiatto confPiatto = new ConfiguratorePiatto();
					for (File fileMC : ServizioFile.getElencoFileTxt(f.getPath())) {
						Piatto piatto = (Piatto) confPiatto.caricaIstanzaOggettoDaFile(fileMC.getPath());
						menuCarta.add(piatto);
					}

					Periodo periodoMenuCarta = new Periodo(Giorno.parseGiorno(file.getName()));
					MenuCarta menu = new MenuCarta(periodoMenuCarta);
					menu.setElenco(menuCarta);

					Giornata giornataVecchiaMC = ristorante.getGiornata(Giorno.parseGiorno(file.getName()));
					ristorante.getCalendario().remove(giornataVecchiaMC);
					giornataVecchiaMC.setMenuCarta(menu); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaMC); //giornata vecchia che è diventata nuova
					break;
				case "Menu Tematici":
					ConfiguratoreMenuTematico confMT = new ConfiguratoreMenuTematico();
					for (File fileMT : ServizioFile.getElencoFileTxt(f.getPath())) {
						MenuTematico menuT = (MenuTematico) confMT.caricaIstanzaOggettoDaFile(fileMT.getAbsolutePath());
						menuTematici.add(menuT);
					}

					Giornata giornataVecchiaMT = ristorante.getGiornata(Giorno.parseGiorno(file.getName()));
					ristorante.getCalendario().remove(giornataVecchiaMT);
					giornataVecchiaMT.setMenuTematici(menuTematici); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaMT); //giornata vecchia che è diventata nuova
					break;
				}
			}
		} //fine aggiornamento calendario da file
	}

	public void aggiungiScelte(String pathCompletoFileRistorante, Prenotazione prenotazione, 
			String pathDirectoryMenuCarta, String pathDirectoryMenuTematici) {

		List<File> menuCarta = ServizioFile.getElencoFileTxt(pathDirectoryMenuCarta);
		HashSet<Piatto> piattiMenuCarta = new HashSet<>();
		ConfiguratorePiatto confPiatto = new ConfiguratorePiatto();
		for (File fileMenuCarta : menuCarta) {
			Piatto piatto = (Piatto) confPiatto.caricaIstanzaOggettoDaFile(fileMenuCarta.getPath());
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
				System.out.println(menu.descrizioneMenuTematico()+'\n');
			}

			String nomeScelta = InputDati.leggiStringaNonVuota(messaggioNomeScelta);
			int numScelta = InputDati.leggiInteroConMinimo(messaggioNumScelta, 1);

			//	*aggiungi SceltaPrenotazione all'hashmap della prenotazione*
			HashSet<SceltaPrenotazione> insiemeTotale = new HashSet<>(elencoMenuTematici);
			insiemeTotale.addAll(piattiMenuCarta);

			SceltaPrenotazione scelta = SceltaPrenotazione.trovaDaNome(nomeScelta, insiemeTotale);
			if (scelta!=null) {
				prenotazione.aggiungiScelta(scelta, numScelta);	
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
		Giorno giornoScelto = GiornoView.richiestaCreaGiorno();
		
		String pathCalendario = creaDirectoryCalendario(pathCompletoFileRistorante);

		List<File> elencoDirGiornate = ServizioFile.getElencoDirectory(pathCalendario);
		
		aggiornamentoCalendario(ristorante, elencoDirGiornate);

		TreeSet<Giornata> calendario = ristorante.getCalendario();

		for (Giornata giornata : calendario) {
			if (giornoScelto.equals(giornata.getGiorno())) {
				System.out.println(giornata.descrizionePrenotazioni());
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
