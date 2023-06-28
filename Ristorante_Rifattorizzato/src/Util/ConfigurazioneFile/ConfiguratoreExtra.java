package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;


public class ConfiguratoreExtra extends ConfiguratoreManager{

	public ConfiguratoreExtra() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object oggetto, BufferedWriter writer) {
		try {
			writer.write("Insieme Extra=");
			writer.newLine();

			ConfiguratoreHashMapStringDouble confIns = new ConfiguratoreHashMapStringDouble();
			confIns.scriviParametriNelFile(oggetto, writer);
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		ConfiguratoreHashMapStringDouble confIns = new ConfiguratoreHashMapStringDouble();
		confIns.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, oggetto);
		
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		ConfiguratoreHashMapStringDouble confIns = new ConfiguratoreHashMapStringDouble();
		return confIns.creaIstanzaOggetto(nomeOggetto);
	}


}
