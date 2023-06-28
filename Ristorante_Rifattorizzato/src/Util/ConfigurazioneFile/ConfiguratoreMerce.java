package Util.ConfigurazioneFile;

import java.io.BufferedWriter;
import java.io.IOException;
import Magazzino.Merce.*;

public class ConfiguratoreMerce extends ConfiguratoreManager {

	public ConfiguratoreMerce() {
		super();
	}

	@Override
	void scriviParametriNelFile(Object oggetto, BufferedWriter writer) {
		try {
			Merce merce = (Merce) oggetto;
			if (merce instanceof Ingrediente) {
				merce = (Ingrediente) merce;
			} else if (merce instanceof Bevanda) {
				merce = (Bevanda) merce;
			} else if(merce instanceof GenereExtra) {
				merce = (GenereExtra) merce;
			} else {
				merce = null; 
			}

			if (merce != null) {
				writer.write("Sotto-categoria=" + merce.getClass().getName());
				writer.newLine();
				writer.write("nomeMerce=" + merce.getNome());
				writer.newLine();
				writer.write("unitaMisura=" + merce.getUnitaMisura());
				writer.newLine();
				writer.write("scadenza=" + merce.getScadenza().toString());
				writer.newLine();
				writer.write("qualita=" + merce.getQualita());
			}
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto merce");
			e.printStackTrace();
		}
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object merce) {
		if (merce != null) {
			switch (nomeAttributo) {
			case "Sotto-categoria":
				break;
			case "nomeMerce":
				((Merce) merce).setNome(valoreAttributo);
				break;
			case "unitaMisura":
				((Merce) merce).setUnitaMisura(valoreAttributo);
				break;
			case "scadenza":
				((Merce) merce).setScadenza(Giorno.Giorno.parseGiorno(valoreAttributo));
				break;
			case "qualita":
				((Merce) merce).setQualita(Boolean.parseBoolean(valoreAttributo));
				break;
			default:
				System.out.println("Attributo non riconosciuto");
				break;
			}
		}
	}

	@Override
	public Object creaIstanzaOggetto(String nomeOggetto) {
		Merce merce = null;
		switch (nomeOggetto) {
		case "Magazzino.Merce.Ingrediente":
			merce = new Ingrediente("", ""); // Crea un'istanza di Ingrediente vuota
			break;
		case "Magazzino.Merce.Bevanda":
			merce = new Bevanda("", 0.0); // Crea un'istanza di Bevanda vuota
			break;
		case "Magazzino.Merce.GenereExtra":
			merce = new GenereExtra("", 0.0); // Crea un'istanza di GenereExtra vuota
			break;
		default:
			System.out.println("Tipo di merce non riconosciuto");
			break;
		}
		return merce;
	}

}
