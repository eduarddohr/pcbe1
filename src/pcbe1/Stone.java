package pcbe1;

public class Stone implements Resource {
	public String getName() {
		return "Stone";
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Stone) && ((Stone)obj).getName()==this.getName();
	}
}
