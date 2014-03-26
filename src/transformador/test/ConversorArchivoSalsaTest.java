package transformador.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.junit.Assert;
import org.junit.Test;

import transformador.ConversorArchivoSalsa;
import transformador.formatoFramenet.ArchivoFormatoFramenet;


public class ConversorArchivoSalsaTest {
	
	@Test
	public void testTransformarEnSalsa() throws IOException{
		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet("src/transformador/test/ike2.framenet.xml");
		
		try {
			(new ConversorArchivoSalsa(archivFrame)).obtenerEnformatoSalsa().guardarAArchivo("src/transformador/test/archivoEnFormatoSalsa.xml");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		compararArchivos("src/transformador/test/ike2-OK.salsa.xml", "src/transformador/test/archivoEnFormatoSalsa.xml");
		
		new File("src/transformador/test/archivoEnFormatoSalsa.xml").deleteOnExit();
	}

	private void compararArchivos(String esperado, String obtenido) throws IOException {
		List<String> lineasEsperadas=Files.readAllLines(Paths.get(esperado),Charset.defaultCharset());
		List<String> lineasObtenidas=Files.readAllLines(Paths.get(obtenido),Charset.defaultCharset());
		
		Assert.assertEquals(lineasEsperadas.size(),lineasObtenidas.size());
		for(int i=0;i<lineasEsperadas.size();i++)
				Assert.assertEquals(lineasEsperadas.get(0), lineasObtenidas.get(0));
	}
}
