package Ristorante.ElementiRistorante.ElementiRistorantiView;

import java.util.HashSet;
import Ristorante.ElementiRistorante.Piatto;

public class PiattiView {
	
	private HashSet<Piatto> elencoPiatti;

	public PiattiView(HashSet<Piatto> elencoPiatti) {
		this.elencoPiatti = elencoPiatti;
	}
	
	public String descrizionePiatti() {
		String daTornare = "Piatti:\n";
		int i=1;
		for(Piatto piatto : elencoPiatti) {
			daTornare += i+")";
			PiattoView piattoView = new PiattoView(piatto);
			daTornare += piattoView.descrizionePiatto();
			i++;
		}
		return daTornare;
	}
	
	public void mostraDescrizionePiatti() {
		System.out.println(descrizionePiatti());
	}
	
	public String descrizioneNomePiatti() {
		String daTornare = "";
		int i = 1;
		for(Piatto piatto : elencoPiatti) {
			daTornare += i+")";
			PiattoView piattoView = new PiattoView(piatto);
			daTornare += piattoView.descrizioneNomePiatto();
			i++;
		}
		return daTornare;
	}
	
	public void mostraDescrizioneNomePiatti() {
		System.out.println(descrizioneNomePiatti());
	}
}
