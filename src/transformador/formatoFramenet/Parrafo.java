package transformador.formatoFramenet;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Parrafo {

	ArrayList<Oracion> listaOraciones = new ArrayList<Oracion>();
	
	public Parrafo(Element parrafo) {
		NodeList oraciones = parrafo.getElementsByTagName("sentence");
		for(int i=0;i<oraciones.getLength();i++){
			this.listaOraciones.add(new Oracion((Element)oraciones.item(i)));
			
		}
	}

	public ArrayList<Oracion> getListaOraciones() {
		return listaOraciones;
	}
}
