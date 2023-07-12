package Magazzino.MagazzinoView;

import java.util.HashSet;
import java.util.PriorityQueue;

import Magazzino.ElementoMagazzino;
import Magazzino.RegistroMagazzino;
import Util.InputDati;

public class RegistroMagazzinoView {

	static final String MSG_ALTRE_MERCI = "Vuoi aggiungere altre merci? ";

	private RegistroMagazzino registro;

	public RegistroMagazzinoView(RegistroMagazzino registro) {
		this.registro = registro;
	}

	public HashSet<ElementoMagazzino> inserisciElementiMagazzinoComprati(){
		HashSet<ElementoMagazzino> comprati = new HashSet<>();

		boolean scelta = false;
		do {
			ElementoMagazzino elemento = ElementoMagazzinoView.creaElementoMagazzino();
			comprati.add(elemento);

			scelta = InputDati.yesOrNo(MSG_ALTRE_MERCI);
		} while (scelta);

		return comprati;
	}

	public String descrizioneRegistroMagazzino() {
		String daTornare = "Registro Magazzino:\n\n";
		for (String nome : registro.getRegistro().keySet()) {
			daTornare += "Merce: " + nome + "\n";
			PriorityQueue<ElementoMagazzino> codaMerce = registro.getRegistro().get(nome);
			for (ElementoMagazzino elemento : codaMerce) {
				ElementoMagazzinoView elementoMagazzinoView = new ElementoMagazzinoView(elemento);
				daTornare += elementoMagazzinoView.descrizioneElementoMagazzino();
				daTornare += "\n\n";
			}
			daTornare +="---\n\n";
		}
		return daTornare;
	}

	public void mostraDescrizioneRegistroMagazzino() {
		System.out.println(descrizioneRegistroMagazzino());
	}
}
