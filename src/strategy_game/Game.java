package strategy_game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;

import objective.Objective;
import objective.Road;
import objective.Settlement;
import objective.Town;
import resource.Brick;
import resource.Resource;
import resource.Stone;
import resource.Wood;

public class Game {
    // liste resurse
	public static ArrayList<Resource> bricks = new ArrayList<Resource>();
	public static ArrayList<Resource> wood = new ArrayList<Resource>();
	public static ArrayList<Resource> stones = new ArrayList<Resource>();
	
	// semafoare pt resurse
	private static Semaphore bricksSemaphore = new Semaphore(1);
	private static Semaphore woodSemaphore = new Semaphore(1);
	private static Semaphore stonesSemaphore = new Semaphore(1);
	
	//lista IMUTABILA cu obiective puse in ordinea importantei lor
	final public static ArrayList < Objective > objectiveslist = new ArrayList < Objective > (
			Arrays.asList(
			 new Town(),
			 new Settlement(),
			 new Road()));
	
	private ArrayList<Trade> marketplace;
	
	final static int nrPlayers = 4;
	
	private boolean checkIfCanTrade(){
		return true;
	}
	
	public Game() {
		for(int i = 0; i < 20; i++) {
			bricks.add(new Brick());
			wood.add(new Wood());
			stones.add(new Stone());
		}
	}
	
	public static void main(String[] argv){
		Game game = new Game();
		Semaphore semaphore = new Semaphore(1);
		for(int i = 0; i < nrPlayers; i++) {
			Player player = new Player(semaphore, "player " + (i+1));
			player.start();
		}	
		
	}
	
	public static ArrayList<Resource> getRandomResources(int nrRes) throws InterruptedException {
		Random r = new Random();
		ArrayList<Resource> resources = new ArrayList<>();
		for (int nr : r.ints(nrRes, 0, 3).toArray()) {
			switch(nr) {
			case 0:
				bricksSemaphore.acquire();
				if(!bricks.isEmpty()) {
					resources.add(new Brick());
					Game.bricks.remove(0);
				}
				bricksSemaphore.release();
				break;
			case 1:
				stonesSemaphore.acquire();
				if(!stones.isEmpty()) {
					resources.add(new Stone());
					Game.stones.remove(0);
				}
				stonesSemaphore.release();
				break;
			case 2:
				woodSemaphore.acquire();
				if(!wood.isEmpty()) {
					resources.add(new Wood());
					Game.wood.remove(0);
				}
				woodSemaphore.release();
				break;
			}
		}
		return resources;
	}
	
	public static void giveBackResources(ArrayList<Resource> givenResources) throws InterruptedException {
		for(Resource resource: givenResources) {
			if(resource instanceof Brick) {
				bricksSemaphore.acquire();
				bricks.add(resource);
				bricksSemaphore.release();
				continue;
			}
			if(resource instanceof Wood) {
				woodSemaphore.acquire();
				wood.add(resource);
				woodSemaphore.release();
				continue;
			}
			if(resource instanceof Stone) {
				stonesSemaphore.acquire();
				stones.add(resource);
				stonesSemaphore.release();
			}
		}
	}
}