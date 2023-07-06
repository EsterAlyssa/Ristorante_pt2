package Util.ConfigurazioneFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import Giorno.Periodo;
import Ristorante.ElementiRistorante.MenuCarta;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Util.ServizioFile;

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
			writer.newLine();
			ConfiguratorePiatto confPiat = new ConfiguratorePiatto();
			for (Piatto piatto : elenco) {
				confPiat.scriviParametriNelFile(piatto, writer);
				writer.append("---");
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto menu carta");
			e.printStackTrace();
		}
	}

	@Override
	public Object caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		String nomeMenu = ServizioFile.getNomeFileSenzaEstensione(pathFileOggetto);
		MenuCarta menuCarta = (MenuCarta) creaIstanzaOggetto(nomeMenu);
		Piatto piattoCorrente = null; // Per tenere traccia del piatto corrente
		boolean inSezionePiatto = false; // Per indicare se si è all'interno di una sezione di un piatto

		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathFileOggetto));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.equals("---")) {
					// Se si incontra il separatore, si aggiunge il piattoCorrente all'insieme MenuCarta
					if (piattoCorrente != null) {
						menuCarta.aggiungiPiatto(piattoCorrente);
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
						setAttributiDatoOggetto(nomeAttributo, valoreAttributo, menuCarta);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Impossibile caricare l'oggetto");
		}
		return menuCarta;
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		// Imposta l'attributo nell'oggetto menu carta utilizzando i metodi setter corrispondenti
		switch (nomeAttributo) {
		case "validitaMenu":
			//questa linea è validitaMenu= quindi non dovrebbe salvare valori, solo far capire che
			//deve iniziare un elenco di giorni
			break;
		case "giorno":
			// Il valoreAttributo contiene i giorni nel formato "gg-mm-aaaa;"
			ConfiguratorePeriodo confP = new ConfiguratorePeriodo();
			confP.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, ((Piatto)oggetto).getValidita());
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
		return new MenuCarta();
	}
}
