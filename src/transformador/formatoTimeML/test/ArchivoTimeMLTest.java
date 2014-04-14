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

	private static String textoEsperadoPlano="\n\n\n ABC19980108.1830.0711 \n NEWS STORY \n\n\n\n\n\n On the other hand, it's turning out to be another very bad financial week\n for Asia. The financial assistance from the World Bank and the\n International Monetary Fund are not helping. In the last twenty four hours, the\n value of the Indonesian stock market has fallen by twelve percent. The Indonesian\n currency has lost\n twenty six percent of its value. In Singapore, stocks hit a five year low. In the\n Philippines, a four year low. And in Hong Kong, a three percent drop. More\n problems in Hong Kong for a place, for an economy, that many experts thought was\n once invincible. Here's ABC's Jim Laurie.\n\n\n Not that long ago, before the Chinese takeover, the news about real estate\n here was that the sky was the limit the highest prices in the world. So\n when Wong Kwan\n spent seventy million dollars for this house, he thought it was a\n great deal. He sold the property to five buyers and said he'd double his\n money.\n\n\n In Hong Kong, is always belongs to the seller's market.\n\n\n Now with new construction under way, three of his buyers have backed out.\n And Wong Kwan will be lucky to break even. All across Hong Kong, the\n property market has crashed. Pamela Pak\n owns eight condominiums here.\n Pak ca n't find buyers. She estimates her properties, worth a hundred thirty million dollars in October, are worth only half that now.\n\n\n They believe ah it will be always up going up and up ah forever. Nobody believe\n this any more.\n\n\n Of all of Asia's economies, Hong Kong is the most robust. But in the past\n three months, stocks have plunged, interest rates have soared and the\n downturn all across Asia means that people are not spending here.\n\n\n Hotels are only thirty percent full. You can get seventy percent discounts at the\n shopping malls. Three thousand dollar pearls for eight hundred dollars. A two hundred dollar wool jacket for fifty dollars.\n Still, there are few buyers. And at the big brokerage houses, after ten\n years of boom, they're talking about layoffs.\n\n\n I think that the mood is fairly gloomy, and I think it's not going to\n change for a couple of years.\n\n\n So for Hong Kong, it's time, as investment bankers like to say, to reposition. To either hold on tight or get out, as much of Asia goes into\n recession. Jim Laurie, ABC News, Hong Kong.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
	@Test
	public void levantarArchivoTimeMLTest() {
		ArchivoTimeML archTimeMl = new ArchivoTimeML("src/transformador/formatoTimeML/test/ABC19980108.1830.0711.tml");
		Assert.assertEquals(ArchivoTimeML.tokenizar(textoEsperadoPlano), archTimeMl.getTextoPlano());
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
		Assert.assertEquals("hola , " , ArchivoTimeML.tokenizar("hola, "));
		Assert.assertEquals("hola ." , ArchivoTimeML.tokenizar("hola."));
		Assert.assertEquals("Perez S.A. es una empresa ." , ArchivoTimeML.tokenizar("Perez S.A. es una empresa."));
		Assert.assertEquals("hola : " , ArchivoTimeML.tokenizar("hola: "));
		Assert.assertEquals("hola ; " , ArchivoTimeML.tokenizar("hola; "));
		Assert.assertEquals("hola ' " , ArchivoTimeML.tokenizar("hola' "));
		Assert.assertEquals("hola \" " , ArchivoTimeML.tokenizar("hola\" "));
	}
	
	@Test
	public void limitesConsumidoresTextoTest(){
		ArchivoTimeML archTimeMl = new ArchivoTimeML("src/transformador/formatoTimeML/test/ABC19980108.1830.0711.tml");
		String archivoProcesadoConEspaciosParaComparar = ArchivoTimeML.tokenizar(textoEsperadoPlano);
		Event event = archTimeMl.getEventTabla().get("e53");
		Assert.assertEquals("talking",archivoProcesadoConEspaciosParaComparar.substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e34");
		Assert.assertEquals("find",archivoProcesadoConEspaciosParaComparar.substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e371");
		Assert.assertEquals("thirty",archivoProcesadoConEspaciosParaComparar.substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e368");
		Assert.assertEquals("recession",archivoProcesadoConEspaciosParaComparar.substring(event.getStart(), event.getEnd()));
		
		Timex3 timex= archTimeMl.getTimex3Tabla().get("t98");
		Assert.assertEquals("the past three months ",archivoProcesadoConEspaciosParaComparar.substring(timex.getStart(), timex.getEnd()));
		timex= archTimeMl.getTimex3Tabla().get("t196");
		Assert.assertEquals("Now",archivoProcesadoConEspaciosParaComparar.substring(timex.getStart(), timex.getEnd()));
		timex= archTimeMl.getTimex3Tabla().get("t90");
		Assert.assertEquals("now",archivoProcesadoConEspaciosParaComparar.substring(timex.getStart(), timex.getEnd()));
		timex= archTimeMl.getTimex3Tabla().get("t87");
		Assert.assertEquals("four year",archivoProcesadoConEspaciosParaComparar.substring(timex.getStart(), timex.getEnd()));
		timex= archTimeMl.getTimex3Tabla().get("t85");
		Assert.assertEquals("the last twenty four hours",archivoProcesadoConEspaciosParaComparar.substring(timex.getStart(), timex.getEnd()));
		
		Signal signal=archTimeMl.getSignalTabla().get("s13");
		Assert.assertEquals("before",archivoProcesadoConEspaciosParaComparar.substring(signal.getStart(), signal.getEnd()));
		signal=archTimeMl.getSignalTabla().get("s15");
		Assert.assertEquals("when",archivoProcesadoConEspaciosParaComparar.substring(signal.getStart(), signal.getEnd()));
		signal=archTimeMl.getSignalTabla().get("s306");
		Assert.assertEquals("after",archivoProcesadoConEspaciosParaComparar.substring(signal.getStart(), signal.getEnd()));
		signal=archTimeMl.getSignalTabla().get("s15");
		Assert.assertEquals("when",archivoProcesadoConEspaciosParaComparar.substring(signal.getStart(), signal.getEnd()));
		
		
	}
}
