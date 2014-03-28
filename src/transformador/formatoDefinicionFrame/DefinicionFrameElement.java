package transformador.formatoDefinicionFrame;


public class DefinicionFrameElement {
	private long ID;
	private String coreType;
	private String name;
	private String abbrev;
	private DefinicionFrame frame;
	
	public DefinicionFrameElement(String id, String abbrev,
			String name, String coreType, DefinicionFrame frame) {
		this.ID=Long.valueOf(id);
		this.coreType=coreType;
		this.name=name;
		this.abbrev=abbrev;
		this.frame=frame;
	}

	public long getID() {
		return ID;
	}

	public String getCoreType() {
		return coreType;
	}

	public String getName() {
		return name;
	}

	public String getAbbrev() {
		return abbrev;
	}

	public DefinicionFrame getFrame() {
		return frame;
	}
	
	@Override
	public String toString() {
		return  frame.getName()+"-"+name ;
	}
}
