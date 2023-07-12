package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

import Magazzino.ElementoMagazzino;
import Magazzino.Merce.Merce;
import Util.GestioneFile.ServizioFile;

public class ConfiguratoreElementoMagazzino extends ConfiguratoreManager<ElementoMagazzino>{

	public ConfiguratoreElementoMagazzino() {
		super();
	}

	@Override
	public void scriviParametriNelFile(ElementoMagazzino oggetto, BufferedWriter writer) {
		try {
			Merce merce = oggetto.getMerce();
			writer.write("merce= ");
			writer.newLine();

			ConfiguratoreManager<Merce> configuratoreMerce = new ConfiguratoreMerce();
			configuratoreMerce.scriviParametriNelFile(merce, writer);

			writer.newLine();
			writer.write("quantita=" + oggetto.getQuantita());
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto Elemento Magazzino");
			e.printStackTrace();
		}
	}

	@Override
	public ElementoMagazzino caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		String nomeElementoMagazzino = ServizioFile.getNomeFileSenzaEstensione(pathFileOggetto);
		ElementoMagazzino elementoMagazzino = creaIstanzaOggetto(nomeElementoMagazzino);
		Merce merceCorrente = null; // Per tenere traccia della merce corrente
		boolean inSezioneMerce = false; // Per indicare se si è all'interno di una sezione di una merce
		ConfiguratoreManager<Merce> confMerce = new ConfiguratoreMerce();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathFileOggetto));
			String line;
			while ((line = reader.readLine()) != null) {
				letturaIstanza(line, merceCorrente, elementoMagazzino, inSezioneMerce, reader, confMerce);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Impossibile caricare l'oggetto");
		}
		return elementoMagazzino;
	}

	public ElementoMagazzino letturaIstanza(String line, Merce merceCorrente, 
			ElementoMagazzino elementoMagazzino, boolean inSezioneMerce, 
			BufferedReader reader, ConfiguratoreManager<Merce> confMerce) {
		
		if (line.startsWith("merce=")) {
			// Si aggiunge la merceCorrente all'elementoMagazzino
			if (merceCorrente != null) {
				elementoMagazzino.setMerce(merceCorrente);
				merceCorrente = null; //la merce viene poi annullato perchè dovrà "lasciare posto" a una nuova merce
			}
			inSezioneMerce = false; // Segna la fine della sezione della merce corrente

			// Inizia una nuova sezione di merce
			// Si legge la linea dopo per sapere il tipo di merce
			String tipo = "";
			try {
				tipo = reader.readLine();
				tipo = tipo.substring(tipo.indexOf('=')+1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			merceCorrente = confMerce.creaIstanzaOggetto(tipo);
			elementoMagazzino.setMerce(merceCorrente);
			inSezioneMerce = true;
		} else if (inSezioneMerce) {
			// Se si è all'interno di una sezione di merce, carica gli attributi
			merceCorrente = elementoMagazzino.getMerce();
			merceCorrente = confMerce.caricaIstanzaOggetto(merceCorrente, line);
			elementoMagazzino.setMerce(merceCorrente);
		} else {
			// Altrimenti, gestisci gli attributi generici dell'elemento magazzino
			String[] parte = line.split("=");
			if (parte.length == 2) {
				String nomeAttributo = parte[0].trim();
				String valoreAttributo = parte[1].trim();
				setAttributiDatoOggetto(nomeAttributo, valoreAttributo, elementoMagazzino);
			}
		}
		return elementoMagazzino;
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, ElementoMagazzino elementoMagazzino) {
		switch (nomeAttributo) {
		case "quantita":
			elementoMagazzino.setQuantita(Double.parseDouble(valoreAttributo));
			break;
		default: 
			System.out.println("Errore nel settaggio dei parametri");
			break;
		}
	}

	@Override
	public ElementoMagazzino creaIstanzaOggetto(String nomeOggetto) {
		return new ElementoMagazzino(null, 0.0);
	}
}
