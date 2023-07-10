package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

import Giorno.GiornoView.GiornoView;
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
				writer.write("Sotto-categoria=Ingrediente");
			} else if (merce instanceof Bevanda) {
				writer.write("Sotto-categoria=Bevanda");
			} else if (merce instanceof GenereExtra) {
				writer.write("Sotto-categoria=GenereExtra");
			}
			writer.newLine();

			if (merce != null) {
				writer.write("nomeMerce=" + merce.getNome());
				writer.newLine();
				writer.write("unitaMisura=" + merce.getUnitaMisura());
				writer.newLine();
				GiornoView giornoView = new GiornoView (merce.getScadenza().getGiorno());
				writer.write("scadenza=" + giornoView.descrizioneGiorno());
				writer.newLine();
				writer.write("qualita=" + merce.getQualita());
			}
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto merce");
			e.printStackTrace();
		}
	}

	@Override
	public Object caricaIstanzaOggettoDaFile(String pathFileOggetto) {
		Merce oggettoCaricato = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(pathFileOggetto));
			String line;
			String tipo = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("Sotto-categoria=")) {
					tipo = line.substring(16).trim(); // Estrarre il tipo dalla riga
					// Determina la classe specifica in base al tipo e crea un'istanza appropriata
					if (tipo != null) {
						oggettoCaricato = (Merce) creaIstanzaOggetto(tipo);
					}
				}
				oggettoCaricato = (Merce) caricaIstanzaOggetto(oggettoCaricato, line);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Impossibile caricare l'oggetto");
		}
		return oggettoCaricato;
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Object merce) {
		if (merce != null) {
			switch (nomeAttributo) {
			case "merce":
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
		case "Ingrediente":
			merce = new Ingrediente("", ""); // Crea un'istanza di Ingrediente vuota
			break;
		case "Bevanda":
			merce = new Bevanda("", 0.0); // Crea un'istanza di Bevanda vuota
			break;
		case "GenereExtra":
			merce = new GenereExtra("", 0.0); // Crea un'istanza di GenereExtra vuota
			break;
		default:
			System.out.println("Tipo di merce non riconosciuto");
			break;
		}
		return merce;
	}

	
	
}
