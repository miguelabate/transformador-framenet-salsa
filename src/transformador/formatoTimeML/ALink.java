package transformador.formatoTimeML;

import java.util.HashMap;

import org.w3c.dom.Element;

import transformador.formatoTimeML.types.ALinkType;
import transformador.formatoTimeML.types.LinkType;

public class ALink extends Link{

	private MakeInstance eventInstanceID;//eventInstanceID
	private MakeInstance relatedToEventInstanceID;//relatedToEventInstanceID
	private ALinkType relType;
	
	private Signal signalID;
	private String syntax;

	public ALink(String lid, MakeInstance eventInstanceID,
			MakeInstance relatedToEventInstanceID, ALinkType relType) {
		super(lid);
		this.eventInstanceID = eventInstanceID;
		this.relatedToEventInstanceID = relatedToEventInstanceID;
		this.relType = relType;
	}
	
	public ALink(Element nodo,HashMap<String, MakeInstance> eventosMITable,HashMap<String, Signal> signalTable){
		this(nodo.getAttribute("lid"),eventosMITable.get(nodo.getAttribute("eventInstanceID")),eventosMITable.get(nodo.getAttribute("relatedToEventInstance")),ALinkType.valueOf(nodo.getAttribute("relType")));
		if(nodo.hasAttribute("syntax"))this.setSyntax(nodo.getAttribute("syntax"));
		if(nodo.hasAttribute("signalID"))this.setSignalID(signalTable.get(nodo.getAttribute("signalID")));
	}

	public Signal getSignalID() {
		return signalID;
	}

	public void setSignalID(Signal signalID) {
		this.signalID = signalID;
	}

	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public String getLid() {
		return lid;
	}

	public ALinkType getRelType() {
		return relType;
	}

	public MakeInstance getEventInstanceID() {
		return eventInstanceID;
	}

	public MakeInstance getRelatedToEventInstanceID() {
		return relatedToEventInstanceID;
	}

	@Override
	public LinkType obtenerTipoDeLink() {
		return LinkType.ALINK;
	}
}
