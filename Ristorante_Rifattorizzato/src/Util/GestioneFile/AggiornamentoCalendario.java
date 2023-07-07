package Util.GestioneFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import Giorno.Giorno;
import Giorno.Periodo;
import Prenotazioni.Prenotazione;
import Ristorante.Giornata;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.MenuCarta;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;

public class AggiornamentoCalendario {

	public static void aggiornamentoCalendario(Ristorante ristorante, List<File> elencoDirGiornate) {
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
					prenotazioni = CreazioneInsiemi.creaPrenotazioni(f.getPath());

					Giornata giornataVecchiaP = ristorante.getGiornata(Giorno.parseGiorno(file.getName()));
					ristorante.getCalendario().remove(giornataVecchiaP);
					giornataVecchiaP.setPrenotazioni(prenotazioni); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaP); //giornata vecchia che è diventata nuova
					break;
				case "Menu alla carta":
					menuCarta = CreazioneInsiemi.creaMenuCarta(f.getPath());

					Periodo periodoMenuCarta = new Periodo(Giorno.parseGiorno(file.getName()));
					MenuCarta menu = new MenuCarta(periodoMenuCarta);
					menu.setElenco(menuCarta);

					Giornata giornataVecchiaMC = ristorante.getGiornata(Giorno.parseGiorno(file.getName()));
					ristorante.getCalendario().remove(giornataVecchiaMC);
					giornataVecchiaMC.setMenuCarta(menu); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaMC); //giornata vecchia che è diventata nuova
					break;
				case "Menu Tematici":
					menuTematici = CreazioneInsiemi.creaMenuTematici(f.getPath());

					Giornata giornataVecchiaMT = ristorante.getGiornata(Giorno.parseGiorno(file.getName()));
					ristorante.getCalendario().remove(giornataVecchiaMT);
					giornataVecchiaMT.setMenuTematici(menuTematici); //giornata nuova
					ristorante.getCalendario().add(giornataVecchiaMT); //giornata vecchia che è diventata nuova
					break;
				}
			}
		} //fine aggiornamento calendario da file
	}

}
