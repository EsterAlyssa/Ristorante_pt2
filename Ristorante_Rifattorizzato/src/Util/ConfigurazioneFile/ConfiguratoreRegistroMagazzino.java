package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

import Magazzino.ElementoMagazzino;
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

			writer.write("registro magazzino=");
			writer.newLine();
			// Scriviamo i singoli elementi del registro nel file
			for (String nomeMerce : registro.keySet()) {
				PriorityQueue<ElementoMagazzino> codaMerce = registro.get(nomeMerce);
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

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object oggetto) {
		switch (nomeAttributo) {
		case "registro magazzino":
			HashMap<String, PriorityQueue<ElementoMagazzino>> registro = new HashMap<>();
			registro = ausiliareSetAttributiDatoOggetto(nomeAttributo, valoreAttributo, registro);
			((RegistroMagazzino) oggetto).setRegistro(registro);
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
