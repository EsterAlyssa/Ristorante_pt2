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
			} else if (sceltaPrenotazione instanceof Piatto) {
				ConfiguratoreManager<Piatto> confPiat = new ConfiguratorePiatto();
				writer.write("Piatto= ");
				writer.newLine();
				confPiat.scriviParametriNelFile((Piatto) sceltaPrenotazione, writer);
			} else {
				System.out.println("Errore! L'oggetto non è una possibile scelta per le prenotazioni");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public SceltaPrenotazione caricaIstanzaOggetto(SceltaPrenotazione oggetto, String line) {
		String[] parte = line.split("=");
		if (parte.length==2) {
			String nomeAttributo = parte[0].trim();
			String valoreAttributo = parte[1].trim();
			// Imposta l'attributo corrispondente nell'oggetto
			/*if (oggetto instanceof MenuTematico) {
				ConfiguratoreMenuTematico confMenuT = new ConfiguratoreMenuTematico();
				confMenuT.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, (MenuTematico) oggetto);
			} else */ if (oggetto instanceof Piatto) {
				ConfiguratorePiatto confPiat = new ConfiguratorePiatto();
				confPiat.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, (Piatto) oggetto);
			}
		}
		return oggetto;
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, 
			SceltaPrenotazione oggetto) {
		System.out.println("Oggetto scelta non valido");
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
