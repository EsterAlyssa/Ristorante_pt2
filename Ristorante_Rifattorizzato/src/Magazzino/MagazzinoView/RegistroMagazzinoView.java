package Magazzino.MagazzinoView;

import java.util.HashSet;

import Magazzino.ElementoMagazzino;
import Util.InputDati;

public class RegistroMagazzinoView {

	final static String messaggioAltreMerci = "Vuoi aggiungere altre merci? ";
	
	public HashSet<ElementoMagazzino> elementiMagazzinoComprati(){
		HashSet<ElementoMagazzino> comprati = new HashSet<>();
		
		boolean scelta = false;
		do {
			ElementoMagazzino elemento = ElementoMagazzinoView.creaElementoMagazzino();
			comprati.add(elemento);

			scelta = InputDati.yesOrNo(messaggioAltreMerci);
		} while (scelta);

		return comprati;
	}
}
