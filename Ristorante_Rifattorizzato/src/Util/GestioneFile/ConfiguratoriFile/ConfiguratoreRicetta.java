package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import Ristorante.ElementiRistorante.Ricetta;

public class ConfiguratoreRicetta extends ConfiguratoreManager<Ricetta> {

	public ConfiguratoreRicetta() {
		super();
	}

	@Override
	void scriviParametriNelFile(Ricetta ricetta, BufferedWriter writer) {
		try {
			writer.write("nome=" + ricetta.getNome());
			writer.newLine();
			writer.write("numPorzioni=" + ricetta.getNumPorzioni());
			writer.newLine();
			writer.write("caricoLavoroPorzione=" + ricetta.getCaricoLavoroPorzione());
			writer.newLine();
			writer.write("ingredienti=");
			writer.newLine();
			HashMap<String, Double> ingredienti = ricetta.getIngredienti();

			ConfiguratoreManager<HashMap<String, Double>> conf = new ConfiguratoreHashMapStringDouble();
			conf.scriviParametriNelFile(ingredienti, writer);
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto ricetta");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Ricetta oggetto) {
		// Imposta l'attributo nell'oggetto ricetta utilizzando i metodi setter corrispondenti
		switch (nomeAttributo) {
		case "nome":
			oggetto.setNome(valoreAttributo);
			break;
		case "numPorzioni":
			oggetto.setNumPorzioni(Integer.parseInt(valoreAttributo));
			break;
		case "caricoLavoroPorzione":
			oggetto.setCaricoLavoroPorzione(Double.parseDouble(valoreAttributo));
			break;
		case "ingredienti":
			//questa linea è ingredienti= quindi non dovrebbe salvare valori, solo far capire che
			//deve iniziare un elenco di ingredienti
			break;
		default:
			// Il valoreAttributo contiene gli ingredienti nel formato "nomeIngrediente=dose", separatore ";"
			ConfiguratoreManager<HashMap<String, Double>> conf = new ConfiguratoreHashMapStringDouble();
			conf.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, oggetto.getIngredienti());
			break;
		}
	}

	@Override
	public Ricetta creaIstanzaOggetto(String nomeOggetto) {
		return new Ricetta(nomeOggetto);
	}

}
