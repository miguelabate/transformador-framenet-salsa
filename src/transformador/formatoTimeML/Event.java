package transformador.formatoTimeML;

import org.w3c.dom.Element;

import transformador.formatoTimeML.types.EventClassType;

public class Event extends ConsumidorTexto{
	private String eid;
	private EventClassType clase;
	private String stem;
	
	public Event(String sid, EventClassType clase, String contenido, Integer indice) {
		super(contenido, indice);
		this.eid = sid;
		this.clase=clase;
	}

	public Event(Element nodo, Integer indice) {
		this(nodo.getAttribute("eid"),EventClassType.valueOf(nodo.getAttribute("class")), nodo.getTextContent(), indice);
		
		if(nodo.hasAttribute("stem"))this.setStem(nodo.getAttribute("stem"));
	}

	public String getEid() {
		return eid;
	}

	public EventClassType getClase() {
		return clase;
	}

	public void setClase(EventClassType clase) {
		this.clase = clase;
	}

	public String getStem() {
		return stem;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

}
