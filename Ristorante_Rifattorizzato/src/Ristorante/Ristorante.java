package Ristorante;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import Giorno.Giorno;
import Magazzino.RegistroMagazzino;
import Ristorante.ElementiRistorante.MenuTematico;
import Ristorante.ElementiRistorante.Piatto;
import Ristorante.ElementiRistorante.Ricetta;

public class Ristorante {

	private static Ristorante instance;

	private String nome;
	private int caricoLavoroPersona;
	private int numPosti;
	private double caricoLavoroRistorante; 
	private TreeSet<Giornata> calendario;
	private HashMap<String, Double> insiemeGE;
	private HashMap<String, Double> insiemeB;
	private HashSet<Ricetta> ricettario;
	private HashSet<Piatto> piatti;
	private HashSet<MenuTematico> menuTematici;
	private RegistroMagazzino registroMagazzino;

	//costruttore privato per Singleton
	private Ristorante(String nome) {
		this.nome = nome;
		this.calendario = new TreeSet<>();
		this.insiemeGE = new HashMap<>();
		this.insiemeB = new HashMap<>();
		this.ricettario = new HashSet<>();
		this.piatti = new HashSet<>();
		this.menuTematici = new HashSet<>();
		this.registroMagazzino = new RegistroMagazzino();
	}

	public static Ristorante getInstance(String nome) {
		if (instance == null) {
			instance = new Ristorante(nome);
		}
		return instance;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCaricoLavoroPersona() {
		return caricoLavoroPersona;
	}

	public void setCaricoLavoroPersona(int caricoLavoroPersona) {
		this.caricoLavoroPersona = caricoLavoroPersona;
	}

	public int getNumPosti() {
		return numPosti;
	}

	public void setNumPosti(int numPosti) {
		this.numPosti = numPosti;
	}

	public double getCaricoLavoroRistorante() {
		return caricoLavoroRistorante;
	}

	public void setCaricoLavoroRistorante(double caricoLavoroRistorante) {
		this.caricoLavoroRistorante = caricoLavoroRistorante;
	}

	public TreeSet<Giornata> getCalendario() {
		return calendario;
	}

	public void setCalendario(TreeSet<Giornata> calendario) {
		this.calendario = calendario;
	}

	public Giornata getGiornata (Giorno giorno) {
		for (Giornata giornata : calendario) {
			if (giornata.getGiorno().equals(giorno)) {
				return giornata;
			}
		}
		return null;
	}

	public HashMap<String, Double> getInsiemeGE() {
		return insiemeGE;
	}

	public void setInsiemeGE(HashMap<String, Double> insiemeGE) {
		this.insiemeGE = insiemeGE;
	}

	public void aggiungiGenereExtra(String nome, double consumoProCapite) {
		insiemeGE.put(nome, consumoProCapite);
	}

	public void rimuoviGenereExtra(String nome) {
		if (insiemeGE.containsKey(nome)) {
			insiemeGE.remove(nome);
		}
		else System.out.println("Il genere extra non e' presente nell'insieme");
	}

	public HashMap<String, Double> getInsiemeB() {
		return insiemeB;
	}

	public void setInsiemeB(HashMap<String, Double> insiemeB) {
		this.insiemeB = insiemeB;
	}

	public void aggiungiBevanda(String nome, double consumoProCapite) {
		insiemeB.put(nome, consumoProCapite);
	}

	public void rimuoviBevanda(String nome) {
		if (insiemeB.containsKey(nome)) {
			insiemeB.remove(nome);
		}
		else System.out.println("La bevanda non e' (piu') presente nell'insieme");
	}
	public HashSet<Ricetta> getRicettario() {
		return ricettario;
	}

	public void setRicettario(HashSet<Ricetta> ricettario) {
		this.ricettario = ricettario;
	}

	public void aggiungiRicetta(Ricetta ricetta) {
		this.ricettario.add(ricetta);
	}

	public HashSet<Piatto> getPiatti() {
		return piatti;
	}

	public void setPiatti(HashSet<Piatto> piatti) {
		this.piatti = piatti;
	}

	public void aggiungiPiatto(Piatto piatto) {
		this.piatti.add(piatto);
	}

	public HashSet<MenuTematico> getMenuTematici() {
		return menuTematici;
	}

	public void setMenuTematici(HashSet<MenuTematico> menuTematici) {
		this.menuTematici = menuTematici;
	}

	public void aggiungiMenuTematico (MenuTematico menu) {
		this.menuTematici.add(menu);
	}

	public RegistroMagazzino getRegistroMagazzino() {
		return registroMagazzino;
	}

	public void setRegistroMagazzino(RegistroMagazzino registroMagazzino) {
		this.registroMagazzino = registroMagazzino;
	}

}
