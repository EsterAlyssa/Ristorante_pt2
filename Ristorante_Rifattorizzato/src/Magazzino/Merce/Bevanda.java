package Magazzino.Merce;
import Giorno.Giorno;

public class Bevanda extends Extra {

	private static String unitaMisuraB = "l";

	public Bevanda(String nome, double consumoProCapite) {
		super(nome, unitaMisuraB, consumoProCapite);
	}

	public Bevanda (String nome, Giorno scadenza, double consumoProCapite) {
		super(nome, unitaMisuraB, scadenza, consumoProCapite);
	}


}
