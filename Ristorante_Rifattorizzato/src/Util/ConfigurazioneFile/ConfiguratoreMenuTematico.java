package Util.ConfigurazioneFile;

import java.io.*;
import java.util.HashSet;

import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Util.ServizioFile;

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
			writer.write("validitaMenu=");
			writer.newLine();
			ConfiguratorePeriodo confP = new ConfiguratorePeriodo();
			confP.scriviParametriNelFile(menu.getValidita(), writer);
			writer.write("caricoLavoroMenuTematico=" + menu.getCaricoLavoro());
			writer.newLine();

			HashSet<Piatto> elenco = ((MenuTematico) menu).getElenco();
			writer.write("elencoMenu= ");
			writer.newLine();
			writer.newLine();
			ConfiguratoreManager confPiat = new ConfiguratorePiatto();
			for (Piatto piatto : elenco) {
				confPiat.scriviParametriNelFile(piatto, writer);
				writer.append("---");
				writer.newLine();
			}

		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto menu tematico");
			e.printStackTrace();
		}
	}

	@Override
	public Object caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		String nomeMenu = ServizioFile.getNomeFileSenzaEstensione(pathFileOggetto);
		MenuTematico menuTematico = (MenuTematico) creaIstanzaOggetto(nomeMenu);
		Piatto piattoCorrente = null; // Per tenere traccia del piatto corrente
		boolean inSezionePiatto = false; // Per indicare se si è all'interno di una sezione di un piatto

		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathFileOggetto));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.equals("---")) {
					// Se si incontra il separatore, si aggiunge il piattoCorrente all'insieme MenuTematico
					if (piattoCorrente != null) {
						menuTematico.aggiungiPiatto(piattoCorrente);
						piattoCorrente = null; //il piatto viene poi annullato perchè dovrà "lasciare posto" a un nuovo piatto
					}
					inSezionePiatto = false; // Segna la fine della sezione del piatto corrente
				} else if (line.startsWith("nomePiatto")) {
					// Inizia una nuova sezione di piatto
					piattoCorrente = new Piatto(line.substring(line.indexOf('=') + 1));
					inSezionePiatto = true;
				} else if (inSezionePiatto) {
					// Se si è all'interno di una sezione di piatto, carica gli attributi del piatto
					ConfiguratoreManager confPiatto = new ConfiguratorePiatto();
					confPiatto.caricaIstanzaOggetto(piattoCorrente, line);
				} else {
					// Altrimenti, gestisci gli attributi generici del menu tematico
					String[] parte = line.split("=");
					if (parte.length == 2) {
						String nomeAttributo = parte[0].trim();
						String valoreAttributo = parte[1].trim();
						setAttributiDatoOggetto(nomeAttributo, valoreAttributo, menuTematico);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Impossibile caricare l'oggetto");
		}
		return menuTematico;
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		// Imposta l'attributo nell'oggetto menu tematico utilizzando i metodi setter corrispondenti
		switch (nomeAttributo) {
		case "nomeMenuTematico":
			((MenuTematico) oggetto).setNome(valoreAttributo);
			break;
		case "validitaMenu":
			//questa linea è validitaPiatto= quindi non dovrebbe salvare valori, solo far capire che
			//deve iniziare un elenco di giorni
			break;
		case "giorno":
			// Il valoreAttributo contiene i giorni nel formato "gg-mm-aaaa;"
			ConfiguratorePeriodo confP = new ConfiguratorePeriodo();
			confP.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, ((MenuTematico)oggetto).getValidita());
			break;
		case "caricoLavoroMenuTematico":
			((MenuTematico) oggetto).setCaricoLavoro(Double.parseDouble(valoreAttributo));
			break;   
		case "elencoMenu":
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
