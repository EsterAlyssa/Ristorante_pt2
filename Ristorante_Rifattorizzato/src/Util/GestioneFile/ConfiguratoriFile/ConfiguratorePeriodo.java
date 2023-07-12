package Util.GestioneFile.ConfiguratoriFile;

import Giorno.Giorno;
import Giorno.Periodo;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConfiguratorePeriodo extends ConfiguratoreManager<Periodo>{

	@Override
	void scriviParametriNelFile(Periodo oggetto, BufferedWriter writer) {
		for (Giorno giorno : oggetto.getPeriodoValidita()) {
			ConfiguratoreManager<Giorno> configuratoreGiorno = new ConfiguratoreGiorno();
			configuratoreGiorno.scriviParametriNelFile(giorno, writer);
			try {
				writer.append(';');
				writer.newLine();
			} catch (IOException e) {
				System.out.println("Impossibile scrivere il periodo nel file");
			}
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, 
			Periodo oggetto) {
		String[] giorni = valoreAttributo.split(";");
		for (String giornoStr : giorni) {
			Giorno giorno = Giorno.parseGiorno(giornoStr.trim());
			oggetto.getPeriodoValidita().add(giorno);
		}
	}

	@Override
	public Periodo creaIstanzaOggetto(String nomeOggetto) {
		return new Periodo();
	}
}
