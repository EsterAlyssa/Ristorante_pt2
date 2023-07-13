package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.Ricetta;

class Test_CreazioneRicetta {

	@Test
	void testCreazioneRicetta() {
		String nomeRicetta = "Nome_Ricetta";
		int numPorzioni = 1;
		double caricoLavoroPorzione = 2.1;

		Ricetta nuova = new Ricetta(nomeRicetta, numPorzioni, caricoLavoroPorzione);

		String nomeRistorante = "Stelle";
		Ristorante ristorante = Ristorante.getInstance(nomeRistorante);
		
		ristorante.aggiungiRicetta(nuova);
		
		assertEquals(nomeRicetta, nuova.getNome());
		assertEquals(numPorzioni, nuova.getNumPorzioni());
		assertEquals(caricoLavoroPorzione, nuova.getCaricoLavoroPorzione(), 0.01);
		assertTrue(ristorante.getRicettario().contains(nuova));
	}

	
	@Test
	void testAggiuntaIngredienti() {

		Ricetta ricetta = new Ricetta("nome", 1, 3.3 );;

		String nomeIngrediente = "Nome_ingrediente";
		double doseIngrediente = 3.4;

		ricetta.aggiungiIngrediente(nomeIngrediente, doseIngrediente);	

		assertTrue(ricetta.getIngredienti().containsKey(nomeIngrediente));
		assertEquals(doseIngrediente, ricetta.getIngredienti().get(nomeIngrediente), 0.01);
	}

	@Test
	void testAggiuntaIngredientiNomeVuoto() {

		Ricetta ricetta = new Ricetta("nome", 1, 3.3 );;

		String nomeIngrediente = "";
		double doseIngrediente = 3.4;

		ricetta.aggiungiIngrediente(nomeIngrediente, doseIngrediente);	

		assertFalse(ricetta.getIngredienti().containsKey(nomeIngrediente));
	}

	@Test
	void testAggiuntaIngredientiDoseNulla() {

		Ricetta ricetta = new Ricetta("nom", 1, 3.3 );;

		String nomeIngrediente = "Nome_ingrediente";
		double doseIngrediente = 0.0;

		ricetta.aggiungiIngrediente(nomeIngrediente, doseIngrediente);	

		assertFalse(ricetta.getIngredienti().containsKey(nomeIngrediente));
	}

}
