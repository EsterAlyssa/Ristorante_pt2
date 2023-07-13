package Test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.DateTimeException;
import Giorno.Giorno;
import Giorno.Periodo;

public class TestPeriodo {

	@Test
	public void testCreazioneGiorno() {
		int a = 2023;
		int m = 12;
		int g = 3;
		
		LocalDate data = LocalDate.of(a, m, g);
		Giorno giorno = new Giorno(data);
		
		Giorno giorno2 = new Giorno(a,m,g);
		
		assertEquals(giorno, giorno2);
	}

	@Test (expected = DateTimeException.class)
	public void testCreazioneGiornoNonValido() {
		int a = 2023;
		int m = 2;
		int g = 31;
		
		new Giorno(a,m,g);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGiorno_Eccezione() {
	    LocalDate giorno = null;
	    
	    new Giorno(giorno);
	}
	
	@Test
	public void testAggiungiGiorno() {
	    Periodo periodo = new Periodo();
	    Giorno giorno = new Giorno(2023, 1, 5);
	    
	    boolean risultato = periodo.aggiungiGiorno(giorno);
	    
	    assertTrue(risultato);
	    assertTrue(periodo.getPeriodoValidita().contains(giorno));
	}
	
	@Test
	public void testCreaPeriodo() {
	    Periodo periodo = new Periodo();
	    Giorno giorno1 = new Giorno(2023, 1, 5);
	    Giorno giorno2 = new Giorno(2023, 2, 6);
	    
	    periodo.aggiungiGiorno(giorno1);
	    periodo.aggiungiGiorno(giorno2);
	    
	    
	    assertFalse(periodo.getPeriodoValidita().isEmpty());
	    assertEquals(2, periodo.getPeriodoValidita().size());
	}
	
	@Test
	public void testCreaPeriodoConGiorniUguali() {
	    Periodo periodo = new Periodo();
	    Giorno giorno = new Giorno(2023, 1, 5);
	    
	    periodo.aggiungiGiorno(giorno);
	    periodo.aggiungiGiorno(giorno);
	    
	    assertFalse(periodo.getPeriodoValidita().isEmpty());
	    assertEquals(1, periodo.getPeriodoValidita().size());
	}

	@Test
	public void testUnisciPeriodi() {
	    Periodo periodo1 = new Periodo();
	    Giorno giorno1 = new Giorno(2023, 7, 12);
	    periodo1.aggiungiGiorno(giorno1);
	    
	    Periodo periodo2 = new Periodo();
	    Giorno giorno2 = new Giorno(2023,8,13);
	    periodo2.aggiungiGiorno(giorno2);
	    
	    Periodo periodoSomma = Periodo.unisciPeriodi(periodo1, periodo2);
	    
	    assertTrue(periodoSomma.getPeriodoValidita().contains(giorno1));
	    assertTrue(periodoSomma.getPeriodoValidita().contains(giorno2));
	    assertEquals(2, periodoSomma.getPeriodoValidita().size());
	}
	
	@Test
	public void testUnisciPeriodiConGiorniUguali() {
	    Periodo periodo1 = new Periodo();
	    Giorno giorno1 = new Giorno(2023, 7, 12);
	    periodo1.aggiungiGiorno(giorno1);
	    
	    Periodo periodo2 = new Periodo();
	    Giorno giorno2 = new Giorno(2023,8,13);
	    periodo2.aggiungiGiorno(giorno1);
	    periodo2.aggiungiGiorno(giorno2);
	    
	    Periodo periodoSomma = Periodo.unisciPeriodi(periodo1, periodo2);
	    
	    assertTrue(periodoSomma.getPeriodoValidita().contains(giorno1));
	    assertTrue(periodoSomma.getPeriodoValidita().contains(giorno2));
	    assertEquals(2, periodoSomma.getPeriodoValidita().size());
	}

	@Test
	public void testUnisciPeriodiConPeriodoNullo() {
	    Periodo periodo1 = new Periodo();
	    Giorno giorno1 = new Giorno(2023, 7, 12);
	    periodo1.aggiungiGiorno(giorno1);
	    
	    Periodo periodo2 = new Periodo();
	     
	    Periodo periodoSomma = Periodo.unisciPeriodi(periodo1, periodo2);
	    
	    assertTrue(periodoSomma.getPeriodoValidita().contains(giorno1));
	    assertTrue(periodo2.getPeriodoValidita().isEmpty());
	    assertEquals(1, periodoSomma.getPeriodoValidita().size());
	}
	
	@Test
	public void testParsePeriodo() {
	    String input = "2023-07-01;\n2023-07-02;\n2023-07-03;\n";
	    
	    Periodo periodo = Periodo.parsePeriodo(input);
	    
	    Giorno giorno1 = new Giorno(2023, 7, 1);
	    Giorno giorno2 = new Giorno(2023, 7, 2);
	    Giorno giorno3 = new Giorno(2023, 7, 3);
	    
	    assertEquals(3, periodo.getPeriodoValidita().size());
	    // Verifica che i giorni nel periodo siano corretti
	    assertTrue(periodo.getPeriodoValidita().contains(giorno1));
	    assertTrue(periodo.getPeriodoValidita().contains(giorno2));
	    assertTrue(periodo.getPeriodoValidita().contains(giorno3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAggiungiGiorno_Eccezione() {
	    Periodo periodo = new Periodo();
	    Giorno giorno = null;
	    
	    periodo.aggiungiGiorno(giorno);
	}

}
