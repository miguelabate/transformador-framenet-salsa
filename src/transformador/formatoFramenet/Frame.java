package transformador.formatoFramenet;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Frame {

	String 				frameName;
	Label				target;
	ArrayList<Label> 	listaFE = new ArrayList<Label>();
	
	public Frame(Element annotationSet) {
		NodeList layers = annotationSet.getElementsByTagName("layer");
		frameName=annotationSet.getAttribute("frameName");
		
		for(int i=0;i<layers.getLength();i++){
			Element layer = (Element)layers.item(i);
			if(layer.getAttribute("name").equals("Target"))target=new Label((Element)layer.getElementsByTagName("label").item(0));
			if(layer.getAttribute("name").equals("FE")){
				NodeList labels = layer.getElementsByTagName("label");
				for(int j=0;j<labels.getLength();j++)listaFE.add(new Label((Element)labels.item(j)));
			}
			
		}
	}

	
}
