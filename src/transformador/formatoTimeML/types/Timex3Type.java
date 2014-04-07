package transformador.formatoTimeML.types;

public enum Timex3Type {
	DATE("DATE"),TIME("TIME"),DURATION("DURATION"),SET("SET");

    private final String text;

    /**
     * @param text
     */
    private Timex3Type(final String text) {
    	this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}
