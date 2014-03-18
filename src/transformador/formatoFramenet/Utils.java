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

	public static String getSId() {
		return String.valueOf(id++)+"idS";
	}

	public static String getGraphId() {
		return String.valueOf(id++)+"idGraph";
	}

	public static String getTId() {
		return String.valueOf(id++)+"idT";
	}

	public static String getNtId() {
		return String.valueOf(id++)+"idNt";
	}
}
