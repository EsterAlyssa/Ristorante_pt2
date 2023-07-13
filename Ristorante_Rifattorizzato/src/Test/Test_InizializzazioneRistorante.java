package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Main.Main;
import Ristorante.Ristorante;
import Util.GestioneFile.ServizioFile;

class Test_InizializzazioneRistorante {

	@Test
	public void testCreaFileRistorante() {
		// Input nome
		String nomeRistorante = "Stelle";
		String percorsoCompleto = "./FileRistorante/Stelle.txt";

		assertTrue(ServizioFile.controlloEsistenzaFile(percorsoCompleto));
		assertEquals(nomeRistorante, ServizioFile.trovaNomePrimoFileTxt(percorsoCompleto));
	}

	@Test
	public void testCreaOggettoFileRistorante() {
		// Input nome
		String nomeRistorante = "Stelle";

		// Attributi inseriti su file
		int caricoLavoroPersona=14;	
		int numPosti=55;

		Ristorante ristorante = Ristorante.getInstance(nomeRistorante);
		ristorante.setCaricoLavoroPersona(caricoLavoroPersona);
		ristorante.setNumPosti(numPosti);

		assertEquals(nomeRistorante, ristorante.getNome());
		assertEquals(caricoLavoroPersona, ristorante.getCaricoLavoroPersona());
		assertEquals(numPosti, ristorante.getNumPosti());
	}


	@Test
	public void testAccediRistoranteEsistente() {
		String appDirectoryPath = "./FileRistorante/";

		Ristorante ristorante = Main.accediRistorante(appDirectoryPath);

		String nomeTrovato = ServizioFile.trovaNomePrimoFileTxt(appDirectoryPath);

		assertTrue(ServizioFile.controlloEsistenzaFile(appDirectoryPath));
		assertNotNull(ristorante);
		assertEquals(nomeTrovato, ristorante.getNome());
	}

	@Test
	public void testAccediRistoranteNonEsistente() {
		String percorsoCompleto = "./FileRistorante/";
		
		String nomeRistoranteTrovato = ServizioFile.trovaNomePrimoFileTxt(percorsoCompleto);
		
		assertNull(nomeRistoranteTrovato);
	}

}
