package transformador.generadorFeatures.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import transformador.formatoDefinicionFrame.DefinicionFramenet;
import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoTimeML.ArchivoTimeML;
import transformador.generadorFeatures.ArchivosInconsistentesGeneradorFeaturesException;
import transformador.generadorFeatures.events.GeneradorFeatures;
import transformador.generadorFeatures.events.GeneradorFeaturesCRFPorPalabra;
import transformador.generadorFeatures.events.GeneradorFeaturesPorPalabra;
import transformador.generadorFeatures.events.GeneradorFeaturesTabBasico;

public class GeneradorFeaturesTest {

	@Test
	public void testGeneracionFeatures() throws ArchivosInconsistentesGeneradorFeaturesException, IOException {
		ArchivoTimeML unArchTimeMl=new ArchivoTimeML("src/transformador/generadorFeatures/test/ABC19980108.1830.0711.tml");
		ArchivoFormatoFramenet unArchFormatoFramenet = new ArchivoFormatoFramenet("src/transformador/generadorFeatures/test/ABC19980108.1830.0711.tml.txt.out");
		GeneradorFeatures gf = new GeneradorFeaturesTabBasico(unArchTimeMl, unArchFormatoFramenet,new DefinicionFramenet());
		gf.generarFeatures();
		
	}

	@Test
	public void testGeneracionFeaturesOtroArchivo() throws ArchivosInconsistentesGeneradorFeaturesException, IOException {
		ArchivoTimeML unArchTimeMl=new ArchivoTimeML("src/transformador/generadorFeatures/test/ABC19980120.1830.0957.tml");
		ArchivoFormatoFramenet unArchFormatoFramenet = new ArchivoFormatoFramenet("src/transformador/generadorFeatures/test/ABC19980120.1830.0957.tml.txt.out");
		GeneradorFeatures gf = new GeneradorFeaturesTabBasico(unArchTimeMl, unArchFormatoFramenet,new DefinicionFramenet());
		gf.generarFeatures();
		
	}
	
	@Test
	public void testConsistencia() throws ArchivosInconsistentesGeneradorFeaturesException {
		ArchivoTimeML unArchTimeMl=new ArchivoTimeML("src/transformador/generadorFeatures/test/ABC19980108.1830.0711.tml");
		ArchivoFormatoFramenet unArchFormatoFramenet = new ArchivoFormatoFramenet("src/transformador/generadorFeatures/test/ABC19980108.1830.0711.tml.txt.out");
		GeneradorFeatures gf = new GeneradorFeaturesTabBasico(unArchTimeMl, unArchFormatoFramenet,new DefinicionFramenet());
		Assert.assertEquals(true,gf.isArchivosConsistentes());
		
	}
	
	@Test
	public void testConsistencia2() throws ArchivosInconsistentesGeneradorFeaturesException {
		ArchivoTimeML unArchTimeMl=new ArchivoTimeML("src/transformador/generadorFeatures/test/wsj_0173.tml");
		ArchivoFormatoFramenet unArchFormatoFramenet = new ArchivoFormatoFramenet("src/transformador/generadorFeatures/test/wsj_0173.tml.txt.out");
		GeneradorFeatures gf = new GeneradorFeaturesTabBasico(unArchTimeMl, unArchFormatoFramenet,new DefinicionFramenet());
		Assert.assertEquals(true,gf.isArchivosConsistentes());
		
	}
	
	@Test(expected=ArchivosInconsistentesGeneradorFeaturesException.class)
	public void testConsistenciaFallida() throws ArchivosInconsistentesGeneradorFeaturesException {
		ArchivoTimeML unArchTimeMl=new ArchivoTimeML("src/transformador/generadorFeatures/test/AP900815-0044.tml");
		ArchivoFormatoFramenet unArchFormatoFramenet = new ArchivoFormatoFramenet("src/transformador/generadorFeatures/test/ABC19980108.1830.0711.tml.txt.out");
		GeneradorFeatures gf = new GeneradorFeaturesTabBasico(unArchTimeMl, unArchFormatoFramenet,new DefinicionFramenet());
		
	}
	
	@Test
	public void testGeneracionFeaturesPorPalabra() throws ArchivosInconsistentesGeneradorFeaturesException, IOException {
		ArchivoTimeML unArchTimeMl=new ArchivoTimeML("src/transformador/generadorFeatures/test/ABC19980120.1830.0957.tml");
		ArchivoFormatoFramenet unArchFormatoFramenet = new ArchivoFormatoFramenet("src/transformador/generadorFeatures/test/ABC19980120.1830.0957.tml.txt.out");
		GeneradorFeaturesPorPalabra gf = new GeneradorFeaturesCRFPorPalabra(unArchTimeMl, unArchFormatoFramenet,new DefinicionFramenet());
		gf.generarFeatures();
		
	}
	
	@Test
	public void testGeneracionFeaturesPorPalabra2() throws ArchivosInconsistentesGeneradorFeaturesException, IOException {
		ArchivoTimeML unArchTimeMl=new ArchivoTimeML("src/transformador/generadorFeatures/test/wsj_0505.tml");
		ArchivoFormatoFramenet unArchFormatoFramenet = new ArchivoFormatoFramenet("src/transformador/generadorFeatures/test/wsj_0505.tml.txt.out");
		GeneradorFeaturesPorPalabra gf = new GeneradorFeaturesCRFPorPalabra(unArchTimeMl, unArchFormatoFramenet,new DefinicionFramenet());
		gf.generarFeatures();
		
	}
}
