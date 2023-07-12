package Ristorante.ElementiRistorante.ElementiRistorantiView;

import Ristorante.ElementiRistorante.InsiemeExtra;

public class InsiemeExtraView {

	private InsiemeExtra insiemeExtra;

	public InsiemeExtraView(InsiemeExtra insiemeExtra) {
		this.insiemeExtra = insiemeExtra;
	}

	public String descrizioneInsiemeExtra() {
		String daTornare = "Insieme Extra:\n";
		for (String nome : insiemeExtra.getInsiemeExtra().keySet()) {
			daTornare += "Nome elemento: " + nome + ", consumo pro capite: " + 
					insiemeExtra.getInsiemeExtra().get(nome);
			daTornare += "\n";
		}
		return daTornare;
	}
	
	public void mostraDescrizioneInsiemeExtra() {
		System.out.println(descrizioneInsiemeExtra());
	}

}
