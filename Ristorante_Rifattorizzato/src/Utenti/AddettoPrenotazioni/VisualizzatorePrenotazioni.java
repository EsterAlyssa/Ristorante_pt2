package Utenti.AddettoPrenotazioni;

import java.io.File;
import java.util.List;
import java.util.TreeSet;

import Giorno.Giorno;
import Giorno.GiornoView.GiornoView;
import Ristorante.Giornata;
import Ristorante.Ristorante;
import Util.GestioneFile.AggiornamentoCalendario;
import Util.GestioneFile.CreazioneDirectory;
import Util.GestioneFile.ServizioFile;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRistorante;

public class VisualizzatorePrenotazioni {
	
	private static final String MSG_GIORNATA = "Inserire la giornata di cui si vuole vedere le prenotazioni";
	
	private ConfiguratoreRistorante configuratoreRistorante;
	
	public VisualizzatorePrenotazioni() {
		this.configuratoreRistorante = new ConfiguratoreRistorante();
	}


	public void visualizzaPrenotazioni(String pathCompletoFileRistorante) {
		Ristorante ristorante = (Ristorante) configuratoreRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		System.out.println(MSG_GIORNATA);
		Giorno giornoScelto = GiornoView.richiestaCreaGiorno();
		
		String pathCalendario = CreazioneDirectory.creaDirectoryCalendario(pathCompletoFileRistorante);

		List<File> elencoDirGiornate = ServizioFile.getElencoDirectory(pathCalendario);
		
		AggiornamentoCalendario.aggiornamentoCalendario(ristorante, elencoDirGiornate);

		TreeSet<Giornata> calendario = ristorante.getCalendario();

		for (Giornata giornata : calendario) {
			if (giornoScelto.compareTo(giornata.getGiorno())==0) {
				System.out.println(giornata.descrizionePrenotazioni());
			}
		}
	}

}
