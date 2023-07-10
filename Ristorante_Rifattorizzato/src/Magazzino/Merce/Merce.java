package Magazzino.Merce;
import java.util.HashMap;

import Giorno.Giorno;

public abstract class Merce {

	private String nomeMerce;
	private String unitaMisura;
	private Giorno scadenza;
	private boolean qualita = true;

	//creiamo un costruttore per inizializzare alcuni gli attributi → l'unità di misura andra' settata con il set
	public Merce(String nome, Giorno scadenza) {
		this.nomeMerce = nome;
		this.scadenza = scadenza;
		this.qualita = true;
	}

	//creiamo un costruttore per inizializzare alcuni gli attributi
	public Merce(String nome, String unitaMisura) {
		this.nomeMerce = nome;
		this.unitaMisura = unitaMisura;
		this.qualita = true;
	}

	//creiamo un costruttore per inizializzare tutti gli attributi
	public Merce(String nome, String unitaMisura, Giorno scadenza) {
		this.nomeMerce = nome;
		this.unitaMisura = unitaMisura;
		this.scadenza = scadenza;
		this.qualita = true;
	}

	public String getNome() {
		return nomeMerce;
	}

	public void setNome(String nome) {
		this.nomeMerce = nome;
	}

	public String getUnitaMisura() {
		return unitaMisura;
	}

	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	public Giorno getScadenza() {
		return scadenza;
	}

	public void setScadenza(Giorno scadenza) {
		this.scadenza = scadenza;
	}

	public boolean getQualita() {
		return qualita;
	}

	public void setQualita(boolean qualita) {
		this.qualita = qualita;
	}


	public static void gestioneDuplicati(HashMap<String, Double> noDuplicati, HashMap<String, Double> conDuplicati){
		for (String merce : conDuplicati.keySet()) {
			if (noDuplicati.keySet().contains(merce)) {
				noDuplicati.put(merce, noDuplicati.get(merce)+ conDuplicati.get(merce));
			} else {
				noDuplicati.put(merce, conDuplicati.get(merce));
			}
		}
	}

	public boolean eScaduto(Giorno giornoAttuale) {
		if (giornoAttuale.getGiorno().isAfter(scadenza.getGiorno()) || giornoAttuale.getGiorno().isEqual(scadenza.getGiorno())) {
			qualita = false; //se il prodotto e' scaduto, la qualita'� = false 
		}
		return qualita;
	}
	
	public boolean confrontoMerci(Merce altraMerce) {
		if (this.nomeMerce == altraMerce.getNome() & this.unitaMisura == altraMerce.getUnitaMisura() & this.scadenza == altraMerce.getScadenza()) {
			return true;
		}
		else return false;
	}

}
