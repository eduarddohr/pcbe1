package strategy_game;

import resource.Resource;

public class Trade {
	private String name;
	private Resource givenRes;
	private Resource takenRes;
	
	public Trade(String name, Resource givenRes, Resource takenRes) {
		this.name = name;
		this.givenRes = givenRes;
		this.takenRes = takenRes;
	}
	
	public String getGivenResourceName() {
		return this.givenRes.getClass().toString();
	}
	
	public Resource getTakenResourceName(){
		return takenRes;
	}
	
	@Override 
	public boolean equals(Object obj) {
		return obj instanceof Trade && 
				((Trade)obj).givenRes.getClass().toString().equals(this.givenRes.getClass().toString()) &&
				((Trade)obj).takenRes.getClass().toString().equals(this.takenRes.getClass().toString());
	}
	@Override	
	public String toString() {
		return name + " wants " + takenRes + " for " + givenRes + ".\n"; 
	}
}
