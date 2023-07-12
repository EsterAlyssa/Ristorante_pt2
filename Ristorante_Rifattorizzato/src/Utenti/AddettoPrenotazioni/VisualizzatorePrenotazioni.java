package Utenti.AddettoPrenotazioni;

import java.util.TreeSet;

import Giorno.Giorno;
import Giorno.GiornoView.GiornoView;
import Ristorante.Giornata;
import Ristorante.Ristorante;
import Ristorante.RistoranteView.GiornataView;
import Util.GestioneFile.Aggiornamento;
import Util.GestioneFile.CreazioneDirectory;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreManager;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRistorante;

public class VisualizzatorePrenotazioni {

	static final String MSG_GIORNATA = "Inserire la giornata di cui si vuole vedere le prenotazioni";

	private ConfiguratoreManager<Ristorante> configuratoreRistorante;

	public VisualizzatorePrenotazioni() {
		this.configuratoreRistorante = new ConfiguratoreRistorante();
	}

	public void visualizzaPrenotazioni(String pathCompletoFileRistorante) {
		Ristorante ristorante = configuratoreRistorante.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		System.out.println(MSG_GIORNATA);
		Giorno giornoScelto = GiornoView.richiestaCreaGiorno();

		String pathCalendario = CreazioneDirectory.creaDirectoryCalendario(pathCompletoFileRistorante);

		Aggiornamento.aggiornamentoCalendario(ristorante, pathCalendario);

		TreeSet<Giornata> calendario = ristorante.getCalendario();

		for (Giornata giornata : calendario) {
			if (giornoScelto.compareTo(giornata.getGiorno())==0) {
				GiornataView giornataView = new GiornataView(giornata);
				giornataView.mostraDescrizionePrenotazioni();
			}
		}
	}

}
