package transformador.formatoSalsa;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import transformador.conversorASalsa.ReferenciablePorUnEdgeSalsa;

public class FeNodeSalsa {

	private ReferenciablePorUnEdgeSalsa referencia;
	
	public Node obtenerNodo(Document doc){
		Element feNodeElement = doc.createElement("fenode");
		feNodeElement.setAttribute("idref",referencia.getId());
		return feNodeElement;
	}

	public void setReferencia(ReferenciablePorUnEdgeSalsa referencia) {
		this.referencia = referencia;
	}
}
