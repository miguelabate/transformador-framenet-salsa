package transformador.test;

import java.util.ArrayList;

import javax.xml.transform.TransformerException;

import junit.framework.Assert;

import org.junit.Test;

import transformador.ConversorArchivoSalsa;
import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoFramenet.Documento;
import transformador.formatoFramenet.Frame;
import transformador.formatoFramenet.Oracion;
import transformador.formatoFramenet.Parrafo;


public class ArchivoFormatoFramenetTest {

	@Test
	public void testLevantarArchivoTipoframenet(){
		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet("src/transformador/test/eisenRawText.txt.out");
		ArrayList<Documento> docs =archivFrame.getListaDocumentos();
		ArrayList<Parrafo> parrafos=docs.get(0).getListaParrafos();
		ArrayList<Oracion> oraciones=parrafos.get(0).getListaOraciones();
		ArrayList<Frame> listaFrames= oraciones.get(0).getListaDeFramesAnotados();
		
		Assert.assertEquals(25, listaFrames.size());
		
	}
	
	@Test
	public void testTransformarEnSalsa(){
		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet("src/transformador/test/eisenRawText.txt.out");
		
		try {
			(new ConversorArchivoSalsa(archivFrame)).obtenerEnformatoSalsa().guardarAArchivo("base.xml");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
