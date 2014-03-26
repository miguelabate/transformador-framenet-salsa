package transformador.formatoSalsa;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SemSalsa {

	private ArrayList<FrameSalsa> listaFramesSalsa = new ArrayList<FrameSalsa>();

	public Node obtenerNodo(Document doc){
		Element semElement = doc.createElement("sem");
		Element framesElement = doc.createElement("frames");
		semElement.appendChild(framesElement);
		
		for(FrameSalsa frame:listaFramesSalsa)
			framesElement.appendChild(frame.obtenerNodo(doc));
		
		return semElement;
	}
	
	public void agregarFrame(FrameSalsa frame){
		this.listaFramesSalsa.add(frame);
	}
}
