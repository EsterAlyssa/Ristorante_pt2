package Ristorante.RistoranteView;

import Ristorante.Ristorante;
import Util.Formattazione;

public class RistoranteView {
	
	private Ristorante ristorante;

	public RistoranteView(String nomeRistorante) {
		this.ristorante = Ristorante.getInstance(nomeRistorante);
	}
	
	public String descrizioneRistorante() {
		return "Nome del ristorante: " + ristorante.getNome() +
				"\nNumeri di posti a sedere nel ristorante: " + ristorante.getNumPosti() +
				"\nCarico di lavoro per persona: " + ristorante.getCaricoLavoroPersona() + 
				"\nCarico di lavoro sostenibile dal ristorante: " + 
				Formattazione.ritornaDoubleFormattato(ristorante.getCaricoLavoroRistorante())
				+ "\n";
	}
	
	public void mostraDescrizioneRistorante() {
		System.out.println(descrizioneRistorante());
	}
	
}
