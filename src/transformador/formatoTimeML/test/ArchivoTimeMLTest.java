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
import transformador.formatoTimeML.types.Timex3Type;

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
	public void limitesConsumidoresTextoTest1(){
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
	public void tokenizarTextoArchivoTimeML1Test() {
		String resultado="ABC19980108 .1830.0711 NEWS STORY On the other hand , it 's turning out to be another very bad financial week for Asia . The financial assistance from the World Bank and the International Monetary Fund are not helping . In the last twenty four hours , the value of the Indonesian stock market has fallen by twelve percent . The Indonesian currency has lost twenty six percent of its value . In Singapore , stocks hit a five year low . In the Philippines , a four year low . And in Hong Kong , a three percent drop . More problems in Hong Kong for a place , for an economy , that many experts thought was once invincible . Here 's ABC 's Jim Laurie . Not that long ago , before the Chinese takeover , the news about real estate here was that the sky was the limit the highest prices in the world . So when Wong Kwan spent seventy million dollars for this house , he thought it was a great deal . He sold the property to five buyers and said he 'd double his money . In Hong Kong , is always belongs to the seller 's market . Now with new construction under way , three of his buyers have backed out . And Wong Kwan will be lucky to break even . All across Hong Kong , the property market has crashed . Pamela Pak owns eight condominiums here . Pak ca n't find buyers . She estimates her properties , worth a hundred thirty million dollars in October , are worth only half that now . They believe ah it will be always up going up and up ah forever . Nobody believe this any more . Of all of Asia 's economies , Hong Kong is the most robust . But in the past three months , stocks have plunged , interest rates have soared and the downturn all across Asia means that people are not spending here . Hotels are only thirty percent full . You can get seventy percent discounts at the shopping malls . Three thousand dollar pearls for eight hundred dollars . A two hundred dollar wool jacket for fifty dollars . Still , there are few buyers . And at the big brokerage houses , after ten years of boom , they 're talking about layoffs . I think that the mood is fairly gloomy , and I think it 's not going to change for a couple of years . So for Hong Kong , it 's time , as investment bankers like to say , to reposition . To either hold on tight or get out , as much of Asia goes into recession . Jim Laurie , ABC News , Hong Kong . ";
		
		ArchivoTimeML archTimeMl = new ArchivoTimeML("src/transformador/formatoTimeML/test/ABC19980108.1830.0711.tml");
		Assert.assertEquals(resultado,archTimeMl.getTextoPlano());
	}
	
	@Test
	public void tokenizarTextoArchivoTimeML2Test() {
		String resultado="WSJ891102-0017 = 891102 891102-0017 . Business Brief -- Coleco Industries Inc. : @ Ailing Toy Maker Submits @ Chapter 11 Revamp Plan 11/02/89 WALL STREET JOURNAL -LRB- J -RRB- CLO BANKRUPTCIES -LRB- BCY -RRB- Coleco Industries Inc. , a once high-flying toy maker whose stock peaked at $ 65 a share in the early 1980s , filed a Chapter 11 reorganization plan that provides just 1.125 cents a share for common stockholders . Under the plan , unsecured creditors , who are owed about $ 430 million , would receive about $ 92 million , or 21 cents for each dollar they are owed . In addition , they will receive stock in the reorganized company , which will be named Ranger Industries Inc. . After these payments , about $ 225,000 will be available for the 20 million common shares outstanding . The Avon , Conn. , company 's stock hit a high in 1983 after it unveiled its Adam home computer , but the product was plagued with glitches and the company 's fortunes plunged . But Coleco bounced back with the introduction of the Cabbage Patch dolls , whose sales hit $ 600 million in 1985 . But as the craze died , Coleco failed to come up with another winner and filed for bankruptcy-law protection in July 1988 . The plan was filed jointly with unsecured creditors in federal bankruptcy court in New York and must be approved by the court . ";
		
		ArchivoTimeML archTimeMl = new ArchivoTimeML("src/transformador/formatoTimeML/test/wsj_0173.tml");
		Assert.assertEquals(resultado,archTimeMl.getTextoPlano());
	}
	
	@Test
	public void limitesConsumidoresTextoTest2(){
		ArchivoTimeML archTimeMl = new ArchivoTimeML("src/transformador/formatoTimeML/test/wsj_0173.tml");
		Event event = archTimeMl.getEventTabla().get("e1988");
		Assert.assertEquals("high-flying",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e1");
		Assert.assertEquals("peaked",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e2");
		Assert.assertEquals("filed",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e3");
		Assert.assertEquals("provides",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e4");
		Assert.assertEquals("owed",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e5");
		Assert.assertEquals("receive",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e7");
		Assert.assertEquals("owed",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e8");
		Assert.assertEquals("receive",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e9");
		Assert.assertEquals("named",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e1990");
		Assert.assertEquals("payments",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e1991");
		Assert.assertEquals("available",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e10");
		Assert.assertEquals("hit",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e11");
		Assert.assertEquals("unveiled",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e12");
		Assert.assertEquals("plagued",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e13");
		Assert.assertEquals("plunged",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e14");
		Assert.assertEquals("bounced",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e1993");
		Assert.assertEquals("introduction",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e15");
		Assert.assertEquals("hit",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e16");
		Assert.assertEquals("died",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e17");
		Assert.assertEquals("failed",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e18");
		Assert.assertEquals("come",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e19");
		Assert.assertEquals("filed",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e1995");
		Assert.assertEquals("protection",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e20");
		Assert.assertEquals("filed",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		event = archTimeMl.getEventTabla().get("e22");
		Assert.assertEquals("approved",archTimeMl.getTextoPlano().substring(event.getStart(), event.getEnd()));
		
	}
	
	@Test
	public void obtenerTextoTageadoTest2() {
		String resultado="WSJ891102-0017 = 891102 891102-0017 . Business Brief -- Coleco Industries Inc. : @ Ailing Toy Maker Submits @ Chapter 11 Revamp Plan <TIMEX3>11/02/89</TIMEX3> WALL STREET JOURNAL -LRB- J -RRB- CLO BANKRUPTCIES -LRB- BCY -RRB- Coleco Industries Inc. , a <TIMEX3>once</TIMEX3> <EVENT>high-flying</EVENT> toy maker whose stock <EVENT>peaked</EVENT> at $ 65 a share <SIGNAL>in</SIGNAL> <TIMEX3>the early 1980s</TIMEX3> , <EVENT>filed</EVENT> a Chapter 11 reorganization plan that <EVENT>provides</EVENT> just 1.125 cents a share for common stockholders . Under the plan , unsecured creditors , who are <EVENT>owed</EVENT> about $ 430 million , would <EVENT>receive</EVENT> about $ 92 million , or 21 cents for each dollar they are <EVENT>owed</EVENT> . In addition , they will <EVENT>receive</EVENT> stock in the reorganized company , which will be <EVENT>named</EVENT> Ranger Industries Inc. . <SIGNAL>After</SIGNAL> these <EVENT>payments</EVENT> , about $ 225,000 will be <EVENT>available</EVENT> for the 20 million common shares outstanding . The Avon , Conn. , company 's stock <EVENT>hit</EVENT> a high <SIGNAL>in</SIGNAL> <TIMEX3>1983</TIMEX3> <SIGNAL>after</SIGNAL> it <EVENT>unveiled</EVENT> its Adam home computer , but the product was <EVENT>plagued</EVENT> with glitches and the company 's fortunes <EVENT>plunged</EVENT> . But Coleco <EVENT>bounced</EVENT> back with the <EVENT>introduction</EVENT> of the Cabbage Patch dolls , whose sales <EVENT>hit</EVENT> $ 600 million in <TIMEX3>1985</TIMEX3> . But as the craze <EVENT>died</EVENT> , Coleco <EVENT>failed</EVENT> to <EVENT>come</EVENT> up with another winner and <EVENT>filed</EVENT> for bankruptcy-law <EVENT>protection</EVENT> <SIGNAL>in</SIGNAL> <TIMEX3>July 1988</TIMEX3> . The plan was <EVENT>filed</EVENT> jointly with unsecured creditors in federal bankruptcy court in New York and must be <EVENT>approved</EVENT> by the court . ";
		
		ArchivoTimeML archTimeMl = new ArchivoTimeML("src/transformador/formatoTimeML/test/wsj_0173.tml");
		Assert.assertEquals(resultado,archTimeMl.obtenerTextoTageado());
	}
	
	@Test
	public void pruebaGeneracionTimeMLManual() {
		String resultado1="Esto es una oracion con un <EVENT>evento</EVENT>. Otra oracion con otro eventito.";
		String resultado2="Esto es una oracion con un <EVENT>evento</EVENT>. Otra oracion con otro <EVENT>eventito</EVENT>.";
		
		ArchivoTimeML archTimeMl = new ArchivoTimeML();
		archTimeMl.setTextoPlano("Esto es una oracion con un evento. Otra oracion con otro eventito.");
		Event unEvento=new Event("e1", EventClassType.OCCURRENCE, 27,33 );
		archTimeMl.marcar(unEvento);
		Assert.assertEquals(resultado1,archTimeMl.obtenerTextoTageado());
		
		Event unEvento2=new Event("e2", EventClassType.OCCURRENCE, 57,65 );
		archTimeMl.marcar(unEvento2);
		Assert.assertEquals(resultado2,archTimeMl.obtenerTextoTageado());
		
		
		
	}
	
	@Test
	public void pruebaGeneracionTimeMLManual2() {
		ArchivoTimeML archTimeMl = new ArchivoTimeML();
		
		String textoResultado3="a <TIMEX3>once</TIMEX3> <EVENT>high-flying</EVENT> toy maker whose stock <EVENT>peaked</EVENT> at $ 65 a share <SIGNAL>in</SIGNAL> <TIMEX3>the early 1980s</TIMEX3>";
		String texto="a once high-flying toy maker whose stock peaked at $ 65 a share in the early 1980s";
		
		archTimeMl.setTextoPlano(texto);
				
		Timex3 unTimex=new Timex3("t1", Timex3Type.TIME, "1",2,6);
		Timex3 otroTimex=new Timex3("t2", Timex3Type.TIME, "1",67,82);
		Event unEvento3=new Event("e1", EventClassType.OCCURRENCE, 7,18 );
		Event unEvento4=new Event("e2", EventClassType.OCCURRENCE, 41,47 );
		Signal signal=new Signal("s1", 64, 66);
		
		archTimeMl.marcar(unTimex);
		archTimeMl.marcar(otroTimex);
		archTimeMl.marcar(unEvento3);
		archTimeMl.marcar(unEvento4);
		archTimeMl.marcar(signal);
		
		Assert.assertEquals(textoResultado3,archTimeMl.obtenerTextoTageado());
	}
}
