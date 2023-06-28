package Util.ConfigurazioneFile;

import Util.ServizioFile;

import java.io.*;

public abstract class ConfiguratoreManager {

	public void salvaIstanzaOggetto(Object oggetto, String pathRistorante) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathRistorante))) {
			// Scrittura dei parametri nel file
			scriviParametriNelFile(oggetto, writer);
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto");
		}
	}

	abstract void scriviParametriNelFile(Object oggetto, BufferedWriter writer);

	public Object caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		Object oggettoCaricato = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathFileOggetto));
			String nomeOggetto = ServizioFile.getNomeFileSenzaEstensione(pathFileOggetto);
			String line;
			while ((line = reader.readLine()) != null) {
				oggettoCaricato = caricaIstanzaOggetto(nomeOggetto, line);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Impossibile caricare l'oggetto");
		}
		return oggettoCaricato;
	}


	public Object caricaIstanzaOggetto(String nomeOggetto, String line) {
		Object oggetto = creaIstanzaOggetto(nomeOggetto);
		String[] parte = line.split("=");
		if (parte.length==2) {
			String nomeAttributo = parte[0].trim();
			String valoreAttributo = parte[1].trim();
			// Imposta l'attributo corrispondente nell'oggetto
			setAttributiDatoOggetto(nomeAttributo, valoreAttributo, oggetto);
		}
		return oggetto;
	}


	public abstract void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto);
	public abstract Object creaIstanzaOggetto(String nomeOggetto);

}