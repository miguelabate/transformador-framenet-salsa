package transformador.generadorFeatures.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import transformador.formatoDefinicionFrame.DefinicionFramenet;
import transformador.formatoFramenet.ArchivoFormatoFramenet;
import transformador.formatoTimeML.ArchivoTimeML;
import transformador.generadorFeatures.ArchivosInconsistentesGeneradorFeaturesException;
import transformador.generadorFeatures.events.GeneradorFeatures;
import transformador.generadorFeatures.events.GeneradorFeaturesTabBasico;
import transformador.generadorFeatures.links.GeneradorFeaturesParaLinks;

public class GeneradorFeaturesParaLinksTest {

	@Test
	public void testGeneracionFeatures() throws ArchivosInconsistentesGeneradorFeaturesException, IOException {
		ArchivoTimeML unArchTimeMl=new ArchivoTimeML("src/transformador/generadorFeatures/test/ABC19980108.1830.0711.tml");
		ArchivoFormatoFramenet unArchFormatoFramenet = new ArchivoFormatoFramenet("src/transformador/generadorFeatures/test/ABC19980108.1830.0711.tml.txt.out");
		GeneradorFeaturesParaLinks gf = new GeneradorFeaturesParaLinks(unArchTimeMl, unArchFormatoFramenet,new DefinicionFramenet());
		gf.generarFeatures();
		
	}

}
