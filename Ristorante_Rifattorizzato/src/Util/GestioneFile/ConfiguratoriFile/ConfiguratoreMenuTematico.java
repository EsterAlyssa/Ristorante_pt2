package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import Giorno.Periodo;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Util.GestioneFile.ServizioFile;

public class ConfiguratoreMenuTematico extends ConfiguratoreManager<MenuTematico> {
	public ConfiguratoreMenuTematico() {
		super();
	}

	@Override
	void scriviParametriNelFile(MenuTematico menuTematico, BufferedWriter writer) {
		try {
			writer.write("nomeMenuTematico=" + menuTematico.getNome());
			writer.newLine();
			writer.write("validitaMenu=");
			writer.newLine();
			ConfiguratoreManager<Periodo> confP = new ConfiguratorePeriodo();
			confP.scriviParametriNelFile(menuTematico.getValidita(), writer);
			writer.write("caricoLavoroMenuTematico=" + menuTematico.getCaricoLavoro());
			writer.newLine();

			HashSet<Piatto> elenco = menuTematico.getElenco();
			writer.write("elencoMenu= ");
			writer.newLine();
			writer.newLine();
			ConfiguratoreManager<Piatto> confPiat = new ConfiguratorePiatto();
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
	public MenuTematico caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		String nomeMenu = ServizioFile.getNomeFileSenzaEstensione(pathFileOggetto);
		MenuTematico menuTematico = creaIstanzaOggetto(nomeMenu);
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
					ConfiguratoreManager<Piatto> confPiatto = new ConfiguratorePiatto();
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
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, 
			MenuTematico oggetto) {
		// Imposta l'attributo nell'oggetto menu tematico utilizzando i metodi setter corrispondenti
		switch (nomeAttributo) {
		case "nomeMenuTematico":
			oggetto.setNome(valoreAttributo);
			break;
		case "validitaMenu":
			//questa linea è validitaPiatto= quindi non dovrebbe salvare valori, solo far capire che
			//deve iniziare un elenco di giorni
			break;
		case "giorno":
			// Il valoreAttributo contiene i giorni nel formato "gg-mm-aaaa;"
			ConfiguratoreManager<Periodo> confP = new ConfiguratorePeriodo();
			confP.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, oggetto.getValidita());
			break;
		case "caricoLavoroMenuTematico":
			oggetto.setCaricoLavoro(Double.parseDouble(valoreAttributo));
			break;   
		case "elencoMenu":
			break;
		default:
			System.out.println("Errore nel settaggio dei parametri");
			break;
		}
	}

	@Override
	public MenuTematico creaIstanzaOggetto(String nomeOggetto) {
		return new MenuTematico(nomeOggetto);
	}

}
