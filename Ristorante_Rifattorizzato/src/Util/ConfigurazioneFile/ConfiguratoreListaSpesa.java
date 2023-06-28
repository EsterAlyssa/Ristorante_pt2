package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import Magazzino.ListaSpesa;

public class ConfiguratoreListaSpesa extends ConfiguratoreManager{

	public ConfiguratoreListaSpesa() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object listaSpesa, BufferedWriter writer) {
		try {
			HashMap<String, Double> lista = ((ListaSpesa) listaSpesa).getLista();
			writer.write("lista= ");
			writer.newLine();
			ConfiguratoreManager conf = new ConfiguratoreHashMapStringDouble();
			conf.scriviParametriNelFile(lista, writer);
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto lista spesa");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		switch (nomeAttributo) {
		case "lista":
			HashMap<String, Double> mapLista = new HashMap<>();
			ConfiguratoreHashMapStringDouble conf = new ConfiguratoreHashMapStringDouble();
			conf.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, mapLista);
			((ListaSpesa) oggetto).setLista(mapLista);
			break;
		default:
			System.out.println("Attributo non riconosciuto: " + nomeAttributo);
			break;
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new ListaSpesa();
	}
}
