package transformador.generadorFeatures.events;

import java.util.Iterator;

import transformador.formatoDefinicionFrame.DefinicionFrame;
import transformador.formatoDefinicionFrame.DefinicionFramenet;
import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoFramenet.Frame;
import transformador.formatoFramenet.Label;
import transformador.formatoTimeML.ArchivoTimeML;
import transformador.formatoTimeML.ConsumidorTexto;
import transformador.formatoTimeML.Event;
import transformador.generadorFeatures.ArchivosInconsistentesGeneradorFeaturesException;

public class GeneradorFeaturesCRF extends GeneradorFeatures{

	public GeneradorFeaturesCRF(ArchivoTimeML archivoTimeML,
			ArchivoFormatoFramenet archivoFramenet,
			DefinicionFramenet defFramenet)
			throws ArchivosInconsistentesGeneradorFeaturesException {
		super(archivoTimeML, archivoFramenet, defFramenet);
	}

	public GeneradorFeaturesCRF(ArchivoTimeML archivoTimeML2,
			ArchivoFormatoFramenet archFrameNet,
			DefinicionFramenet defFramenet2, String pathArchivoSalida)
			throws ArchivosInconsistentesGeneradorFeaturesException {
		super(archivoTimeML2, archFrameNet, defFramenet2, pathArchivoSalida);
	}

	protected String generarSetDeFeatures(Frame unFrame, Label label,
			Integer indiceAbsolutoFramenet) {
		String resultt="";
		ConsumidorTexto consumidorTimemlt=archivoTimeML.obtenerConsumidorDeTextoEn(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1);
		if((consumidorTimemlt!=null)&&(consumidorTimemlt instanceof Event))//Es un EVENT
			{
			resultt+="EVENT";
			Event evento=((Event)archivoTimeML.obtenerConsumidorDeTextoEn(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1));
			eventosDetectadosFramenet++;
			}
		else
			{//NO ES un EVENT
			resultt+="NO_EVENT";
			}
		
		
		resultt+="\ttipoFrame="+label.getName();//tipo de frame element
		resultt+="\tnombreFrame="+unFrame.getFrameName();//nombre del frame al que pertenece
		//frame padre
		Iterator<DefinicionFrame> it=this.defFramenet.getFramesPadres(unFrame.getFrameName()).iterator();
		if(it.hasNext()){
			DefinicionFrame fPadre=it.next();
			resultt+="\tpadre="+fPadre.getName();
			Iterator<DefinicionFrame> it2=this.defFramenet.getFramesPadres(fPadre.getName()).iterator();
			if(it2.hasNext()){
				DefinicionFrame fPadre2=it2.next();
				resultt+="\tpadrepadre="+fPadre2.getName();
			}
			else resultt+="\tpadrepadre=NO_PADRE";
		}
		else resultt+="\tpadre=NO_PADRE\tpadrepadre=NO_PADRE";

		//es hijo del frame Event
		if(framesHijosDeEvent.contains(unFrame.getFrameName()))resultt+="\tesHijoDeEvent=SI";
		else resultt+="\tesHijoDeEvent=NO";
		
		//texto al que hace referencia
//		resultt+="\t"+archivoTimeML.getTextoPlano().substring(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1);
	
		//cantidad de  palabras
		resultt+="\tcantidadPalabras="+archivoTimeML.getTextoPlano().substring(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1).split(" ").length;
		
		//cantidad de FE que engloban a este label(FE)
		resultt+="\tcantidadFramesQueEngloban="+label.obtenerLabelsQueEngloban().size();
				
		
		
		return resultt;
	}
}
