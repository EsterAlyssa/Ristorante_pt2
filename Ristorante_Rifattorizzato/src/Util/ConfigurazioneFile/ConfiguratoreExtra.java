package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import Ristorante.InsiemeExtra;


public class ConfiguratoreExtra extends ConfiguratoreManager{

	public ConfiguratoreExtra() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object oggetto, BufferedWriter writer) {
		try {
			writer.write("Insieme Extra=");
			writer.newLine();
			HashMap<String, Double> hashOggetto = ((InsiemeExtra) oggetto).getInsiemeExtra();
			ConfiguratoreHashMapStringDouble confIns = new ConfiguratoreHashMapStringDouble();
			confIns.scriviParametriNelFile(hashOggetto, writer);
			writer.flush();
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		switch(nomeAttributo) {
		case "Insieme Extra":
			ConfiguratoreHashMapStringDouble confIns = new ConfiguratoreHashMapStringDouble();
			confIns.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, ((InsiemeExtra) oggetto).getInsiemeExtra());
			break;
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new InsiemeExtra();
	}

	@Override
	public Object caricaIstanzaOggetto(String nomeOggetto, String line) {
		// Implementazione necessaria per caricare i singoli attributi dell'oggetto
		// da una riga letta dal file
		// Esempio:
		Object oggetto = creaIstanzaOggetto(nomeOggetto);
		String[] parte = line.split("=");
		if (parte.length == 2) {
			String nomeAttributo = parte[0].trim();
			String valoreAttributo = parte[1].trim();
			setAttributiDatoOggetto(nomeAttributo, valoreAttributo, oggetto);
		}
		return oggetto;
	}
}
