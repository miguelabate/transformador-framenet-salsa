package transformador.formatoTimeML;

import org.w3c.dom.Element;

public class Signal extends ConsumidorTexto implements ReferenciablePorLink{
	private String sid;
	
	public Signal(String sid, String contenido, Integer indice) {
		super(contenido, indice);
		this.sid = sid;
	}

	public Signal(Element nodo, Integer indice) {
		this(nodo.getAttribute("sid"), nodo.getTextContent(),indice);
	}

	public String getSid() {
		return sid;
	}
}
