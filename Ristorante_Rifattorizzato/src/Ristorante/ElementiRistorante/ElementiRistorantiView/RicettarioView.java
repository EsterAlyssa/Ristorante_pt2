package Ristorante.ElementiRistorante.ElementiRistorantiView;

import java.util.HashSet;

import Ristorante.ElementiRistorante.Ricetta;

public class RicettarioView {
	
	private HashSet<Ricetta> ricettario;
	
	public RicettarioView(HashSet<Ricetta> ricettario) {
		this.ricettario = ricettario;
	}
	
	public String descrizioneRicettario() {
		String daTornare = "Ricettario:\n";
		int i=1;
		for (Ricetta ricetta : ricettario) {
			RicettaView ricettaView = new RicettaView (ricetta);
			daTornare += i+") " + ricettaView.descrizioneRicetta();
			i++;
		}
		return daTornare;
	}
	
	public void mostraDescrizioneRicettario() {
		System.out.println(descrizioneRicettario());
	}
	
	public String descrizioneNomiRicettario() {
		String daTornare = "Ricettario:\n";
		int i=1;
		for (Ricetta ricetta : ricettario) {
			RicettaView ricettaView = new RicettaView (ricetta);
			daTornare += i+") " + ricettaView.descrizioneNomeRicetta();
			i++;
		}
		return daTornare;
	}
	
	public void mostraDescrizioneNomiRicettario() {
		System.out.println(descrizioneNomiRicettario());
	}
}
