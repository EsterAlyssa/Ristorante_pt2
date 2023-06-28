package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;

import Prenotazioni.SceltaPrenotazione;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;

public class ConfiguratoreSceltaPrenotazione extends ConfiguratoreManager {

	public ConfiguratoreSceltaPrenotazione() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object sceltaPrenotazione, BufferedWriter writer) {
		try {
			SceltaPrenotazione sceltaPren = (SceltaPrenotazione) sceltaPrenotazione;
			if (sceltaPren instanceof MenuTematico) {
				ConfiguratoreManager confMenuT = new ConfiguratoreMenuTematico();
				writer.write("MenuTematico=");
				writer.newLine();
				confMenuT.scriviParametriNelFile(sceltaPren, writer);
			};

			if (sceltaPren instanceof Piatto) {
				ConfiguratoreManager confPiat = new ConfiguratorePiatto();
				writer.write("Piatto=");
				writer.newLine();
				confPiat.scriviParametriNelFile(sceltaPren, writer);
			}
			else {
				System.out.println("Errore! L'oggetto non è una possibile scelta per le prenotazioni");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		if (nomeAttributo.contains("MenuTematico")) {
			ConfiguratoreManager confMenuT = new ConfiguratoreMenuTematico();
			confMenuT.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, oggetto);
		} else {
			ConfiguratoreManager confPiat = new ConfiguratorePiatto();
			confPiat.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, oggetto);
		}

	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		if (nomeOggetto.contains("Piatto")) {
			return new Piatto(nomeOggetto); 
		} else {
			return new MenuTematico(nomeOggetto);
		}
	}
}
