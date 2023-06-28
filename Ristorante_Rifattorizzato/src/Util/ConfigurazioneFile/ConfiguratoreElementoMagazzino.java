package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;

import Magazzino.ElementoMagazzino;
import Magazzino.Merce.Merce;

public class ConfiguratoreElementoMagazzino extends ConfiguratoreManager{

	public ConfiguratoreElementoMagazzino() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object oggetto, BufferedWriter writer) {
		try {
			ElementoMagazzino elementoMagazzino = (ElementoMagazzino) oggetto;
			Merce merce = elementoMagazzino.getMerce();
			writer.write("merce= ");
			writer.newLine();
			
			ConfiguratoreMerce configuratoreMerce = new ConfiguratoreMerce();
			configuratoreMerce.scriviParametriNelFile(merce, writer);
			
			writer.newLine();
			writer.write("quantita=" + elementoMagazzino.getQuantita());
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto Elemento Magazzino");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object elementoMagazzino) {
		switch (nomeAttributo) {
		case "merce":
			ConfiguratoreMerce configuratoreMerce = new ConfiguratoreMerce();
			Merce merce = (Merce) configuratoreMerce.creaIstanzaOggetto(nomeAttributo);
			String nomeAttributoSenzaPrefisso = nomeAttributo.substring(nomeAttributo.indexOf("=") + 1);
			configuratoreMerce.setAttributiDatoOggetto(nomeAttributoSenzaPrefisso, valoreAttributo, merce);
			((ElementoMagazzino) elementoMagazzino).setMerce(merce);
			break;
		case "quantita":
			((ElementoMagazzino) elementoMagazzino).setQuantita(Double.parseDouble(valoreAttributo));
			break;
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new ElementoMagazzino(null, 0.0);
	}
}
