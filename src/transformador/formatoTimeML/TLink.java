package transformador.formatoTimeML;

import java.util.HashMap;

import org.w3c.dom.Element;

import transformador.formatoTimeML.types.TLinkType;

public class TLink {

	private String lid;
	private ReferenciablePorLink referenciaEventTime;//eventInstanceID o timeID
	private ReferenciablePorLink relatedEventTime;//relatedToEventInstance o relatedToTime
	private TLinkType relType;
	
	private String origin;
	private Signal signalID;
	private String syntax;

	public TLink(String lid, ReferenciablePorLink referenciaEventTime,
			ReferenciablePorLink relatedEventTime, TLinkType relType) {
		super();
		this.lid = lid;
		this.referenciaEventTime = referenciaEventTime;
		this.relatedEventTime = relatedEventTime;
		this.relType = relType;
	}
	
	public TLink(Element nodo,HashMap<String, MakeInstance> eventosMITable,HashMap<String, Timex3> timexTable,HashMap<String, Signal> signalTable){
		this(nodo.getAttribute("lid"),resolverReferencia(nodo,eventosMITable,timexTable),resolverRelated(nodo,eventosMITable,timexTable),TLinkType.valueOf(nodo.getAttribute("relType")));
		if(nodo.hasAttribute("origin"))this.setOrigin(nodo.getAttribute("origin"));
		if(nodo.hasAttribute("syntax"))this.setSyntax(nodo.getAttribute("syntax"));
		if(nodo.hasAttribute("signalID"))this.setSignalID(signalTable.get(nodo.getAttribute("signalID")));
	}

	private static ReferenciablePorLink resolverReferencia(Element nodo, HashMap<String, MakeInstance> eventosMITable, HashMap<String, Timex3> timexTable) {
		if(nodo.hasAttribute("eventInstanceID")) 
			return (ReferenciablePorLink) eventosMITable.get(nodo.getAttribute("eventInstanceID"));
		else  
			return (ReferenciablePorLink) timexTable.get(nodo.getAttribute("timeID"));
	}

	private static ReferenciablePorLink resolverRelated(Element nodo,HashMap<String, MakeInstance> eventosMITable, HashMap<String, Timex3> timexTable) {
		if(nodo.hasAttribute("relatedToEventInstance")) 
			return (ReferenciablePorLink) eventosMITable.get(nodo.getAttribute("relatedToEventInstance"));
		else  
			return (ReferenciablePorLink) timexTable.get(nodo.getAttribute("relatedToTime"));
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
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

	public ReferenciablePorLink getReferenciaEventTime() {
		return referenciaEventTime;
	}

	public ReferenciablePorLink getRelatedEventTime() {
		return relatedEventTime;
	}

	public TLinkType getRelType() {
		return relType;
	}
}
