package transformador.app;
import javax.xml.transform.TransformerException;

import transformador.ConversorArchivoSalsa;
import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoFramenet.Utils;


public class PrincipalConversorFramenetSalsa {

	/**
	 * @param args
	 * @throws TransformerException 
	 */
	public static void main(String[] args) throws TransformerException {
		String entradaFramenet=args[0];
		String salidaSalsa=args[1];
		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet(entradaFramenet);
		
		try {
			(new ConversorArchivoSalsa(archivFrame)).obtenerEnformatoSalsa().guardarAArchivo(salidaSalsa);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
