package transformador.formatoTimeML;

import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import transformador.formatoTimeML.types.Timex3Type;

public class Timex3 extends ConsumidorTexto implements ReferenciablePorLink{
	private String tid;
	private Timex3Type type;
	private String value;
	private String mod;//podria ser enum
	private Boolean temporalFunction;
	private Timex3 anchorTimeID;
	private Timex3FunctionInDocument functionInDocument;
	private ConsumidorTexto beginPoint;
	private ConsumidorTexto endPoint;
	private String quant;//aparecen en el caso de type SET
	private String freq;//aparecen en el caso de type SET
	
	public Timex3(String tid, Timex3Type type, String value, String contenido, Integer indice) {
		super(contenido,indice);
		this.tid = tid;
		this.type = type;
		this.value = value;
	}

	public Timex3(Element nodo, HashMap<String, Timex3> timex3Tabla, Integer indice) {
		this(nodo.getAttribute("tid"), Timex3Type.valueOf(nodo.getAttribute("type")),nodo.getAttribute("value"),nodo.getTextContent(),indice);
		
		if(nodo.hasAttribute("mod"))this.setMod(nodo.getAttribute("mod"));
		if(nodo.hasAttribute("temporalFunction"))this.setTemporalFunction(nodo.getAttribute("temporalFunction").equals("true"));
		if(nodo.hasAttribute("anchorTimeID"))this.setAnchorTimeID(timex3Tabla.get(nodo.getAttribute("anchorTimeID")));
		if(nodo.hasAttribute("functionInDocument"))this.setFunctionInDocument(Timex3FunctionInDocument.valueOf(nodo.getAttribute("functionInDocument")));
		if(nodo.hasAttribute("beginPoint"))this.setBeginPoint(timex3Tabla.get(nodo.getAttribute("beginPoint")));
		if(nodo.hasAttribute("endPoint"))this.setEndPoint(timex3Tabla.get(nodo.getAttribute("endPoint")));
		if(nodo.hasAttribute("quant"))this.setQuant(nodo.getAttribute("quant"));
		if(nodo.hasAttribute("freq"))this.setFreq(nodo.getAttribute("freq"));
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Timex3Type getType() {
		return type;
	}

	public void setType(Timex3Type type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	}

	public Boolean getTemporalFunction() {
		return temporalFunction;
	}

	public void setTemporalFunction(Boolean temporalFunction) {
		this.temporalFunction = temporalFunction;
	}

	public Timex3 getAnchorTimeID() {
		return anchorTimeID;
	}

	public void setAnchorTimeID(Timex3 anchorTimeID) {
		this.anchorTimeID = anchorTimeID;
	}

	public Timex3FunctionInDocument getFunctionInDocument() {
		return functionInDocument;
	}

	public void setFunctionInDocument(Timex3FunctionInDocument functionInDocument) {
		this.functionInDocument = functionInDocument;
	}

	public ConsumidorTexto getBeginPoint() {
		return beginPoint;
	}

	public void setBeginPoint(ConsumidorTexto beginPoint) {
		this.beginPoint = beginPoint;
	}

	public ConsumidorTexto getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(ConsumidorTexto endPoint) {
		this.endPoint = endPoint;
	}

	public String getQuant() {
		return quant;
	}

	public void setQuant(String quant) {
		this.quant = quant;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}
}
