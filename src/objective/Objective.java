package objective;

import java.util.*;

import resource.Resource;

public abstract class Objective {
	protected ArrayList<Resource> objectiveResources;
	protected int points;
	
	public Map<String,ArrayList<Resource>> checkIfCanBuild(ArrayList<Resource> resources){
		//parametru lista de resurse ale jucatorului intoarce dictionar cu cheile locked si needed
		Map<String,ArrayList<Resource>> response = new HashMap<String,ArrayList<Resource>>();
		ArrayList<Resource> needed = new ArrayList<Resource>();
		ArrayList<Resource> locked = new ArrayList<Resource>();
		if(resources == null) {
			needed = objectiveResources;
			locked = null;
		}
		else {
			for(Resource res: objectiveResources) {
				if(resources.contains(res)) {
					locked.add(res);
					resources.remove(res);
				}
				else {
					needed.add(res);
				}
			}
		}
		response.put("needed", needed);
		response.put("locked", locked);
		response.put("remaining", resources);
		return response;
	}
	
	@Override
	public int hashCode() {
		return this.getClass().toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.hashCode()==obj.hashCode();
	}
	
	public int getPoints() {
		return points;
	}
	
}
