package strategy_game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import objective.Objective;
import resource.Resource;

public class Player extends Thread {

	private String name;
	private Semaphore semaphore;
	private Map<Objective, Integer> objectives;
	private ArrayList<Resource> resources = new ArrayList<Resource>();

	public Player(Semaphore semaphore, String name) {
		this.semaphore = semaphore;
		this.name = name;
		this.objectives = new HashMap<Objective, Integer>();
		for (Objective objective : Game.objectiveslist)
			try {
				this.objectives.put(objective.getClass().newInstance(), 0);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Opa");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Oopsie");
			}
	}

	private void giveResource(Resource res) {
		resources.remove(resources.indexOf(res));
	}

	private int calculatePoints() {
		int totalPoints = 0;
		for (Map.Entry<Objective, Integer> entry : objectives.entrySet()) {
			totalPoints += entry.getKey().getPoints() * entry.getValue();
		}
		return totalPoints;
	}

	// cred ca asta e practic takeTurn, dar nu stiu :))
	public void run() {

		try {
			// System.out.println("Hello, me is player and will implement the whole game -"
			// + name);
			ArrayList<Objective> objectiveslist = Game.objectiveslist;
			Map<String, ArrayList<Resource>> nLResorcesMap;

			while (Game.getWon() == false) {
				getRandomResources(1);
				System.out.println("resorces: " + name + " " + resources.toString());
				for (Objective objective : objectiveslist) {
					System.out.println(
							System.currentTimeMillis() + " " + name + " tries to build " + objective.toString());
					nLResorcesMap = objective.checkIfCanBuild(new ArrayList<Resource>(resources));
					ArrayList<Resource> needed = nLResorcesMap.get("needed");
					ArrayList<Resource> locked = nLResorcesMap.get("locked");
					ArrayList<Resource> remaining = nLResorcesMap.get("remaining");
					nLResorcesMap.toString();
					// System.out.println(name);
					if (needed.isEmpty()) {
						System.out.println(
								System.currentTimeMillis() + " " + name + " can build " + objective.toString());
						semaphore.acquire();
						buildObjective(remaining, locked, objective);
						semaphore.release();
					} else {
						for (Resource res : needed) {
							if (remaining.isEmpty()) {
								// nu plaseaza cerere, e sarac
							} else {
								String exchangeResourceName = Game.getExchangeResourceName(res);
								if (exchangeResourceName == null) {
									// plaseaza cerere
								} else {
									boolean exchangeResonse = decideIfCanExchange(res, exchangeResourceName, remaining);
								}
							}
						}
					}

					// System.out.println(name + " " + objective.toString());
				}
			}

		} catch (Exception e) {
			System.out.println(this.name + e.getMessage() + e.toString() + resources.toString());
		}
	}

	private boolean decideIfCanExchange(Resource needed, String requestResourceName, ArrayList<Resource> remaining)
			throws InterruptedException {
		Resource[] requestedResources = (Resource[]) remaining.stream()
				.filter(el -> el.getClass().toString().equals(requestResourceName)).toArray();
		boolean response = false;
		if (requestedResources != null && requestedResources.length > 0) {
			Trade trade = new Trade(this.name, requestedResources[0], needed);
			response = Game.makeTrade(trade);
		}

		return response;
	}

	// thread safe
	private void getRandomResources(int nrRes) throws InterruptedException {
		ArrayList<Resource> newResources = Game.getRandomResources(nrRes);
		resources.addAll(newResources);
	}

	private void buildObjective(ArrayList<Resource> remaining, ArrayList<Resource> locked, Objective objective)
			throws InterruptedException {
		this.resources = remaining;
		int value = objectives.get(objective);
		objectives.put(objective, value + 1);
		System.out.println(System.currentTimeMillis() + " " + this.name + " built " + objective.toString());

		int points = calculatePoints();
		if (points >= Game.necessaryPointsToWin && Game.getWon() == false)
			Game.wonGame(this.name, points);

		Game.giveBackResources(locked);
	}

}
