

import javax.swing.*;
import java.awt.Dimension;

public class Main {
	public static void main(String[] args){
		JFrame frame=new JFrame("Gobang");
		GamePanel gamePanel=new GamePanel();
		frame.getContentPane().add(gamePanel);
		frame.setPreferredSize(new Dimension(300,300));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}



