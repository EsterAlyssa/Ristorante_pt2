package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;

import Giorno.Periodo;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;

public class ConfiguratoreMenuTematico extends ConfiguratoreManager {
	public ConfiguratoreMenuTematico() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object menuTematico, BufferedWriter writer) {
		try {
			MenuTematico menu = (MenuTematico) menuTematico;
			writer.write("nomeMenuTematico=" + menu.getNome());
			writer.newLine();
			writer.write("validitaMenu=" + menu.getValidita().toString());
			writer.newLine();
			writer.write("caricoLavoroMenuTematico=" + menu.getCaricoLavoro());
			writer.newLine();
			
			HashSet<Piatto> elenco = ((MenuTematico) menu).getElenco();
			writer.write("elencoMenu= ");
			ConfiguratoreManager confPiat = new ConfiguratorePiatto();
			for (Piatto piatto : elenco) {
				confPiat.scriviParametriNelFile(piatto, writer);
				writer.newLine();
				writer.append("---");
				writer.newLine();
			}
			
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto menu tematico");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		// Imposta l'attributo nell'oggetto menu tematico utilizzando i metodi setter corrispondenti
		switch (nomeAttributo) {
		case "nomeMenuTematico":
			((MenuTematico) oggetto).setNome(valoreAttributo);
			break;
		case "validitaMenu":
			// Chiamata al metodo statico parsePeriodo per ottenere un oggetto di tipo Periodo
			((MenuTematico) oggetto).setValidita(Periodo.parsePeriodo(valoreAttributo));
			break;
		case "caricoLavoroMenuTematico":
			((MenuTematico) oggetto).setCaricoLavoro(Double.parseDouble(valoreAttributo));
			break;
		case "elencoMenu":
			HashSet<Piatto> elenco = new HashSet<>();
			String[] piatti = valoreAttributo.split("\n---\n");
			ConfiguratoreManager confPiat = new ConfiguratorePiatto();
			for (String piatto : piatti) {
				int i=1;
				String nomePiatto = "Piatto"+i;
				Piatto p = new Piatto(nomePiatto); 
				confPiat.caricaIstanzaOggetto(nomePiatto, piatto);
				elenco.add(p);
				i++;
			}
			((MenuTematico) oggetto).setElenco(elenco);
			break;   
		default:
			System.out.println("Errore nel settaggio dei parametri");
			break;
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new MenuTematico(nomeOggetto);
	}

}
