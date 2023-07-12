package Util.GestioneFile.ConfiguratoriFile;

import Giorno.Giorno;
import Giorno.GiornoView.GiornoView;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConfiguratoreGiorno extends ConfiguratoreManager<Giorno> {

	@Override
	void scriviParametriNelFile(Giorno oggetto, BufferedWriter writer) {
		GiornoView giornoView = new GiornoView (oggetto.getGiorno());
		try {
			writer.write("giorno=" + giornoView.descrizioneGiorno());
		} catch (IOException e) {
			System.out.println("Impossibile scrivere il giorno nel file");
		}

	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Giorno oggetto) {
		oggetto.setGiorno(Giorno.parseGiorno(valoreAttributo));
	}

	@Override
	public Giorno creaIstanzaOggetto(String nomeOggetto) {
		return new Giorno(null);
	}
}
