package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import Giorno.Giorno;
import Prenotazioni.Prenotazione;
import Prenotazioni.SceltaPrenotazione;

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
			writer.write("data=" + ((Prenotazione) prenotazione).getData().toString());
			writer.newLine();

			HashMap<SceltaPrenotazione, Integer> elenco = ((Prenotazione) prenotazione).getElenco();
			writer.write("elenco= ");
			writer.newLine();
			ConfiguratoreManager confScelta = new ConfiguratoreSceltaPrenotazione();
			for (SceltaPrenotazione scelta : elenco.keySet()) {
				writer.write("quantitaPrenotate=" + elenco.get(scelta));
				writer.newLine();
				writer.append('~');
				confScelta.scriviParametriNelFile(scelta, writer);
				writer.newLine();
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
		case "data":
			((Prenotazione)oggetto).setData(Giorno.parseGiorno(valoreAttributo));
			break;
		case "elenco":
			HashMap<SceltaPrenotazione, Integer> elenco = new HashMap<>();
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
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new Prenotazione(nomeOggetto);
	}
}
