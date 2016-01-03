package br.player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.Game;
import br.Global;

public class PlayerBuilder {

	private List<Player> players = new LinkedList<Player>();
	
	private static final List<String> femaleNames = Arrays.asList(
			"Alice", "Anna", "Annie", "Barbara", "Bertha", "Bessie", "Betty",
			"Brenda", "Carol", "Carolyn", "Carrie", "Cheryl", "Clara", "Cora",
			"Cynthia", "Deborah", "Debra", "Diane", "Donna", "Doris", 
			"Dorothy", "Elizabeth", "Ella", "Emma", "Ethel", "Evelyn",
			"Florence", "Frances", "Grace", "Helen", "Ida", "Irene", "Janet",
			"Janice", "Jean", "Joan", "Joyce", "Judith", "Judy", "Karen", 
			"Kathleen", "Kathy", "Laura", "Lillian", "Linda", "Lois", "Mabel", 
			"Margaret", "Marie", "Marjorie", "Martha", "Mary", "Maude", 
			"Mildred", "Minnie", "Nancy", "Nellie", "Pamela", "Patricia",
			"Rose", "Ruth", "Sandra", "Sarah", "Sharon", "Shirley", "Susan", 
			"Timothy", "Virginia"
	);
	
	private static final List<String> maleNames = Arrays.asList(
			"Albert", "Arthur", "Charles", "Charlie", "Clarence", "Daniel", 
			"David", "Dennis", "Donald", "Edward", "Ernest", "Eugene", "Frank",
			"Fred", "Gary", "George", "Harold", "Harry", "Henry", "Jack", 
			"James", "Jeffrey", "Jerry", "Joe", "John", "Joseph", "Kenneth",
			"Larry", "Louis", "Mark", "Michael", "Paul", "Ralph", "Raymond", 
			"Richard", "Robert", "Roger", "Ronald", "Roy", "Samuel", "Stephen", 
			"Steven", "Thomas", "Walter", "William"
	);
	
	public List<Player> get() {
		return players;
	}
	
	public PlayerBuilder faceToCenter() {
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setDirection(0, 0);
		}
		return this;
	}
	
	public PlayerBuilder(Game game, int number) {
		number = Math.min(number, femaleNames.size() + maleNames.size());
		Map<String, Gender> chosen = new HashMap<String, Gender>();
		while (chosen.size() < number) {
			Gender g;
			List<String> names;
			if (Global.rollDice(50)) {
				g = Gender.MALE;
				names = maleNames;
			} else {
				g = Gender.FEMALE;
				names = femaleNames;
			}
			int random = Global.random.nextInt(names.size());
			while (true) {
				String name = names.get(random);
				if (chosen.containsKey(name)) {
					random = (random + 1) % names.size();
				} else {
					chosen.put(name, g);
					break;
				}
			}
		}
		double angle = 0;
		for (String name : chosen.keySet()) {
			players.add(new Player(game, name, chosen.get(name), 
					(int)(5000 * Math.cos(angle)), 
					(int)(5000 * Math.sin(angle))));
			angle += Math.toRadians(360) / number;
		}
	}
	
	public PlayerBuilder(Game game) {
		this(game, 10);
	}
	
}
