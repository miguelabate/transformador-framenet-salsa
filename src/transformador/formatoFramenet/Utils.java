package transformador.formatoFramenet;

public class Utils {

	private static int id = 0;
	
	public static String getFrameId() {
		return String.valueOf(id++)+"idFrame";
	}

	public static String getTargetId() {
		return String.valueOf(id++)+"idTarget";
	}

	public static String getFeId() {
		return String.valueOf(id++)+"idFe";
	}
}
