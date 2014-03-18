package transformador.formatoFramenet;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Documento {

	ArrayList<Parrafo> listaParrafos = new ArrayList<Parrafo>();
	
	public Documento(Element nodoDocumento){
//		this.id=nodoOracion.getAttribute("id");
		
		NodeList parrafos = nodoDocumento.getElementsByTagName("paragraph");
		for(int i=0;i<parrafos.getLength();i++){
			this.listaParrafos.add(new Parrafo((Element)parrafos.item(i)));
			
		}
	}

	public ArrayList<Parrafo> getListaParrafos() {
		return listaParrafos;
	}
	
	

}
