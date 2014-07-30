package transformador.generadorFeatures.events;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import transformador.common.ClaveDeReferenciable;
import transformador.formatoDefinicionFrame.DefinicionFrame;
import transformador.formatoDefinicionFrame.DefinicionFramenet;
import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoFramenet.Frame;
import transformador.formatoFramenet.Label;
import transformador.formatoFramenet.Oracion;
import transformador.formatoTimeML.ArchivoTimeML;
import transformador.formatoTimeML.ConsumidorTexto;
import transformador.formatoTimeML.Event;
import transformador.generadorFeatures.ArchivosInconsistentesGeneradorFeaturesException;

public class GeneradorFeaturesCRFPorPalabra extends GeneradorFeaturesPorPalabra{

	public GeneradorFeaturesCRFPorPalabra(ArchivoTimeML archivoTimeML,
			ArchivoFormatoFramenet archivoFramenet,
			DefinicionFramenet defFramenet)
			throws ArchivosInconsistentesGeneradorFeaturesException {
		super(archivoTimeML, archivoFramenet, defFramenet);
	}

	public GeneradorFeaturesCRFPorPalabra(ArchivoTimeML archivoTimeML2,
			ArchivoFormatoFramenet archFrameNet,
			DefinicionFramenet defFramenet2, String pathArchivoSalida,float porcentajeATest)
			throws ArchivosInconsistentesGeneradorFeaturesException {
		super(archivoTimeML2, archFrameNet, defFramenet2, pathArchivoSalida,porcentajeATest);
	}

