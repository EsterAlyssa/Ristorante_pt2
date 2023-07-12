package Magazzino.MagazzinoView;

import Magazzino.ElementoMagazzino;
import Magazzino.Merce.Merce;
import Magazzino.Merce.MerceView.MerceView;
import Util.Formattazione;
import Util.InputDati;

public class ElementoMagazzinoView {

	static final String MSG_QUANTITA = "Inserisci quante merci hanno queste caratteristiche: ";

	private ElementoMagazzino elementoMagazzino;

	public ElementoMagazzinoView(ElementoMagazzino elementoMagazzino) {
		this.elementoMagazzino = elementoMagazzino;
	}

	public static ElementoMagazzino creaElementoMagazzino() {
		Merce merce = MerceView.creaMerce();
		double quantita = InputDati.leggiDoubleConMinimo(MSG_QUANTITA, 0.0);

		ElementoMagazzino elemento = new ElementoMagazzino(merce, quantita);
		return elemento;
	}

	public String descrizioneElementoMagazzino() {
		String daTornare = "Elemento Magazzino:\n";
		MerceView merceView = new MerceView(elementoMagazzino.getMerce());

		daTornare += merceView.descrizioneMerce();
		daTornare += "\nQuantita': " + 
				Formattazione.ritornaDoubleFormattato(elementoMagazzino.getQuantita());
		return daTornare;
	}
	
	public void mostraDescrizioneElementoMagazzino() {
		System.out.println(descrizioneElementoMagazzino());
	}
}
