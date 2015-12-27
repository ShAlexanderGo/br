package br.player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import br.Game;
import br.Global;
import br.Group;

public class PlayerBuilder {

	private Group players = new Group();
	
	private static final List<String> names = Arrays.asList(
			"Albert", "Alice", "Anna", "Annie", "Arthur", "Barbara", "Bertha",
			"Bessie", "Betty", "Brenda", "Carol", "Carolyn", "Carrie", 
			"Charles", "Charlie", "Cheryl", "Clara", "Clarence", "Cora", 
			"Cynthia", "Daniel", "David", "Deborah", "Debra", "Dennis", 
			"Diane", "Donald", "Donna", "Doris", "Dorothy", "Edward", 
			"Elizabeth", "Ella", "Emma", "Ernest", "Ethel", "Eugene", "Evelyn",
			"Florence", "Frances", "Frank", "Fred", "Gary", "George", "Grace",
			"Harold", "Harry", "Helen", "Henry", "Ida", "Irene", "Jack", 
			"James", "Janet", "Janice", "Jean", "Jeffrey", "Jerry", "Joan", 
			"Joe", "John", "Joseph", "Joyce", "Judith", "Judy", "Karen", 
			"Kathleen", "Kathy", "Kenneth", "Larry", "Laura", "Lillian", 
			"Linda", "Lois", "Louis", "Mabel", "Margaret", "Marie", "Marjorie",
			"Mark", "Martha", "Mary", "Maude", "Michael", "Mildred", "Minnie",
			"Nancy", "Nellie", "Pamela", "Patricia", "Paul", "Ralph", 
			"Raymond", "Richard", "Robert", "Roger", "Ronald", "Rose", "Roy",
			"Ruth", "Samuel", "Sandra", "Sarah", "Sharon", "Shirley", 
			"Stephen", "Steven", "Susan", "Thomas", "Timothy", "Virginia", 
			"Walter", "William"
	);
	
	public Group get() {
		return players;
	}
	
	public PlayerBuilder(Game game, int number) {
		if (number > names.size())
			number = names.size();
		HashSet<String> chosen = new HashSet<String>();
		while (chosen.size() < number) {
			int random = Global.random.nextInt(names.size());
			while (true) {
				String name = names.get(random);
				if (chosen.contains(name)) {
					random = (random + 1) % names.size();
				} else {
					chosen.add(name);
					break;
				}
			}
		}
		double angle = 0;
		for (String name : chosen) {
			players.add(new Player(game, name, (int)(5000 * Math.cos(angle)), 
					(int)(5000 * Math.sin(angle))));
			angle += Math.toRadians(360) / number;
		}
	}
	
	public PlayerBuilder(Game game) {
		this(game, 10);
	}
	
}
