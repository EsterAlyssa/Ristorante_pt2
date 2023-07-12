package Util.GestioneFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import Giorno.Giorno;
import Giorno.Periodo;
import Magazzino.ListaSpesa;
import Prenotazioni.Prenotazione;
import Ristorante.Giornata;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.InsiemeExtra;
import Ristorante.ElementiRistorante.MenuCarta;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;

public class Aggiornamento {

	public static void aggiornamentoCalendario(Ristorante ristorante, String pathDirectoryCalendario) {
		List<File> elencoDirGiornate = ServizioFile.getElencoDirectory(pathDirectoryCalendario);

		TreeSet<Giornata> calendarioNoParam = CreazioneOggetti.creaCalendarioVuoto(elencoDirGiornate);
		ristorante.setCalendario(calendarioNoParam); //il ristorante così ha le giornate vuote (solo con il giorno)

		HashSet<Prenotazione> prenotazioni = new HashSet<>();
		HashSet<Piatto> piattiMenuCarta = new HashSet<>();
		HashSet<MenuTematico> menuTematici = new HashSet<>();
		ListaSpesa listaSpesa = new ListaSpesa();

		for (File file : elencoDirGiornate) { //per ogni cartella del calendario
			List<File> elencoDir1Giornata = ServizioFile.getElencoDirectory(file.getAbsolutePath());
			for (File f : elencoDir1Giornata) { //per ogni cartella in ogni cartella del calendario, quindi per ogni cartella giornata
				String nomeCartella = f.getName();
				switch (nomeCartella) {
				case "Prenotazioni":
					prenotazioni = CreazioneOggetti.creaPrenotazioni(f.getPath());

					Giornata giornataVecchiaP = ristorante.getGiornata(Giorno.parseGiorno(file.getName()));
					ristorante.getCalendario().remove(giornataVecchiaP);
					giornataVecchiaP.setPrenotazioni(prenotazioni); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaP); //giornata vecchia che è diventata nuova
					break;
				case "Menu alla carta":
					piattiMenuCarta = CreazioneOggetti.creaPiatti(f.getPath());
					
					Periodo periodoMenuCarta = new Periodo(Giorno.parseGiorno(file.getName()));
					MenuCarta menu = new MenuCarta(periodoMenuCarta);
					menu.setElenco(piattiMenuCarta);

					Giornata giornataVecchiaMC = ristorante.getGiornata(Giorno.parseGiorno(file.getName()));
					ristorante.getCalendario().remove(giornataVecchiaMC);
					giornataVecchiaMC.setMenuCarta(menu); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaMC); //giornata vecchia che è diventata nuova
					break;
				case "Menu tematici":
					menuTematici = CreazioneOggetti.creaMenuTematici(f.getPath());
					
					Giornata giornataVecchiaMT = ristorante.getGiornata(Giorno.parseGiorno(file.getName()));
					ristorante.getCalendario().remove(giornataVecchiaMT);
					giornataVecchiaMT.setMenuTematici(menuTematici); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaMT); //giornata vecchia che è diventata nuova
					break;
				case "Da comprare":
					try {
						listaSpesa = CreazioneOggetti.creaListaSpesa(f.getPath());

						Giornata giornataVecchiaLS = ristorante.getGiornata(Giorno.parseGiorno(file.getName()));
						ristorante.getCalendario().remove(giornataVecchiaLS);
						giornataVecchiaLS.setDaComprare(listaSpesa);
						ristorante.getCalendario().add(giornataVecchiaLS);
					} catch (NullPointerException e) {
						System.out.println("Non c'e' una lista della spesa");
					}	
					break;
				}
			}
		} 
	}

	public static void aggiornamentoInsiemiExtra(String pathCompletoFileRistorante, Ristorante ristorante) {
		String pathDirectoryInsiemiExtra = CreazioneDirectory.creaDirectoryInsiemiExtra(pathCompletoFileRistorante);
		String pathFileBevande = CreazioneFile.creaFileInsiemeBevande(pathDirectoryInsiemiExtra);
		String pathFileGeneriExtra = CreazioneFile.creaFileInsiemeGeneriExtra(pathDirectoryInsiemiExtra);

		InsiemeExtra insiemeB = CreazioneOggetti.creaInsiemeExtra(pathFileBevande);
		ristorante.getInsiemeB().setInsiemeExtra(insiemeB.getInsiemeExtra());

		InsiemeExtra insiemeGE = CreazioneOggetti.creaInsiemeExtra(pathFileGeneriExtra);
		ristorante.getInsiemeGE().setInsiemeExtra(insiemeGE.getInsiemeExtra());
	}
	
	public static void aggiornamentoRicettario(String pathCompletoFileRistorante, Ristorante ristorante) {
		String pathDirectoryRicettario = CreazioneDirectory.creaDirectoryRicettario(pathCompletoFileRistorante);
		HashSet<Ricetta> ricettario = CreazioneOggetti.creaRicettario(pathDirectoryRicettario);
		
		for (Ricetta ricetta : ricettario) {
			ristorante.aggiungiRicetta(ricetta);
		}
	}
}
