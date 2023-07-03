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
		ConfiguratoreHashMapStringDouble confIns = new ConfiguratoreHashMapStringDouble();
		confIns.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, ((InsiemeExtra) oggetto).getInsiemeExtra());
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new InsiemeExtra();
	}

}
