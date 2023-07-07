package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

import Magazzino.ElementoMagazzino;
import Magazzino.Merce.*;
import Magazzino.RegistroMagazzino;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Util.GestioneFile.ServizioFile;

public class ConfiguratoreRegistroMagazzino extends ConfiguratoreManager {

	public ConfiguratoreRegistroMagazzino() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object oggetto, BufferedWriter writer) {
		try {
			RegistroMagazzino registroMagazzino = (RegistroMagazzino) oggetto;
			HashMap<String, PriorityQueue<ElementoMagazzino>> registro = registroMagazzino.getRegistro();

			writer.write("registro magazzino= ");
			writer.newLine();
			// Scriviamo i singoli elementi del registro nel file
			for (String nomeMerce : registro.keySet()) {
				PriorityQueue<ElementoMagazzino> codaMerce = registro.get(nomeMerce);
				writer.write("~");
				writer.write("nomeMerce=" + nomeMerce);
				writer.newLine();

				for (ElementoMagazzino elemento : codaMerce) {

					ConfiguratoreElementoMagazzino configuratoreElementoMagazzino = new ConfiguratoreElementoMagazzino();
					configuratoreElementoMagazzino.scriviParametriNelFile(elemento, writer);
					writer.newLine();
					writer.write("---");
					writer.newLine();
				}
			}
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto Registro Magazzino");
			e.printStackTrace();
		}
	}

	//da aggiustare 
	@Override
	public Object caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		RegistroMagazzino registroMagazzino = new RegistroMagazzino();
		HashMap<String, PriorityQueue<ElementoMagazzino>> registro = new HashMap<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathFileOggetto));
			String line;
			String nomeMerce = null;
			PriorityQueue<ElementoMagazzino> codaMerce = null;
			boolean inSezioneMerce = false;
			boolean inSezioneElemento = false;

			while ((line = reader.readLine()) != null) {
				if (line.equals("registro magazzino=")) {
					inSezioneMerce = true;
				} else if (inSezioneMerce && line.startsWith("~")) {
					// Nuovo elemento del registro
					if (nomeMerce != null && codaMerce != null) {
						registro.put(nomeMerce, codaMerce);
					}
					nomeMerce = null;
					codaMerce = null;
					inSezioneElemento = false;
				} else if (inSezioneMerce && line.startsWith("nomeMerce=")) {
					// Nome della merce
					nomeMerce = line.substring(line.indexOf('=') + 1);
					codaMerce = new PriorityQueue<>();
					inSezioneElemento = false;
				} else if (inSezioneElemento && line.equals("---")) {
					// Fine dell'elemento corrente
					inSezioneElemento = false;
				} else if (line.equals("merce=")) {
					// Nuovo elemento del registro
					ElementoMagazzino elementoMagazzino = new ElementoMagazzino(null, 0);
					Merce merce = (Merce) creaIstanzaOggetto("Merce");
					elementoMagazzino.setMerce(merce);
					codaMerce.offer(elementoMagazzino);
					inSezioneElemento = true;
				} else if (inSezioneElemento) {
					// Carica gli attributi dell'elemento del registro
					ConfiguratoreManager confElemento = new ConfiguratoreElementoMagazzino();
					confElemento.caricaIstanzaOggetto(codaMerce.peek(), line);
				}
			}

			// Aggiungi l'ultimo elemento del registro alla mappa
			if (nomeMerce != null && codaMerce != null) {
				registro.put(nomeMerce, codaMerce);
			}

			reader.close();
		} catch (IOException e) {
			System.out.println("Impossibile caricare l'oggetto");
		}

		registroMagazzino.setRegistro(registro);
		return registroMagazzino;
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		switch (nomeAttributo) {
		case "registro magazzino":
			break;
		case "nomeMerce":
			PriorityQueue<ElementoMagazzino> codaVuota = new PriorityQueue<ElementoMagazzino>();
			((RegistroMagazzino) oggetto).getRegistro().put(valoreAttributo, codaVuota);
			break;
		}
	}

	public HashMap<String, PriorityQueue<ElementoMagazzino>> ausiliareSetAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, HashMap<String, PriorityQueue<ElementoMagazzino>> registro) {
		String[] elementi = valoreAttributo.split("---");
		for (String elemento : elementi) {
			if (!elemento.trim().isEmpty()) {
				String nomeMerce = "";
				PriorityQueue<ElementoMagazzino> codaMerce = new PriorityQueue<>((em1, em2) ->
				em1.getMerce().getScadenza().confrontoScadenza(em2.getMerce().getScadenza()));

				String[] righe = elemento.split("\n");
				ElementoMagazzino elementoMagazzino = new ElementoMagazzino(null, 0.0);
				for (String riga : righe) {
					if (!riga.trim().isEmpty()) {
						String[] coppia = riga.split("=");
						String nomeAttr = coppia[0].trim();
						String valoreAttr = coppia[1].trim();

						ConfiguratoreElementoMagazzino configuratoreElementoMagazzino = new ConfiguratoreElementoMagazzino();
						configuratoreElementoMagazzino.setAttributiDatoOggetto(nomeAttr, valoreAttr, elementoMagazzino);

						if (nomeAttr.equals("nomeMerce")) {
							nomeMerce = valoreAttr;
						}
					}
				}
				codaMerce.add(elementoMagazzino);
				registro.put(nomeMerce, codaMerce);
			}
		}
		return registro;
	}


	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new RegistroMagazzino();
	}
}
