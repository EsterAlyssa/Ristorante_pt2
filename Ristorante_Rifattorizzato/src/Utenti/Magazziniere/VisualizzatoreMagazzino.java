package Utenti.Magazziniere;

import Giorno.Giorno;
import Magazzino.ListaSpesa;
import Magazzino.RegistroMagazzino;
import Magazzino.MagazzinoView.ListaSpesaView;
import Magazzino.MagazzinoView.RegistroMagazzinoView;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRegistroMagazzino;
import Util.GestioneFile.CreazioneDirectory;
import Util.GestioneFile.CreazioneFile;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreListaSpesa;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreManager;

public class VisualizzatoreMagazzino {

	private ConfiguratoreManager<RegistroMagazzino> confRegMag;
	private ConfiguratoreManager<ListaSpesa> confLisSpe;
	
	public VisualizzatoreMagazzino() {
		this.confRegMag = new ConfiguratoreRegistroMagazzino();
		this.confLisSpe = new ConfiguratoreListaSpesa();
	}

	public void visualizzaRegistroMagazzino(String pathFileRegistroMagazzino) {
		RegistroMagazzino registro = confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);
		RegistroMagazzinoView registroView = new RegistroMagazzinoView (registro);
		registroView.mostraDescrizioneRegistroMagazzino();
	}
	
	public void visualizzaListaSpesa(String pathCompletoFileRistorante, Giorno giornoCorrente) {
		String pathDirectoryCalendario = CreazioneDirectory.creaDirectoryCalendario(pathCompletoFileRistorante);
		String pathDirectoryGiornata = CreazioneDirectory.creaDirectoryGiornata(giornoCorrente, pathDirectoryCalendario);			
		String pathDirectoryDaComprare = CreazioneDirectory.creaSubDirectoryDaComprare(pathDirectoryGiornata);
		String pathFileListaSpesa = CreazioneFile.creaFileListaSpesa(pathDirectoryDaComprare);
		
		ListaSpesa lista = confLisSpe.caricaIstanzaOggettoDaFile(pathFileListaSpesa);
		ListaSpesaView listaSpesaView = new ListaSpesaView(lista);
		listaSpesaView.mostraDescrizioneListaSpesa();
	}

}
