package transformador.formatoDefinicionFrame.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import transformador.formatoDefinicionFrame.DefinicionFrameElement;
import transformador.formatoDefinicionFrame.DefinicionFramenet;

public class DefinicionFramenetTest {

	@Test
	public void cargaIncialTest() {
		DefinicionFramenet definicionFramenet = new DefinicionFramenet();
		Assert.assertEquals(1019, definicionFramenet.getCantidadDeFrames().intValue());
		Assert.assertEquals(9633, definicionFramenet.getCantidadDeFrameElements().intValue());
		
		ArrayList<DefinicionFrameElement> FEs =definicionFramenet.getFrame("Event").getFrameElements();
		Assert.assertEquals("Event", FEs.get(0).getName());
		Assert.assertEquals("Place", FEs.get(1).getName());
		Assert.assertEquals("Reason", FEs.get(2).getName());
		Assert.assertEquals("Time", FEs.get(3).getName());
		Assert.assertEquals("Duration", FEs.get(4).getName());
		Assert.assertEquals("Manner", FEs.get(5).getName());
		Assert.assertEquals("Frequency", FEs.get(6).getName());
		
		Assert.assertEquals("Core-Unexpressed", FEs.get(0).getCoreType());
		Assert.assertEquals("Core", FEs.get(1).getCoreType());
		Assert.assertEquals("Extra-Thematic", FEs.get(2).getCoreType());
		Assert.assertEquals("Core", FEs.get(3).getCoreType());
		Assert.assertEquals("Extra-Thematic", FEs.get(4).getCoreType());
		Assert.assertEquals("Peripheral", FEs.get(5).getCoreType());
		Assert.assertEquals("Extra-Thematic", FEs.get(6).getCoreType());
		
		Assert.assertEquals(19,definicionFramenet.getFramesHijos("Event").size());
		Assert.assertEquals(331,definicionFramenet.getFramesHijosTotal("Event").size());
		Assert.assertEquals(35,definicionFramenet.getFramesHijosTotal("State").size());
		
//		System.out.println(definicionFramenet.findFrameElementsLike("Time"));
//		System.out.println(definicionFramenet.getFramesPreceden("Being_awake"));
//		System.out.println(definicionFramenet.getFramesPreceden("Getting_up"));
		System.out.println(definicionFramenet.getRelacionesPrecedes().size());
	}

}
