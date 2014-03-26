package transformador.formatoSalsa;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TargetSalsa {

	private String id;
	private String pos;
	private String lemma;
	private FeNodeSalsa fenode;
	
	public TargetSalsa(String id, String pos, String lemma) {
		super();
		this.id = id;
		this.pos = pos;
		this.lemma = lemma;
	}

	public Node obtenerNodo(Document doc) {
		Element targetElement = doc.createElement("target");
		targetElement.setAttribute("id", id);
		targetElement.setAttribute("pos", pos);
		targetElement.setAttribute("lemma", lemma);
		targetElement.appendChild(fenode.obtenerNodo(doc));
		return targetElement;
	}

	public void setFenode(FeNodeSalsa fenode) {
		this.fenode = fenode;
	}
}
