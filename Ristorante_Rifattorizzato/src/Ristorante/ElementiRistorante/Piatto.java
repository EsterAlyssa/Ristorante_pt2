package Ristorante.ElementiRistorante;

import java.util.HashSet;

import Giorno.Periodo;
import Prenotazioni.SceltaPrenotazione;

public class Piatto implements SceltaPrenotazione{

	private String nomePiatto;
	private double caricoLavoroPiatto;
	private Periodo validitaPiatto;

	public Piatto(String nomePiatto, double caricoLavoroPiatto) {
		this.nomePiatto = nomePiatto;
		this.caricoLavoroPiatto = caricoLavoroPiatto;
		this.validitaPiatto = new Periodo();
	}
	
	public Piatto (String nomePiatto) {
		this.nomePiatto = nomePiatto;
		this.caricoLavoroPiatto = 0.0; //da settare poi 
		this.validitaPiatto = new Periodo();
	}

	@Override
	public String getNome() {
		return nomePiatto;
	}

	public void setNome(String nome) {
		this.nomePiatto = nome;
	}

	public double getCaricoLavoro() {
		return caricoLavoroPiatto;
	}

	public void setCaricoLavoro(double caricoLavoro) {
		this.caricoLavoroPiatto = caricoLavoro;
	}

	public Periodo getValidita() {
		return validitaPiatto;
	}

	public void setValidita(Periodo validita) {
		this.validitaPiatto = validita;
	}
	
	public static Piatto trovaPiattoDaNome(String piatto, HashSet<Piatto> piatti) {
		for (Piatto p : piatti) {
			if (p.getNome().equals(piatto)) {
				return p;
			}
		}
		// Se il piatto non viene trovato si ritorna null
		throw null;
	}

	@Override
	public HashSet<Piatto> getPiatti() {
		HashSet<Piatto> piatti = new HashSet<Piatto>();
		piatti.add(this);
		return piatti;
	}

	
}
