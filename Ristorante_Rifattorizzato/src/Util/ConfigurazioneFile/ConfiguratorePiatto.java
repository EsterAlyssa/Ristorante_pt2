package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;

import Giorno.Periodo;
import Ristorante.ElementiRistorante.Piatto;

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
			writer.write("validitaPiatto="+((Piatto) piatto).getValidita().toString());
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
			((Piatto) oggetto).setValidita(Periodo.parsePeriodo(valoreAttributo));
			break;
		default:
			System.out.println("Errore nel settaggio dei parametri");
			break;
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new Piatto(nomeOggetto);
	}


}
