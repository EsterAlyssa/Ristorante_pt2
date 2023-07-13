package Ristorante;

import java.util.HashSet;
import java.util.TreeSet;

import Giorno.Giorno;
import Magazzino.RegistroMagazzino;
import Ristorante.ElementiRistorante.InsiemeExtra;
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

	private InsiemeExtra insiemeGE;
	private InsiemeExtra insiemeB;

	private HashSet<Ricetta> ricettario;
	private HashSet<Piatto> piatti;
	private HashSet<MenuTematico> menuTematici;
	private RegistroMagazzino registroMagazzino;

	//costruttore privato per Singleton
	private Ristorante(String nome) {
		this.nome = nome;
		this.calendario = new TreeSet<>();
		this.insiemeB = new InsiemeExtra();
		this.insiemeGE = new InsiemeExtra();

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
			if (giornata.getGiorno().compareTo(giorno)==0){
				return giornata;
			}
		}
		return null;
	}

	public HashSet<Ricetta> getRicettario() {
		return ricettario;
	}

	public InsiemeExtra getInsiemeGE() {
		return insiemeGE;
	}

	public void setInsiemeGE(InsiemeExtra insiemeGE) {
		this.insiemeGE = insiemeGE;
	}

	public InsiemeExtra getInsiemeB() {
		return insiemeB;
	}

	public void setInsiemeB(InsiemeExtra insiemeB) {
		this.insiemeB = insiemeB;
	}

	public void aggiungiBevanda(String nomeB, double consumoProCapiteB) {
		if (nomeB != "" && consumoProCapiteB > 0.0) {
			this.insiemeB.aggiungiElementoExtra(nomeB, consumoProCapiteB);
		}
	}

	public boolean rimuoviBevanda(String nome) {
		if (nome != "") {
			return insiemeB.rimuoviElementoExtra(nome);
		}
		return false;
	}

	public void aggiungiGenereExtra(String nomeGE, double consumoProCapiteGE) {
		if (nomeGE != "" && consumoProCapiteGE>0) {
			insiemeGE.aggiungiElementoExtra(nomeGE, consumoProCapiteGE);
		}
	}

	public boolean rimuoviGenereExtra(String nome) {
		if (nome != "") {
			return insiemeGE.rimuoviElementoExtra(nome);
		}
		return false;
	}

	public void setRicettario(HashSet<Ricetta> ricettario) {
		this.ricettario = ricettario;
	}

	public boolean aggiungiRicetta(Ricetta ricetta) {
		return this.ricettario.add(ricetta);
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
