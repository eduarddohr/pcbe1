package resource;

public class Wood implements Resource {
	public String getName() {
		return "Wood";
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Wood) && ((Wood)obj).getName()==this.getName();
	}
}
