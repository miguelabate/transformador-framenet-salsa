package transformador.common.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import transformador.common.ClaveDeReferenciable;

public class ClaveDeReferenciableTest {

	@Test
	public void testOrden() {
		ClaveDeReferenciable clave1=new ClaveDeReferenciable(0, 1);
		ClaveDeReferenciable clave2=new ClaveDeReferenciable(5, 10);
		ClaveDeReferenciable clave3=new ClaveDeReferenciable(7, 20);
		ClaveDeReferenciable clave4=new ClaveDeReferenciable(9, 10);
		ArrayList<ClaveDeReferenciable> lista=new ArrayList<ClaveDeReferenciable>();
		lista.add(clave3);
		lista.add(clave2);
		lista.add(clave1);
		lista.add(clave4);
		
		Collections.sort(lista);
		assertEquals(clave1,lista.get(0));
		assertEquals(clave2,lista.get(1));
		assertEquals(clave3,lista.get(2));
		assertEquals(clave4,lista.get(3));
	}

}
