package transformador.formatoFramenet.test;

import org.junit.Assert;
import org.junit.Test;

import transformador.formatoFramenet.ArchivoFormatoFramenetFullText;

public class ArchivoFormatoFramenetFullTextTest {
	
	@Test
	public void testTransformarEnSalsa(){
		ArchivoFormatoFramenetFullText archivFrame = new ArchivoFormatoFramenetFullText("src/transformador/formatoFramenet/test/ANC__EntrepreneurAsMadonna.xml");
		Assert.assertEquals(33,archivFrame.getListaOraciones().size());
		
		Assert.assertEquals(archivFrame.getListaOraciones().get(0).getListaDeFramesAnotados().get(0).getFrameName(),"Age");
		Assert.assertEquals(archivFrame.getListaOraciones().get(31).getListaDeFramesAnotados().get(0).getFrameName(),"Coming_to_believe");
		
		Assert.assertEquals(archivFrame.getListaOraciones().get(0).getListaDeFramesAnotados().get(0).getListaFE().get(0).getName(),"Entity");
		Assert.assertEquals(archivFrame.getListaOraciones().get(0).getListaDeFramesAnotados().get(0).getListaFE().get(1).getName(),"Age");
	}
}
