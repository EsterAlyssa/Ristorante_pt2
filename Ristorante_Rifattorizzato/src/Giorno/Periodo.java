package Giorno;

import java.util.TreeSet;

import Util.InputDati;

public class Periodo {
	private TreeSet<Giorno> periodoValidita;
	
	private final static String MSG_PERIODO = "Inserisci il periodo di validità: ";
	private final static String MSG_PIU_GIORNI = "\nVuoi inserire altri giorni di validita'? ";

	public Periodo () {
		this.periodoValidita = new TreeSet<>();
	}

	// crea un periodo dato un singolo giorno
	public Periodo(Giorno giorno) {
		this.periodoValidita = new TreeSet<>();
		periodoValidita.add(giorno);
	}
	public TreeSet<Giorno> getPeriodoValidita() {
		return periodoValidita;
	}

	public void setPeriodoValidita(TreeSet<Giorno> periodoValidita) {
		this.periodoValidita = periodoValidita;
	}
	
	public void creaPeriodoValidita() {
		boolean rispostaGiorno = false;
		System.out.println(MSG_PERIODO);
		do {
			Giorno giorno = Giorno.richiestaCreaGiorno();
			periodoValidita.add(giorno);
			
			rispostaGiorno = InputDati.yesOrNo(MSG_PIU_GIORNI);
		} while(rispostaGiorno);
	}

	@Override
	public String toString() {
		String daTornare = "Periodo: \n";
		
		for (Giorno giorno : periodoValidita) {
			daTornare += giorno.toString() + ";";
			daTornare += "\n";
		}
		
		return daTornare;
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