	protected String generarSetDeFeatures(int numeroPalabra, Integer indicePalabraDentroOracion,
			Oracion oracion, Integer indiceAbsolutoFramenet) {
		String resultado="";
		String[] oracionPartida=oracion.getTexto().split(" ");
		String	unaPalabra=oracionPartida[numeroPalabra];
		HashMap<String, String> mapaFeatures = new HashMap<String, String>();
		
		//Atributo CLASE
		ConsumidorTexto consumidorTimemlt=archivoTimeML.obtenerConsumidorDeTextoFlexibleEn(indiceAbsolutoFramenet+indicePalabraDentroOracion, indiceAbsolutoFramenet+indicePalabraDentroOracion+unaPalabra.length());
		if((consumidorTimemlt!=null)&&(consumidorTimemlt instanceof Event))//Es un EVENT
			{
			if(consumidorTimemlt.getStart().equals(indiceAbsolutoFramenet+indicePalabraDentroOracion))
				{
				resultado+="B_EVENT";
				eventosDetectadosFramenet++;
				}
			else
				resultado+="I_EVENT";
			
			}
		else
			{//NO ES un EVENT
			resultado+="O_EVENT";
			}
		
		//FEATURES
		generarFeaturesIndexados(indicePalabraDentroOracion,0, oracion, mapaFeatures,
				unaPalabra);
		if(numeroPalabra+1<oracionPartida.length){
			generarFeaturesIndexados(indicePalabraDentroOracion+oracionPartida[numeroPalabra].length()+1,1, oracion, mapaFeatures,
				oracionPartida[numeroPalabra+1]);
		}
		if(numeroPalabra+2<oracionPartida.length){
			generarFeaturesIndexados(indicePalabraDentroOracion+oracionPartida[numeroPalabra].length()+oracionPartida[numeroPalabra+1].length()+2,2, oracion, mapaFeatures,
				oracionPartida[numeroPalabra+2]);
		}
		if(numeroPalabra-1>=0){
			generarFeaturesIndexados(indicePalabraDentroOracion-oracionPartida[numeroPalabra].length()-1,-1, oracion, mapaFeatures,
					oracionPartida[numeroPalabra-1]);
		}
		if(numeroPalabra-2>=0){
			 generarFeaturesIndexados(indicePalabraDentroOracion-oracionPartida[numeroPalabra].length()-oracionPartida[numeroPalabra-1].length()-2,-2, oracion, mapaFeatures,
				oracionPartida[numeroPalabra-2]);
		}
		
		//Recorro el mapa y genero los features
		for(String nombreFeature:mapaFeatures.keySet()){
			resultado+="\t"+nombreFeature+"="+mapaFeatures.get(nombreFeature);
		}
		
		resultado+=generarMezclaFeatures(mapaFeatures, "F", "-2", "-1");
		resultado+=generarMezclaFeatures(mapaFeatures, "F", "-1", "0");
		resultado+=generarMezclaFeatures(mapaFeatures, "F", "0", "1");
		resultado+=generarMezclaFeatures(mapaFeatures, "F", "1", "2");
		
		resultado+=generarMezclaFeatures(mapaFeatures, "FE", "-2", "-1");
		resultado+=generarMezclaFeatures(mapaFeatures, "FE", "-1", "0");
		resultado+=generarMezclaFeatures(mapaFeatures, "FE", "0", "1");
		resultado+=generarMezclaFeatures(mapaFeatures, "FE", "1", "2");
		
		resultado+=generarMezclaFeatures(mapaFeatures, "F", "-2", "-1","0");
		resultado+=generarMezclaFeatures(mapaFeatures, "F", "-1", "0","1");
		resultado+=generarMezclaFeatures(mapaFeatures, "F", "0", "1","2");
		
		resultado+=generarMezclaFeatures(mapaFeatures, "FE", "-2", "-1","0");
		resultado+=generarMezclaFeatures(mapaFeatures, "FE", "-1", "0","1");
		resultado+=generarMezclaFeatures(mapaFeatures, "FE", "0", "1","2");
		
		if(numeroPalabra==0) resultado += "\t__BOS__";
		if(numeroPalabra==oracionPartida.length-1) resultado += "\t__EOS__";
		
		//frame padre
//		Iterator<DefinicionFrame> it=this.defFramenet.getFramesPadres(unFrame.getFrameName()).iterator();
//		if(it.hasNext()){
//			DefinicionFrame fPadre=it.next();
//			resultt+="\tpadre="+fPadre.getName();
//			Iterator<DefinicionFrame> it2=this.defFramenet.getFramesPadres(fPadre.getName()).iterator();
//			if(it2.hasNext()){
//				DefinicionFrame fPadre2=it2.next();
//				resultt+="\tpadrepadre="+fPadre2.getName();
//			}
//			else resultt+="\tpadrepadre=NO_PADRE";
//		}
//		else resultt+="\tpadre=NO_PADRE\tpadrepadre=NO_PADRE";

		//es hijo del frame Event
//		if(framesHijosDeEvent.contains(unFrame.getFrameName()))resultt+="\tesHijoDeEvent=SI";
//		else resultt+="\tesHijoDeEvent=NO";
		
		
	
		//cantidad de  palabras
//		resultt+="\tcantidadPalabras="+archivoTimeML.getTextoPlano().substring(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1).split(" ").length;
		
		//cantidad de FE que engloban a este label(FE)
//		resultt+="\tcantidadFramesQueEngloban="+label.obtenerLabelsQueEngloban().size();
				
		
		
		return resultado;
	}

	private String generarMezclaFeatures(HashMap<String, String> mapaFeatures, String nombreFeature,String primerIdx,String segundoIdx){
		String result="";
		if((mapaFeatures.get(""+nombreFeature+"["+primerIdx+"]")!=null)&&(mapaFeatures.get(""+nombreFeature+"["+segundoIdx+"]")!=null))
			result+="\t"+nombreFeature+"["+primerIdx+"]|"+nombreFeature+"["+segundoIdx+"]="+mapaFeatures.get(""+nombreFeature+"["+primerIdx+"]")+"|"+mapaFeatures.get(""+nombreFeature+"["+segundoIdx+"]");
		return result;
	}
	
