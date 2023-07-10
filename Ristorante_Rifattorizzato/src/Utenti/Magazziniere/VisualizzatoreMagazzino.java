package Utenti.Magazziniere;

import Magazzino.RegistroMagazzino;
import Util.GestioneFile.ConfiguratoriFile.ConfiguratoreRegistroMagazzino;

public class VisualizzatoreMagazzino {

	public void visualizzaRegistroMagazzino(String pathFileRegistroMagazzino) {
		ConfiguratoreRegistroMagazzino confRegMag = new ConfiguratoreRegistroMagazzino();
		RegistroMagazzino registro = (RegistroMagazzino) confRegMag.caricaIstanzaOggettoDaFile(pathFileRegistroMagazzino);
	
		System.out.println(registro.descrizioneRegistroMagazzino());
	}

}
