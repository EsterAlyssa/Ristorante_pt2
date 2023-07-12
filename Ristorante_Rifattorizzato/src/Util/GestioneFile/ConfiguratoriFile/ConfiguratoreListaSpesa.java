package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import Magazzino.ListaSpesa;

public class ConfiguratoreListaSpesa extends ConfiguratoreManager<ListaSpesa>{

	public ConfiguratoreListaSpesa() {
		super();
	}

	@Override
	void scriviParametriNelFile(ListaSpesa listaSpesa, BufferedWriter writer) {
		try {
			HashMap<String, Double> lista = listaSpesa.getLista();
			writer.write("lista= ");
			writer.newLine();
			ConfiguratoreManager<HashMap<String, Double>> conf = new ConfiguratoreHashMapStringDouble();
			conf.scriviParametriNelFile(lista, writer);
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto lista spesa");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, 
			ListaSpesa oggetto) {
		switch (nomeAttributo) {
		case "lista":
			break;
		default:
			ConfiguratoreHashMapStringDouble conf = new ConfiguratoreHashMapStringDouble();
			conf.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, oggetto.getLista());
			break;
		}
	}

	@Override
	public ListaSpesa creaIstanzaOggetto(String nomeOggetto) {
		return new ListaSpesa();
	}
}
