package transformador;
import javax.xml.transform.TransformerException;


public class Principal2 {

	/**
	 * @param args
	 * @throws TransformerException 
	 */
	public static void main(String[] args) throws TransformerException {
//		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet("prueba", "/home/miguel/Escritorio/miguel/Documentos/PruebaTimeML4/eisenRawText.txt.out");
//		ArrayList<Documento> docs =archivFrame.getListaDocumentos();
//		ArrayList<Parrafo> parrafos=docs.get(0).getListaParrafos();
//		ArrayList<Oracion> oraciones=parrafos.get(0).getListaOraciones();
		
		ArchivoFormatoSalsa archivoSalsa2 = new ArchivoFormatoSalsa();

		archivoSalsa2.guardarAArchivo("/home/miguel/Documentos/PruebaConversorSalsaMejorado/base.xml");
	}

}
