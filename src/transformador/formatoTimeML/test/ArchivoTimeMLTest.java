package transformador.formatoTimeML.test;

import org.junit.Assert;
import org.junit.Test;

import transformador.formatoTimeML.ArchivoTimeML;
import transformador.formatoTimeML.Event;
import transformador.formatoTimeML.MakeInstance;
import transformador.formatoTimeML.Signal;
import transformador.formatoTimeML.Timex3;
import transformador.formatoTimeML.types.EventClassType;
import transformador.formatoTimeML.types.MakeInstacePolarityType;
import transformador.formatoTimeML.types.MakeInstanceAspectType;
import transformador.formatoTimeML.types.MakeInstancePosType;
import transformador.formatoTimeML.types.MakeInstanceTenseType;

public class ArchivoTimeMLTest {

	@Test
	public void levantarArchivoTimeMLTest() {
		ArchivoTimeML archTimeMl = new ArchivoTimeML("src/transformador/formatoTimeML/test/ABC19980108.1830.0711.tml");
		chequeosTimex3(archTimeMl);
		chequeosSignal(archTimeMl);//
		chequeosEvent(archTimeMl);
		chequeosMakeInstance(archTimeMl);
		chequeosTLink(archTimeMl);
		chequeosSLink(archTimeMl);
		chequeosALink(archTimeMl);
	}

	private void chequeosALink(ArchivoTimeML archTimeMl) {
		Assert.assertEquals(1,archTimeMl.getAlinkTabla().size());
	}
	
	private void chequeosSLink(ArchivoTimeML archTimeMl) {
		Assert.assertEquals(10,archTimeMl.getSlinkTabla().size());
	}
	
	private void chequeosTLink(ArchivoTimeML archTimeMl) {
		Assert.assertEquals(47,archTimeMl.getTlinkTabla().size());
	}
	
	private void chequeosMakeInstance(ArchivoTimeML archTimeMl) {
		Assert.assertEquals(53,archTimeMl.getMakeinstanceTabla().size());
		MakeInstance mkins = archTimeMl.getMakeinstanceTabla().get("ei392");//<MAKEINSTANCE eventID="e20" eiid="ei392" tense="PAST" aspect="NONE" polarity="POS" pos="VERB"/>
		Assert.assertEquals("e20",mkins.getEventID().getEid());
		Assert.assertEquals(MakeInstanceTenseType.PAST,mkins.getTense());
		Assert.assertEquals(MakeInstanceAspectType.NONE,mkins.getAspect());
		Assert.assertEquals(MakeInstacePolarityType.POS,mkins.getPolarity());
		Assert.assertEquals(MakeInstancePosType.VERB,mkins.getPos());
	}
	
	private void chequeosEvent(ArchivoTimeML archTimeMl) {
		Assert.assertEquals(53,archTimeMl.getEventTabla().size());
		Event event = archTimeMl.getEventTabla().get("e53");
		Assert.assertEquals("talking",event.getContenido());
		Assert.assertEquals(EventClassType.OCCURRENCE,event.getClase());
		
	}
	
	private void chequeosSignal(ArchivoTimeML archTimeMl) {
		Assert.assertEquals(3,archTimeMl.getSignalTabla().size());
		Signal signal = archTimeMl.getSignalTabla().get("s13");
		Assert.assertEquals("before",signal.getContenido());
		
	}

	private void chequeosTimex3(ArchivoTimeML archTimeMl) {
		Assert.assertEquals(11,archTimeMl.getTimex3Tabla().size());
		Timex3 timexQueReferenciaAotro=archTimeMl.getTimex3Tabla().get("t196");
		Assert.assertEquals("t82",timexQueReferenciaAotro.getAnchorTimeID().getTid());
	}
	@Test
	public void reemplazarPuntuacionTest() {
//		Assert.assertEquals("hola , " , ArchivoTimeML.tokenizar("hola, "));
//		Assert.assertEquals("hola . " , ArchivoTimeML.tokenizar("hola."));
//		Assert.assertEquals("Perez S.A. es una empresa . " , ArchivoTimeML.tokenizar("Perez S.A. es una empresa."));
//		Assert.assertEquals("hola : " , ArchivoTimeML.tokenizar("hola: "));
//		Assert.assertEquals("hola ; " , ArchivoTimeML.tokenizar("hola; "));
//		Assert.assertEquals("hola ' " , ArchivoTimeML.tokenizar("hola' "));
	}
	
	@Test
	public void limitesConsumidoresTextoTest(){
		ArchivoTimeML archTimeMl = new ArchivoTimeML("src/transformador/formatoTimeML/test/ABC19980108.1830.0711.tml");
		Event event = archTimeMl.getEventTabla().get("e53");
		Assert.assertEquals("talking",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e34");
		Assert.assertEquals("find",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e371");
		Assert.assertEquals("thirty",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e368");
		Assert.assertEquals("recession",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		
		Timex3 timex= archTimeMl.getTimex3Tabla().get("t98");
		Assert.assertEquals("the past three months",archTimeMl.getTextoPlano().substring(timex.getStart(), timex.getEnd()));
		timex= archTimeMl.getTimex3Tabla().get("t196");
		Assert.assertEquals("Now",archTimeMl.getTextoPlano().substring(timex.getStart(), timex.getEnd()));
		timex= archTimeMl.getTimex3Tabla().get("t90");
		Assert.assertEquals("now",archTimeMl.getTextoPlano().substring(timex.getStart(), timex.getEnd()));
		timex= archTimeMl.getTimex3Tabla().get("t87");
		Assert.assertEquals("four year",archTimeMl.getTextoPlano().substring(timex.getStart(), timex.getEnd()));
		timex= archTimeMl.getTimex3Tabla().get("t85");
		Assert.assertEquals("the last twenty four hours",archTimeMl.getTextoPlano().substring(timex.getStart(), timex.getEnd()));
		
		Signal signal=archTimeMl.getSignalTabla().get("s13");
		Assert.assertEquals("before",archTimeMl.getTextoPlano().substring(signal.getStart(), signal.getEnd()));
		signal=archTimeMl.getSignalTabla().get("s15");
		Assert.assertEquals("when",archTimeMl.getTextoPlano().substring(signal.getStart(), signal.getEnd()));
		signal=archTimeMl.getSignalTabla().get("s306");
		Assert.assertEquals("after",archTimeMl.getTextoPlano().substring(signal.getStart(), signal.getEnd()));
		signal=archTimeMl.getSignalTabla().get("s15");
		Assert.assertEquals("when",archTimeMl.getTextoPlano().substring(signal.getStart(), signal.getEnd()));
		
		
	}
	
	@Test
	public void tokenizarTextoArchivoTimeMLTest() {
		ArchivoTimeML archTimeMl = new ArchivoTimeML("src/transformador/formatoTimeML/test/wsj_0173.tml");
		archTimeMl.getTextoPlano();
	}
}
