package Magazzino;
import java.util.HashMap;

public class ListaSpesa {

	private HashMap<String, Double> lista;

	public ListaSpesa() {
		this.lista = new HashMap<>();
	}

	public HashMap<String, Double> getLista() {
		return lista;
	}

	public void setLista(HashMap<String, Double> lista) {
		this.lista = lista;
	}
}
