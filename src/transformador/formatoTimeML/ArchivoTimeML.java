package transformador.formatoTimeML;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import transformador.common.ClaveDeReferenciable;
import transformador.generadorFeatures.links.PosibleAsociacion;

public class ArchivoTimeML {

	private String textoPlano="";
//	private HashMap<String, TextoSinMarcarTimeML> textoSinMarcarTabla= new HashMap<String, TextoSinMarcarTimeML>();
	private HashMap<String, Timex3> timex3Tabla= new HashMap<String, Timex3>();
	private HashMap<String, Signal> signalTabla= new HashMap<String, Signal>();
	private HashMap<String, Event> eventTabla= new HashMap<String, Event>();
	private HashMap<String, MakeInstance> makeinstanceTabla= new HashMap<String, MakeInstance>();
	private HashMap<String, TLink> tlinkTabla= new HashMap<String, TLink>();
	private HashMap<String, SLink> slinkTabla= new HashMap<String, SLink>();
	private HashMap<String, ALink> alinkTabla= new HashMap<String, ALink>();
	private HashMap<ClaveDeReferenciable, ConsumidorTexto> tablaReferenciables = new HashMap<ClaveDeReferenciable, ConsumidorTexto>();
	
	
	public ArchivoTimeML() {
	}
	
	public ArchivoTimeML(String pathArchivo) {
		ParserArchivoTimeML parserTimeML = new ParserArchivoTimeML(this);
		parserTimeML.cargarDatos(pathArchivo);
	}

	public HashMap<String, Timex3> getTimex3Tabla() {
		return timex3Tabla;
	}

	public String getTextoPlano() {
		return textoPlano;
	}
	
	public void setTextoPlano(String texto) {
		textoPlano=texto;
	}
	
	public HashMap<String, Signal> getSignalTabla() {
		return signalTabla;
	}

	public HashMap<String, Event> getEventTabla() {
		return eventTabla;
	}

	public HashMap<String, MakeInstance> getMakeinstanceTabla() {
		return makeinstanceTabla;
	}

	public HashMap<String, TLink> getTlinkTabla() {
		return tlinkTabla;
	}

	public HashMap<String, SLink> getSlinkTabla() {
		return slinkTabla;
	}

	public HashMap<String, ALink> getAlinkTabla() {
		return alinkTabla;
	}

	public void guardarComoArchivoDeTextoPlano(String path) throws FileNotFoundException{
		PrintWriter out = new PrintWriter(path);
		out.print(this.textoPlano);
		out.close();
		
	}
	
	public String obtenerTextoTageado(){
		StringBuilder resultado=new StringBuilder();
		
		for(int indiceTexto=0;indiceTexto<this.getTextoPlano().length();indiceTexto++){
			final int indiceComparar=indiceTexto;//copio en otra variable para poder usarlo en la busqueda del predicate
			
			ClaveDeReferenciable referenciableEncontrado= (ClaveDeReferenciable) CollectionUtils.find(this.tablaReferenciables.keySet(), new Predicate() {
				
				@Override
				public boolean evaluate(Object arg0) {
					return ((ClaveDeReferenciable) arg0).getStart()<=indiceComparar&&((ClaveDeReferenciable) arg0).getEnd()>indiceComparar;
				}
			});
			if(referenciableEncontrado!=null){
				ConsumidorTexto consumidorEncontrado=this.tablaReferenciables.get(referenciableEncontrado);
				consumidorEncontrado.generarTagTimeML(resultado, indiceTexto,this.getTextoPlano().charAt(indiceTexto),
							consumidorEncontrado);
			}else{
				resultado.append(this.getTextoPlano().charAt(indiceTexto));//estoy fuera de un consumidor
			}
		}
		
		return resultado.toString();
	}

	/**
	 * Devuelve el consumidor de texto coincidente en ese rango. Puede ser Event, Timex3 o Signal. O null.
	 * @param comienzo
	 * @param fin
	 * @return
	 */
	public ConsumidorTexto obtenerConsumidorDeTextoEn(Integer comienzo, Integer fin){
		ClaveDeReferenciable clave=new ClaveDeReferenciable(comienzo,fin);
		return this.tablaReferenciables.get(clave);
	}
	
