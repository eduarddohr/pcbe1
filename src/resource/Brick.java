package resource;

public class Brick implements Resource {
	public String getName() {
		return "Brick";
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Brick) && ((Brick)obj).getName()==this.getName();
	}
}
