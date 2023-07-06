package Util.ConfigurazioneFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import Giorno.Giorno;
import Prenotazioni.Prenotazione;
import Prenotazioni.SceltaPrenotazione;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Util.ServizioFile;

public class ConfiguratorePrenotazione extends ConfiguratoreManager{

	public ConfiguratorePrenotazione() {
		super();
	}

	void scriviParametriNelFile(Object prenotazione, BufferedWriter writer) {        
		try {
			writer.write("cliente=" + ((Prenotazione) prenotazione).getCliente());
			writer.newLine();
			writer.write("numCoperti=" + ((Prenotazione) prenotazione).getNumCoperti());
			writer.newLine();
			writer.write("data=" + ((Prenotazione) prenotazione).getData().descrizioneGiorno());
			writer.newLine();

			HashMap<SceltaPrenotazione, Integer> elenco = ((Prenotazione) prenotazione).getElenco();
			writer.write("elenco= ");
			writer.newLine();
			ConfiguratoreManager confScelta = new ConfiguratoreSceltaPrenotazione();
			for (SceltaPrenotazione scelta : elenco.keySet()) {
				writer.append('~');
				writer.newLine();
				writer.write("quantitaPrenotate=" + elenco.get(scelta));
				writer.newLine();
				writer.newLine();
				confScelta.scriviParametriNelFile(scelta, writer);
				writer.append("----");
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto prenotazione");
			e.printStackTrace();
		}
	}


	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		// Imposta l'attributo nell'oggetto menu carta utilizzando i metodi setter corrispondenti
		switch (nomeAttributo) {
		case "cliente":
			((Prenotazione)oggetto).setCliente(valoreAttributo);
			break;
		case "numCoperti":
			((Prenotazione)oggetto).setNumCoperti(Integer.parseInt(valoreAttributo));
			break;
			// probabile errore
		case "data":
			ConfiguratoreManager confGiorno = new ConfiguratoreGiorno();
			confGiorno.setAttributiDatoOggetto(nomeAttributo, valoreAttributo, ((Prenotazione)oggetto).getData());
			break;
		case "elenco":
			break;
			/*		HashMap<SceltaPrenotazione, Integer> elenco = new HashMap<>();
			String[] sceltePrenotate = valoreAttributo.split("\n----\n");
			ConfiguratoreManager confScelta = new ConfiguratoreSceltaPrenotazione();
			for (String scelta : sceltePrenotate) {
				String[] coppia = scelta.split("~");
				String quantitaPrenotate = coppia[0].trim();
				String descrizioneScelta = coppia[1].trim();

				String[] perQuantitaSelta = quantitaPrenotate.split("=");
				int numScelta = Integer.parseInt(perQuantitaSelta[1].trim());

				SceltaPrenotazione sceltaPren = (SceltaPrenotazione) confScelta.creaIstanzaOggetto(descrizioneScelta);
				confScelta.setAttributiDatoOggetto(scelta, descrizioneScelta, sceltaPren);

				elenco.put(sceltaPren, numScelta);
			}
			((Prenotazione)oggetto).setElenco(elenco);
			break;
			 */
		default:
			System.out.println("Errore nel settaggio dei parametri");
			break;
		}
	}

	//da aggiustare per la prenotazione
	@Override
	public Object caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		String nomePrenotazione = ServizioFile.getNomeFileSenzaEstensione(pathFileOggetto);
		Prenotazione pren = new Prenotazione(nomePrenotazione);
		SceltaPrenotazione sceltaCorrente = null; // Per tenere traccia della scelta corrente
		boolean inSezioneScelta = false; // Per indicare se si è all'interno di una sezione di una scelta
		ConfiguratoreManager confScelta = new ConfiguratoreSceltaPrenotazione();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathFileOggetto));
			String line;
			while ((line = reader.readLine()) != null) {
				int quantita = 0;
				if (line.equals("~")) {
					// Se si incontra il separatore, si aggiunge sceltaCorrente all'insieme MenuTematico
					if (sceltaCorrente != null) {
						pren.aggiungiScelta(sceltaCorrente, quantita);
						sceltaCorrente = null; //la scelta viene poi annullato perchè dovrà "lasciare posto" a quella nuova
					}
					inSezioneScelta = false; // Segna la fine della sezione della scelta corrente
				} else if (line.startsWith("quantitaPrenotate")) {
					// Inizia una nuova sezione della scelta
					quantita = Integer.parseInt(line.substring(line.indexOf('=') + 1));
					sceltaCorrente = (SceltaPrenotazione) confScelta.creaIstanzaOggetto("");
					inSezioneScelta = true;
				} else if (inSezioneScelta) {
					// Se si è all'interno di una sezione di scelta, carica gli attributi
					confScelta.caricaIstanzaOggetto(sceltaCorrente, line);
				} else {
					// Altrimenti, gestisci gli attributi generici della prenotazione
					String[] parte = line.split("=");
					if (parte.length == 2) {
						String nomeAttributo = parte[0].trim();
						String valoreAttributo = parte[1].trim();
						setAttributiDatoOggetto(nomeAttributo, valoreAttributo, pren);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Impossibile caricare l'oggetto");
		}
		return pren;
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new Prenotazione(nomeOggetto);
	}
}
