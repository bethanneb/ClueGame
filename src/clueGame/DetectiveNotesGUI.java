/*
 * author: Elizabeth Bauch
 * author: Danella Bunavi
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class DetectiveNotesGUI extends JPanel {
	//private JTextField name; 

	public DetectiveNotesGUI() { 
		setBorder(new TitledBorder (new EtchedBorder(), "Detective Notes"));
		setLayout(new GridLayout(6,1));
		JPanel panel = people();
		JPanel panel1 = weapons(); 
		JPanel panel2 = rooms(); 
		 
		add(panel);
		panel = peopleGuess(); 
		add(panel);
		
		add(panel1);
		panel1 = weaponsGuess(); 
		add(panel1);

		add(panel2);		
		panel2 = roomsGuess(); 
		add(panel2);
		
		
	}

	private JPanel people() {
		 
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(2,3));
		
		String[] people = {"Michael Scott", "Dwight Schrute", "Jim Halpert", "Pam Halpert", "Kevin Malone", "Andy Bernard", "Oscar Martinez", "Angela Martin"};
		for (int i = 0; i < people.length; i ++) { 
			JCheckBox person = new JCheckBox(people[i]); 
			panel.add(person); 
		} 
		
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		return panel;
	}
	
	private JPanel weapons() {
		 
			JPanel panel = new JPanel();
			
			panel.setLayout(new GridLayout(2,3));
			
			String[] weapons = {"Poison Dart", "Knife", "Taser", "Boomerang", "Samurai Sword", "Pepper Spray", "Crossbow", "Nunchucks"};
			for (int i = 0; i < weapons.length; i ++) { 
				JCheckBox weapon = new JCheckBox(weapons[i]); 
				panel.add(weapon); 
			} 
			
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon"));
			return panel;
	}
	
	private JPanel rooms() {
		 
			JPanel panel = new JPanel();
			
			panel.setLayout(new GridLayout(3,3));
			
			
			String[] rooms = {"Conference Room", "Warehouse", "Front Desk", "Vance Refrigeration", "Break Room", "Kitchen", "Jim's Desk", "Michael's House", "Dwight's Desk"};
			for (int i = 0; i < rooms.length; i ++) { 
				JCheckBox room = new JCheckBox(rooms[i]); 
				panel.add(room); 
			} 
			
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Room"));
			return panel;
	}
	


	private JPanel peopleGuess() {
		String[] people = {" ","Michael Scott", "Dwight Schrute", "Jim Halpert", "Pam Halpert", "Kevin Malone", "Andy Bernard", "Oscar Martinez", "Angela Martin"}; 
		JComboBox peopleList = new JComboBox(people); 
		JPanel panel = new JPanel();
		panel.add(peopleList);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People Guess"));
		return panel;
	}
	
	private JPanel weaponsGuess() { 
		String[] weapons = {" ", "Poison Dart", "Knife", "Taser", "Boomerang", "Samurai Sword", "Pepper Spray", "Crossbow", "Nunchucks"}; 
		JComboBox weaponsList = new JComboBox(weapons); 
		JPanel panel = new JPanel();
		panel.add(weaponsList);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		return panel; 
	}
	
	private JPanel roomsGuess() { 
		String[] rooms = {" ", "Conference Room", "Warehouse", "Front Desk", "Vance Refrigeration", "Break Room", "Kitchen", "Jim’s Desk", "Michael’s House", "Dwight’s Desk"}; 
		JComboBox roomsList = new JComboBox(rooms); 
		JPanel panel = new JPanel();
		panel.add(roomsList);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		return panel;
	}

	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Detective Notes");
		frame.setSize(250, 150);	
		// Create the JPanel and add it to the JFrame
		DetectiveNotesGUI gui = new DetectiveNotesGUI();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}

}
