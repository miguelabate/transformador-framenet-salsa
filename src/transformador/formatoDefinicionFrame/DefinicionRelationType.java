package transformador.formatoDefinicionFrame;

public class DefinicionRelationType {
	
	private long ID;
	private String name;
	private String subFrameName;
	private String superFrameName;

	public DefinicionRelationType(String id, String name,
			String superFrameName, String subFrameName) {
		this.ID=Long.valueOf(id);
		this.name=name;
		this.subFrameName=subFrameName;
		this.superFrameName=superFrameName;
	}

	public long getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public String getSubFrameName() {
		return subFrameName;
	}

	public String getSuperFrameName() {
		return superFrameName;
	}
	
}
