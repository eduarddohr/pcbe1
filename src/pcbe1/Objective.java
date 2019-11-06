package pcbe1;

import java.util.*;

public abstract class Objective {
	protected ArrayList<Resource> objectiveResources;
	protected int points;
	
	protected Map<String,ArrayList<Resource>> checkIfCanBuild(ArrayList<Resource> resources){
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
					//System.out.println("Contains"+res);
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
	
	protected int getPoints() {
		return points;
	}
	
}
