package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
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
		String[] elementi = valoreAttributo.split(";");
		for (String elemento : elementi) {
			String[] coppia = elemento.split("=");
			if (coppia.length == 2){
				String chiave = coppia[0].trim();
				double valore = Double.parseDouble(coppia[1].trim());
				((HashMap<String, Double>) oggetto).put(chiave, valore);
				System.out.printf("Inserito:%s, %.2f", chiave,((HashMap<String, Double>) oggetto).get(chiave));
			}
		}
	}


	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		HashMap<String, Double> mappa = new HashMap<>();
		return mappa;
	}

}