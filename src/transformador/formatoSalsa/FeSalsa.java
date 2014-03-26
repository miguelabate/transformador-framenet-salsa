package transformador.formatoSalsa;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class FeSalsa {

	private String id;
	private String name;
	private FeNodeSalsa fenode;
	
	public FeSalsa(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Node obtenerNodo(Document doc) {
		Element feElement = doc.createElement("fe");
		feElement.setAttribute("id", id);
		feElement.setAttribute("name", name);
		feElement.appendChild(fenode.obtenerNodo(doc));
		return feElement;
	}

	public void setFenode(FeNodeSalsa fenode) {
		this.fenode = fenode;
	}

}
