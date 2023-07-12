package Utenti.Magazziniere;

import java.util.HashMap;
import java.util.HashSet;

import Giorno.Giorno;
import Giorno.GiornoView.GiornoView;
import Magazzino.ElementoMagazzino;
import Magazzino.ListaSpesa;
import Magazzino.RegistroMagazzino;
import Magazzino.MagazzinoView.RegistroMagazzinoView;
import Magazzino.Merce.Merce;
import Magazzino.Merce.MerceView.MerceView;
import Ristorante.Giornata;
import Ristorante.Ristorante;
import Util.InputDati;
import Util.GestioneFile.Aggiornamento;
import Util.GestioneFile.CreazioneDirectory;
import Util.GestioneFile.CreazioneFile;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreListaSpesa;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreManager;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRegistroMagazzino;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRistorante;

public class GestoreMagazzino {
	
	static final String MSG_QUANTITA_MERCI = "Inserisci quante merci hanno queste caratteristiche: ";
	static final String MSG_ALTRE_MERCI = "Vuoi aggiungere altre merci? ";

	ConfiguratoreManager<Ristorante> confRist;
	ConfiguratoreManager<RegistroMagazzino> confRegMag;
	ConfiguratoreManager<ListaSpesa> confListaSpesa;

	public GestoreMagazzino() {
		this.confRist = new ConfiguratoreRistorante(); 
		this.confRegMag = new ConfiguratoreRegistroMagazzino();
		this.confListaSpesa = new ConfiguratoreListaSpesa();
	}

	public void aggiuntaProdottiAcquistati(String pathCompletoFileRistorante, String pathFileRegistroMagazzino) {
		Ristorante ristorante = confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);
		RegistroMagazzino registro = confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);
		RegistroMagazzinoView registroMagazzinoView = new RegistroMagazzinoView(registro);
		HashSet<ElementoMagazzino> comprati = registroMagazzinoView.inserisciElementiMagazzinoComprati();

		registro.acquistatiI(comprati);
		ristorante.setRegistroMagazzino(registro);

		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public void prelievoIngredientiPerCucina(Giorno giornoCorrente, String pathCompletoFileRistorante, 
			String pathFileRegistroMagazzino) {

		Ristorante ristorante = confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		String pathDirectoryCalendario = CreazioneDirectory.creaDirectoryCalendario(pathCompletoFileRistorante);

		//aggiornamento di tutte le giornate del calendario
		Aggiornamento.aggiornamentoCalendario(ristorante, pathDirectoryCalendario);

		RegistroMagazzino registro = confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		Giornata giornataCorrente = ristorante.getGiornata(giornoCorrente);
		registro.inCucinaO(giornataCorrente);
		ristorante.setRegistroMagazzino(registro);

		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public void prelievoExtraPerTavoli(Giorno giornoCorrente, String pathCompletoFileRistorante, 
			String pathFileRegistroMagazzino) {
		
		Ristorante ristorante = confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);
		String pathDirectoryCalendario = CreazioneDirectory.creaDirectoryCalendario(pathCompletoFileRistorante);
		//aggiornamento di tutte le giornate del calendario
		Aggiornamento.aggiornamentoCalendario(ristorante, pathDirectoryCalendario);
		Aggiornamento.aggiornamentoInsiemiExtra(pathCompletoFileRistorante, ristorante);

		Giornata giornataCorrente = ristorante.getGiornata(giornoCorrente);

		RegistroMagazzino registro = confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		registro.extraO(ristorante, giornataCorrente);

		ristorante.setRegistroMagazzino(registro);
		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public void aggiuntaMerciInutilizzati(String pathCompletoFileRistorante, String pathFileRegistroMagazzino) {
		Ristorante ristorante = confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		HashMap<Merce, Double> avanzi = new HashMap<>();
		
		boolean scelta = false;
		do {
			Merce merce = MerceView.creaMerce();
			double quantita = InputDati.leggiDoubleConMinimo(MSG_QUANTITA_MERCI, 0.0);

			avanzi.put(merce, quantita);

			scelta = InputDati.yesOrNo(MSG_ALTRE_MERCI);
		} while (scelta);

		RegistroMagazzino registro = confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		registro.avanziI(avanzi);
		ristorante.setRegistroMagazzino(registro);
		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public void eliminazioneScarti(Giorno giornoCorrente, String pathCompletoFileRistorante, 
			String pathFileRegistroMagazzino) {
		
		Ristorante ristorante = confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		GiornoView giornoView = new GiornoView (giornoCorrente.getGiorno());
		Giornata giornataCorrente = new Giornata(giornoView.descrizioneGiorno());

		Merce merceNonDiQualita = MerceView.dichiarazioneMerceDeteriorata();

		RegistroMagazzino registro = confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		registro.setFalseQualitaMerce(merceNonDiQualita);
		registro.scartiO(giornataCorrente);
		ristorante.setRegistroMagazzino(registro);
		confRegMag.salvaIstanzaOggetto(registro, pathFileRegistroMagazzino);
	}

	public void generaListaSpesa(Giorno giornoCorrente, String pathCompletoFileRistorante, 
			String pathFileRegistroMagazzino) {

		Ristorante ristorante = confRist.caricaIstanzaOggettoDaFile(pathCompletoFileRistorante);

		RegistroMagazzino registroMagazzino = confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);

		String pathDirectoryCalendario = CreazioneDirectory.creaDirectoryCalendario(pathCompletoFileRistorante);
		String pathDirectoryGiornata = CreazioneDirectory.creaDirectoryGiornata(giornoCorrente, pathDirectoryCalendario);			
		String pathDirectoryDaComprare = CreazioneDirectory.creaSubDirectoryDaComprare(pathDirectoryGiornata);

		String pathFileListaSpesa = CreazioneFile.creaFileListaSpesa(pathDirectoryDaComprare);

		Aggiornamento.aggiornamentoCalendario(ristorante, pathDirectoryCalendario);
		Aggiornamento.aggiornamentoInsiemiExtra(pathCompletoFileRistorante, ristorante);
		Aggiornamento.aggiornamentoRicettario(pathCompletoFileRistorante, ristorante);

		Giornata giornataCorrente = ristorante.getGiornata(giornoCorrente);
		giornataCorrente.creaListaSpesaIniziale(ristorante);

		ListaSpesa lista = giornataCorrente.getDaComprare(); 
		for (String nome : lista.getLista().keySet()) {
			if (registroMagazzino.getRegistro().containsKey(nome)) {
				double effettivoDaComprare = (registroMagazzino.ritornaQuantitaDatoNome(nome)) - (lista.getLista().get(nome)*1.1) ;
				if (effettivoDaComprare < 0) {
					effettivoDaComprare = 0.0 - effettivoDaComprare ;
				}
				lista.getLista().put(nome, effettivoDaComprare);
			} 
		}
		confListaSpesa.salvaIstanzaOggetto(lista, pathFileListaSpesa);
	}

}
