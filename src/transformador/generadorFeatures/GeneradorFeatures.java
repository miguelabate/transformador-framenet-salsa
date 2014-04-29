package transformador.generadorFeatures;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

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

public class GeneradorFeatures {

	private ArchivoTimeML archivoTimeML;//archivo timeml que mria ucando quiere sacar el atributo clase, entrenaimiento
	private ArchivoFormatoFramenet archivoFramenet;//archivo framenet de donde 
	private DefinicionFramenet defFramenet;
	private String pathArchivoSalida;
	
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
		
		Integer indiceAbsolutoFramenet=0;//indice que se usa para hacer coincidir la oracion de framenet con el texto de timeml
		//voy oracion por oracion
		for(Documento doc:archivoFramenet.getListaDocumentos()){
			for(Parrafo parrafo:doc.getListaParrafos()){
				for(Oracion oracion:parrafo.getListaOraciones()){
					indiceAbsolutoFramenet=obtenerIndiceDeOracionAbsoluto(oracion.getTexto());
					for(Frame unFrame:oracion.getListaDeFramesAnotados()){
						String featuresGenerados=this.generarSetDeFeatures(unFrame,unFrame.getTarget(),indiceAbsolutoFramenet);
						guardarLineaSalida(featuresGenerados,out);
						for(Label unLabel:unFrame.getListaFE()){
							guardarLineaSalida(this.generarSetDeFeatures(unFrame,unLabel,indiceAbsolutoFramenet),out);
						}
					}
				}
			}
		}
		
		if(pathArchivoSalida!=null)out.close();
	}

	private String generarSetDeFeatures(Frame unFrame, Label label,
			Integer indiceAbsolutoFramenet) {
		String resultt="";
		resultt+=label.getName();//tipo de frame element
		resultt+="\t"+unFrame.getFrameName();//nombre del frame al que pertenece
		//frame padre
		Iterator<DefinicionFrame> it=this.defFramenet.getFramesPadres(unFrame.getFrameName()).iterator();
		if(it.hasNext()){
			DefinicionFrame fPadre=it.next();
			resultt+="\t"+fPadre.getName();
			Iterator<DefinicionFrame> it2=this.defFramenet.getFramesPadres(fPadre.getName()).iterator();
			if(it2.hasNext()){
				DefinicionFrame fPadre2=it2.next();
				resultt+="\t"+fPadre2.getName();
			}
			else resultt+="\tNO_PADRE";
		}
		else resultt+="\tNO_PADRE\tNO_PADRE";

		//texto al que hace referencia
		resultt+="\t"+archivoTimeML.getTextoPlano().substring(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1);
	
		ConsumidorTexto consumidorTimemlt=archivoTimeML.obtenerConsumidorDeTextoEn(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1);
		if((consumidorTimemlt!=null)&&(consumidorTimemlt instanceof Event))//Es un EVENT
			{
			resultt+="\tEVENT";
			Event evento=((Event)archivoTimeML.obtenerConsumidorDeTextoEn(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1));
			resultt+="\t"+evento.getClase().toString();
			resultt+="\t"+evento.getEid();
		
			}
		else
			{//NO ES un EVENT
			resultt+="\tNO_EVENT";
			resultt+="\tNO_CLASE_EVENT";
			resultt+="\tNO_EVENT_ID";
			}
		
		return resultt;
	}

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
}
