package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import Ristorante.ElementiRistorante.Ricetta;

public class ConfiguratoreRicetta extends ConfiguratoreManager {

	public ConfiguratoreRicetta() {
		super();
	}

	@Override
    void scriviParametriNelFile(Object ricetta, BufferedWriter writer) {
        try {
            Ricetta r = (Ricetta) ricetta;
            writer.write("nome=" + r.getNome());
            writer.newLine();
            writer.write("numPorzioni=" + r.getNumPorzioni());
            writer.newLine();
            writer.write("caricoLavoroPorzione=" + r.getCaricoLavoroPorzione());
            writer.newLine();
            writer.write("ingredienti=");
            writer.newLine();
            HashMap<String, Double> ingredienti = r.getIngredienti();
            
            ConfiguratoreHashMapStringDouble conf = new ConfiguratoreHashMapStringDouble();
            conf.scriviParametriNelFile(ingredienti, writer);
        } catch (IOException e) {
            System.out.println("Impossibile salvare l'oggetto ricetta");
            e.printStackTrace();
        }
    }

	@Override
    public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
        // Imposta l'attributo nell'oggetto ricetta utilizzando i metodi setter corrispondenti
        switch (nomeAttributo) {
            case "nome":
            	((Ricetta) oggetto).setNome(valoreAttributo);
                break;
            case "numPorzioni":
            	((Ricetta) oggetto).setNumPorzioni(Integer.parseInt(valoreAttributo));
                break;
            case "caricoLavoroPorzione":
            	((Ricetta) oggetto).setCaricoLavoroPorzione(Double.parseDouble(valoreAttributo));
                break;
            case "ingredienti":
                // Il valoreAttributo contiene gli ingredienti nel formato "nomeIngrediente=dose", separatore ";"
                ConfiguratoreHashMapStringDouble conf = new ConfiguratoreHashMapStringDouble();
                conf.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, ((Ricetta)oggetto).getIngredienti());
                break;
            default:
                System.out.println("Errore nel settaggio dei parametri");
                break;
        }
    }

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new Ricetta(nomeOggetto);
	}

}
