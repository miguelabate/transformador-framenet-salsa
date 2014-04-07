package transformador.generadorFeatures;

import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoTimeML.ArchivoTimeML;

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
		
	}
	
}
