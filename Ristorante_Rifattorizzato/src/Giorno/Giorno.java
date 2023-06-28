package Giorno;
import java.time.LocalDate;

import Util.InputDati;

public class Giorno implements Comparable<Giorno> {

	private LocalDate giorno;
	
	private final static String messaggioAnno = "\nInserisci l'anno: ";
	private final static String messaggioMese = "\nInserisci il mese: ";
	private final static String messaggioGiorno = "\nInserisci il giorno: ";


	public Giorno(LocalDate giorno) {
		this.giorno = giorno;
	}
	
	public Giorno (int anno, int mese, int giorno) { //aaaa-mm-gg
		this.giorno = LocalDate.of(anno, mese, giorno);
	}

	public LocalDate getGiorno() {
		return giorno;
	}

	public void setGiorno(LocalDate giorno) {
		this.giorno = giorno;
	}

	@Override
    public int compareTo(Giorno altroGiorno) {
        return this.giorno.compareTo(altroGiorno.getGiorno());
    }
	
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
	
	
	public boolean contains (LocalDate data, LocalDate inizio, LocalDate fine) {
		return (data.isEqual(inizio) || data.isAfter(inizio)) &&
				(data.isEqual(fine) || data.isBefore(fine));
	}

	public int confrontoScadenza(Giorno giorno2) {
		return giorno.compareTo(giorno2.getGiorno()); //negativo se giorno scade prima di giorno2,	
		//positivo se giorno scade dopo giorno2, 
		//0 se sono lo stesso giorno
	}
	
	public static Giorno ritornaGiornoCorrente() {
		return new Giorno (LocalDate.now());
	}

	@Override
	public String toString() {
		return giorno.getDayOfMonth() + "-" + giorno.getMonthValue() + "-" + giorno.getYear();
	}
	
	public static Giorno parseGiorno(String input) { //String stile aaaa-mm-gg
	    String[] parts = input.split("-");
	    int giorno = Integer.parseInt(parts[2]);
	    int mese = Integer.parseInt(parts[1]);
	    int anno = Integer.parseInt(parts[0]);

	    return new Giorno(anno, mese, giorno);
	}
	
}
