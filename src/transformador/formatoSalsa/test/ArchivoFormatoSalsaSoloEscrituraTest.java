package transformador.formatoSalsa.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.junit.Assert;
import org.junit.Test;

import transformador.formatoSalsa.ArchivoFormatoSalsaSoloEscritura;

public class ArchivoFormatoSalsaSoloEscrituraTest {

	@Test
	public void test() throws TransformerException, IOException {
		ArchivoFormatoSalsaSoloEscritura archivoSalsa = new ArchivoFormatoSalsaSoloEscritura();
		archivoSalsa.guardarAArchivo("src/transformador/formatoSalsa/test/salidaBase.salsa.xml");
		compararArchivos("src/transformador/formatoSalsa/baseArchivoSalsa.xml","src/transformador/formatoSalsa/test/salidaBase.salsa.xml");
		new File("src/transformador/formatoSalsa/test/salidaBase.salsa.xml").deleteOnExit();
	}

	private void compararArchivos(String esperado, String obtenido) throws IOException {
		List<String> lineasEsperadas=Files.readAllLines(Paths.get(esperado),Charset.defaultCharset());
		List<String> lineasObtenidas=Files.readAllLines(Paths.get(obtenido),Charset.defaultCharset());
		
		Assert.assertEquals(lineasEsperadas.size(),lineasObtenidas.size());
		for(int i=0;i<lineasEsperadas.size();i++)
				Assert.assertEquals(lineasEsperadas.get(0), lineasObtenidas.get(0));
	}
}
