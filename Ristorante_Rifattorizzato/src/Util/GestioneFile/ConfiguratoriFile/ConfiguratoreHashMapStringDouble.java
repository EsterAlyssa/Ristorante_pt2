package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ConfiguratoreHashMapStringDouble extends ConfiguratoreManager {


	public ConfiguratoreHashMapStringDouble() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object oggettoMappa, BufferedWriter writer) {
		try {
			HashMap<String, Double> mappa = (HashMap<String, Double>) oggettoMappa;
			for (String nomeProdotto : mappa.keySet()) {
				writer.write(nomeProdotto + "=" + mappa.get(nomeProdotto) + ";");
				writer.newLine();
				writer.flush();
			}
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto mappa");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		double valore = Double.parseDouble(valoreAttributo.replace(";", ""));
		((HashMap<String, Double>) oggetto).put(nomeAttributo, valore);
	}


	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		HashMap<String, Double> mappa = new HashMap<>();
		return mappa;
	}

	@Override
	public Object caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		HashMap<String, Double> mappaCaricata = new HashMap<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathFileOggetto));
			String line;
			while ((line = reader.readLine()) != null) {
				// Estrarre il nome e il valore dalla riga letta
				String[] parte = line.split("=");
				if (parte.length == 2) {
					String nome = parte[0].trim();
					double valore = Double.parseDouble(parte[1].trim().replace(";", ""));
					// Aggiungere la coppia nome-valore alla mappa
					mappaCaricata.put(nome, valore);
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Impossibile caricare la mappa");
		}
		return mappaCaricata;
	}


}