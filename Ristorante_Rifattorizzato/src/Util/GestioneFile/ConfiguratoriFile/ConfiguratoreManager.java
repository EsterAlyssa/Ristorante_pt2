package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Util.GestioneFile.ServizioFile;

public abstract class ConfiguratoreManager<T> {

	public void salvaIstanzaOggetto(T oggetto, String pathFile) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile))) {
	        // Scrittura dei parametri nel file
	        scriviParametriNelFile(oggetto, writer);
	    } catch (IOException e) {
	        System.out.println("Impossibile salvare l'oggetto");
	    }
	}

	abstract void scriviParametriNelFile(T oggetto, BufferedWriter writer);

	public T caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		T oggettoCaricato = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathFileOggetto));
			String nomeOggetto = ServizioFile.getNomeFileSenzaEstensione(pathFileOggetto);
			oggettoCaricato = creaIstanzaOggetto(nomeOggetto);
			String line;
			while ((line = reader.readLine()) != null) {
				oggettoCaricato = caricaIstanzaOggetto(oggettoCaricato, line);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Impossibile caricare l'oggetto");
		}
		return oggettoCaricato;
	}

	public T caricaIstanzaOggetto(T oggetto, String line) {
		String[] parte = line.split("=");
		if (parte.length==2) {
			String nomeAttributo = parte[0].trim();
			String valoreAttributo = parte[1].trim();
			// Imposta l'attributo corrispondente nell'oggetto
			setAttributiDatoOggetto(nomeAttributo, valoreAttributo, oggetto);
		}
		return oggetto;
	}

	public abstract void setAttributiDatoOggetto(String nomeAttributo, 
			String valoreAttributo, T oggetto);
	public abstract T creaIstanzaOggetto(String nomeOggetto);

}