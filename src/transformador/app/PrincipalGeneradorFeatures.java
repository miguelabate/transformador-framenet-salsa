package transformador.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import transformador.formatoDefinicionFrame.DefinicionFramenet;
import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoTimeML.ArchivoTimeML;
import transformador.generadorFeatures.ArchivosInconsistentesGeneradorFeaturesException;
import transformador.generadorFeatures.events.GeneradorFeatures;
import transformador.generadorFeatures.events.GeneradorFeaturesCRF;

public class PrincipalGeneradorFeatures {

	public static void main(String[] args) throws IOException {
		File dirArchivosTimeML=new File(args[1]);
		File dirArchivosFrameNet=new File(args[0]);
		String extension = args[2];
		String pathArchivoSalida = args[3];
		int totalErr=0,totalOK=0;
		int totalEventosFramenet=0,totalEventosTimeML=0;
		Hashtable<String,String> archivosFramenet=new Hashtable<String, String>();
		for(File archivoFramenet:dirArchivosFrameNet.listFiles()){
			archivosFramenet.put(limpiarNombre(archivoFramenet.getName(),extension), archivoFramenet.getAbsolutePath());
		}
		DefinicionFramenet defFramenet=new DefinicionFramenet();
		for(File archivoTimeML:dirArchivosTimeML.listFiles()){
			ArchivoFormatoFramenet archFrameNet = new ArchivoFormatoFramenet(archivosFramenet.get(archivoTimeML.getName()));
			try {
				GeneradorFeatures gf=new GeneradorFeaturesCRF(new ArchivoTimeML(archivoTimeML.getAbsolutePath()), archFrameNet,defFramenet,pathArchivoSalida);
				gf.generarFeatures();
//				System.out.println("Todo OK");
				totalEventosFramenet+=gf.getEventosDetectadosFramenet();
				totalEventosTimeML+=gf.getEventosTimeML();
				totalOK++;
			} catch (ArchivosInconsistentesGeneradorFeaturesException e) {
				System.out.println(archivoTimeML.getAbsolutePath());
				System.out.println("Error de consistencia");
				totalErr++;
			}
		}
		System.out.println("Errores: "+totalErr+" Ok: "+totalOK);
		System.out.println("Eventos Framenet total: "+totalEventosFramenet+"Eventos TimeML total:  "+totalEventosTimeML);
	}

	private static String limpiarNombre(String name, String extension) {
		return name.substring(0, name.indexOf(extension));
	}

}
