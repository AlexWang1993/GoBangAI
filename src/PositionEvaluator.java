import java.awt.Color;
import java.awt.Point;

public class PositionEvaluator {
	int five=0;
	int four=0;
	int dfour=0;
	int qfour=0;
	int three=0;
	int qthree=0;
	int dthree=0;
	int two=0;
	int dtwo=0;
	static Point dangerousPoint(int[][] grid,int side, int width, int height){
		int vectors[][]=new int[][]{{1,0},{0,1},{1,1},{1,-1}};
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				for (int k=0;k<4;k++){
					if ((i+4*vectors[k][0]<width)&&(j+4*vectors[k][1]<height)&&(j+4*vectors[k][1]>=0)){
						int num=0;
						Point ret=null;
						if ((i==5)&&(j==8)){
							int l=1;
						}
						for (int l=0;l<5;l++){
							if (grid[i+l*vectors[k][0]][j+l*vectors[k][1]]==side){
								num++;
							} else if (grid[i+l*vectors[k][0]][j+l*vectors[k][1]]==-side){
								num=-1;
								break;
							} else {
								ret=new Point(i+l*vectors[k][0],j+l*vectors[k][1]);
							}
						}
						if (num==4){
							System.out.println("dushangle");
							return ret;
						}
					}
				}
			}
		}	
		return null;
	}
	int checkqFour(int[][] grid,int side,int left,int right,int up,int down,int x,int y,boolean bools[]){
		int ret=0;
		if ((x+4<right)&&(!bools[0])){
			if ((grid[x][y]==side)&&(grid[x+4][y]==side)){
				if ((grid[x+1][y]==side)&&(grid[x+2][y]==0)&&(grid[x+3][y]==0)||(grid[x+1][y]==0)&&(grid[x+2][y]==side)&&(grid[x+3][y]==0)||(grid[x+1][y]==0)&&(grid[x+2][y]==0)&&(grid[x+3][y]==side)){
					ret++;
					bools[0]=true;					
				}
			}
		}
		if ((y+4<down)&&(!bools[1])){
			if ((grid[x][y]==side)&&(grid[x][y+4]==side)){
				if ((grid[x][y+1]==0)&&(grid[x][y+2]==side)&&(grid[x][y+3]==0)||(grid[x][y+1]==0)&&(grid[x][y+2]==side)&&(grid[x][y+3]==0)||(grid[x][y+1]==0)&&(grid[x][y+2]==0)&&(grid[x][y+3]==side)){
					ret++;
					bools[1]=true;					
				}
			}		
		}
		if ((x+4<right)&&(y+4<down)&&(!bools[2])){
			if ((grid[x][y]==side)&&(grid[x+4][y+4]==side)){
				if ((grid[x+1][y+1]==side)&&(grid[x+2][y+2]==0)&&(grid[x+3][y+3]==0)||(grid[x+1][y+1]==0)&&(grid[x+2][y+2]==side)&&(grid[x+3][y+3]==0)||(grid[x+1][y+1]==0)&&(grid[x+2][y+2]==0)&&(grid[x+3][y+3]==side)){
					ret++;
					bools[2]=true;					
				}
			}				
		}
		if ((x-4>=left)&&(y+4<down)&&(!bools[3])){
			if ((grid[x][y]==side)&&(grid[x-4][y+4]==side)){
				if ((grid[x-1][y+1]==side)&&(grid[x-2][y+2]==0)&&(grid[x-3][y+3]==0)||(grid[x-1][y+1]==0)&&(grid[x-2][y+2]==side)&&(grid[x-3][y+3]==0)||(grid[x-1][y+1]==0)&&(grid[x-2][y+2]==0)&&(grid[x-3][y+3]==side)){
					ret++;
					bools[3]=true;					
				}
			}						
		}
		return ret;
	}
	int checkqThree(int[][] grid,int side,int left, int right, int up, int down,int x,int y,boolean bools[]){
		if (grid[x][y]!=0) return 0;
		int ret=0;
		if ((x+5<right)&&(!bools[0])){
			if ((grid[x+1][y]==side)&&(grid[x+4][y]==side)&&(grid[x+5][y]==0)){
				if ((grid[x+2][y]==side)&&(grid[x+3][y]==0)||(grid[x+2][y]==0)&&(grid[x+3][y]==side)){
					ret++;
					bools[0]=true;					
				}
			}
		}
		if ((y+5<down)&&(!bools[1])){
			if ((grid[x][y+1]==side)&&(grid[x][y+4]==side)&&(grid[x][y+5]==0)){
				if ((grid[x][y+2]==side)&&(grid[x][y+3]==0)||(grid[x][y+2]==0)&&(grid[x][y+3]==side)){
					ret++;
					bools[1]=true;					
				}
			}
		}
		if ((x+5<right)&&(y+5<down)&&(!bools[2])){
			if ((grid[x+1][y+1]==side)&&(grid[x+4][y+4]==side)&&(grid[x+5][y+5]==0)){
				if ((grid[x+2][y+2]==side)&&(grid[x+3][y+3]==0)||(grid[x+2][y+2]==0)&&(grid[x+3][y+3]==side)){
					ret++;
					bools[2]=true;					
				}
			}
		}
		if ((x-5>=0)&&(y+5<down)&&(!bools[3])){
			if ((grid[x-1][y+1]==side)&&(grid[x-4][y+4]==side)&&(grid[x-5][y+5]==0)){
				if ((grid[x-2][y+2]==side)&&(grid[x-3][y+3]==0)||(grid[x-2][y+2]==0)&&(grid[x-3][y+3]==side)){
					ret++;
					bools[3]=true;					
				}
			}
		}
		return ret;
	}
	int checkNum(int[][] grid,int side,int left, int right, int up, int down,int x,int y,int num, boolean bools[]){
		if (grid[x][y]!=0) return 0;
		int ret=0;
		if ((x+num+1<right)&&(!bools[0])){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x+1+i][y]!=side){
					fl=false;
					break;
				}
			}
			if ((grid[x+1+num][y]!=0)||(grid[x][y]!=0)){
				fl=false;
			}
			if (fl) {
				ret++;
				bools[0]=true;
			}
		}
		if ((y+num+1<down)&&(!bools[1])){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x][y+1+i]!=side){
					fl=false;
					break;
				}
			}
			if ((grid[x][y+1+num]!=0)||(grid[x][y]!=0)){
				fl=false;
			}
			if (fl) {
				ret++;
				bools[1]=true;			
			}
		}
		if ((x+num+1<right)&&(y+num+1<down)&&(!bools[2])){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x+1+i][y+1+i]!=side){
					fl=false;
					break;
				}
			}
			if ((grid[x+1+num][y+1+num]!=0)||(grid[x][y]!=0)){
				fl=false;
			}
			if (fl) {
				ret++;
				bools[2]=true;				
			}
		}
		if ((x-num-1>=left)&&(y+num+1<down)&&(!bools[3])){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x-1-i][y+1+i]!=side){
					fl=false;
					break;
				}
			}
			if ((grid[x-1-num][y+1+num]!=0)||(grid[x][y]!=0)){
				fl=false;
			}
			if (fl){
				ret++;
				bools[3]=true;				
			}
		}
		return ret;
	}
	int checkdNum(int[][] grid,int side,int left,int right,int up,int down,int x,int y,int num, boolean bools[]){
		int ret=0;
		if ((x+num<right)&&(!bools[0])){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x+1+i][y]!=side){
					fl=false;
					break;
				}
			}
			if (((x+1+num==right)||(grid[x+1+num][y]!=0))&&(grid[x][y]!=0)){
				fl=false;
			}
			if (fl){
				ret++;
				bools[0]=true;
			}
		}
		/*if (x-num>=0){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x-1-i][y]!=side){
					fl=false;
					break;
				}
			}
			if (((x-1-num<0)||(grid[x-1-num][y]!=0))&&(grid[x][y]!=0)){
				fl=false;
			}
			if (fl) return true;
		}*/
		if ((y+num<down)&&(!bools[1])){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x][y+1+i]!=side){
					fl=false;
					break;
				}
			}
			if (((y+1+num==down)||(grid[x][y+1+num]!=0))&&(grid[x][y]!=0)){
				fl=false;
			}
			if (fl) {
				ret++;
				bools[1]=true;
			};			
		}
		/*if (y-num>=0){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x][y-i-1]!=side){
					fl=false;
					break;
				}
			}
			if (((y-num-1<0)||(grid[x][y-num-1]!=0))&&(grid[x][y]!=0)){
				fl=false;
			}
			if (fl) return true;
		}*/
		if ((x+num<right)&&(y+num<down)&&(!bools[2])){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x+1+i][y+1+i]!=side){
					fl=false;
					break;
				}
			}
			if (((y+1+num==down)||(x+1+num==right)||(grid[x+1+num][y+1+num]!=0))&&(grid[x][y]!=0)){
				fl=false;
			}
			if (fl) {
				ret++;
				bools[2]=true;
			};				
		}
		/*if ((x-num>=0)&&(y+num<height)){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x-1-i][y+1+i]!=side){
					fl=false;
					break;
				}
			}
			if (((y+1+num==height)||(x-1-num<0)||(grid[x-1-num][y+1+num]!=0))&&(grid[x][y]!=0)){
				fl=false;
			}
			if (fl) return true;				
		}*/
		if ((x-num>=left)&&(y+num<down)&&(!bools[3])){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x-1-i][y+1+i]!=side){
					fl=false;
					break;
				}
			}
			if (((x-1-num<left)||(y+1+num==down)||(grid[x-1-num][y+1+num]!=0))&&(grid[x][y]!=0)){
				fl=false;
			}
			if (fl) {
				ret++;
				bools[3]=true;
			};					
		}
		/*if ((x-num>=0)&&(y-num>=0)){
			boolean fl=true;
			for (int i=0;i<num;i++){
				if (grid[x-1-i][y-1-i]!=side){
					fl=false;
					break;
				}
			}
			if (((y-1-num<0)||(x-1-num<0)||(grid[x-1-num][y-1-num]!=0))&&(grid[x][y]!=0)){
				fl=false;
			}
			if (fl) return true;				
		}*/
		return ret;
	}
	boolean checkWin(int[][] grid,int side,int left,int right, int up, int down,int x,int y){
		if (grid[x][y]!=side) return false;
		if ((y+4<down)&&
			(grid[x][y]==grid[x][y+1])&&
			(grid[x][y+1]==grid[x][y+2])&&
			(grid[x][y+2]==grid[x][y+3])&&
			(grid[x][y+3]==grid[x][y+4])){
			return true;
		}
		if ((x+4<right)&&
			(grid[x][y]==grid[x+1][y])&&
			(grid[x+1][y]==grid[x+2][y])&&
			(grid[x+2][y]==grid[x+3][y])&&
			(grid[x+3][y]==grid[x+4][y])){
			return true;
		}
		if ((x+4<right)&&(y+4<down)&&
			(grid[x][y]==grid[x+1][y+1])&&
			(grid[x+1][y+1]==grid[x+2][y+2])&&
			(grid[x+2][y+2]==grid[x+3][y+3])&&
			(grid[x+3][y+3]==grid[x+4][y+4])){
			return true;
		}
		if ((x-4>=left)&&(y+4<down)&&
				(grid[x][y]==grid[x-1][y+1])&&
				(grid[x-1][y+1]==grid[x-2][y+2])&&
				(grid[x-2][y+2]==grid[x-3][y+3])&&
				(grid[x-3][y+3]==grid[x-4][y+4])){
				return true;
			}
		return false;		
	}
	int evaluate(int[][] grid,int side,int left,int right,int up,int down){
		for (int i=left;i<=right;i++){
			boolean fl=false;
			for (int j=up;j<=down;j++){
				//if (grid[i][j]!=side) continue;
				if (this.checkWin(grid, side, left, right+1, up, down+1, i, j)){
					five++;
					fl=true;
					break;
				}
				boolean bools[]=new boolean[]{false,false,false,false};
				//System.out.println(left+" "+right+" "+up+" "+down);
				qfour+=this.checkqFour(grid, side, left, right+1, up, down+1, i, j, bools);
				qthree+=this.checkqThree(grid, side, left, right+1, up, down+1, i, j, bools);
				two+=this.checkNum(grid, side, left, right+1, up, down+1, i, j, 2, bools);
				three+=this.checkNum(grid, side, left, right+1, up, down+1, i, j, 3, bools);
				four+=this.checkNum(grid, side, left, right+1, up, down+1, i, j, 4, bools);
				dtwo+=this.checkdNum(grid, side, left, right+1, up, down+1, i, j, 2, bools);
				dthree+=this.checkdNum(grid, side, left, right+1, up, down+1, i, j, 3, bools);
				dfour+=this.checkdNum(grid, side, left, right+1, up, down+1, i, j, 4, bools);
			}
			if (fl) break;
		}
		if (three+qthree+dfour+qfour>=2){
			three*=2;
			qthree*=2;
			dfour*=2;
			qfour*=2;
		}
		return five*1200+four*600+dfour*120+qfour*120+three*100+qthree*85+dthree*40+two*30+dtwo*10;
		/*if (five>0){
			return 400;
		} else if ((four>0)||(dfour>=2)||((dfour>=1)&&(three>=1))){
			return 200;
		} else if (three>=2){
			return 130;
		} else if ((three>=1)&&(dthree>=1)){
			return 100;
		} else if (dfour>=1){
			return 80;
		} else if (three>=1){
			return 50;
		} else if ((dthree>=1)&&(two>=1)){
			return 45;
		} else if (two>=2){
			return 40;
		} else if (dthree>=1){
			return 30;
		} else if (two>=1){
			return 20;
		} else if (dtwo>=1){
			return 10;
		} else {
			return 0;
		}*/
	}
}
