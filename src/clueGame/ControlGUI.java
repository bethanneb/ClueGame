
package clueGame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
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
	Accusation accusationClass = new Accusation();
	Suggestion suggestionHuman = new Suggestion("");


	public ControlGUI(Board board) {

		this.board = board; 

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
		panel.setLayout(new GridLayout(1,1));
		JLabel nameLabel = new JLabel("Name");
		currentName = new JTextField();
		panel.add(nameLabel);
		currentName.setEditable(false);

		panel.add(currentName);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn?"));
		return panel;
	}

	//panel 2
	private JPanel createNextPlayerButtonPanel() {
		JButton nextPlayer = new JButton("Next player");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		panel.add(nextPlayer);
		nextPlayer.addActionListener(new NextPlayerButtonListener());
		return panel;
	}

	//panel 3
	private JPanel createAccusationButtonPanel() {
		JButton accusation = new JButton("Make an accusation");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		accusation.addActionListener(new MakeAccusationButtonListener());
		panel.add(accusation); 
		return panel;
	}

	//panel 4
	private JPanel createDiePanel() {
		currentDie = new JTextField(String.valueOf(board.currentDieRollValue()));
		currentDie.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel nameLabel = new JLabel("Roll"); 

		
		panel.add(nameLabel);
		panel.add(currentDie);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		return panel;
	}

	// panel 5
	private JPanel createGuessPanel() {
		currentGuess = new JTextField();
		currentGuess.setPreferredSize(new Dimension(20, 20));
		currentGuess.setEditable(false);
		currentGuess.setFont(new java.awt.Font("Lucida Grande", Font.PLAIN, 10));

		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(2,4));
		panel.add(currentGuess);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}

	// panel 6
	private JPanel createGuessResultPanel() {
		currentResult = new JTextField();
		currentResult.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));

		JLabel nameLabel = new JLabel("Disproved:");


		panel.add(nameLabel);
		panel.add(currentResult);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
	}

	//We decided to implement this class within this one to make it easier to access certain variables
	private class NextPlayerButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

			if (board.doneWithHuman && !board.gameFinished){
				//plays game
				board.nextPlayerButtonMethod();
				//updates player name and die panels
				refreshDieAndNamePanel();
				//updates guess and result pane;s
				refreshGuessResultPanels();

			}
		}
	}

	public void refreshGuessResultPanels() {
		currentGuess.setText(board.whoIsTheCurrentPLayer().getGuess());
		currentResult.setText(board.whoIsTheCurrentPLayer().getResult());
	}

	public void refreshDieAndNamePanel() {
		currentName.setText(board.whoIsTheCurrentPLayer().getPlayerName());  
		currentDie.setText(String.valueOf(board.currentDieRollValue()));
	}

	private class MakeAccusationButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event){
			if(board.whoIsTheCurrentPLayer() instanceof HumanPlayer && board.hasNotAccused) {
				accusationWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				JPanel accusationPanel = new JPanel();

				accusationPanel = accusationClass;
				accusationPanel.setLayout(new BoxLayout(accusationPanel, BoxLayout.Y_AXIS));
				accusationPanel.setOpaque(true);

				accusationWindow.getContentPane().add(BorderLayout.CENTER, accusationPanel);
				accusationWindow.pack();
				accusationWindow.setLocationByPlatform(true);
				accusationWindow.setVisible(true);
				accusationWindow.setResizable(true);
				accusationClass.passFrame(accusationWindow);

				//can only accuse once
				board.hasNotAccused = false;
			}
			else {
				JOptionPane.showMessageDialog(null, "Cannot make accusation now.", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

}