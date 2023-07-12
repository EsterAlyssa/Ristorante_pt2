package Giorno.GiornoView;

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

	public static Giorno richiestaCreaGiorno() {
		int anno = InputDati.leggiInteroConMinimo(MSG_ANNO, 2023);
		int mese = InputDati.leggiIntero(MSG_MESE, 1, 12);
		int giorno = 0;
		if (mese == 1 || mese == 3 || mese == 5 || mese == 7 || mese == 8 || mese == 10|| mese == 12) {
			giorno = InputDati.leggiIntero(MSG_GIORNO, 1, 31);
		} else if (mese == 4 || mese == 6 || mese == 9 || mese == 11) {
			giorno = InputDati.leggiIntero(MSG_GIORNO, 1, 30);
		} else {
			giorno = InputDati.leggiIntero(MSG_GIORNO, 1, 29);
		}

		return new Giorno (anno, mese, giorno);
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