	/**
	 * Devuelve el consumidor de texto que tocan este rango, aunque sea parcial. Puede ser Event, Timex3 o Signal. O null.
	 * @param comienzo
	 * @param fin
	 * @return
	 */
	public ConsumidorTexto obtenerConsumidorDeTextoFlexibleEn(Integer comienzo, Integer fin){
		final ClaveDeReferenciable clave=new ClaveDeReferenciable(comienzo,fin);
		ClaveDeReferenciable referenciableEncontrado= (ClaveDeReferenciable) CollectionUtils.find(this.tablaReferenciables.keySet(), new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				return clave.estaIncluidoEn((ClaveDeReferenciable) arg0);
			}
		});
		
		return this.tablaReferenciables.get(referenciableEncontrado);
	}

	public HashMap<ClaveDeReferenciable, ConsumidorTexto> getTablaReferenciables() {
		return tablaReferenciables;
	}
	
	//metodos para la edicion manual
	
	public void marcar(ConsumidorTexto referenciable){
		switch(referenciable.getTipoConsumidorTexto()){
		case EVENT:
			marcarEvento((Event)referenciable);
			break;
		case SIGNAL:
			marcarSignal((Signal)referenciable);
			break;
		case TIMEX3:
			marcarTimex((Timex3)referenciable);
			break;
		}
	}
	private void marcarEvento(Event eventoParaAgregar){
		this.eventTabla.put(eventoParaAgregar.getEid(), eventoParaAgregar);
		agregarATableReferenciables(eventoParaAgregar);
	}

	private void marcarTimex(Timex3 timexParaAgregar){
		this.timex3Tabla.put(timexParaAgregar.getTid(), timexParaAgregar);
		agregarATableReferenciables(timexParaAgregar);
	}
	private void marcarSignal(Signal signalParaAgregar){
		this.signalTabla.put(signalParaAgregar.getSid(), signalParaAgregar);
		agregarATableReferenciables(signalParaAgregar);
	}
	
	private void agregarATableReferenciables(ConsumidorTexto referenciable) {
		//ignora el contenido que tenga, toma el del texto plano
		referenciable.setContenido(this.textoPlano.substring(referenciable.getStart(), referenciable.getEnd()));
		referenciable.setEnd(referenciable.getContenido().length()+referenciable.getStart());
		this.tablaReferenciables.put(new ClaveDeReferenciable(referenciable.getStart(), referenciable.getEnd()), referenciable);
	}

	/**
	 * A partir de una PosibleAsociacion (evento-evento o evento-timex) busca el link si existe, sino devuelve null
	 * @param posibleAsociacion
	 * @return
	 */
	public Link obtenerLink(final PosibleAsociacion posibleAsociacion) {
		Collection<TLink> tlinks=this.tlinkTabla.values();
		TLink tlinkEncontrado=(TLink) CollectionUtils.find(tlinks, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				TLink tlink=(TLink)arg0;
				return (tlink.getReferenciaEventTime().esIgualA(posibleAsociacion.getreferenciablePorLinkA())&&tlink.getRelatedEventTime().esIgualA(posibleAsociacion.getreferenciablePorLinkB()))||
						tlink.getReferenciaEventTime().esIgualA(posibleAsociacion.getreferenciablePorLinkB())&&tlink.getRelatedEventTime().esIgualA(posibleAsociacion.getreferenciablePorLinkA());
			}
		});
		if(tlinkEncontrado!=null) return tlinkEncontrado;
		
		Collection<SLink> slinks=this.slinkTabla.values();
		SLink slinkEncontrado=(SLink) CollectionUtils.find(slinks, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				SLink slink=(SLink)arg0;
				return (slink.getEventInstanceID().esIgualA(posibleAsociacion.getreferenciablePorLinkA())&&slink.getSubordinatedEventInstanceID().esIgualA(posibleAsociacion.getreferenciablePorLinkB()))||
						slink.getEventInstanceID().esIgualA(posibleAsociacion.getreferenciablePorLinkB())&&slink.getSubordinatedEventInstanceID().esIgualA(posibleAsociacion.getreferenciablePorLinkA());
			}
		});
		if(slinkEncontrado!=null) return slinkEncontrado;
		
		Collection<ALink> alinks=this.alinkTabla.values();
		ALink alinkEncontrado=(ALink) CollectionUtils.find(alinks, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				ALink alink=(ALink)arg0;
				return (alink.getEventInstanceID().esIgualA(posibleAsociacion.getreferenciablePorLinkA())&&alink.getRelatedToEventInstanceID().esIgualA(posibleAsociacion.getreferenciablePorLinkB()))||
						alink.getEventInstanceID().esIgualA(posibleAsociacion.getreferenciablePorLinkB())&&alink.getRelatedToEventInstanceID().esIgualA(posibleAsociacion.getreferenciablePorLinkA());
			}
		});
		if(alinkEncontrado!=null) return alinkEncontrado;
		
		return null;//no encontro
	}
	
}
