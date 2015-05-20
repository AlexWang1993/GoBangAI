import java.awt.*;
import java.lang.Math.*;
import java.util.*;

class CounterBack {
	boolean searched;
	Point pos;
	int value;
	ArrayList<CounterBack> counterBacks=new ArrayList<CounterBack>();
	ArrayList<Point> counterPointList=new ArrayList<Point>();
	CounterBack counterBackGrid[][];
	boolean maximizing;
	int left,right,up,down,width,height;
	void generatePointList(){
		for (int i=left;i<=right;i++){
			for (int j=up;j<=down;j++){
				if (this.counterBackGrid[i][j]!=null){
					counterBacks.add(this.counterBackGrid[i][j]);
				} else {
					CounterBack pruned=new CounterBack(width,height,new Point(i,j),!maximizing,0,0,0,0);
					pruned.setValue((maximizing)?-19999:19999);
					counterBacks.add(pruned);
				}
			}
		}
		Collections.sort(counterBacks, new CounterBackCompare((right-left+1)/2+left,(down-up+1)/2+up,maximizing));
		Collections.reverse(counterBacks);
		int mark=0;
		while ((mark<counterBacks.size())&&(Math.abs(counterBacks.get(mark).value)!=19999)){
			mark++;
		}
		mark=mark+(counterBacks.size()-1-mark)/3*2;
		int counter=0;
		//while (mark<counterBacks.size()) {counter++; counterBacks.remove(mark);}
		if (counter>0){
			System.out.println("deleted"+':'+counter);
		}
		for (int i=0;i<counterBacks.size();i++){
			counterPointList.add(counterBacks.get(i).pos);
			if (counterBacks.get(i).searched){
				counterBacks.get(i).generatePointList();
			}
		}
		
	}
	CounterBack(int width,int height,Point point,boolean maximizing,int left,int right,int up,int down){
		this.width=width;
		this.height=height;
		searched=false;
		counterBackGrid=new CounterBack[width][];
		for (int i=0;i<height;i++){
			counterBackGrid[i]=new CounterBack[height];
		}
		this.pos=point;
		this.maximizing=maximizing;
		this.left=left;
		this.right=right;
		this.up=up;
		this.down=down;
	}
	CounterBack(int width,int height,Point point,boolean maximizing){
		this.width=width;
		this.height=height;
		searched=false;
		this.pos=point;
		this.maximizing=maximizing;
	}
	void setValue(int value){
		this.value=value;
	}
	
}
class Result {
	int value;
	Point pos;
	boolean valid;
	Point searchedSpace[][]=null;
	Result(int value,Point pos,boolean valid){
		this.value=value;
		this.pos=pos;
		this.valid=valid;
	}
}
public class AIEngine {
	static int depth=4;
	Board board;
	int[][] grid;
	int side;
	int height,width;
	Point lastStep;
	boolean first=true;
	CounterBack history;
	AIEngine(Board board){
		this.board=board;
		height=board.board_height;
		width=board.board_width;
		grid=new int[board.board_height][];
		for (int i=0;i<board.board_height;i++){
			grid[i]=new int[board.board_width];
		}
		side=-1;
		for (int i=0;i<board.board_height;i++){
			for (int j=0;j<board.board_width;j++){
				grid[i][j]=0;
			}
		}
	}
	boolean checkWin(int[][] grid,int side,int width,int height,int x,int y){
		if (grid[x][y]!=side) return false;
		int x1,y1,x2,y2;
		x1=x;
		y1=y;
		x2=x;
		y2=y;
		while ((y1>=0)&&(grid[x][y1]==side)) {
			y1--;
		}
		while ((y2<height)&&(grid[x][y2]==side)) {
			y2++;
		}
		if (y2-y1>5){return true;}
		x1=x;
		y1=y;
		x2=x;
		y2=y;
		while ((x1>=0)&&(grid[x1][y]==side)) {
			x1--;
		}
		while ((x2<width)&&(grid[x2][y]==side)) {
			x2++;
		}
		if (x2-x1>5){return true;}
		x1=x;
		y1=y;
		x2=x;
		y2=y;
		while ((x1>=0)&&(y1>=0)&&(grid[x1][y1]==side)) {
			x1--;y1--;
		}
		while ((x2<width)&&(y2<height)&&(grid[x2][y2]==side)) {
			x2++;y2++;
		}
		if (x2-x1>5){return true;}
		x1=x;
		y1=y;
		x2=x;
		y2=y;
		while ((x1<width)&&(y1>=0)&&(grid[x1][y1]==side)) {
			x1++;y1--;
		}
		while ((x2>=0)&&(y2<height)&&(grid[x2][y2]==side)) {
			x2--;y2++;
		}
		if (y2-y1>5){return true;}
		return false;		
	}
	Point makeMove(Cell[][] grid){
		if (first){
			first=false;
			if (side==1){
			for (int i=0;i<board.board_height;i++){
				for (int j=0;j<board.board_width;j++){
					if (grid[i][j].getColor()!=null){
						return new Point(i+1,j+1);
					}
				}
			}
			} else {
				return new Point(width/2,height/2);
			}
		}
		for (int i=0;i<board.board_height;i++){
			for (int j=0;j<board.board_width;j++){
				if (grid[i][j].getColor()==Color.BLACK){
					this.grid[i][j]=-1;
				} else if  (grid[i][j].getColor()==Color.WHITE){
					this.grid[i][j]=1;
				} else {
					this.grid[i][j]=0;
				}
			}
		}
		if ((history!=null)&&(history.searched)){
			history=history.counterBackGrid[board.recent.x][board.recent.y];
		}
		Point dPoint=PositionEvaluator.dangerousPoint(this.grid, side, width, height);
		if (dPoint!=null){
			if ((history!=null)&&(history.searched)){
				history=history.counterBackGrid[dPoint.x][dPoint.y];
			}
			return dPoint;
		}
		dPoint=PositionEvaluator.dangerousPoint(this.grid, -side, width, height);
		if (dPoint!=null){
			if ((history!=null)&&(history.searched)){
				history=history.counterBackGrid[dPoint.x][dPoint.y];
			}
			return dPoint;
		}
		int left=99;
		int right=-2;
		int up=99;
		int down=-2;
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				if (this.grid[i][j]!=0){
					if (i-1<left){left=Math.max(0,i-1);}
					if (i+1>right){right=Math.min(i+1,width-1);}
					if (j-1<up){up=Math.max(0,j-1);}
					if (j+1>down){down=Math.min(j+1,width-1);}
				}
			}
		}
		if ((history!=null)&&(history.searched)){
			history.generatePointList();
		}
		CounterBack record=new CounterBack(width, height, null, true, left, right, up, down);
		alphabeta(this.grid,3,-19999,19999,true,side,left,right,up,down,null,record);
		history=record;
		record=new CounterBack(width, height, null, true, left, right, up, down);
		history.generatePointList();
		Result ret=alphabeta(this.grid,3,-19999,19999,true,side,left,right,up,down,history,record);
		history=record;
		if (history!=null){
			history=history.counterBackGrid[ret.pos.x][ret.pos.y];
		}
		if ((ret.pos.x==-1)&&(ret.pos.y==-1)){
			while (true){
			int x=new Random().nextInt(13);
			int y=new Random().nextInt(13);
			if (board.grid[x][y].getColor()==null){
				return new Point(x,y);
			}
		}
		}
		return ret.pos;
	}
	Result alphabeta(int[][] node,int depth,int a,int b,boolean maximizingPlayer,int side,int left,int right,int up,int down,CounterBack history,CounterBack record){
		//System.out.println(depth);
		
		if (depth==0){
			return new Result(evaluate(node,left,right,up,down),new Point(0,0),true);
		}
		Point move=null;
		ArrayList<Point> points;
		if ((history==null)||(history.searched==false)){
			points=this.generatePointList(node, left, right, up, down);
		} else {
			points=history.counterPointList;
			System.out.println(depth+":"+points);
		}
		//System.out.println(points.size());
		record.searched=true;
		if (depth==1){
			int l=1;
		}
		if (maximizingPlayer){
			int ans=-19999;
			for (int k=0;k<points.size();k++){
				int i=points.get(k).x;
				int j=points.get(k).y;
					if (node[i][j]==0){
						if ((depth==3)&&(i==8)&&(j==9)){
							int l=1;
						}
						if ((depth==1)&&(i==8)&&(j==9)){
							int l=1;
						}
						node[i][j]=side;
						lastStep=new Point(i,j);
						Result result;
						if (depth==1){
							record.counterBackGrid[i][j]=new CounterBack(width,height,new Point(i,j),false);
						} else {
							record.counterBackGrid[i][j]=new CounterBack(width,height,new Point(i,j),false,Math.min(left,Math.max(0,i-1)),Math.max(right,Math.min(width-1,i+1)),Math.min(up,Math.max(0,j-1)),Math.max(down,Math.min(height-1,j+1)));
						}
						if (this.checkWin(node, side, width, height, i, j)){
							result=new Result((side==this.side)?10000:-10000,new Point(-1,-1),true);
							record.counterBackGrid[i][j].value=result.value;
						} else {
							result=alphabeta(node,depth-1,a,b,!maximizingPlayer,-side,Math.min(left,Math.max(0,i-1)),Math.max(right,Math.min(width-1,i+1)),Math.min(up,Math.max(0,j-1)),Math.max(down,Math.min(height-1,j+1)),((history!=null)&&(history.searched!=false))?history.counterBackGrid[i][i]:null,record.counterBackGrid[i][j]);
						}
						node[i][j]=0;
						if (!result.valid) continue;
						if (result.value>ans){
							move=new Point(i,j);
							ans=result.value;
						}
						if (result.value>a){
							a=result.value;
						}
						if (b<=a){
							//System.out.println("works");
							return new Result(0,null,false);
						}
					}
			}
			record.value=ans;
			return new Result(ans,move,true);
		} else {
			int ans=19999;
			for (int k=0;k<points.size();k++){
				int i=points.get(k).x;
				int j=points.get(k).y;
					if (node[i][j]==0){
						if ((depth==2)&&(i==5)&&(j==6)){
							int l=1;
						}
						node[i][j]=side;
						lastStep=new Point(i,j);
						Result result;
						if (depth==1){
							record.counterBackGrid[i][j]=new CounterBack(width,height,new Point(i,j),true);
						} else {
							record.counterBackGrid[i][j]=new CounterBack(width,height,new Point(i,j),true,Math.min(left,Math.max(0,i-1)),Math.max(right,Math.min(width-1,i+1)),Math.min(up,Math.max(0,j-1)),Math.max(down,Math.min(height-1,j+1)));
						}
						if (this.checkWin(node, side, width, height, i, j)){
							result=new Result((side==this.side)?10000:-10000,new Point(-1,-1),true);
							record.counterBackGrid[i][j].value=result.value;
						} else {
							result=alphabeta(node,depth-1,a,b,!maximizingPlayer,-side,Math.min(left,Math.max(0,i-1)),Math.max(right,Math.min(width-1,i+1)),Math.min(up,Math.max(0,j-1)),Math.max(down,Math.min(height-1,j+1)),((history!=null)&&(history.searched!=false))?history.counterBackGrid[i][i]:null,record.counterBackGrid[i][j]);
						}
						node[i][j]=0;
						if ((depth==2)&&(result.value==10000)){
							int l=1;
						}
						if (!result.valid) continue;
						if (result.value<ans){
							move=new Point(i,j);
							ans=result.value;
						}
						if (result.value<b){
							b=result.value;
						}
						if (b<=a){
							return new Result(0,null,false);
						}
					}
			}	
			record.value=ans;
			return new Result(ans,move,true);
		}
	}
	int evaluate(int[][] node,int left,int right,int up,int down){
		/*int AIScore=new PositionEvaluator().evaluate(node, side, width, height);
		node[lastStep.x][lastStep.y]=-node[lastStep.x][lastStep.y];
		int ret= AIScore-
				(new PositionEvaluator().evaluate(node, -side, width, height));
		node[lastStep.x][lastStep.y]=-node[lastStep.x][lastStep.y];
		return ret;*/
		int ret=((new PositionEvaluator().evaluate(node, side, left,right,up,down))/3*5)-(new PositionEvaluator().evaluate(node, -side, left,right,up,down));
		if (ret==110){
			int l=1;
		}
		return ret;
		//return (new PositionEvaluator().evaluate(node, side, width, height))-(new PositionEvaluator().evaluate(node, -side, width, height));
	}
	ArrayList<Point> generatePointList(int grid[][],int left,int right,int up,int down){
		ArrayList<Point> ret=new ArrayList<Point>();
		for (int i=left;i<=right;i++){
			for (int j=up;j<=down;j++){
				if (grid[i][j]==0){
					ret.add(new Point(i,j));
				}
			}
		}
		int midx=(right-left+1)/2+left;
		int midy=(down-up+1)/2+up;
		Collections.sort(ret,new PointCompare(midx,midy));
		return ret;
		
	}
	
}


