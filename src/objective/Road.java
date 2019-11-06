package objective;

import java.util.ArrayList;

import resource.Brick;
import resource.Resource;
import resource.Wood;

public class Road extends Objective implements Resource{

	public Road() {
		this.points = 0;
		this.objectiveResources = new ArrayList<>();
		this.objectiveResources.add(new Brick());
		this.objectiveResources.add(new Wood());
		
	}
	
	public String getName() {
		return "Road";
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Road) && ((Road)obj).getName()==this.getName();
	}

}
