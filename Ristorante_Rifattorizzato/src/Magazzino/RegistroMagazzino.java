package Magazzino;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import Magazzino.Merce.Ingrediente;
import Magazzino.Merce.Merce;
import Ristorante.Giornata;
import Ristorante.Ristorante;

public class RegistroMagazzino {

	private HashMap<String, PriorityQueue<ElementoMagazzino>> registro;

	public RegistroMagazzino() {
		this.registro = new HashMap<>();
	}


	public HashMap<String, PriorityQueue<ElementoMagazzino>> getRegistro() {
		return registro;
	}

	public void setRegistro(HashMap<String, PriorityQueue<ElementoMagazzino>> registro) {
		this.registro = registro;
	}

	public void aggiungiMerce(Merce merce, double quantita) {
		String nomeMerce = merce.getNome();

		if (registro.containsKey(nomeMerce)) {
			PriorityQueue<ElementoMagazzino> codaMerce = registro.get(nomeMerce);

			// Controllo se esiste un elemento con la stessa scadenza
			for (ElementoMagazzino elemento : codaMerce) {
				if (elemento.getMerce().getScadenza().equals(merce.getScadenza())) {
					// Aggiorno la quantita' se la scadenza e' la stessa
					elemento.setQuantita(elemento.getQuantita() + quantita);
					return;
				}
			}
			// Se non esiste un elemento con la stessa scadenza, aggiungo un nuovo elemento nella coda
			ElementoMagazzino nuovoElemento = new ElementoMagazzino(merce, quantita);
			codaMerce.add(nuovoElemento); //il vincolo sulla scadenza è rispettato per default
		} else {
			// Se la merce non e' ancora presente nel magazzino, creo una nuova coda e aggiungo il primo elemento
			PriorityQueue<ElementoMagazzino> nuovaCoda = new PriorityQueue<>((em1, em2) -> em1.getMerce().getScadenza().confrontoScadenza(em2.getMerce().getScadenza()));
			ElementoMagazzino nuovoElemento = new ElementoMagazzino(merce, quantita);
			nuovaCoda.add(nuovoElemento);
			registro.put(nomeMerce, nuovaCoda);
		}
	}

	//per ogni merce della lista dei prodotti acquistati inseriamo nel registro la merce con la dose aggiornata
	public void acquistatiI (HashSet<ElementoMagazzino> comprati) {	
		//i prodotti comprati vanno inseriti nel magazzino
		for (ElementoMagazzino merce : comprati) {
			aggiungiMerce(merce.getMerce(), merce.getQuantita());
		}
	}

	//metodo richiamato per togliere merci dal registro magazzino
	public void togliMerce (String nomeMerce, double quantita) {
		//se il registro contiene la merce
		if (registro.containsKey(nomeMerce)) {
			//ottengo la codaMerce relativa a quella merce
			PriorityQueue<ElementoMagazzino> codaMerce = registro.get(nomeMerce);
			//devo aggiornare la quantita'� prelevando gli elementi a partire da quello che scade prima

			//se la coda non e' vuota e la quantita' del primo elemento e' minore della quantita'  data (= non ci sono 
			//abbastanza merci di quella scadenza) allora continuo a prelevare elementi e a rimuoverli
			//dalla coda
			while (!codaMerce.isEmpty() && codaMerce.peek().getQuantita() <= quantita){
				codaMerce.poll();
			}

			//se la coda non e' vuota e quindi c'e' un elemento con quantita'� > della quantita'� da prelevare
			if (!codaMerce.isEmpty()) {
				ElementoMagazzino elemento = codaMerce.peek();
				//si setta la nuova quantità
				elemento.setQuantita(elemento.getQuantita() - quantita);
			} else { 
				//se la coda e' vuota vuol dire che non ci sono piu' elementi con quel nome nel magazzino
				//quindi va rimossa anche l'etichetta che li rappresenta nella mappa
				registro.remove(nomeMerce);
			}
		}
	}

