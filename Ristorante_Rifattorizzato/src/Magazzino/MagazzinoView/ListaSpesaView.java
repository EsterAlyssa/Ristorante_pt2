package Magazzino.MagazzinoView;

import Magazzino.ListaSpesa;

public class ListaSpesaView {
	
	private ListaSpesa listaSpesa;

	public ListaSpesaView(ListaSpesa listaSpesa) {
		this.listaSpesa = listaSpesa;
	}
	
	public String descrizioneListaSpesa() {
		String daTornare = "Lista Spesa:\n";
		for (String nome : listaSpesa.getLista().keySet()) {
			daTornare += nome + ": " + listaSpesa.getLista().get(nome);
			daTornare += ";\n";
		}
		return daTornare;
	}
	
	public void mostraDescrizioneListaSpesa() {
		System.out.println(descrizioneListaSpesa());
	}
}
