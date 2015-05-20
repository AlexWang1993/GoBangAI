import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;
//import javafx.scene.shape.Circle;






public class Board extends JPanel{
	static int board_width=15;
	static int board_height=15;
	int num=0;
	boolean over=false;
	Color winner;
	Cell[][] grid;
	AIEngine computer;
	JLabel status;
	GamePanel gamePanel;
	Point recent;
	void setRecent(Point pos){
		recent=pos;
	}
	Color checkWin(int x,int y){
		if ((y+4<board_height)&&
			(grid[x][y].getColor()==grid[x][y+1].getColor())&&
			(grid[x][y+1].getColor()==grid[x][y+2].getColor())&&
			(grid[x][y+2].getColor()==grid[x][y+3].getColor())&&
			(grid[x][y+3].getColor()==grid[x][y+4].getColor())){
			return grid[x][y].getColor();
		}
		if ((x+4<board_width)&&
			(grid[x][y].getColor()==grid[x+1][y].getColor())&&
			(grid[x+1][y].getColor()==grid[x+2][y].getColor())&&
			(grid[x+2][y].getColor()==grid[x+3][y].getColor())&&
			(grid[x+3][y].getColor()==grid[x+4][y].getColor())){
			return grid[x][y].getColor();
		}
		if ((x+4<board_width)&&(y+4<board_height)&&
			(grid[x][y].getColor()==grid[x+1][y+1].getColor())&&
			(grid[x+1][y+1].getColor()==grid[x+2][y+2].getColor())&&
			(grid[x+2][y+2].getColor()==grid[x+3][y+3].getColor())&&
			(grid[x+3][y+3].getColor()==grid[x+4][y+4].getColor())){
			return grid[x][y].getColor();
		}
		if ((x+4<board_width)&&(y-4>=0)&&
				(grid[x][y].getColor()==grid[x+1][y-1].getColor())&&
				(grid[x+1][y-1].getColor()==grid[x+2][y-2].getColor())&&
				(grid[x+2][y-2].getColor()==grid[x+3][y-3].getColor())&&
				(grid[x+3][y-3].getColor()==grid[x+4][y-4].getColor())){
				return grid[x][y].getColor();
			}
		return null;		
	}
	void checkResult(){
		System.out.println(this.getWidth());
		for (int i=0;i<board_width;i++){
			for (int j=0;j<board_width;j++){
				Color winner=checkWin(i,j);
				if (winner!=null){
					System.out.println(winner.toString());
					announceResult(winner);
					return;
				}
			}
		}
		return;
	}
	String translateColor(Color color){
		if (color==Color.BLACK){
			return "Black";
		} else {
			return "White";
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D)g;
	    if (this.over) {
	    	g2.setFont(new Font("winner",0,30));
	    	String s=translateColor(winner)+" wins!!";
	    	g2.drawString(s,this.getWidth()/2-(int)(g2.getFontMetrics().getStringBounds(s, g2).getWidth())/2,this.getHeight()/2);
	    }
	}
	void announceResult(Color winner){
		this.winner=winner;
		this.over=true;
		//repaint();
		JOptionPane.showMessageDialog(this, translateColor(winner)+" wins!!", "GameOver", JOptionPane.INFORMATION_MESSAGE);
	}
	void alternatePlayer(){
		if (num%2==0){
			status.setText("	Calculating...");
			status.paintImmediately(0, 0, status.getWidth(), status.getHeight());
			Point computerMove=computer.makeMove(this.grid);
			status.setText("	Please make your move");
			System.out.println(computerMove);
			grid[computerMove.x][computerMove.y].mouseClickedInCell();
		}
	}
	Board(GamePanel gamePanel,JLabel status){
		this.status=status;
		this.gamePanel=gamePanel;
		this.setLayout(new GridLayout(board_width,board_height));
		grid=new Cell[board_width][];
		for (int i=0;i<board_width;i++){
			grid[i]=new Cell[board_height];
			for (int j=0;j<board_height;j++){
				grid[i][j]=new Cell(this,i,j);
			}
		}
		for (int i=0;i<board_width;i++){
			for (int j=0;j<board_height;j++){
				this.add(grid[i][j]);
			}
		}
		computer=new AIEngine(this);
		alternatePlayer();
	}
}

class Cell extends JPanel{
	Boolean hidden=true;
	Color color;
	Boolean drawn=false;
	Board board;
	int x,y;
	protected void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.draw(new Line2D.Double(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight()));
		g2.draw(new Line2D.Double(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2));
		if  (!hidden) {
			g2.setColor(color);
			drawn=true;
			g2.fillOval((int)(this.getWidth()*0.05), (int)(this.getHeight()*0.05),(int)(this.getWidth()*0.9), (int)(this.getHeight()*0.9));
		}
	}
	Cell(Board board,int x,int y){
		this.x=x;
		this.y=y;
		this.board=board;
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mouseClickedInCell();				
			}
		});
	}
	void mouseClickedInCell(){
		if (drawn) {return;}
		hidden=false;
		board.num++;
		color=(board.num%2==0)?Color.WHITE:Color.BLACK;
		System.out.print("outout");
		board.setRecent(new Point(x,y));
		repaint();
		board.checkResult();
		board.alternatePlayer();
	}
	Color getColor(){
		return color;
	}
}

//class Announcement extends JPanel