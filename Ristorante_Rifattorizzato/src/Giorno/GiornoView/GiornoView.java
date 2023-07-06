package Giorno.GiornoView;

import java.time.LocalDate;

import Giorno.Giorno;
import Util.InputDati;

public class GiornoView {
	public LocalDate giorno;
	final static String messaggioAnno = "\nInserisci l'anno: ";
	final static String messaggioMese = "\nInserisci il mese: ";
	final static String messaggioGiorno = "\nInserisci il giorno: ";

	public static Giorno richiestaCreaGiorno() {
		int anno = InputDati.leggiInteroConMinimo(messaggioAnno, 2023);
		int mese = InputDati.leggiIntero(messaggioMese, 1, 12);
		int giorno = 0;
		if (mese == 1 || mese == 3 || mese == 5 || mese == 7 || mese == 8 || mese == 10|| mese == 12) {
			giorno = InputDati.leggiIntero(messaggioGiorno, 1, 31);
		} else if (mese == 4 || mese == 6 || mese == 9 || mese == 11) {
			giorno = InputDati.leggiIntero(messaggioGiorno, 1, 30);
		} else {
			giorno = InputDati.leggiIntero(messaggioGiorno, 1, 29);
		}
	
		return new Giorno (anno, mese, giorno);
	}
	
	public static void mostraDescrizioneGiorno(String descrizioneGiorno) {
        System.out.println(descrizioneGiorno);
	}
}