package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedWriter;
import java.io.IOException;

import Giorno.Periodo;
import Ristorante.ElementiRistorante.Piatto;

public class ConfiguratorePiatto extends ConfiguratoreManager<Piatto>{

	public ConfiguratorePiatto() {
		super();
	}

	@Override
	void scriviParametriNelFile(Piatto piatto, BufferedWriter writer) {
		try {
			writer.write("nomePiatto="+ piatto.getNome());
			writer.newLine();
			writer.write("caricoLavoroPiatto="+piatto.getCaricoLavoro());
			writer.newLine();
			writer.write("validitaPiatto=");
			writer.newLine();
			ConfiguratoreManager<Periodo> confP = new ConfiguratorePeriodo();
			confP.scriviParametriNelFile(piatto.getValidita(), writer);
			writer.flush();
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto piatto");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, 
			Piatto oggetto) {
		// Imposta l'attributo nell'oggetto singleton utilizzando i metodi setter corrispondenti
		switch (nomeAttributo) {
		case "nomePiatto":
			oggetto.setNome(valoreAttributo);
			break;
		case "caricoLavoroPiatto":
			oggetto.setCaricoLavoro(Double.parseDouble(valoreAttributo));
			break;
		case "validitaMenu":
			//questa linea è validitaPiatto= quindi non dovrebbe salvare valori, solo far capire che
			//deve iniziare un elenco di giorni
			break;
		case "giorno":
			// Il valoreAttributo contiene i giorni nel formato "gg-mm-aaaa;"
			ConfiguratoreManager<Periodo> confP = new ConfiguratorePeriodo();
			confP.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, oggetto.getValidita());
			break;
		default:
			System.out.println("Attributo non riconosciuto");
			break;
		}
	}

	@Override
	public Piatto creaIstanzaOggetto(String nomeOggetto) {
		return new Piatto(nomeOggetto);
	}


}