	private String generarMezclaFeatures(HashMap<String, String> mapaFeatures, String nombreFeature,String primerIdx,String segundoIdx,String tercerIdx){
		String result="";
		if((mapaFeatures.get(""+nombreFeature+"["+primerIdx+"]")!=null)&&(mapaFeatures.get(""+nombreFeature+"["+segundoIdx+"]")!=null)&&(mapaFeatures.get(""+nombreFeature+"["+tercerIdx+"]")!=null))
			result+="\t"+nombreFeature+"["+primerIdx+"]|"+nombreFeature+"["+segundoIdx+"]|"+nombreFeature+"["+tercerIdx+"]="+mapaFeatures.get(""+nombreFeature+"["+primerIdx+"]")+"|"+mapaFeatures.get(""+nombreFeature+"["+segundoIdx+"]")+"|"+mapaFeatures.get(""+nombreFeature+"["+tercerIdx+"]");
		return result;
	}
	/**FE:tipo frame elemenent (Target,etc)
	 * F:nombre frame
	 * FP: frame padre
	 * FPP:frame padre padre
	 * cEng: cantidad de labels que lo engloban
	 * 
	 * Genera features segun el indice.
	 * @param indicePalabradentroOracion
	 * @param oracion
	 * @param resultado
	 * @param unaPalabra
	 * @return
	 */
	private void generarFeaturesIndexados(Integer indicePalabradentroOracion,Integer offset,
			Oracion oracion, HashMap<String, String> mapaFeatures, String unaPalabra) {
		Collection<Label> labels=oracion.obtenerLabelsFlexibleEn(indicePalabradentroOracion, indicePalabradentroOracion+unaPalabra.length());
		
		
		Iterator it=labels.iterator();
		if(it.hasNext())
			{
				Label unLabel=(Label)it.next();
//				resultt+="\ttipoFrameElement["+offset+"]="+unLabel.getName();//tipo de frame element
//				resultt+="\tnombreFrame["+offset+"]="+unLabel.getFramePadre().getFrameName();//nombre del frame al que pertenece
				mapaFeatures.put("FE["+offset+"]", unLabel.getName());
				mapaFeatures.put("F["+offset+"]", unLabel.getFramePadre().getFrameName());
				//frame padre
				Iterator<DefinicionFrame> it2=this.defFramenet.getFramesPadres(unLabel.getFramePadre().getFrameName()).iterator();
				if(it2.hasNext()){
					DefinicionFrame fPadre=it2.next();
//					resultt+="\tpadre["+offset+"]="+fPadre.getName();
					mapaFeatures.put("FP["+offset+"]", fPadre.getName());
					Iterator<DefinicionFrame> it3=this.defFramenet.getFramesPadres(fPadre.getName()).iterator();
					if(it3.hasNext()){
						DefinicionFrame fPadre2=it3.next();
//						resultt+="\tpadrepadre["+offset+"]="+fPadre2.getName();
						mapaFeatures.put("FPP["+offset+"]", fPadre2.getName());
					}
					else {
//						resultt+="\tpadrepadre["+offset+"]=NO_PADRE";
						mapaFeatures.put("FPP["+offset+"]", "NO_PADRE");
					}
					
				}
				else {
//					resultt+="\tpadre["+offset+"]=NO_PADRE\tpadrepadre["+offset+"]=NO_PADRE";
					mapaFeatures.put("FP["+offset+"]", "NO_PADRE");
					mapaFeatures.put("FPP["+offset+"]", "NO_PADRE");
				}
				
				//cantidad de FE que engloban a este label(FE)
//				resultt+="\tcantidadFramesQueEngloban="+unLabel.obtenerLabelsQueEngloban().size();
				mapaFeatures.put("cEng["+offset+"]", String.valueOf(unLabel.obtenerLabelsQueEngloban().size()));
			}
		else
			{
//				resultt+="\ttipoFrameElement["+offset+"]=NO_FRAME_ELEMENT";
//				resultt+="\tnombreFrame["+offset+"]=NO_FRAME";
				mapaFeatures.put("FE["+offset+"]", "NO_FRAME_ELEMENT");
				mapaFeatures.put("F["+offset+"]", "NO_FRAME");
			}
		
		
//		mapaFeatures.put("palabra["+offset+"]", unaPalabra);

		
		//texto al que hace referencia
//		resultt+="\tpalabra["+offset+"]="+unaPalabra;
	}
}
