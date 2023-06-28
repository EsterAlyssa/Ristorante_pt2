package Magazzino.Merce;
import Giorno.Giorno;

public class GenereExtra extends Extra {

	private static String unitaMisuraGE = "hg";

	public GenereExtra(String nome, double consumoProCapite) {
		super(nome, unitaMisuraGE, consumoProCapite);
	}

	public GenereExtra (String nome, Giorno scadenza, double consumoProCapite) {
		super(nome, unitaMisuraGE, scadenza, consumoProCapite);
	}
	
}
