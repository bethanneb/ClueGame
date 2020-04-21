package clueGame;

import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class GuessPanel extends JDialog{

	public GuessPanel() {
		setTitle("Make a Guess");
		setSize(200, 300);
		
		//panel that will show this
		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new GridLayout(3,2));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
