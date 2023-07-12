package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import Ristorante.ElementiRistorante.InsiemeExtra;


public class ConfiguratoreExtra extends ConfiguratoreManager<InsiemeExtra>{

	public ConfiguratoreExtra() {
		super();
	}

	@Override
	void scriviParametriNelFile(InsiemeExtra oggetto, BufferedWriter writer) {
		try {
			writer.write("Insieme Extra=");
			writer.newLine();
			HashMap<String, Double> hashOggetto = oggetto.getInsiemeExtra();
			ConfiguratoreManager<HashMap<String, Double>> confIns = new ConfiguratoreHashMapStringDouble();
			confIns.scriviParametriNelFile(hashOggetto, writer);
			writer.flush();
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, 
			InsiemeExtra oggetto) {
		ConfiguratoreManager<HashMap<String, Double>> confIns = new ConfiguratoreHashMapStringDouble();
		confIns.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, oggetto.getInsiemeExtra());
	}

	@Override
	public InsiemeExtra creaIstanzaOggetto(String nomeOggetto) {
		return new InsiemeExtra();
	}

}
