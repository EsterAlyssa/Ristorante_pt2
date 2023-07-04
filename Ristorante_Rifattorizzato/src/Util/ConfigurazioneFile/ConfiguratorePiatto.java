package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;

import Giorno.Giorno;
import Giorno.Periodo;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;

public class ConfiguratorePiatto extends ConfiguratoreManager{

	public ConfiguratorePiatto() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object piatto, BufferedWriter writer) {
		try {
			writer.write("nomePiatto="+((Piatto) piatto).getNome());
			writer.newLine();
			writer.write("caricoLavoroPiatto="+((Piatto) piatto).getCaricoLavoro());
			writer.newLine();
			writer.write("validitaPiatto=");
			writer.newLine();
			ConfiguratorePeriodo confP = new ConfiguratorePeriodo();
			confP.scriviParametriNelFile(((Piatto)piatto).getValidita(), writer);
			writer.flush();
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto piatto");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		// Imposta l'attributo nell'oggetto singleton utilizzando i metodi setter corrispondenti
		switch (nomeAttributo) {
		case "nomePiatto":
			((Piatto) oggetto).setNome(valoreAttributo);
			break;
		case "caricoLavoroPiatto":
			((Piatto) oggetto).setCaricoLavoro(Double.parseDouble(valoreAttributo));
			break;
		case "validitaPiatto":
			//questa linea è validitaPiatto= quindi non dovrebbe salvare valori, solo far capire che
			//deve iniziare un elenco di giorni
			break;
		default:
			// Il valoreAttributo contiene i giorni nel formato "gg-mm-aaaa;"
			ConfiguratorePeriodo confP = new ConfiguratorePeriodo();
			confP.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, ((Piatto)oggetto).getValidita());
			break;
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new Piatto(nomeOggetto);
	}


}
