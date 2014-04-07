package transformador.formatoTimeML.types;

public enum EventClassType {
	REPORTING("REPORTING"),PERCEPTION("PERCEPTION"),ASPECTUAL("ASPECTUAL"),I_ACTION("I_ACTION"),I_STATE("I_STATE"),STATE("STATE"),OCCURRENCE("OCCURRENCE");

    private final String text;

    /**
     * @param text
     */
    private EventClassType(final String text) {
    	this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}
