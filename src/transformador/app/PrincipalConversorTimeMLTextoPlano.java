package transformador.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

import transformador.formatoTimeML.ArchivoTimeML;

public class PrincipalConversorTimeMLTextoPlano {

	public static void main(String[] args) {
		String outputDir=args[1];
		File directorioTimeML=new File(args[0]);
		File[] listarchivosAnotadosTimeML = directorioTimeML.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("tml");
			}
		});

		for(File archivoTimeML:listarchivosAnotadosTimeML){
			try {
				new ArchivoTimeML(archivoTimeML.getAbsolutePath()).guardarComoArchivoDeTextoPlano(outputDir+archivoTimeML.getName()+".txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
