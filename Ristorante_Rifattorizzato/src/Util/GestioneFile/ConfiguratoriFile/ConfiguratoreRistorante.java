package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedWriter;
import java.io.IOException;

import Ristorante.Ristorante;


public class ConfiguratoreRistorante extends ConfiguratoreManager<Ristorante> {

	public ConfiguratoreRistorante() {
		super();
	}

	@Override
	void scriviParametriNelFile(Ristorante ristorante, BufferedWriter writer){
		try {
			writer.write("nome="+ ristorante.getNome());
			writer.newLine();
			writer.write("caricoLavoroPersona="+ ristorante.getCaricoLavoroPersona());
			writer.newLine();
			writer.write("numPosti="+ ristorante.getNumPosti());
			writer.newLine();
			writer.write("caricoLavoroRistorante="+ ristorante.getCaricoLavoroRistorante());	
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto ristorante");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Ristorante oggetto) {
		// Imposta l'attributo nell'oggetto singleton utilizzando i metodi setter corrispondenti
		switch (nomeAttributo) {
		case "nome":
			oggetto.setNome(valoreAttributo);
			break;
		case "caricoLavoroPersona":
			oggetto.setCaricoLavoroPersona(Integer.parseInt(valoreAttributo));
			break;
		case "numPosti":
			oggetto.setNumPosti(Integer.parseInt(valoreAttributo));
			break;
		case "caricoLavoroRistorante":
			oggetto.setCaricoLavoroRistorante(Double.parseDouble(valoreAttributo));
			break;
		default:
			System.out.println("Errore nel settaggio dei parametri");
			break;
		}
	}

	@Override
	public Ristorante creaIstanzaOggetto(String nomeOggetto) {	
		return Ristorante.getInstance(nomeOggetto);
	}

}