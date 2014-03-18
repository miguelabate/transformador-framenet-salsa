package transformador;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class EdgeSalsa {
	 private ReferenciablePorUnEdgeSalsa referenciableTerminal;//id referencia a un nodo terminal o no terminal
	 private String label="-";
	
	public EdgeSalsa(ReferenciablePorUnEdgeSalsa referenciableTerminal,
			String label) {
		super();
		this.referenciableTerminal = referenciableTerminal;
		this.label = label;
	}

	public Node obtenerNodo(Document doc) {
		Element edgeElement = doc.createElement("edge");
		edgeElement.setAttribute("idref", referenciableTerminal.getId());
		edgeElement.setAttribute("label", label);
		
		return edgeElement;
	}
}
