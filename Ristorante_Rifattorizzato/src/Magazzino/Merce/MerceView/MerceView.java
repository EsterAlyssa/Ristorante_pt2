package Magazzino.Merce.MerceView;

import Giorno.Giorno;
import Giorno.GiornoView.GiornoView;
import Magazzino.Merce.Bevanda;
import Magazzino.Merce.GenereExtra;
import Magazzino.Merce.Ingrediente;
import Magazzino.Merce.Merce;
import Util.InputDati;

public class MerceView {
	static final String MSG_NOME = "Inserisci il nome della merce: ";
	static final String MSG_UNITA_MISURA = "Inserisci l'unita' di misura della merce: ";
	static final String MSG_SCADENZA = "Inserisci la scadenza della merce: ";
	static final String MSG_TIPO = "Inserire il tipo della merce [ingrediente/bevanda/genere extra]: ";
	static final String MSG_CONSUMO_PRO_CAPITE = "Inserire il consumo pro capite: ";
	static final String MSG_ERR_TIPO = "ATTENZIONE! Il tipo inserito non è valido. Riprovare";

	public static Merce creaMerce() {
		
		String nomeMerce = InputDati.leggiStringaNonVuota(MSG_NOME);
		String unitaMisura = InputDati.leggiStringaNonVuota(MSG_UNITA_MISURA);
		System.out.println(MSG_SCADENZA);
		Giorno scadenza = GiornoView.richiestaCreaGiorno();
	
		String tipo = "";
		boolean controllo = true;
		do {
			tipo = InputDati.leggiStringaNonVuota(MSG_TIPO);
			if (tipo.equalsIgnoreCase("ingrediente") || tipo.equalsIgnoreCase("bevanda") || tipo.equalsIgnoreCase("genere extra")) {
				controllo = false;
				break;
			}
			System.out.println(MSG_ERR_TIPO);
		} while (controllo);
	
		double consumoProCapite = 0.0;
		if (tipo == "bevanda" || tipo == "genere extra") {
			consumoProCapite = InputDati.leggiDoubleConMinimo(MSG_CONSUMO_PRO_CAPITE, 0.0);
		}
	
		return creaMerceDaTipo(nomeMerce, tipo, unitaMisura, scadenza, consumoProCapite);
	}
	
	//metodo per "registrare" i prodotti acquistati che dovranno essere poi inseriti dal magazziniere nel magazzino
	public static Merce creaMerceDaTipo (String nome, String tipo, String unitaMisura, Giorno scadenza, double consumoProCapite) {
		switch(tipo) {
		case "bevanda" :  
			return new Bevanda (nome, scadenza, consumoProCapite);
		case "genere extra" :
			return new GenereExtra (nome, scadenza, consumoProCapite);
		case "ingrediente" :
			return new Ingrediente (nome, unitaMisura, scadenza); 
		}
		return null;
	};
}
