package transformador.formatoFramenet.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoFramenet.Documento;
import transformador.formatoFramenet.Frame;
import transformador.formatoFramenet.Label;
import transformador.formatoFramenet.Oracion;
import transformador.formatoFramenet.Parrafo;


public class ArchivoFormatoFramenetTest {

	@Test
	public void testLevantarArchivoTipoframenet(){
		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet("src/transformador/formatoFramenet/test/ike2.framenet.xml");
		ArrayList<Documento> docs =archivFrame.getListaDocumentos();
		ArrayList<Parrafo> parrafos=docs.get(0).getListaParrafos();
		ArrayList<Oracion> oraciones=parrafos.get(0).getListaOraciones();
		ArrayList<Frame> listaFrames= oraciones.get(0).getListaDeFramesAnotados();
		
		Assert.assertEquals(9, listaFrames.size());
		
	}
	
	@Test
	public void testOracionCreacionTablaReferenciables(){
		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet("src/transformador/formatoFramenet/test/ike2.framenet.xml");
		ArrayList<Documento> docs =archivFrame.getListaDocumentos();
		ArrayList<Parrafo> parrafos=docs.get(0).getListaParrafos();
		ArrayList<Oracion> oraciones=parrafos.get(0).getListaOraciones();
		
		Oracion primerOracion=oraciones.get(0);
		List<Label> labels=primerOracion.obtenerLabelsEn(0, 4);
		for(Label unLabel:labels){
			unLabel.getName();
			unLabel.getFramePadre().getFrameName();
		}
	}
}
