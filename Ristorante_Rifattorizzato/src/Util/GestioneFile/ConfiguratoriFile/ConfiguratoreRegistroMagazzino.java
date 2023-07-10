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
			writer.write("~");
			writer.newLine();
			// Scriviamo i singoli elementi del registro nel file
			for (String nomeMerce : registro.keySet()) {
				PriorityQueue<ElementoMagazzino> codaMerce = registro.get(nomeMerce);

				writer.write("chiaveMerce=" + nomeMerce);
				writer.newLine();

				for (ElementoMagazzino elemento : codaMerce) {

					ConfiguratoreElementoMagazzino configuratoreElementoMagazzino = new ConfiguratoreElementoMagazzino();
					configuratoreElementoMagazzino.scriviParametriNelFile(elemento, writer);
					writer.newLine();
					writer.write("---");
					writer.newLine();
				}
				writer.write("~");
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto Registro Magazzino");
			e.printStackTrace();
		}
	}

	@Override
	public Object caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		RegistroMagazzino registroMagazzino = new RegistroMagazzino();
		HashMap<String, PriorityQueue<ElementoMagazzino>> registro = new HashMap<>();
		ConfiguratoreElementoMagazzino confEleMag = new ConfiguratoreElementoMagazzino();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathFileOggetto));
			String line;
			String keyElementoRegistro = null;
			PriorityQueue<ElementoMagazzino> codaElementoRegistro = new PriorityQueue<>();
			ElementoMagazzino elementoMagazzinoCorrente = null;
			boolean inSezioneElementoMagazzino = false;
			boolean inSezioneElementoRegistro = true;
			Merce merceCorrente = null; // Per tenere traccia della merce corrente
			boolean inSezioneMerce = false; // Per indicare se si è all'interno di una sezione di una merce
			ConfiguratoreMerce confMerce = new ConfiguratoreMerce();


			while ((line = reader.readLine()) != null) {
				if (inSezioneElementoRegistro && line.equals("~")) {
					if (keyElementoRegistro != null && codaElementoRegistro != null) {
						registro.put(keyElementoRegistro, codaElementoRegistro);
					}
					keyElementoRegistro = null;
					codaElementoRegistro = null;
					inSezioneElementoRegistro = false;
				} else if (line.startsWith("chiaveMerce=")) {
					// Nome della merce
					keyElementoRegistro = line.substring(line.indexOf('=') + 1);
					if (registro.containsKey(keyElementoRegistro)) {
						codaElementoRegistro = registro.get(keyElementoRegistro);
					} else {
						codaElementoRegistro = new PriorityQueue<>();
					}
					elementoMagazzinoCorrente = (ElementoMagazzino) confEleMag.creaIstanzaOggetto(keyElementoRegistro);
					inSezioneElementoRegistro = true;
				} else if (line.startsWith("merce=")) {
					inSezioneElementoMagazzino=true;
					elementoMagazzinoCorrente = confEleMag.letturaIstanza(line, merceCorrente, elementoMagazzinoCorrente, inSezioneMerce, reader, confMerce);
					inSezioneMerce = true;
				} else if(line.startsWith("quantita")) {
					inSezioneMerce = false;
					elementoMagazzinoCorrente = confEleMag.letturaIstanza(line, merceCorrente, elementoMagazzinoCorrente, inSezioneMerce, reader, confMerce);
				} else if (inSezioneElementoMagazzino && line.equals("---")) {
					//tra un ElementoMagazzino e l'altro c'è il separatore "---", 
					//quindi si deve salvare l'elemento nella priorityQueue
					
					codaElementoRegistro.add(elementoMagazzinoCorrente);
					elementoMagazzinoCorrente = (ElementoMagazzino) confEleMag.creaIstanzaOggetto(keyElementoRegistro);
					inSezioneElementoMagazzino = false;
				} else if(inSezioneElementoRegistro && inSezioneElementoMagazzino) {
					elementoMagazzinoCorrente = confEleMag.letturaIstanza(line, merceCorrente, elementoMagazzinoCorrente, inSezioneMerce, reader, confMerce);
				} else {
					String[] parte = line.split("=");
					if (parte.length == 2) {
						String nomeAttributo = parte[0].trim();
						String valoreAttributo = parte[1].trim();
						setAttributiDatoOggetto(nomeAttributo, valoreAttributo, registro);
					}
				}
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
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		return new RegistroMagazzino();
	}
}
