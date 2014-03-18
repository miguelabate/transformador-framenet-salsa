package transformador;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class OracionSalsa {

	private String id;
	private GraphSalsa graphSalsa;
	private SemSalsa semSalsa;
	
	public OracionSalsa(String id, GraphSalsa graphSalsa, SemSalsa semSalsa) {
		super();
		this.id = id;
		this.graphSalsa = graphSalsa;
		this.semSalsa = semSalsa;
	}
	
	public Node obtenerNodo(Document doc){
		Element sElement = doc.createElement("s");
		sElement.setAttribute("id", id);
		sElement.appendChild(graphSalsa.obtenerNodo(doc));
//		sElement.appendChild(semSalsa.obtenerNodo(doc));
		return sElement;
	}
	
}
