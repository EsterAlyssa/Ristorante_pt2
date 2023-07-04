package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;

import Giorno.Periodo;
import Ristorante.ElementiRistorante.MenuCarta;
import Ristorante.ElementiRistorante.Piatto;

public class ConfiguratoreMenuCarta extends ConfiguratoreManager {

	public ConfiguratoreMenuCarta() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object menuCarta, BufferedWriter writer) {
		try {
			writer.write("validitaMenu=");
			writer.newLine();
			ConfiguratorePeriodo confP = new ConfiguratorePeriodo();
			confP.scriviParametriNelFile(((MenuCarta)menuCarta).getValidita(), writer);
			HashSet<Piatto> elenco = ((MenuCarta) menuCarta).getElenco();
			writer.write("elencoMenu= ");
			writer.newLine();
			ConfiguratorePiatto confPiat = new ConfiguratorePiatto();
			for (Piatto piatto : elenco) {
				confPiat.scriviParametriNelFile(piatto, writer);
				writer.newLine();
				writer.append("---");
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto menu carta");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		// Imposta l'attributo nell'oggetto menu carta utilizzando i metodi setter corrispondenti
		switch (nomeAttributo) {
		case "elencoMenu":
			HashSet<Piatto> elenco = new HashSet<>();
			String[] piatti = valoreAttributo.split("\n---\n");
			ConfiguratorePiatto confPiat = new ConfiguratorePiatto();
			for (String piatto : piatti) {
				Piatto p = new Piatto(piatto); //l'oggetto piatto ha nel attributo nome tutta la stringa
				int i=1;
				String nomePiatto = "Piatto"+i;
				confPiat.caricaIstanzaOggetto(nomePiatto, piatto);; //sovrascritto il nome dell'oggetto
				elenco.add(p);
				i++;
			}
			((MenuCarta) oggetto).setElenco(elenco);
			break;    
		case "validitaMenu":
			// Chiamata al metodo statico parsePeriodo per ottenere un oggetto di tipo Periodo
			((MenuCarta) oggetto).setValidita(Periodo.parsePeriodo(valoreAttributo));
			break;
		default:
			System.out.println("Errore nel settaggio dei parametri");
			break;
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new MenuCarta();
	}
}
