package Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Ristorante.Ristorante;

class Test_Bevande {

	@Test
	public void testAggiuntaBevanda() {
		String nomeRistorante = "Stelle";
		Ristorante ristorante = Ristorante.getInstance(nomeRistorante);
		
		String nome = "nome_bevanda" ;
		double consumoProCapite = 3.2 ;
		
		ristorante.aggiungiBevanda(nome, consumoProCapite);
		
		double valoreOttenuto = ristorante.getInsiemeB().getInsiemeExtra().get(nome);
		
		assertTrue(ristorante.getInsiemeB().getInsiemeExtra().containsKey(nome));
		assertEquals(consumoProCapite, valoreOttenuto, 0.01);
	}
	
	@Test
	public void testAggiuntaBevandaNomeVuoto() {
		String nomeRistorante = "Stelle";
		Ristorante ristorante = Ristorante.getInstance(nomeRistorante);
		
		String nome = "" ;
		double consumoProCapite = 3.2 ;
		
		ristorante.aggiungiBevanda(nome, consumoProCapite);
			
		assertFalse(ristorante.getInsiemeB().getInsiemeExtra().containsKey(nome));	
	}
	
	@Test
	public void testAggiuntaBevandaConsumoProCapiteNullo() {
		String nomeRistorante = "Stelle";
		Ristorante ristorante = Ristorante.getInstance(nomeRistorante);
		
		String nome = "nome_bevanda" ;
		double consumoProCapite = 0.0;
		
		ristorante.aggiungiBevanda(nome, consumoProCapite);
			
		assertFalse(ristorante.getInsiemeB().getInsiemeExtra().containsKey(nome));	
	}
	
	@Test
	public void testRimozioneBevanda() {
		String nomeRistorante = "Stelle";
		Ristorante ristorante = Ristorante.getInstance(nomeRistorante);
		
		String nome = "nome_bevanda" ;
		
		ristorante.rimuoviBevanda(nome);
		
		assertFalse(ristorante.getInsiemeB().getInsiemeExtra().containsKey(nome));
		assertTrue(ristorante.rimuoviBevanda(nome));
	}
	
	@Test
	public void testRimozioneBevandaNomeVuoto() {
		String nomeRistorante = "Stelle";
		Ristorante ristorante = Ristorante.getInstance(nomeRistorante);
		
		String nome = "" ;
		
		assertFalse(ristorante.rimuoviBevanda(nome));
	}
	
}
