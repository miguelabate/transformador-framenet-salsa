package transformador.formatoFramenet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import transformador.common.ClaveDeReferenciable;
import transformador.conversorASalsa.ReferenciablePorUnEdgeSalsa;
import transformador.formatoSalsa.EdgeSalsa;
import transformador.formatoSalsa.FeNodeSalsa;
import transformador.formatoSalsa.FeSalsa;
import transformador.formatoSalsa.FrameSalsa;
import transformador.formatoSalsa.NoTerminalSalsa;
import transformador.formatoSalsa.OracionSalsa;
import transformador.formatoSalsa.TargetSalsa;
import transformador.formatoTimeML.ConsumidorTexto;


public class Oracion {

	String texto;
	ArrayList<Frame> listaDeFramesAnotados= new ArrayList<Frame>();
	private MultiHashMap tablaReferenciables = new MultiHashMap();//Uso multihashmap porque puedo tener varios values para un mismo key
	
	public Oracion(Element oracion) {
		this.texto=((Element)oracion.getElementsByTagName("text").item(0)).getTextContent();
		NodeList annotationSets = oracion.getElementsByTagName("annotationSet");
		for(int i=0;i<annotationSets.getLength();i++){
			if(!((Element)annotationSets.item(i)).getAttribute("frameName").isEmpty())//solo me interesan los layers de frames, el otro que es el analisis sintactico, no.
				this.listaDeFramesAnotados.add(new Frame((Element)annotationSets.item(i),this));
			
		}
		
		//dejo la tabla llena
		llenarTabla();
	}

	public ArrayList<Frame> getListaDeFramesAnotados() {
		return listaDeFramesAnotados;
	}

	public String getTexto() {
		return texto;
	}
	
	///Metodos del manejo de la tabla de referencias
	private void llenarTabla() {
		ArrayList<Frame> listaFramesFramenet=this.getListaDeFramesAnotados();
		for(Frame unFrame:listaFramesFramenet){
			//Frame elements salsa
			for(Label frameElement:unFrame.getListaFE()){
				ClaveDeReferenciable clave= new ClaveDeReferenciable(frameElement.getStart(),frameElement.getEnd()+1);
					this.tablaReferenciables.put(clave, frameElement);
			}
			//Target de salsa
			Label target=unFrame.getTarget();
			ClaveDeReferenciable clave= new ClaveDeReferenciable(target.getStart(),target.getEnd()+1);
				this.tablaReferenciables.put(clave, target);
		}
	}
	
	/**
	 * Devuelve los labels correspondientes al rango dado o null
	 * @param comienzo
	 * @param fin
	 * @return
	 */
	public List obtenerLabelsEn(int comienzo, int fin){
		ClaveDeReferenciable clave=new ClaveDeReferenciable(comienzo,fin);
		return (List) this.tablaReferenciables.get(clave);
	}
	
	public Collection<Label> obtenerLabelsFlexibleEn(Integer comienzo, Integer fin){
		final ClaveDeReferenciable clave=new ClaveDeReferenciable(comienzo,fin);
		Collection<ClaveDeReferenciable> referenciablesEncontrado= (Collection<ClaveDeReferenciable>) CollectionUtils.select(this.tablaReferenciables.keySet(), new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				return clave.estaIncluidoEn((ClaveDeReferenciable) arg0);
			}
		});
		

		Collection<List> listaDeListaDeLabels=CollectionUtils.collect(referenciablesEncontrado,new Transformer() {
			
			@Override
			public Object transform(Object arg0) {
				return Oracion.this.tablaReferenciables.get((ClaveDeReferenciable)arg0);
			}
		}); 
		final Collection<Label> resultado= new ArrayList<Label>();
		CollectionUtils.forAllDo(listaDeListaDeLabels, new Closure() {
			
			@Override
			public void execute(Object arg0) {
				resultado.addAll((Collection<? extends Label>) arg0);
				
			}
		});
		return resultado;
	}
	
	public MultiHashMap getTablaReferenciables(){
		return this.tablaReferenciables;
	}
}
