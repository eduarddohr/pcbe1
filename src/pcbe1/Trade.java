package pcbe1;

public class Trade {
	private String name;
	private Resource givenRes;
	private Resource takenRes;
	
	public Trade(String name, Resource givenRes, Resource takenRes) {
		this.name = name;
		this.givenRes = givenRes;
		this.takenRes = takenRes;
	}
}