	//metodo che ritorna un hashmap di stringhe relative alle merci da togliere dal magazzino (per portarle nel ristorante) 
	//e le rispettive quantità
	public HashMap<String, Double> solo1Tipo(ListaSpesa listaSpesa, Class<? extends Merce> tipoMerce) {
		//inizializiamo una mappa che contenga solo gli ingredienti della lista della spesa
		HashMap<String, Double> solo1Tipo = new HashMap<>();
		//per ogni elemento nella lista della spesa
		for (String nomeMerce : listaSpesa.getLista().keySet()) {
			//otteniamo la relativa priorityqueue
			PriorityQueue<ElementoMagazzino> codaMerce = registro.get(nomeMerce);
			Merce trovata = codaMerce.peek().getMerce();
			//se la merce trovata e' del tipo passato
			if (trovata.getClass().equals(tipoMerce)) {
				//salviamo la quantita'� dalla lista della spesa
				double quantita = listaSpesa.getLista().get(nomeMerce);
				solo1Tipo.put(nomeMerce, quantita);
			}
		}
		return solo1Tipo;
	}

	public void inCucinaO (Giornata giornata) {
		//in cucina vanno portati gli ingredienti della lista della spesa relativi a quel giorno
		ListaSpesa listaSpesa = giornata.getDaComprare();

		HashMap<String, Double> soloIngredienti = solo1Tipo(listaSpesa, Ingrediente.class);

		//per ogni elemento di soloIngredienti applichi togli le merci dal magazzino
		for (String nomeIngrediente : soloIngredienti.keySet()) {
			//la quantita'� va moltiplicata per 1.1 perche' in cucina si deve portare il 10% in piu'
			togliMerce(nomeIngrediente, soloIngredienti.get(nomeIngrediente) * 1.1);
		}			
	}

	public void extraO (Ristorante ristorante, Giornata giornata) {	
		//creiamo un unico insieme di elementi extra
		HashMap<String, Double> extra = new HashMap<>(ristorante.getInsiemeB());
		extra.putAll(ristorante.getInsiemeGE());

		//per ogni elemento dell'insieme preleviamo la merce da portare in sala dal magazzino
		for (String elemento : extra.keySet()) {
			togliMerce (elemento, (extra.get(elemento)) * giornata.numCopertiPrenotati());
		}
	}

	public void avanziI (HashMap<Merce, Double> avanzi) {
		//per ogni merce avanzata, quindi non solo ingredienti, ma anche bevande e generi extra, li andiamo a rimettere nel registro magazzino
		for (Merce merceAvanzata : avanzi.keySet()) {
			aggiungiMerce(merceAvanzata, avanzi.get(merceAvanzata));
		}
	}

	public void scartiO (Giornata giornata) {
		for (String merce : registro.keySet()) {
			PriorityQueue<ElementoMagazzino> codaMerce = registro.get(merce);
			for (ElementoMagazzino elemento : codaMerce) {
				codaMerce.peek().getMerce().eScaduto(giornata.getGiorno()); //se scaduto setta la qualità a false
				if (!codaMerce.peek().getMerce().getQualita()) { //se è scaduto
					codaMerce.remove(elemento); //viene rimosso l'elemento dalla coda
				}

			}
			if (codaMerce.isEmpty()) { //se la coda e' vuota = se non ci sono piu' elementi
				registro.remove(merce);//si rimuove la chiave dal registro
			}
		}
	}
	
	public void setFalseQualitaMerce(Merce merceNonDiQualita) {
		PriorityQueue<ElementoMagazzino> codaMerce = registro.get(merceNonDiQualita.getNome());
		for (ElementoMagazzino elemento : codaMerce) {
			if (elemento.getMerce().confrontoMerci(merceNonDiQualita)) {
				elemento.setFalseQualitaMerce();
				break;
			}
		}
	}
	
	public double ritornaQuantitaDatoNome(String nome) {
		double quantita = 0.0;
		
		PriorityQueue<ElementoMagazzino> codaMerce = registro.get(nome);
		
		for (ElementoMagazzino elemento : codaMerce) {
			quantita += elemento.getQuantita();
		}
		
		return quantita;
	}
}
