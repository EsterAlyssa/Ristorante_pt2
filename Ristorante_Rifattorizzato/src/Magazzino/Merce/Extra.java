package Magazzino.Merce;

import java.util.HashMap;

import Giorno.Giorno;
import Prenotazioni.Prenotazione;

public abstract class Extra extends Merce {

	private double consumoProCapite; 

	//costruttore per inizializzare gli attributi
	public Extra(String nome, String unitaMisura, double consumoProCapite) {
		super (nome,unitaMisura);
		this.consumoProCapite = consumoProCapite;
	}

	public Extra(String nome, String unitaMisura, Giorno scadenza, double consumoProCapite) {
		super (nome, unitaMisura, scadenza);
		this.consumoProCapite = consumoProCapite;
	}

	public double getConsumoProCapite() {
		return consumoProCapite;
	}

	public void setConsumoProCapite(double consumoProCapite) {
		this.consumoProCapite = consumoProCapite;
	}

	//questo metodo vale sia per l'insieme delle Bevande, sia per quello dei generi Extra
	public static HashMap<String, Double> creaListaExtraDaPrenotazione (Prenotazione prenotazione, HashMap<String, Double> insieme){
		HashMap<String, Double> listaExtra = new HashMap<>();		
		int num = prenotazione.getNumCoperti(); 

		for (String extra : insieme.keySet()) {
			listaExtra.put(extra, num*(insieme.get(extra)));
		}
		return listaExtra;	
	}
}
