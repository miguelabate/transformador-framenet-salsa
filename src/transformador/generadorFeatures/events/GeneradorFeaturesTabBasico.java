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

public class GeneradorFeaturesTabBasico extends GeneradorFeatures{

	public GeneradorFeaturesTabBasico(ArchivoTimeML archivoTimeML,
			ArchivoFormatoFramenet archivoFramenet,
			DefinicionFramenet defFramenet)
			throws ArchivosInconsistentesGeneradorFeaturesException {
		super(archivoTimeML, archivoFramenet, defFramenet);
	}

	public GeneradorFeaturesTabBasico(ArchivoTimeML archivoTimeML2,
			ArchivoFormatoFramenet archFrameNet,
			DefinicionFramenet defFramenet2, String pathArchivoSalida)
			throws ArchivosInconsistentesGeneradorFeaturesException {
		super(archivoTimeML2, archFrameNet, defFramenet2, pathArchivoSalida);
	}

	protected String generarSetDeFeatures(Frame unFrame, Label label,
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

		//es hijo del frame Event
		if(framesHijosDeEvent.contains(unFrame.getFrameName()))resultt+="\tSI";
		else resultt+="\tNO";
		
		//texto al que hace referencia
		resultt+="\t"+archivoTimeML.getTextoPlano().substring(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1);
	
		//cantidad de  palabras
		resultt+="\t"+archivoTimeML.getTextoPlano().substring(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1).split(" ").length;
		
		//cantidad de FE que engloban a este label(FE)
		resultt+="\t"+label.obtenerLabelsQueEngloban().size();
				
		ConsumidorTexto consumidorTimemlt=archivoTimeML.obtenerConsumidorDeTextoEn(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1);
		if((consumidorTimemlt!=null)&&(consumidorTimemlt instanceof Event))//Es un EVENT
			{
			resultt+="\tEVENT";
			Event evento=((Event)archivoTimeML.obtenerConsumidorDeTextoEn(indiceAbsolutoFramenet+label.getStart(), indiceAbsolutoFramenet+label.getEnd()+1));
			resultt+="\t"+evento.getClase().toString();
			resultt+="\t"+evento.getEid();
			eventosDetectadosFramenet++;
			}
		else
			{//NO ES un EVENT
			resultt+="\tNO_EVENT";
			resultt+="\tNO_CLASE_EVENT";
			resultt+="\tNO_EVENT_ID";
			}
		
		return resultt;
	}
}