class PointCompare implements Comparator<Point> {
	int midx,midy;
	PointCompare(int midx,int midy){
		this.midx=midx;
		this.midy=midy;
	}
    public int compare(Point a, Point b) {
    	int da=Math.abs(a.x-midx)+Math.abs(a.y-midy);
    	int db=Math.abs(b.x-midx)+Math.abs(b.y-midy);
        if (da<db){
        	return -1;
        } else if (da>db){
        	return 1;
        } else {
        	return 0;
        }
    }
}

class CounterBackCompare implements Comparator<CounterBack> {
	int midx,midy;
	int minimizing;
	CounterBackCompare(int midx,int midy,boolean maximizing){
		this.midx=midx;
		this.midy=midy;
		if (maximizing){
			minimizing=1;
		} else {
			minimizing=-1;
		}
	}
    public int compare(CounterBack a, CounterBack b) {
    	int va=a.value;
    	int vb=b.value;
        if (minimizing*va<minimizing*vb){
        	return -1;
        } else if (minimizing*va>minimizing*vb){
        	return 1;
        } else {
        	int da=Math.abs(a.pos.x-midx)+Math.abs(a.pos.y-midy);
        	int db=Math.abs(b.pos.x-midx)+Math.abs(b.pos.y-midy);
            if (minimizing*da<minimizing*db){
            	return -1;
            } else if (minimizing*da>minimizing*db){
            	return 1;
            } else {
            	return 0;
            }
        	
        }
    }
}

