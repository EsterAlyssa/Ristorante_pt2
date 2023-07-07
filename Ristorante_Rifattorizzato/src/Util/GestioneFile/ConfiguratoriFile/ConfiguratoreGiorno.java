package Util.GestioneFile.ConfiguratoriFile;

import Giorno.Giorno;
import Giorno.GiornoView.GiornoView;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConfiguratoreGiorno extends ConfiguratoreManager {

	@Override
	void scriviParametriNelFile(Object oggetto, BufferedWriter writer) {
		if (oggetto instanceof Giorno) {
			Giorno giorno = (Giorno) oggetto;
			GiornoView giornoView = new GiornoView (giorno.getGiorno());
			try {
				writer.write("giorno=" + giornoView.descrizioneGiorno());
			} catch (IOException e) {
				System.out.println("Impossibile scrivere il giorno nel file");
			}
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		((Giorno)oggetto).setGiorno(Giorno.parseGiorno(valoreAttributo));
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new Giorno(null);
	}
}
