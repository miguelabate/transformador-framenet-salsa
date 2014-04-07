package transformador.formatoTimeML;

import java.util.HashMap;

import org.w3c.dom.Element;

import transformador.formatoTimeML.types.SLinkType;

public class SLink {

	private String lid;
	private MakeInstance eventInstanceID;//eventInstanceID
	private MakeInstance subordinatedEventInstanceID;//subordinatedEventInstanceID
	private SLinkType relType;
	
	private Signal signalID;
	private String syntax;

	public SLink(String lid, MakeInstance eventInstanceID,
			MakeInstance subordinatedEventInstanceID, SLinkType relType) {
		super();
		this.lid = lid;
		this.eventInstanceID = eventInstanceID;
		this.subordinatedEventInstanceID = subordinatedEventInstanceID;
		this.relType = relType;
	}
	
	public SLink(Element nodo,HashMap<String, MakeInstance> eventosMITable,HashMap<String, Signal> signalTable){
		this(nodo.getAttribute("lid"),eventosMITable.get(nodo.getAttribute("eventInstanceID")),eventosMITable.get(nodo.getAttribute("subordinatedEventInstanceID")),SLinkType.valueOf(nodo.getAttribute("relType")));
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

	public SLinkType getRelType() {
		return relType;
	}

	public MakeInstance getEventInstanceID() {
		return eventInstanceID;
	}

	public MakeInstance getSubordinatedEventInstanceID() {
		return subordinatedEventInstanceID;
	}
}
