package Giorno.GiornoView;

import java.time.DateTimeException;
import java.time.LocalDate;

import Giorno.Giorno;
import Util.InputDati;

public class GiornoView {

	private LocalDate giorno;

	public GiornoView(LocalDate giorno) {
		this.giorno = giorno;
	}

	static final String MSG_ANNO = "\nInserisci l'anno: ";
	static final String MSG_MESE = "\nInserisci il mese: ";
	static final String MSG_GIORNO = "\nInserisci il giorno: ";
	
	static final String MSG_SUCCESSO_CREAZIONE_GIORNO = "\nGiorno creato con successo!";
	static final String MSG_ERR_CREAZIONE_GIORNO = "ATTENZIONE! Il giorno inserito non è valido.";

	public static Giorno richiestaCreaGiorno() {
		LocalDate data = null;
		boolean trovato = true;
		while (trovato) {
			int anno = InputDati.leggiInteroConMinimo(MSG_ANNO, 2023);
			int mese = InputDati.leggiIntero(MSG_MESE, 1, 12);
			int giorno = InputDati.leggiIntero(MSG_GIORNO,1,31);

			try {
				data = LocalDate.of(anno, mese, giorno);
				trovato = false;
				System.out.println(MSG_SUCCESSO_CREAZIONE_GIORNO);
			} catch (DateTimeException e) {
				System.out.println(MSG_ERR_CREAZIONE_GIORNO);
				trovato = true;
			}
		}
		return new Giorno (data);
	}

	public String descrizioneGiorno() {
		return giorno.getDayOfMonth() + "-" + 
				giorno.getMonthValue() + "-" + 
				giorno.getYear();
	}

	public void mostraDescrizioneGiorno() {
		System.out.println(descrizioneGiorno());
	}
}