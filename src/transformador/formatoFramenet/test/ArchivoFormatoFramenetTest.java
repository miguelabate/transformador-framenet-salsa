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
		Assert.assertEquals(1,labels.size());
		Assert.assertEquals("Target",labels.get(0).getName());
		Assert.assertEquals("Frequency",labels.get(0).getFramePadre().getFrameName());
		
		labels=primerOracion.obtenerLabelsEn(5, 7);
		Assert.assertEquals(null,labels);
		
		labels=primerOracion.obtenerLabelsEn(5, 14);
		Assert.assertEquals(1,labels.size());
		Assert.assertEquals("Event",labels.get(0).getName());
		Assert.assertEquals("Frequency",labels.get(0).getFramePadre().getFrameName());
		
		labels=primerOracion.obtenerLabelsEn(8, 14);
		Assert.assertEquals(2,labels.size());
		Assert.assertEquals("Building_part",labels.get(0).getName());
		Assert.assertEquals("Building_subparts",labels.get(0).getFramePadre().getFrameName());
		Assert.assertEquals("Target",labels.get(1).getName());
		Assert.assertEquals("Building_subparts",labels.get(1).getFramePadre().getFrameName());
		
		labels=primerOracion.obtenerLabelsEn(91, 145);
		Assert.assertEquals(1,labels.size());
		Assert.assertEquals("Message",labels.get(0).getName());
		Assert.assertEquals("Commitment",labels.get(0).getFramePadre().getFrameName());
		//ahora quiero ver quien es el target
		Assert.assertEquals("threatened",primerOracion.getTexto().substring(labels.get(0).getFramePadre().getTarget().getStart(),labels.get(0).getFramePadre().getTarget().getEnd()+1));
	}
}
