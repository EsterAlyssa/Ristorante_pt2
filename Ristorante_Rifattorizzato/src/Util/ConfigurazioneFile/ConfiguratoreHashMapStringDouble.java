package Util.ConfigurazioneFile;

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
		String[] elementi = valoreAttributo.split(";\n"); // Utilizza ";" come separatore
		for (String elemento : elementi) {
			String[] coppia = elemento.split("=");
			if (coppia.length == 2){
				String chiave = coppia[0].trim();
				double valore = Double.parseDouble(coppia[1].trim());
				((HashMap<String, Double>) oggetto).put(chiave, valore);
				System.out.printf("Inserito:%s, %.2f\n", chiave,((HashMap<String, Double>) oggetto).get(chiave));
			}
		}
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
					double valore = Double.parseDouble(parte[1].trim());
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