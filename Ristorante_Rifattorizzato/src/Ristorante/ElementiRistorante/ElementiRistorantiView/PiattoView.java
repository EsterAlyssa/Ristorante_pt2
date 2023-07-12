package Ristorante.ElementiRistorante.ElementiRistorantiView;

import Giorno.GiornoView.PeriodoView;
import Ristorante.ElementiRistorante.Piatto;
import Util.Formattazione;

public class PiattoView {

	private Piatto piatto;

	public PiattoView(Piatto piatto) {
		this.piatto = piatto;
	}

	public String descrizionePiatto() {
		String daTornare = "Piatto: " + piatto.getNome() + 
				"\nCarico di lavoro del piatto: " + 
				Formattazione.ritornaDoubleFormattato(piatto.getCaricoLavoro())
				+ "\nPeriodo di validita' del piatto:\n";
		PeriodoView periodoView = new PeriodoView(piatto.getValidita());
		daTornare += periodoView.descrizionePeriodo();
		return daTornare;
	}
	
	public void mostraDescrizionePiatto() {
		System.out.println(descrizionePiatto());
	}
	
	public String descrizioneNomePiatto() {		
		return "Piatto: " + piatto.getNome();
	}
	
	public void mostraDescrizioneNomePiatto() {
		System.out.println(descrizioneNomePiatto());
	}
	
}
