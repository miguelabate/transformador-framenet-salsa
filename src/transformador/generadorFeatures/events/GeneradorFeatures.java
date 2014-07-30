package transformador.generadorFeatures.events;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import transformador.formatoDefinicionFrame.DefinicionFrame;
import transformador.formatoDefinicionFrame.DefinicionFramenet;
import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoFramenet.Documento;
import transformador.formatoFramenet.Frame;
import transformador.formatoFramenet.Label;
import transformador.formatoFramenet.Oracion;
import transformador.formatoFramenet.Parrafo;
import transformador.formatoTimeML.ArchivoTimeML;
import transformador.formatoTimeML.ConsumidorTexto;
import transformador.formatoTimeML.Event;
import transformador.generadorFeatures.ArchivosInconsistentesGeneradorFeaturesException;

public abstract class GeneradorFeatures {

	protected ArchivoTimeML archivoTimeML;//archivo timeml que mria ucando quiere sacar el atributo clase, entrenaimiento
	private ArchivoFormatoFramenet archivoFramenet;//archivo framenet de donde 
	protected DefinicionFramenet defFramenet;
	private String pathArchivoSalida;
	protected Collection<String> framesHijosDeEvent;
	protected int eventosDetectadosFramenet=0;
	private int eventosTimeML=0;
	
	public GeneradorFeatures(ArchivoTimeML archivoTimeML,
			ArchivoFormatoFramenet archivoFramenet,DefinicionFramenet defFramenet) throws ArchivosInconsistentesGeneradorFeaturesException {
		super();
		this.archivoTimeML = archivoTimeML;
		this.archivoFramenet = archivoFramenet;
		this.defFramenet=defFramenet;
		if(!isArchivosConsistentes()) throw new ArchivosInconsistentesGeneradorFeaturesException();
	}
	
	public GeneradorFeatures(ArchivoTimeML archivoTimeML2,
			ArchivoFormatoFramenet archFrameNet,
			DefinicionFramenet defFramenet2, String pathArchivoSalida) throws ArchivosInconsistentesGeneradorFeaturesException {
		this(archivoTimeML2,archFrameNet,defFramenet2);
		this.pathArchivoSalida=pathArchivoSalida;
	}

	public void generarFeatures() throws IOException{
		FileWriter out = null;
		if(pathArchivoSalida!=null)out= new FileWriter(pathArchivoSalida,true);
		
		//dejo preparado todos los frames hijos de Event para consulta
		this.framesHijosDeEvent=CollectionUtils.collect(this.defFramenet.getFramesHijosTotal("Event"),new Transformer() {
			
			@Override
			public Object transform(Object arg0) {
				return ((DefinicionFrame)arg0).getName();
			}
		});
		
		//me guardo la cantidad de eventos en el archivo timeml para comparar
		eventosTimeML=archivoTimeML.getEventTabla().size();
		
		Integer indiceAbsolutoFramenet=0;//indice que se usa para hacer coincidir la oracion de framenet con el texto de timeml
		//voy oracion por oracion
		for(Documento doc:archivoFramenet.getListaDocumentos()){
			for(Parrafo parrafo:doc.getListaParrafos()){
				for(Oracion oracion:parrafo.getListaOraciones()){
					indiceAbsolutoFramenet=obtenerIndiceDeOracionAbsoluto(oracion.getTexto());
					for(Frame unFrame:oracion.getListaDeFramesAnotados()){
						String featuresGenerados=this.generarSetDeFeatures(unFrame,unFrame.getTarget(),indiceAbsolutoFramenet);
						guardarLineaSalida(featuresGenerados,out);
						ArrayList<Label> listaFESinElTarget=unFrame.getListaFE();
						if(listaFESinElTarget.contains(unFrame.getTarget()))
							{
								listaFESinElTarget.remove(unFrame.getTarget());
							}
						for(Label unLabel:listaFESinElTarget){
							guardarLineaSalida(this.generarSetDeFeatures(unFrame,unLabel,indiceAbsolutoFramenet),out);
						}
					}
				}
			}
		}
		//logeo cuantos eventos habia en el timeml y cuantos se correspondieron con algo en framenet
		System.out.println("Eventos Framenet: "+this.eventosDetectadosFramenet+" Eventos en TimeML: "+this.eventosTimeML);
		
		if(pathArchivoSalida!=null)out.close();
	}

	protected abstract String generarSetDeFeatures(Frame unFrame, Label label,
			Integer indiceAbsolutoFramenet);

	/**
	 * Dada una oracion de framenet, devuelve el indice en el archivo TimeML. Considera algunas conversiones de formato y le saca el f=punto al final
	 * @param oracion
	 * @return
	 */
	private int obtenerIndiceDeOracionAbsoluto(String oracion) {
		return archivoTimeML.getTextoPlano().indexOf(oracion);///le saco el punto del final
	}
	
	/**
	 * Devuelve true si todas las oraciones del archivo en formato framenet pueden ser encontradas en el archivo timeml
	 * false en caso contrario.
	 * @return
	 */
	public Boolean isArchivosConsistentes(){
		for(Documento doc:archivoFramenet.getListaDocumentos()){
			for(Parrafo parrafo:doc.getListaParrafos()){
				for(Oracion oracion:parrafo.getListaOraciones()){
					if(obtenerIndiceDeOracionAbsoluto(oracion.getTexto())==-1){
						System.out.println("Oracion: "+oracion.getTexto());
						System.out.println(archivoTimeML.getTextoPlano());
						return false;
					}
				}
			}
		}
		
		return true;
		
	}

	public void guardarLineaSalida(String linea,FileWriter out) throws IOException{
		if(out==null)System.out.println(linea);
		else{
			out.write(linea+"\n");
		}
	}

	public int getEventosDetectadosFramenet() {
		return eventosDetectadosFramenet;
	}

	public int getEventosTimeML() {
		return eventosTimeML;
	}
}
