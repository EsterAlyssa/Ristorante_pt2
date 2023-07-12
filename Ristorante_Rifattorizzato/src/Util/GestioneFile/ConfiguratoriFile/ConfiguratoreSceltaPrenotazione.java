package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedWriter;
import java.io.IOException;

import Prenotazioni.SceltaPrenotazione;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;

public class ConfiguratoreSceltaPrenotazione extends ConfiguratoreManager<SceltaPrenotazione> {

	public ConfiguratoreSceltaPrenotazione() {
		super();
	}

	@Override
	void scriviParametriNelFile(SceltaPrenotazione sceltaPrenotazione, BufferedWriter writer) {
		try {
			if (sceltaPrenotazione instanceof MenuTematico) {
				ConfiguratoreManager<MenuTematico> confMenuT = new ConfiguratoreMenuTematico();
				writer.write("MenuTematico= ");
				writer.newLine();
				confMenuT.scriviParametriNelFile((MenuTematico) sceltaPrenotazione, writer);
			};

			if (sceltaPrenotazione instanceof Piatto) {
				ConfiguratoreManager<Piatto> confPiat = new ConfiguratorePiatto();
				writer.write("Piatto= ");
				writer.newLine();
				confPiat.scriviParametriNelFile((Piatto) sceltaPrenotazione, writer);
			}
			else {
				System.out.println("Errore! L'oggetto non è una possibile scelta per le prenotazioni");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, 
			SceltaPrenotazione oggetto) {
		if (nomeAttributo.contains("MenuTematico")) {
			ConfiguratoreManager<MenuTematico> confMenuT = new ConfiguratoreMenuTematico();
			confMenuT.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, (MenuTematico) oggetto);
		} else {
			ConfiguratoreManager<Piatto> confPiat = new ConfiguratorePiatto();
			confPiat.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, (Piatto) oggetto);
		}
	}

	@Override
	public SceltaPrenotazione creaIstanzaOggetto(String nomeOggetto) {
		if (nomeOggetto.contains("Piatto")) {
			return new Piatto(nomeOggetto); 
		} else {
			return new MenuTematico(nomeOggetto);
		}
	}
}
