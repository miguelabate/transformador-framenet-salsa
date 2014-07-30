package transformador.conversorASalsa.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.junit.Assert;
import org.junit.Test;

import transformador.conversorASalsa.ConversorArchivoSalsa;
import transformador.formatoFramenet.ArchivoFormatoFramenet;


public class ConversorArchivoSalsaTest {
	
	private static String PATH="src/transformador/conversorASalsa/test/";
	private static String NOMBRE_ARCHIVO_SALSA_TEMPORAL="archivoEnFormatoSalsa.xml";
	private static String NOMBRE_ARCHIVO_FRAMENET1="ike2.framenet.xml";
	private static String NOMBRE_ARCHIVO_SALSA_PATRON1="ike2-OK.salsa.xml";
	
	private static String NOMBRE_ARCHIVO_FRAMENET2="CNN19980126.1600.1104.tml.txt.out";
	private static String NOMBRE_ARCHIVO_SALSA_PATRON2="CNN19980126.1600.1104.salsa.xml";
	
	private static String NOMBRE_ARCHIVO_FRAMENET3="wsj_0169.tml.txt.out";
	private static String NOMBRE_ARCHIVO_SALSA_PATRON3="wsj_0169.salsa.xml";
	
	@Test
	public void testTransformarEnSalsa1() throws IOException{
		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet(PATH+NOMBRE_ARCHIVO_FRAMENET1);
		
		try {
			(new ConversorArchivoSalsa(archivFrame)).obtenerEnformatoSalsa().guardarAArchivo(PATH+NOMBRE_ARCHIVO_SALSA_TEMPORAL);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		compararArchivos(PATH+NOMBRE_ARCHIVO_SALSA_PATRON1, PATH+NOMBRE_ARCHIVO_SALSA_TEMPORAL);
		
		new File(PATH+NOMBRE_ARCHIVO_SALSA_TEMPORAL).deleteOnExit();
	}

	@Test
	public void testTransformarEnSalsa2() throws IOException{
		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet(PATH+NOMBRE_ARCHIVO_FRAMENET2);
		
		try {
			(new ConversorArchivoSalsa(archivFrame)).obtenerEnformatoSalsa().guardarAArchivo(PATH+NOMBRE_ARCHIVO_SALSA_TEMPORAL);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		compararArchivos(PATH+NOMBRE_ARCHIVO_SALSA_PATRON2, PATH+NOMBRE_ARCHIVO_SALSA_TEMPORAL);
		
		new File(PATH+NOMBRE_ARCHIVO_SALSA_TEMPORAL).deleteOnExit();
	}
	
	@Test
	public void testTransformarEnSalsa3() throws IOException{
		ArchivoFormatoFramenet archivFrame = new ArchivoFormatoFramenet(PATH+NOMBRE_ARCHIVO_FRAMENET3);
		
		try {
			(new ConversorArchivoSalsa(archivFrame)).obtenerEnformatoSalsa().guardarAArchivo(PATH+NOMBRE_ARCHIVO_SALSA_TEMPORAL);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		compararArchivos(PATH+NOMBRE_ARCHIVO_SALSA_PATRON3, PATH+NOMBRE_ARCHIVO_SALSA_TEMPORAL);
		
		new File(PATH+NOMBRE_ARCHIVO_SALSA_TEMPORAL).deleteOnExit();
	}
	private void compararArchivos(String esperado, String obtenido) throws IOException {
		List<String> lineasEsperadas=Files.readAllLines(Paths.get(esperado),Charset.defaultCharset());
		List<String> lineasObtenidas=Files.readAllLines(Paths.get(obtenido),Charset.defaultCharset());
		
		Assert.assertEquals(lineasEsperadas.size(),lineasObtenidas.size());
		for(int i=0;i<lineasEsperadas.size();i++)
				Assert.assertEquals(lineasEsperadas.get(0), lineasObtenidas.get(0));
	}
}
