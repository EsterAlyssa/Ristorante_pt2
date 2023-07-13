package Giorno;
import java.time.LocalDate;

public class Giorno implements Comparable<Giorno> {

	private LocalDate giorno;

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

	public void setGiorno(Giorno giorno) {
		this.giorno = giorno.getGiorno();
	}

	@Override
	public int compareTo(Giorno altroGiorno) {
		return this.giorno.compareTo(altroGiorno.getGiorno());
	}

	public static Giorno ritornaGiornoCorrente() {
		return new Giorno (LocalDate.now());
	}
	
	public static Giorno parseGiorno(String input) { //String stile gg-mm-aaaa
		String[] parts = input.split("-");
		int giorno = Integer.parseInt(parts[0]);
		int mese = Integer.parseInt(parts[1]);
		int anno = Integer.parseInt(parts[2]);

		return new Giorno(anno, mese, giorno);
	}

}
