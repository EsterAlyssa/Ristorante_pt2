package Giorno;

import java.util.TreeSet;

public class Periodo {
	public TreeSet<Giorno> periodoValidita;

	public Periodo () {
		this.periodoValidita = new TreeSet<>();
	}

	public TreeSet<Giorno> getPeriodoValidita() {
		return periodoValidita;
	}

	public void setPeriodoValidita(TreeSet<Giorno> periodoValidita) {
		this.periodoValidita = periodoValidita;
	}

	public boolean aggiungiGiorno(Giorno giorno) {
		return this.periodoValidita.add(giorno);
	}
	
	public static Periodo unisciPeriodi(Periodo periodo1, Periodo periodo2) {
		Periodo periodoSomma = new Periodo();
		// Aggiungi tutti i giorni del primo periodo al periodoSomma
		periodoSomma.getPeriodoValidita().addAll(periodo1.getPeriodoValidita());
		// Aggiungi tutti i giorni del secondo periodo al periodoSomma
		periodoSomma.getPeriodoValidita().addAll(periodo2.getPeriodoValidita());
		return periodoSomma;
	}

	public static Periodo parsePeriodo(String input) { //String stile aaaa-mm-gg;\naaaa-mm-gg ...
		Periodo periodo = new Periodo();

		String[] giorni = input.split(";\n");
		for (String giornoStr : giorni) {
			Giorno giorno = Giorno.parseGiorno(giornoStr);
			periodo.getPeriodoValidita().add(giorno);
		}
		return periodo;
	}

}
