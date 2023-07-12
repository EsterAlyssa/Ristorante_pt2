package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ConfiguratoreHashMapStringDouble extends ConfiguratoreManager<HashMap<String, Double>> {


	public ConfiguratoreHashMapStringDouble() {
		super();
	}

	@Override
	void scriviParametriNelFile(HashMap<String, Double> oggettoMappa, BufferedWriter writer) {
		try {
			for (String nomeProdotto : oggettoMappa.keySet()) {
				String stringaFormattata = String.format("%s=%.2f;", nomeProdotto, oggettoMappa.get(nomeProdotto));
				writer.write(stringaFormattata);
				writer.newLine();
				writer.flush();
			}
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto mappa");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, 
			HashMap<String, Double> oggetto) {
		String valoreFormattato = valoreAttributo.replace(";", "");
		valoreFormattato = valoreFormattato.replace(",", ".");
		double valore = Double.parseDouble(valoreFormattato);
		oggetto.put(nomeAttributo, valore);
	}


	@Override
	public HashMap<String, Double> creaIstanzaOggetto(String nomeOggetto) {
		return new HashMap<>();
	}

	@Override
	public HashMap<String, Double> caricaIstanzaOggettoDaFile(String pathFileOggetto) {
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