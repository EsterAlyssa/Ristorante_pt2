package Ristorante;

import java.util.HashMap;

public class InsiemeExtra {
	private HashMap<String, Double> insiemeExtra;

	public InsiemeExtra() {
		this.insiemeExtra = new HashMap<>();
	}

	public HashMap<String, Double> getInsiemeExtra() {
		return insiemeExtra;
	}

	public void setInsiemeExtra(HashMap<String, Double> insiemeExtra) {
		this.insiemeExtra = insiemeExtra;
	}


	public void aggiungiElementoExtra(String nome, Double consumoProCapite) {
		if (insiemeExtra.isEmpty()) {
			insiemeExtra = new HashMap<>();
		}
		this.insiemeExtra.put(nome, consumoProCapite);
	}

	public void rimuoviElementoExtra (String nome) {
		if (insiemeExtra.containsKey(nome)) {
			this.insiemeExtra.remove(nome);
		} else 
			System.out.println("L'elemento non era presente nell'insieme");

	}
}
