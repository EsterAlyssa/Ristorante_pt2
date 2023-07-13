package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Ristorante.*;
import Ristorante.ElementiRistorante.*;
import Giorno.Giorno;
import Giorno.Periodo;

public class TestMenuTematici {

	@Test
	public void testCreazioneMenuTematico() {
		String nomeMenu = "Nome_Menu";
		Periodo periodo = new Periodo();
		Giorno giorno = new Giorno(2023, 7, 19);
		periodo.aggiungiGiorno(giorno);

		MenuTematico menu = new MenuTematico(nomeMenu, periodo);
		
		String nomeRistorante = "Stelle";
		Ristorante ristorante = Ristorante.getInstance(nomeRistorante);
		
		ristorante.aggiungiMenuTematico(menu);
		
		assertEquals(nomeMenu, menu.getNome());
		assertEquals(periodo, menu.getValidita());
		assertTrue(ristorante.getMenuTematici().contains(menu));
	}
	
	@Test
	public void testCreazioneMenuTematicoPeriodoNullo() {
		String nomeMenu = "Nome_Menu";
		Periodo periodo = new Periodo();

		MenuTematico menu = new MenuTematico(nomeMenu, periodo);
		
		String nomeRistorante = "Stelle";
		Ristorante ristorante = Ristorante.getInstance(nomeRistorante);
		
		ristorante.aggiungiMenuTematico(menu);
		
		assertFalse(ristorante.getMenuTematici().contains(menu));
	}
	
	@Test
	public void testCreazioneMenuTematicoNomeNullo() {
		String nomeMenu = "";
		Periodo periodo = new Periodo();
		Giorno giorno = new Giorno(2023, 7, 19);
		periodo.aggiungiGiorno(giorno);

		MenuTematico menu = new MenuTematico(nomeMenu, periodo);
		
		String nomeRistorante = "Stelle";
		Ristorante ristorante = Ristorante.getInstance(nomeRistorante);
		
		ristorante.aggiungiMenuTematico(menu);
		
		assertFalse(ristorante.getMenuTematici().contains(menu));
	}
	
	@Test
	public void testAggiuntaPiatto() {
		String nomeMenu = "Nome_Menu";
		Periodo periodo = new Periodo();
		Giorno giorno = new Giorno(2023, 7, 19);
		periodo.aggiungiGiorno(giorno);

		MenuTematico menu = new MenuTematico(nomeMenu, periodo);
		
		String nomePiatto = "Nome_Piatto";
		double caricoLavoroPiatto = 3.2;
		Piatto piatto = new Piatto(nomePiatto, caricoLavoroPiatto);
		
		menu.aggiungiPiatto(piatto);
		
		assertTrue(menu.getElenco().contains(piatto));
		assertEquals(caricoLavoroPiatto, menu.getCaricoLavoro(), 0.01);
	}

	@Test
	public void testAggiuntaPiattoNomeVuoto() {

		String nomeMenu = "Nome_Menu";
		Periodo periodo = new Periodo();
		Giorno giorno = new Giorno(2023, 7, 19);
		periodo.aggiungiGiorno(giorno);

		MenuTematico menu = new MenuTematico(nomeMenu, periodo);
		
		String nomePiatto = "";
		double caricoLavoroPiatto = 3.2;
		Piatto piatto = new Piatto(nomePiatto, caricoLavoroPiatto);
		
		menu.aggiungiPiatto(piatto);
		
		assertFalse(menu.getElenco().contains(piatto));
		
	}

	@Test
	public void testAggiuntaPiattoCaricoLavoroNullo() {
		String nomeMenu = "Nome_Menu";
		Periodo periodo = new Periodo();
		Giorno giorno = new Giorno(2023, 7, 19);
		periodo.aggiungiGiorno(giorno);

		MenuTematico menu = new MenuTematico(nomeMenu, periodo);
		
		String nomePiatto = "Nome_Piatto";
		double caricoLavoroPiatto = 0;
		Piatto piatto = new Piatto(nomePiatto, caricoLavoroPiatto);
		
		menu.aggiungiPiatto(piatto);

		assertFalse(menu.getElenco().contains(piatto));
	}

	@Test (expected = NullPointerException.class)
	public void testAggiuntaPiattoNull() {
		String nomeMenu = "Nome_Menu";
		Periodo periodo = new Periodo();
		Giorno giorno = new Giorno(2023, 7, 19);
		periodo.aggiungiGiorno(giorno);

		MenuTematico menu = new MenuTematico(nomeMenu, periodo);
		
		Piatto piatto = null;
		
		menu.aggiungiPiatto(piatto);
	}
}
