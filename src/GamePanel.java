import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener{
	Board board;
	JLabel status;
	GamePanel(){
		JToolBar toolBar=new JToolBar();
		JButton button=new JButton();
		button.setText("Restart");
		button.setActionCommand("restart");
		button.addActionListener(this);
		toolBar.add(button);
		//toolBar.setLayout(new GridLayout(1,2));
		this.setLayout(new BorderLayout());
		add(toolBar,BorderLayout.PAGE_START);
		status=new JLabel("	Please make your move");
		status.setHorizontalAlignment(SwingConstants.RIGHT);
		toolBar.add(status);
		board=new Board(this,status);
		add(board,BorderLayout.CENTER);
	}
	public void actionPerformed(ActionEvent e){
		System.out.println("RESTART");
		if (e.getActionCommand().equals("restart")){
			remove(board);
			revalidate();
			board=new Board(this,status);
			add(board,BorderLayout.CENTER);
		}
	}
}
