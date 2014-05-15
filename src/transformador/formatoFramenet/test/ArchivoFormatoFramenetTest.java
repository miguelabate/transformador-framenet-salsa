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
	
	@Test
	public void testOracionLabelsQueEngloban(){
		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet("src/transformador/formatoFramenet/test/ike2.framenet.xml");
		ArrayList<Documento> docs =archivFrame.getListaDocumentos();
		ArrayList<Parrafo> parrafos=docs.get(0).getListaParrafos();
		ArrayList<Oracion> oraciones=parrafos.get(0).getListaOraciones();
		
		Oracion primerOracion=oraciones.get(0);
		Label targetBegan=primerOracion.getListaDeFramesAnotados().get(5).getTarget();
		Assert.assertEquals("Landmark_event",targetBegan.obtenerLabelsQueEngloban().get(0).getName());
		Assert.assertEquals("Time",targetBegan.obtenerLabelsQueEngloban().get(1).getName());
		Assert.assertEquals(2, targetBegan.obtenerLabelsQueEngloban().size());
		
		Label targetOffice=primerOracion.getListaDeFramesAnotados().get(3).getTarget();
		Assert.assertEquals("Event",targetOffice.obtenerLabelsQueEngloban().get(0).getName());
		Assert.assertEquals("Building_part",targetOffice.obtenerLabelsQueEngloban().get(1).getName());
		Assert.assertEquals(2, targetOffice.obtenerLabelsQueEngloban().size());
		
		Label buildingSubPartOffice=primerOracion.getListaDeFramesAnotados().get(3).getListaFE().get(0);
		Assert.assertEquals("Event",buildingSubPartOffice.obtenerLabelsQueEngloban().get(0).getName());
		Assert.assertEquals("Target",buildingSubPartOffice.obtenerLabelsQueEngloban().get(1).getName());
		Assert.assertEquals(2, buildingSubPartOffice.obtenerLabelsQueEngloban().size());
		
		Label targetThreatened=primerOracion.getListaDeFramesAnotados().get(2).getTarget();
		Assert.assertEquals(0, targetThreatened.obtenerLabelsQueEngloban().size());
		
		Label unFEdeCommitment=primerOracion.getListaDeFramesAnotados().get(2).getListaFE().get(0);
		Assert.assertEquals(0, unFEdeCommitment.obtenerLabelsQueEngloban().size());
		Label unFEdeCommitment2=primerOracion.getListaDeFramesAnotados().get(2).getListaFE().get(1);
		Assert.assertEquals(0, unFEdeCommitment2.obtenerLabelsQueEngloban().size());
		Label heCommitment=primerOracion.getListaDeFramesAnotados().get(2).getListaFE().get(2);
		Assert.assertEquals("Agent",heCommitment.obtenerLabelsQueEngloban().get(0).getName());
		Assert.assertEquals(1, heCommitment.obtenerLabelsQueEngloban().size());
	}
}
