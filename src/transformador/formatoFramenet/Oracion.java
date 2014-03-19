package transformador.formatoFramenet;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import transformador.OracionSalsa;


public class Oracion {

	String texto;
	ArrayList<Frame> listaDeFramesAnotados= new ArrayList<Frame>();
	
	public Oracion(Element oracion) {
		this.texto=((Element)oracion.getElementsByTagName("text").item(0)).getTextContent();
		NodeList annotationSets = oracion.getElementsByTagName("annotationSet");
		for(int i=0;i<annotationSets.getLength();i++){
			this.listaDeFramesAnotados.add(new Frame((Element)annotationSets.item(i)));
			
		}
	}

	public ArrayList<Frame> getListaDeFramesAnotados() {
		return listaDeFramesAnotados;
	}

	public String getTexto() {
		return texto;
	}
	
}
