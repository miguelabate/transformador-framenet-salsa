package transformador.formatoTimeML;

import java.util.HashMap;

import org.w3c.dom.Element;

import transformador.formatoTimeML.types.MakeInstacePolarityType;
import transformador.formatoTimeML.types.MakeInstanceAspectType;
import transformador.formatoTimeML.types.MakeInstancePosType;
import transformador.formatoTimeML.types.MakeInstanceTenseType;

public class MakeInstance implements ReferenciablePorLink {

	private String eiid;
	private Event eventID;
	private MakeInstanceTenseType tense;
	private MakeInstanceAspectType aspect;
	private MakeInstancePosType pos;
	private MakeInstacePolarityType polarity;
	private String modality;
	private Signal signalId;
	private String cardinality;
	
	public MakeInstance(String eiid, Event eventID,
			MakeInstanceTenseType tense, MakeInstanceAspectType aspect,
			MakeInstancePosType pos) {
		super();
		this.eiid = eiid;
		this.eventID = eventID;
		this.tense = tense;
		this.aspect = aspect;
		this.pos = pos;
	}
	
	public MakeInstance(Element node, HashMap<String, Event> tablaEvent,HashMap<String, Signal> tablaSignal){
		this(node.getAttribute("eiid"),tablaEvent.get(node.getAttribute("eventID")),MakeInstanceTenseType.valueOf(node.getAttribute("tense")),MakeInstanceAspectType.valueOf(node.getAttribute("aspect")),MakeInstancePosType.valueOf(node.getAttribute("pos")));
		
		if(node.hasAttribute("polarity"))this.setPolarity(MakeInstacePolarityType.valueOf(node.getAttribute("polarity")));
		if(node.hasAttribute("modality"))this.setModality(node.getAttribute("modality"));
		if(node.hasAttribute("signalID"))this.setSignalId(tablaSignal.get(node.getAttribute("signalID")));
		if(node.hasAttribute("cardinality"))this.setCardinality(node.getAttribute("cardinality"));
	}

	public String getEiid() {
		return eiid;
	}

	public void setEiid(String eiid) {
		this.eiid = eiid;
	}

	public Event getEventID() {
		return eventID;
	}

	public void setEventID(Event eventID) {
		this.eventID = eventID;
	}

	public MakeInstanceTenseType getTense() {
		return tense;
	}

	public void setTense(MakeInstanceTenseType tense) {
		this.tense = tense;
	}

	public MakeInstanceAspectType getAspect() {
		return aspect;
	}

	public void setAspect(MakeInstanceAspectType aspect) {
		this.aspect = aspect;
	}

	public MakeInstancePosType getPos() {
		return pos;
	}

	public void setPos(MakeInstancePosType pos) {
		this.pos = pos;
	}

	public MakeInstacePolarityType getPolarity() {
		return polarity;
	}

	public void setPolarity(MakeInstacePolarityType polarity) {
		this.polarity = polarity;
	}

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public Signal getSignalId() {
		return signalId;
	}

	public void setSignalId(Signal signalId) {
		this.signalId = signalId;
	}

	public String getCardinality() {
		return cardinality;
	}

	public void setCardinality(String cardinality) {
		this.cardinality = cardinality;
	}

	/**
	 * en el caso del makeinstance tomamos que es igual si es que referncian almismo evento
	 */
	@Override
	public Boolean esIgualA(ReferenciablePorLink otroReferenciable) {
		if(otroReferenciable instanceof MakeInstance){
			return ((MakeInstance)otroReferenciable).getEventID().getEid().equals(this.getEventID().getEid());
		}
		else 
			return false;//ni es de la misma clase, son diferentes
	}

	@Override
	public String toString() {
		return "MakeInstance [eiid=" + eiid + "]";
	}
	
	/**
	 * Si es positivo es que el otroREfernciable estta mas adelante
	 */
	@Override
	public Integer distanciaA(ReferenciablePorLink otroReferenciable) {
		if(otroReferenciable instanceof Timex3){
			return ((Timex3)otroReferenciable).getStart()-this.getEventID().getStart();
		}
		if(otroReferenciable instanceof MakeInstance){
			return ((MakeInstance)otroReferenciable).getEventID().getStart()-this.getEventID().getStart();
		}
		if(otroReferenciable instanceof Signal){
			return ((Signal)otroReferenciable).getStart()-this.getEventID().getStart();
		}
		return null;
	}
	
	@Override
	public Integer getStart() {
		return this.eventID.getStart();
	}
}
