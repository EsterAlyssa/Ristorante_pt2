package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

import Magazzino.ElementoMagazzino;
import Magazzino.Merce.Merce;
import Util.GestioneFile.ServizioFile;

public class ConfiguratoreElementoMagazzino extends ConfiguratoreManager{

	public ConfiguratoreElementoMagazzino() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object oggetto, BufferedWriter writer) {
		try {
			ElementoMagazzino elementoMagazzino = (ElementoMagazzino) oggetto;
			Merce merce = elementoMagazzino.getMerce();
			writer.write("merce= ");
			writer.newLine();

			ConfiguratoreMerce configuratoreMerce = new ConfiguratoreMerce();
			configuratoreMerce.scriviParametriNelFile(merce, writer);

			writer.newLine();
			writer.write("quantita=" + elementoMagazzino.getQuantita());
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto Elemento Magazzino");
			e.printStackTrace();
		}
	}

	@Override
	public Object caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		String nomeElementoMagazzino = ServizioFile.getNomeFileSenzaEstensione(pathFileOggetto);
		ElementoMagazzino elementoMagazzino = (ElementoMagazzino) creaIstanzaOggetto(nomeElementoMagazzino);
		Merce merceCorrente = null; // Per tenere traccia della merce corrente
		boolean inSezioneMerce = false; // Per indicare se si è all'interno di una sezione di una merce
		ConfiguratoreMerce confMerce = new ConfiguratoreMerce();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathFileOggetto));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("merce=")) {
					// Si aggiunge la merceCorrente all'elementoMagazzino
					if (merceCorrente != null) {
						elementoMagazzino.setMerce(merceCorrente);
						merceCorrente = null; //la merce viene poi annullato perchè dovrà "lasciare posto" a una nuova merce
					}
					inSezioneMerce = false; // Segna la fine della sezione della merce corrente
				
					// Inizia una nuova sezione di merce
					// Si legge la linea dopo per sapere il tipo di merce
					String tipo = reader.readLine().substring(line.indexOf('=') + 1);
					merceCorrente = (Merce) confMerce.creaIstanzaOggetto(tipo);
					inSezioneMerce = true;
				} else if (inSezioneMerce) {
					// Se si è all'interno di una sezione di merce, carica gli attributi
					confMerce.caricaIstanzaOggetto(merceCorrente, line);
				} else {
					// Altrimenti, gestisci gli attributi generici dell'elemento magazzino
					String[] parte = line.split("=");
					if (parte.length == 2) {
						String nomeAttributo = parte[0].trim();
						String valoreAttributo = parte[1].trim();
						setAttributiDatoOggetto(nomeAttributo, valoreAttributo, elementoMagazzino);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Impossibile caricare l'oggetto");
		}
		return elementoMagazzino;
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object elementoMagazzino) {
		switch (nomeAttributo) {
		case "quantita":
			((ElementoMagazzino) elementoMagazzino).setQuantita(Double.parseDouble(valoreAttributo));
			break;
		default: 
			System.out.println("Errore nel settaggio dei parametri");
			break;
		}
	}


	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new ElementoMagazzino(null, 0.0);
	}
}
