package transformador.test;

import junit.framework.Assert;

import org.junit.Test;

import transformador.OracionSalsa;


public class OracionSalsaTest {

	@Test
	public void testIndices(){
	
		OracionSalsa oracionPrueba=new OracionSalsa("id_oracion", null, null);
//		oracionPrueba.actualizarMapa("Hola", "hola_id");
//		oracionPrueba.actualizarMapa("miguel", "miguel_id");
//		oracionPrueba.actualizarMapa(",", "coma_id");
//		oracionPrueba.actualizarMapa("como", "como_id");
//		oracionPrueba.actualizarMapa("va", "va_id");
//		oracionPrueba.actualizarMapa("?", "?_id");
		
		//Hola miguel , como va ? 
		Assert.assertEquals("miguel_id",oracionPrueba.obtenerRangoIdSegunIndice(1, 7).get(0));
		Assert.assertEquals("va_id",oracionPrueba.obtenerRangoIdSegunIndice(19, 21).get(0));
		Assert.assertEquals("va_id",oracionPrueba.obtenerRangoIdSegunIndice(19, 22).get(0));
		Assert.assertEquals("?_id",oracionPrueba.obtenerRangoIdSegunIndice(19, 22).get(1));
	}
}
