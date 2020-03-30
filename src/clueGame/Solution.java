package clueGame;

public class Solution {

	public static String person, room, weapon;

	//constructor
	public Solution(String person, String weapon, String room) {
		this.person = person;
		this.weapon = weapon;
		this.room= room;
	}

	public String getPerson() {
		return person;
	}

	public String getRoom() {
		return room;
	}

	public String getWeapon() {
		return weapon;
	}

	
}
