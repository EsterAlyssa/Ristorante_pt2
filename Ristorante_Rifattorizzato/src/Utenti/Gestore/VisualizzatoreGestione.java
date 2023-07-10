package Utenti.Gestore;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import Giorno.GiornoView.PeriodoView;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.InsiemeExtra;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;
import Util.InputDati;
import Util.GestioneFile.ServizioFile;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreExtra;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratorePiatto;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRicetta;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRistorante;

public class VisualizzatoreGestione {

	public void visualizzaRistorante(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		System.out.printf("Nome del ristorante: %s\n", ristorante.getNome());
		System.out.printf("Numeri di posti a sedere nel ristorante: %d\n", ristorante.getNumPosti());
		System.out.printf("Carico di lavoro per persona: %d\n", ristorante.getCaricoLavoroPersona());
		System.out.printf("Carico di lavoro sostenibile dal ristorante: %.2f\n", ristorante.getCaricoLavoroRistorante());
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
		InsiemeExtra insiemeB = (InsiemeExtra) confIns.caricaIstanzaOggettoDaFile(pathFileBevande);

		ristorante.setInsiemeB(insiemeB);	

		for (String elemento : insiemeB.getInsiemeExtra().keySet()) {
			System.out.printf("bevanda: %s\tconsumo pro capite: %.2f\n", elemento, insiemeB.getInsiemeExtra().get(elemento));
		}
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
		InsiemeExtra insiemeGE = (InsiemeExtra) confIns.caricaIstanzaOggettoDaFile(pathFileGeneriExtra);

		ristorante.setInsiemeGE(insiemeGE);

		for (String elemento : ristorante.getInsiemeGE().getInsiemeExtra().keySet()) {
			System.out.printf("genere extra: %s\tconsumo pro capite: %.2f\n", elemento, ristorante.getInsiemeGE().getInsiemeExtra().get(elemento));
		}
	}

	public void visualizzaRicetta(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String msgRichiestaRicetta = "Inserisci il nome della ricetta da visualizzare: ";
		String msgNoRicetta = "Non esiste una ricetta con questo nome.";
		String msgRichiestaNuovaRicetta = "Inserisci di nuovo il nome della ricetta: ";

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Ricettario";
		String pathRicettario = pathDirectory + "/" + nomeDirectory;

		ConfiguratoreRicetta confRic = new ConfiguratoreRicetta();

		List<File> elencoRicette = ServizioFile.getElencoFileTxt(pathRicettario);
		HashSet<Ricetta> ricettario = new HashSet<>();
		for (File file : elencoRicette) {
			Ricetta ricetta = (Ricetta) confRic.caricaIstanzaOggettoDaFile(file.getPath());
			ricettario.add(ricetta);
		}
		ristorante.setRicettario(ricettario);

		visualizzaRicettario(pathCompletoFileRistorante);

		String ricettaScelta = InputDati.leggiStringaNonVuota(msgRichiestaRicetta);
		boolean trovata = true;
		do {
			Ricetta ricettaTrovata = Ricetta.trovaRicetta(ricettaScelta, ristorante.getRicettario());
			if (ricettaTrovata != null) {
				//ritornare la ricetta
				System.out.println(ricettaTrovata.descrizioneRicetta());
				trovata = false;
			} else {
				//la ricetta non è nel ricettario
				System.out.println(msgNoRicetta);
				ricettaScelta = InputDati.leggiStringaNonVuota(msgRichiestaNuovaRicetta);
			}
		} while (trovata);
	}

	public void visualizzaRicettario(String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Ricettario";
		String pathRicettario = pathDirectory + "/" + nomeDirectory;

		ConfiguratoreRicetta confRic = new ConfiguratoreRicetta();

		List<File> elencoRicette = ServizioFile.getElencoFileTxt(pathRicettario+"/");
		HashSet<Ricetta> ricettario = new HashSet<>();
		for (File file : elencoRicette) {
			Ricetta ricetta = (Ricetta) confRic.caricaIstanzaOggettoDaFile(file.getPath());
			ricettario.add(ricetta);
		}

		ristorante.setRicettario(ricettario);

		int i = 1;
		for (Ricetta ric : ristorante.getRicettario()) {
			System.out.printf("%d: %s\n", i, ric.getNome());
			i++;
		}
	}

	public void visualizzaInfoRicette (String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectory = pathCompletoFileRistorante.substring(0, pathCompletoFileRistorante.lastIndexOf("/"));
		String nomeDirectory = "Ricettario";
		String pathRicettario = pathDirectory + "/" + nomeDirectory;

		ConfiguratoreRicetta confRic = new ConfiguratoreRicetta();

		List<File> elencoRicette = ServizioFile.getElencoFileTxt(pathRicettario);
		HashSet<Ricetta> ricettario = new HashSet<>();
		for (File file : elencoRicette) {
			Ricetta ricetta = (Ricetta) confRic.caricaIstanzaOggettoDaFile(file.getPath());
			ricettario.add(ricetta);
		}

		ristorante.setRicettario(ricettario);

		for (Ricetta ric : ristorante.getRicettario()) {
			System.out.println(ric.descrizioneRicetta());
		}
	}

	public void visualizzaNomiMenuTematici(Gestore gestore, String pathCompletoFileRistorante) {
		HashSet<MenuTematico> menuTRistorante = gestore.ottieniMenuTematici(pathCompletoFileRistorante);

		int i = 1;
		for (MenuTematico menu : menuTRistorante) {
			System.out.printf("%d) %s\n", i, menu.getNome());
			i++;
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
			System.out.printf("Nome piatto: %s", piatto.getNome());
			PeriodoView periodoView = new PeriodoView(piatto.getValidita());
			periodoView.descrizionePeriodo();
		}
	}

	public void visualizzaInfoMenuTematici(Gestore gestore, String pathCompletoFileRistorante) {
		HashSet<MenuTematico> menuTRistorante = gestore.ottieniMenuTematici(pathCompletoFileRistorante);
		for (MenuTematico menu : menuTRistorante) {
			System.out.println(menu.descrizioneMenuTematico());
		}
	}

	public void visualizzaMenuTematico(Gestore gestore, String pathCompletoFileRistorante) {
		ConfiguratoreRistorante conf = new ConfiguratoreRistorante();
		Ristorante ristorante = (Ristorante) conf.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		HashSet<MenuTematico> menuTRistorante = gestore.ottieniMenuTematici(pathCompletoFileRistorante);
		ristorante.setMenuTematici(menuTRistorante);

		visualizzaNomiMenuTematici(gestore, pathCompletoFileRistorante);

		String msgRichiesta = "Inserisci il nome del menu tematico da visualizzare: ";
		String msgErrMenu= "ATTENZIONE! Il menu inserito non esiste";

		boolean trovato = true;
		do {
			String ricerca = InputDati.leggiStringaNonVuota(msgRichiesta);	
			MenuTematico menu = MenuTematico.trovaMenuTDaNome(ricerca, ristorante.getMenuTematici());
			if (menu != null) {
				System.out.println(menu.descrizioneMenuTematico());
				trovato = false;
			} else {
				System.out.println(msgErrMenu);
			}
		} while (trovato);
	}

}
