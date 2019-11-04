package pcbe1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Player extends Thread{
	
	private String name;
	private Semaphore semaphore;
	private Map<Objective, Integer> objectives;
	private ArrayList<Resource> resources = new ArrayList<Resource>();
	
	public Player(Semaphore semaphore, String name) {
		this.semaphore = semaphore;
		this.name=name;
		this.objectives =  new HashMap<Objective, Integer>();
	}
	private void recieveResource(Resource res){
		resources.add(res);
	}
	
	private void giveResource(Resource res){
		resources.remove(resources.indexOf(res));	
	}
	
	private int calculatePoints(){
		int totalPoints = 0;
		for (Map.Entry<Objective, Integer> entry : objectives.entrySet()) {
			totalPoints += entry.getKey().getPoints() * entry.getValue();
		}
		return totalPoints;
	}
	//cred ca asta e practic takeTurn, dar nu stiu :))
	public void run(){
		
		try {
			//semaphore.acquire();
			getRandomResources(3);
			//semaphore.release();
			
			System.out.println("Hello, me is player and will implement the whole game -" + name);

		}
		catch(Exception e) {
			 System.out.println ("Exception is caught"); 
		}
	}
	//thread safe
	private void getRandomResources(int nrRes) throws InterruptedException{ 
		semaphore.acquire();
		Random r = new Random();
		for (int nr : r.ints(nrRes, 0, 3).toArray()) {
			switch(nr) {
			case 0:
				recieveResource(new Brick());
				Game.bricks.remove(0);
				break;
			case 1:
				recieveResource(new Stone());
				Game.stones.remove(0);
				break;
			case 2:
				recieveResource(new Wood());
				Game.wood.remove(0);
				break;
			}
		}
		semaphore.release();
	}

}
