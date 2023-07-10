package Magazzino.MagazzinoView;

import Magazzino.ElementoMagazzino;
import Magazzino.Merce.Merce;
import Magazzino.Merce.MerceView.MerceView;
import Util.InputDati;

public class ElementoMagazzinoView {
	
	final static String MSG_QUANTITA = "Inserisci quante merci hanno queste caratteristiche: ";
	
	public static ElementoMagazzino creaElementoMagazzino() {
		Merce merce = MerceView.creaMerce();
		double quantita = InputDati.leggiDoubleConMinimo(MSG_QUANTITA, 0.0);

		ElementoMagazzino elemento = new ElementoMagazzino(merce, quantita);
		return elemento;
	}
}
