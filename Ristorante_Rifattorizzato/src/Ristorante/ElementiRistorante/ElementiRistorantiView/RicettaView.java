package Ristorante.ElementiRistorante.ElementiRistorantiView;

import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.Ricetta;
import Util.Formattazione;
import Util.InputDati;

public class RicettaView {

	static final String MSG_NOME_INGREDIENTE = "Inserisci il nome dell'ingrediente da aggiungere: ";
	static final String MSG_DOSE_INGREDIENTE = "Inserisci la dose dell'ingrediente da aggiungere: ";

	static final String MSG_NOME_RICETTA = "Inserisci il nome della ricetta da creare: ";
	static final String MSG_NUM_PORZIONI_RICETTA = "Inserisci il numero delle porzioni della ricetta da creare: ";
	static final String MSG_CARICO_LAVORO_RICETTA = "Inserisci il carico di lavoro per persona della ricetta da creare: ";

	static final String MSG_ALTRI_INGREDIENTI_RICETTA = "Vuoi inserire altri ingredienti? ";

	static final String MSG_ERR_NOME_RICETTA = "ATTENZIONE! Esiste gia' una ricetta con qusto nome ";

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

	public static Ricetta creaRicetta(Ristorante ristorante) {
		String nomeRicetta = "";
		boolean okNome = true;
		do {
			nomeRicetta = InputDati.leggiStringaNonVuota(MSG_NOME_RICETTA);
			for (Ricetta ricetta : ristorante.getRicettario()){
				if (ricetta.getNome().equalsIgnoreCase(nomeRicetta)) {
					System.out.println(MSG_ERR_NOME_RICETTA);
					okNome = false;
				}
			}
		} while (okNome);
		int numPorzioni = InputDati.leggiInteroPositivo(MSG_NUM_PORZIONI_RICETTA);
		double caricoLavoroPorzione = InputDati.leggiDoubleConMinimo(MSG_CARICO_LAVORO_RICETTA, 0);

		Ricetta nuova = new Ricetta(nomeRicetta, numPorzioni, caricoLavoroPorzione);
		boolean scelta = true;
		do {
			aggiungiIngredienti(nuova);
			scelta = InputDati.yesOrNo(MSG_ALTRI_INGREDIENTI_RICETTA);
		} while (scelta);

		return nuova;
	}

	public static void aggiungiIngredienti(Ricetta ricetta) {

		String nomeIngrediente = InputDati.leggiStringaNonVuota(MSG_NOME_INGREDIENTE);
		double doseIngrediente = InputDati.leggiDoubleConMinimo(MSG_DOSE_INGREDIENTE, 0);

		ricetta.aggiungiIngrediente(nomeIngrediente, doseIngrediente);		
	}
}
