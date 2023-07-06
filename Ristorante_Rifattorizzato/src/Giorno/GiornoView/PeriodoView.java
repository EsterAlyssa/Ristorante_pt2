package Giorno.GiornoView;

import Giorno.Giorno;
import Giorno.Periodo;
import Util.InputDati;

public class PeriodoView {
	
	final static String MSG_PERIODO = "Inserisci il periodo di validita': ";
	final static String MSG_PIU_GIORNI = "\nVuoi inserire altri giorni di validita'? ";

	public static Periodo creaPeriodoValidita() {
		Periodo periodo = new Periodo();
		boolean rispostaGiorno = false;
		System.out.println(MSG_PERIODO);
		do {
			Giorno giorno = GiornoView.richiestaCreaGiorno();
			periodo.getPeriodoValidita().add(giorno);
			
			rispostaGiorno = InputDati.yesOrNo(MSG_PIU_GIORNI);
		} while(rispostaGiorno);
		return periodo;
	}
}