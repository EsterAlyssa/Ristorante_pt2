package Ristorante.ElementiRistorante.ElementiRistorantiView;

import Ristorante.ElementiRistorante.Ricetta;
import Util.Formattazione;

public class RicettaView {

	private Ricetta ricetta;

	public RicettaView(Ricetta ricetta){
		this.ricetta = ricetta;
	}

	public String descrizioneRicetta() {
		String descrizione = "Ricetta: "+ ricetta.getNome() + 
				"\nNumero di porzioni previste: " + ricetta.getNumPorzioni() + 
				"\nIngredienti:\n";
		for (String nome : ricetta.getIngredienti().keySet()) {
			descrizione += "- " + nome + ", dose: " + 
					Formattazione.ritornaDoubleFormattato(ricetta.getIngredienti().get(nome)) 
			+ "\n";
		}
		descrizione += "Carico di lavoro per porzione: " + 
				Formattazione.ritornaDoubleFormattato(ricetta.getCaricoLavoroPorzione()) 
		+ "\n";
		
		return descrizione;
	}
	
	public void mostraDescrizioneRicetta() {
		System.out.println(descrizioneRicetta());
	}
	
	public String descrizioneNomeRicetta() {
		return "Ricetta: "+ ricetta.getNome() + "\n";
	}
	
	public void mostraDescrizioneNomeRicetta() {
		System.out.println(descrizioneNomeRicetta());
	}
}
