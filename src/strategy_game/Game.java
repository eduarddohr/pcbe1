package strategy_game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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

	private static Semaphore tradeSemaphore = new Semaphore(1);

	// semafor pentru victorie
	private static Semaphore wonSemaphore = new Semaphore(1);

	// lista IMUTABILA cu obiective puse in ordinea importantei lor
	final public static ArrayList<Objective> objectiveslist = new ArrayList<Objective>(
			Arrays.asList(new Town(), new Settlement(), new Road()));

	private static List<Trade> marketplace = new ArrayList<Trade>();

	final static int nrPlayers = 5;
	final static int necessaryPointsToWin = 10;
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	public static boolean won = false;
	private static Player winnerPlayer;

	public static Resource getExchangeResource(Resource needed) throws InterruptedException {
		if (marketplace == null || marketplace.isEmpty()) {
			return null;
		}
		tradeSemaphore.acquire();
		for(Iterator<Trade> iterator = marketplace.iterator(); iterator.hasNext();) {
			Trade trade = iterator.next();
			if (trade.getGivenResourceName().equals(needed.getClass().toString())) {
				tradeSemaphore.release();
				return trade.getTakenResourceName();
			}
		}
		tradeSemaphore.release();
		return null;
	}

	public Game() {
		for (int i = 0; i < 20; i++) {
			bricks.add(new Brick());
			wood.add(new Wood());
			stones.add(new Stone());
		}
	}

	public static boolean makeTrade(Trade trade) throws InterruptedException {
		Trade originalTrade = null;
		tradeSemaphore.acquire();
		int index = marketplace.indexOf(trade);
		if (index >= 0) {
			originalTrade = marketplace.remove(index);
		}
		else {
			tradeSemaphore.release();
			return false;
		}
		tradeSemaphore.release();

		return true;
	}

	public static void main(String[] argv) throws InterruptedException {
		Game game = new Game();
		for (int i = 0; i < nrPlayers; i++) {
			Player player = new Player(wonSemaphore, "player " + (i + 1));
			players.add(player);
			player.start();
		}
	}

	public static ArrayList<Resource> getRandomResources(int nrRes) throws InterruptedException {
		Random r = new Random();
		ArrayList<Resource> resources = new ArrayList<>();
		for (int nr : r.ints(nrRes, 0, 3).toArray()) {
			switch (nr) {
			case 0:
				bricksSemaphore.acquire();
				if (!bricks.isEmpty()) {
					resources.add(new Brick());
					Game.bricks.remove(0);
				}
				bricksSemaphore.release();
				break;
			case 1:
				stonesSemaphore.acquire();
				if (!stones.isEmpty()) {
					resources.add(new Stone());
					Game.stones.remove(0);
				}
				stonesSemaphore.release();
				break;
			case 2:
				woodSemaphore.acquire();
				if (!wood.isEmpty()) {
					resources.add(new Wood());
					Game.wood.remove(0);
				}
				woodSemaphore.release();
				break;
			}
		}
		if(resources.size() != nrRes) {
			System.out.println("There are not enough resources left to continue the game");
			for(Player player : players) {
				player.cancel();
			}
		}
		return resources;
	}

	public static void giveBackResources(ArrayList<Resource> givenResources) throws InterruptedException {
		for (Resource resource : givenResources) {
			if (resource instanceof Brick) {
				bricksSemaphore.acquire();
				bricks.add(resource);
				bricksSemaphore.release();
				continue;
			}
			if (resource instanceof Wood) {
				woodSemaphore.acquire();
				wood.add(resource);
				woodSemaphore.release();
				continue;
			}
			if (resource instanceof Stone) {
				stonesSemaphore.acquire();
				stones.add(resource);
				stonesSemaphore.release();
			}
		}
	}

	public static void wonGame(String p, int points) throws InterruptedException {
		wonSemaphore.acquire();
		if (won == false) {
			won = true;
			System.out.println(p + " won the game with: " + points + "points");
			for(Player player : players) {
				player.cancel();
			}
		}
		wonSemaphore.release();
	}

	public static boolean getWon() {
		return won;
	}
	
	public static void addTrade(Trade trade) throws InterruptedException {
		tradeSemaphore.acquire();
		marketplace.add(trade);
		tradeSemaphore.release();
	}
	
	public static boolean wasTradeUsed(Trade trade) throws InterruptedException {
		boolean b = false;
		tradeSemaphore.acquire();
		b = marketplace.contains(trade);
		if(b) {
			marketplace.remove(trade);
		}
		tradeSemaphore.release();
		return !b;
	}
}
