package transformador.generadorFeatures.test;

import org.junit.Assert;
import org.junit.Test;

import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoTimeML.ArchivoTimeML;
import transformador.generadorFeatures.GeneradorFeatures;

public class GeneradorFeaturesTest {

	@Test
	public void testGeneracionFeatures() {
		ArchivoTimeML unArchTimeMl=new ArchivoTimeML("src/transformador/generadorFeatures/test/ABC19980108.1830.0711.tml");
		ArchivoFormatoFramenet unArchFormatoFramenet = new ArchivoFormatoFramenet("src/transformador/generadorFeatures/test/ABC19980108.1830.0711.tml.txt.out");
		GeneradorFeatures gf = new GeneradorFeatures(unArchTimeMl, unArchFormatoFramenet);
		gf.generarFeatures();
		
	}

	@Test
	public void testConsistencia() {
		ArchivoTimeML unArchTimeMl=new ArchivoTimeML("src/transformador/generadorFeatures/test/ABC19980108.1830.0711.tml");
		ArchivoFormatoFramenet unArchFormatoFramenet = new ArchivoFormatoFramenet("src/transformador/generadorFeatures/test/ABC19980108.1830.0711.tml.txt.out");
		GeneradorFeatures gf = new GeneradorFeatures(unArchTimeMl, unArchFormatoFramenet);
		Assert.assertEquals(true,gf.isArchivosConsistentes());
		
	}
}
