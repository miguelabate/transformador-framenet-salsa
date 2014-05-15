package transformador.generadorFeatures;

import java.util.Collection;
import java.util.Iterator;

import transformador.ClaveDeReferenciable;
import transformador.formatoDefinicionFrame.DefinicionFrame;
import transformador.formatoDefinicionFrame.DefinicionFramenet;
import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoFramenet.Frame;
import transformador.formatoFramenet.Label;
import transformador.formatoFramenet.Oracion;
import transformador.formatoTimeML.ArchivoTimeML;
import transformador.formatoTimeML.ConsumidorTexto;
import transformador.formatoTimeML.Event;

public class GeneradorFeaturesCRFPorPalabra extends GeneradorFeaturesPorPalabra{

	public GeneradorFeaturesCRFPorPalabra(ArchivoTimeML archivoTimeML,
			ArchivoFormatoFramenet archivoFramenet,
			DefinicionFramenet defFramenet)
			throws ArchivosInconsistentesGeneradorFeaturesException {
		super(archivoTimeML, archivoFramenet, defFramenet);
	}

	public GeneradorFeaturesCRFPorPalabra(ArchivoTimeML archivoTimeML2,
			ArchivoFormatoFramenet archFrameNet,
			DefinicionFramenet defFramenet2, String pathArchivoSalida)
			throws ArchivosInconsistentesGeneradorFeaturesException {
		super(archivoTimeML2, archFrameNet, defFramenet2, pathArchivoSalida);
	}

	protected String generarSetDeFeatures(String unaPalabra, Integer indicePalabra,
			Oracion oracion, Integer indiceAbsolutoFramenet) {
		String resultt="";
		ConsumidorTexto consumidorTimemlt=archivoTimeML.obtenerConsumidorDeTextoFlexibleEn(indiceAbsolutoFramenet+indicePalabra, indiceAbsolutoFramenet+indicePalabra+unaPalabra.length());
		if((consumidorTimemlt!=null)&&(consumidorTimemlt instanceof Event))//Es un EVENT
			{
			Event evento=((Event)consumidorTimemlt);
			if(consumidorTimemlt.getStart().equals(indiceAbsolutoFramenet+indicePalabra))
				resultt+="B_EVENT";
			else
				resultt+="I_EVENT";
			eventosDetectadosFramenet++;
			}
		else
			{//NO ES un EVENT
			resultt+="O_EVENT";
			}
		
		
//		resultt+="\ttipoFrame="+label.getName();//tipo de frame element
//		resultt+="\tnombreFrame="+unFrame.getFrameName();//nombre del frame al que pertenece
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
		
		//texto al que hace referencia
		resultt+="\tpalabra="+unaPalabra;
	
		//cantidad de  palabras
//		resultt+="\tcantidadPalabras="+archivoTimeML.getTextoPlano().substring(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1).split(" ").length;
		
		//cantidad de FE que engloban a este label(FE)
//		resultt+="\tcantidadFramesQueEngloban="+label.obtenerLabelsQueEngloban().size();
				
		
		
		return resultt;
	}
}
