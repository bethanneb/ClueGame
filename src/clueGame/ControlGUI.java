
package clueGame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	
	//private JTextField name; 
		public int dieRoll; 
		// TODO need to be able call methods in Board class
		private Board board;
		private JPanel nextPlayerAndAccusation;
		private JPanel currentPlayerAndDieRoll;
		private JTextField currentName; 
		private JTextField currentDie; 
		private JTextField currentGuess;
		private JTextField currentResult;
		JFrame accusationWindow = new JFrame("Accusation");
//		Accusation accusationClass = new Accusation();
//		Suggestion suggestionHuman = new Suggestion("");


	public ControlGUI() {
		
		setLayout(new GridLayout(2,0));
		JPanel panel1 = createNamePanel();
		JPanel panel2 = createNextPlayerButtonPanel(); 
		JPanel panel3 = createAccusationButtonPanel();
		JPanel panel4 = createDiePanel(); 
		JPanel panel5 = createGuessPanel(); 
		JPanel panel6 = createGuessResultPanel(); 

		add(panel1);
		add(panel2); 
		add(panel3);
		add(panel4); 
		add(panel5);
		add(panel6);
	}

	//panel 1
	public JPanel createNamePanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1,2));
		JTextField name = new JTextField(); 
		// blank gray box aka nothing is in it yet
		name.setEditable(false);
		
		panel.add(name);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn?"));
		return panel;
	}
	
	//panel 2
	private JPanel createNextPlayerButtonPanel() {
		JButton nextPlayer = new JButton("Next player");
		JPanel panel = new JPanel();
		panel.add(nextPlayer);
		return panel;
	}

	//panel 3
	private JPanel createAccusationButtonPanel() {
		JButton accusation = new JButton("Make an accusation");
		JPanel panel = new JPanel();
		panel.add(accusation); 
		return panel;
	}

	//panel 4
	private JPanel createDiePanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1,2));
		
		JLabel nameLabel = new JLabel("Roll"); 
		JTextField dieNumber = new JTextField(); 
		dieNumber.setEditable(false);
		
		panel.add(nameLabel);
		panel.add(dieNumber);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		return panel;
	}
	
	// panel 5
	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(2,6));
		
		JLabel nameLabel = new JLabel("Guess");
		JTextField guess = new JTextField(); 
		guess.setEditable(false);
		
		panel.add(nameLabel);
		panel.add(guess);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}

	// panel 6
	private JPanel createGuessResultPanel() {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1,2));
		
		JLabel nameLabel = new JLabel("Response");
		JTextField response = new JTextField();
		response.setEditable(false);
		
		panel.add(nameLabel);
		panel.add(response);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
	}

	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(800, 200);	
		// Create the JPanel and add it to the JFrame
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}

}