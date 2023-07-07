package Util.GestioneFile.ConfiguratoriFile;

import Giorno.Giorno;
import Giorno.Periodo;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConfiguratorePeriodo extends ConfiguratoreManager{

	@Override
	void scriviParametriNelFile(Object oggetto, BufferedWriter writer) {
		for (Giorno giorno : ((Periodo)oggetto).getPeriodoValidita()) {
			ConfiguratoreGiorno configuratoreGiorno = new ConfiguratoreGiorno();
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
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		String[] giorni = valoreAttributo.split(";");
		for (String giornoStr : giorni) {
			Giorno giorno = Giorno.parseGiorno(giornoStr.trim());
			((Periodo) oggetto).getPeriodoValidita().add(giorno);
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new Periodo();
	}
}
