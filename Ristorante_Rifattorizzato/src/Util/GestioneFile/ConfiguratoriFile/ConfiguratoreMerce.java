package Util.GestioneFile.ConfiguratoriFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

import Giorno.GiornoView.GiornoView;
import Magazzino.Merce.*;

public class ConfiguratoreMerce extends ConfiguratoreManager<Merce> {

	public ConfiguratoreMerce() {
		super();
	}

	@Override
	void scriviParametriNelFile(Merce oggetto, BufferedWriter writer) {
		try {
			if (oggetto instanceof Ingrediente) {
				writer.write("Sotto-categoria=Ingrediente");
			} else if (oggetto instanceof Bevanda) {
				writer.write("Sotto-categoria=Bevanda");
			} else if (oggetto instanceof GenereExtra) {
				writer.write("Sotto-categoria=GenereExtra");
			}
			writer.newLine();

			if (oggetto != null) {
				writer.write("nomeMerce=" + oggetto.getNome());
				writer.newLine();
				writer.write("unitaMisura=" + oggetto.getUnitaMisura());
				writer.newLine();
				GiornoView giornoView = new GiornoView (oggetto.getScadenza().getGiorno());
				writer.write("scadenza=" + giornoView.descrizioneGiorno());
				writer.newLine();
				writer.write("qualita=" + oggetto.getQualita());
			}
		} catch (IOException e) {
			System.out.println("Impossibile salvare l'oggetto merce");
			e.printStackTrace();
		}
	}

	@Override
	public Merce caricaIstanzaOggettoDaFile(String pathFileOggetto) {
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
						oggettoCaricato = creaIstanzaOggetto(tipo);
					}
				}
				oggettoCaricato = caricaIstanzaOggetto(oggettoCaricato, line);
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Impossibile caricare l'oggetto");
		}
		return oggettoCaricato;
	}

	@Override
	public void setAttributiDatoOggetto(String nomeAttributo, String valoreAttributo, Merce merce) {
		if (merce != null) {
			switch (nomeAttributo) {
			case "merce":
				break;
			case "nomeMerce":
				merce.setNome(valoreAttributo);
				break;
			case "unitaMisura":
				merce.setUnitaMisura(valoreAttributo);
				break;
			case "scadenza":
				merce.setScadenza(Giorno.Giorno.parseGiorno(valoreAttributo));
				break;
			case "qualita":
				merce.setQualita(Boolean.parseBoolean(valoreAttributo));
				break;
			default:
				System.out.println("Attributo non riconosciuto");
				break;
			}
		}
	}

	@Override
	public Merce creaIstanzaOggetto(String nomeOggetto) {
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
