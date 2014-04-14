package transformador.generadorFeatures;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public GeneradorFeatures(ArchivoTimeML archivoTimeML,
			ArchivoFormatoFramenet archivoFramenet) {
		super();
		this.archivoTimeML = archivoTimeML;
		this.archivoFramenet = archivoFramenet;
	}
	
	public void generarFeatures(){
		Integer indiceAbsolutoFramenet=0;//indice que se usa para hacer coincidir la oracion de framenet con el texto de timeml
		//voy oracion por oracion
		for(Documento doc:archivoFramenet.getListaDocumentos()){
			for(Parrafo parrafo:doc.getListaParrafos()){
				for(Oracion oracion:parrafo.getListaOraciones()){
					indiceAbsolutoFramenet=obtenerIndiceDeOracionAbsoluto(oracion.getTexto());
					for(Frame unFrame:oracion.getListaDeFramesAnotados()){
						String resultt="FE: ";
						resultt+=unFrame.getTarget().getName();//es el target
						resultt+=" Frame: "+unFrame.getFrameName();
						resultt+=" Texto:"+archivoTimeML.getTextoPlano().substring(indiceAbsolutoFramenet+unFrame.getTarget().getStart(), indiceAbsolutoFramenet+unFrame.getTarget().getEnd()+1);
						
						ConsumidorTexto consumidorTimemlt=archivoTimeML.obtenerConsumidorDeTextoEn(indiceAbsolutoFramenet+unFrame.getTarget().getStart(), indiceAbsolutoFramenet+unFrame.getTarget().getEnd()+1);
						if((consumidorTimemlt!=null)&&(consumidorTimemlt instanceof Event))
							{
							resultt+=" "+((Event)archivoTimeML.obtenerConsumidorDeTextoEn(indiceAbsolutoFramenet+unFrame.getTarget().getStart(), indiceAbsolutoFramenet+unFrame.getTarget().getEnd()+1)).getClase().toString();
							System.out.println(resultt);
							}
						else
							resultt+=" NADA";
//						System.out.println(resultt);
						for(Label unLabel:unFrame.getListaFE()){
							String result="FE: ";
							result+=unLabel.getName();
							result+=" Frame: "+unLabel.getFramePadre().getFrameName();
							result+=" Texto:"+archivoTimeML.getTextoPlano().substring(indiceAbsolutoFramenet+unLabel.getStart(), indiceAbsolutoFramenet+unLabel.getEnd()+1);
							
							ConsumidorTexto consumidorTimeml=archivoTimeML.obtenerConsumidorDeTextoEn(indiceAbsolutoFramenet+unLabel.getStart(), indiceAbsolutoFramenet+unLabel.getEnd()+1);
							if((consumidorTimeml!=null)&&(consumidorTimeml instanceof Event))
								{
								result+=" "+((Event)archivoTimeML.obtenerConsumidorDeTextoEn(indiceAbsolutoFramenet+unLabel.getStart(), indiceAbsolutoFramenet+unLabel.getEnd()+1)).getClase().toString();
								System.out.println(result);
								}
							else
								result+=" NADA";
//							System.out.println(result);
						}
					}
//					indiceAbsolutoFramenet+=oracion.getTexto().length();
				}
			}
		}
	}

	/**
	 * Dada una oracion de framenet, devuelve el indice en el archivo TimeML. Considera algunas conversiones de formato y le saca el f=punto al final
	 * @param oracion
	 * @return
	 */
	private int obtenerIndiceDeOracionAbsoluto(String oracion) {
//		 Pattern pattern =    Pattern.compile(oracion.replaceAll(",","\\.{0,1}").replaceAll("\\s","\\.{0,1}"));
//		 Matcher matcher =         pattern.matcher(archivoTimeML.getTextoPlano());
//		 if(matcher.find())return matcher.start();
//		 else return -1;
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
					if(obtenerIndiceDeOracionAbsoluto(oracion.getTexto())==-1)return false;
				}
			}
		}
		
		return true;
		
	}

}
