package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Giorno.Giorno;
import Prenotazioni.Prenotazione;
import Prenotazioni.SceltaPrenotazione;
import Ristorante.Giornata;
import Ristorante.Ristorante;
import Ristorante.ElementiRistorante.Piatto;
import Util.GestioneFile.Aggiornamento;
import Util.GestioneFile.CreazioneDirectory;

public class TestPrenotazione {

	@Test
	public void testCreazionePrenotazioneSenzaScelte() {
		String nomeCliente = "Paolo";
		int numCoperti = 3;
		Giorno giorno = new Giorno(2023, 7, 19);

		Prenotazione prenotazione = new Prenotazione(nomeCliente, numCoperti, giorno);

		assertEquals(nomeCliente, prenotazione.getCliente());
		assertEquals(numCoperti, prenotazione.getNumCoperti());
		assertEquals(giorno, prenotazione.getData());
	}

	@Test
	public void testAggiuntaPrenotazioneNomeVuoto() {
		String nomeCliente = "";
		int numCoperti = 3;
		Giorno giorno = new Giorno(2023, 7, 19);

		Prenotazione prenotazione = new Prenotazione(nomeCliente, numCoperti, giorno);
		Giornata giornata = new Giornata(giorno);

		giornata.aggiungiPrenotazione(prenotazione);

		assertFalse(giornata.getPrenotazioni().contains(prenotazione));
	}

	@Test
	public void testCreazionePrenotazioneNumNulli() {
		String nomeCliente = "Paolo";
		int numCoperti = 0;
		Giorno giorno = new Giorno(2023, 7, 19);

		Prenotazione prenotazione = new Prenotazione(nomeCliente, numCoperti, giorno);
		Giornata giornata = new Giornata(giorno);

		giornata.aggiungiPrenotazione(prenotazione);

		assertFalse(giornata.getPrenotazioni().contains(prenotazione));
	}

	@Test
	public void testCreazionePrenotazioneDataNulla() {
		String nomeCliente = "Paolo";
		int numCoperti = 3;
		Giorno giorno1 = null;
		Giorno giorno2 = new Giorno(2023, 7, 19);

		Prenotazione prenotazione = new Prenotazione(nomeCliente, numCoperti, giorno1);
		Giornata giornata = new Giornata(giorno2);

		giornata.aggiungiPrenotazione(prenotazione);

		assertFalse(giornata.getPrenotazioni().contains(prenotazione));
	}

	@Test
	public void testAggiuntaCalendarioPrenotazioneSenzaScelte() {
		String nomeCliente = "Paolo";
		int numCoperti = 3;
		Giorno giorno = new Giorno(2023, 7, 19);

		Prenotazione prenotazione = new Prenotazione(nomeCliente, numCoperti, giorno);

		String nomeRistorante = "Stelle";
		String percorsoCompleto = "./FileRistorante/Stelle.txt";
		Ristorante ristorante = Ristorante.getInstance(nomeRistorante);
		String pathDirectoryCalendario = CreazioneDirectory.creaDirectoryCalendario(percorsoCompleto);
		Aggiornamento.aggiornamentoCalendario(ristorante, pathDirectoryCalendario);

		Giornata giornataScelta = null;
		for (Giornata giornata : ristorante.getCalendario()) {
			if (giornata.compareTo(giornata)==0) {
				giornata.aggiungiPrenotazione(prenotazione);
				giornataScelta = giornata;
			}
		}

		ristorante.getCalendario().add(giornataScelta);

		assertFalse(giornataScelta.getPrenotazioni().contains(prenotazione));
		assertFalse(ristorante.getCalendario().contains(giornataScelta));
	}


	@Test (expected = NullPointerException.class)
	public void testAggiuntaPrenotazioneNulla() {
		Prenotazione prenotazione = null;
		Giornata giornata = new Giornata(new Giorno(2023, 7, 19));

		giornata.aggiungiPrenotazione(prenotazione);
	}

	@Test
	public void testAggiuntaScelta() {
		String nomeCliente = "Paolo";
		int numCoperti = 3;
		Giorno giorno = new Giorno(2023, 7, 19);

		Prenotazione prenotazione = new Prenotazione(nomeCliente, numCoperti, giorno);

		String nomeScelta = "lasagne";
		int numScelta = 3;

		SceltaPrenotazione scelta = new Piatto(nomeScelta);

		prenotazione.aggiungiScelta(scelta, numScelta);

		int numeroTrovato = prenotazione.elencoPiattiDaScelte().get(scelta);

		assertEquals(nomeScelta, scelta.getNome());
		assertEquals(numScelta, numeroTrovato);
		assertTrue(prenotazione.getElenco().containsKey(scelta));
	}

	@Test
	public void testAggiuntaSceltaNulla() {
		String nomeCliente = "Paolo";
		int numCoperti = 3;
		Giorno giorno = new Giorno(2023, 7, 19);

		Prenotazione prenotazione = new Prenotazione(nomeCliente, numCoperti, giorno);

		int numScelta = 3;
		SceltaPrenotazione scelta = null;

		prenotazione.aggiungiScelta(scelta, numScelta);

		assertFalse(prenotazione.getElenco().containsKey(scelta));
	}

	@Test
	public void testAggiuntaSceltaNoNumeroScelte() {
		String nomeCliente = "Paolo";
		int numCoperti = 3;
		Giorno giorno = new Giorno(2023, 7, 19);

		Prenotazione prenotazione = new Prenotazione(nomeCliente, numCoperti, giorno);
		
		String nomeScelta = "lasagne";
		int numScelta = 0;
		
		SceltaPrenotazione scelta = new Piatto(nomeScelta);
		
		prenotazione.aggiungiScelta(scelta, numScelta);

		assertFalse(prenotazione.getElenco().containsKey(scelta));
	}
}

