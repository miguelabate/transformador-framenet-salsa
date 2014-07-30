package transformador.formatoTimeML;

import org.w3c.dom.Element;

public class Signal extends ConsumidorTexto implements ReferenciablePorLink{
	private String sid;
	
	public Signal(String sid, Integer indiceStart, Integer indiceEnd) {
		super("", indiceStart);
		super.end=indiceEnd;
		this.sid = sid;
	}
	
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
	
	@Override
	public TipoConsumidorTexto getTipoConsumidorTexto() {
		return TipoConsumidorTexto.SIGNAL;
	}
	
	@Override
	public void generarTagTimeML(StringBuilder resultado, int indiceTexto,char caracterEnIndice,
			ConsumidorTexto consumidorEncontrado) {
		if(consumidorEncontrado.getStart()==indiceTexto){
			resultado.append("<SIGNAL>");
			resultado.append(caracterEnIndice);
		} else if(consumidorEncontrado.getEnd()-1==indiceTexto){
			resultado.append(caracterEnIndice);
			resultado.append("</SIGNAL>");
		}else{//estoy en el medio de un elemento event
			resultado.append(caracterEnIndice);
		}
	}
	
	@Override
	public Boolean esIgualA(ReferenciablePorLink otroReferenciable) {
		if(otroReferenciable instanceof Signal){
			return ((Signal)otroReferenciable).getSid().equals(this.getSid());
		}
		else 
			return false;//ni es de la misma clase, son diferentes
	}

	@Override
	public String toString() {
		return "Signal [sid=" + sid + "]";
	}
	
	@Override
	public Integer distanciaA(ReferenciablePorLink otroReferenciable) {
		if(otroReferenciable instanceof Timex3){
			return ((Timex3)otroReferenciable).getStart()-this.getStart();
		}
		if(otroReferenciable instanceof MakeInstance){
			return ((MakeInstance)otroReferenciable).getEventID().getStart()-this.getStart();
		}
		if(otroReferenciable instanceof Signal){
			return ((Signal)otroReferenciable).getStart()-this.getStart();
		}
		return null;
	}
}
