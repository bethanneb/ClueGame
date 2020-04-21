/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */

package clueGame;

public class Solution {

	private String person, room, weapon;
	
	//constructors
	
	public Solution() {
		super();
		this.person = "";
		this.room = "";
		this.weapon = "";
	}
	
	public Solution(String person, String room, String weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
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
	
	public void setPerson(String person) {
		this.person = person;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}


	
}
