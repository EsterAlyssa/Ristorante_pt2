package Giorno.GiornoView;

import Giorno.Giorno;
import Giorno.Periodo;
import Util.InputDati;

public class PeriodoView {

	private Periodo periodo;

	static final String MSG_PERIODO = "Inserisci il periodo di validita': ";
	static final String MSG_PIU_GIORNI = "\nVuoi inserire altri giorni di validita'? ";

	public PeriodoView(Periodo periodo) {
		this.periodo = periodo;
	}

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

	public String descrizionePeriodo() {
		String daTornare = "";
		for (Giorno giorno : periodo.periodoValidita) {
			GiornoView giornoView = new GiornoView (giorno.getGiorno());
			daTornare += giornoView.descrizioneGiorno() + ";";
			daTornare += "\n";
		}
		return daTornare;
	}

	public void mostraDescrizionePeriodo() {
		System.out.println(descrizionePeriodo());
	}
}