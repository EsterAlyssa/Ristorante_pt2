package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;

import Ristorante.Ristorante;


public class ConfiguratoreRistorante extends ConfiguratoreManager {

	public ConfiguratoreRistorante() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object ristorante, BufferedWriter writer){
		try {
			writer.write("nome="+((Ristorante) ristorante).getNome());
			writer.newLine();
			writer.write("caricoLavoroPersona="+((Ristorante) ristorante).getCaricoLavoroPersona());
			writer.newLine();
			writer.write("numPosti="+((Ristorante) ristorante).getNumPosti());
			writer.newLine();
			writer.write("caricoLavoroRistorante="+((Ristorante) ristorante).getCaricoLavoroRistorante());	
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto ristorante");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		// Imposta l'attributo nell'oggetto singleton utilizzando i metodi setter corrispondenti
		switch (nomeAttributo) {
		case "nome":
			((Ristorante) oggetto).setNome(valoreAttributo);
			break;
		case "caricoLavoroPersona":
			((Ristorante) oggetto).setCaricoLavoroPersona(Integer.parseInt(valoreAttributo));
			break;
		case "numPosti":
			((Ristorante) oggetto).setNumPosti(Integer.parseInt(valoreAttributo));
			break;
		case "caricoLavoroRistorante":
			((Ristorante) oggetto).setCaricoLavoroRistorante(Double.parseDouble(valoreAttributo));
			break;
		default:
			System.out.println("Errore nel settaggio dei parametri");
			break;
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {	
		return Ristorante.getInstance(nomeOggetto);
	}

}