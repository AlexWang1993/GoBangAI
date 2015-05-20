
public class Patterns {
	OnePattern patternList[];
	private Patterns instance=null;
	public Patterns getInstance(){
		if (instance==null){
			instance=new Patterns();
		}
		return instance;
	}
//	static int movePattern(OnePattern pattern,int pos,int side){
		
	//}
	static int getNumFromArray(int line[]){
		return line[5]+line[4]*3+line[3]*3*3+line[2]*3*3*3+line[1]*3*3*3*3+line[0]*3*3*3*3*3;
	}
	private Patterns(){
		this.patternList=new OnePattern[3*3*3*3*3*3];
		int num=0;
		for (int i1=-1;i1<1;i1++){
			for (int i2=-1;i2<1;i2++){
				for (int i3=-1;i3<1;i3++){
					for (int i4=-1;i4<1;i4++){
						for (int i5=-1;i5<1;i5++){
							for (int i6=-1;i6<1;i6++){
								this.patternList[num]=new OnePattern(new int[]{i1,i2,i3,i4,i5,i6});
							}
						}
					}
				}
			}
		}
		patternList[getNumFromArray(new int[]{1,1,1,1,1,0})].scoreW=Constant.FIVE;
		//patternList[getNumFromArray(new int[]{1,1,1,1,1,1})].scoreW=Constant.FIVE;
		patternList[getNumFromArray(new int[]{1,1,1,1,1,-1})].scoreW=Constant.FIVE;
		patternList[getNumFromArray(new int[]{0,1,1,1,1,0})].scoreW=Constant.FOUR;
		patternList[getNumFromArray(new int[]{-1,1,1,1,1,0})].scoreW=Constant.dFOUR;
		patternList[getNumFromArray(new int[]{0,1,1,1,1,-1})].scoreW=Constant.dFOUR;
		patternList[getNumFromArray(new int[]{1,1,1,0,1,-1})].scoreW=Constant.qFOUR;
		patternList[getNumFromArray(new int[]{1,1,0,1,1,-1})].scoreW=Constant.qFOUR;
		patternList[getNumFromArray(new int[]{1,0,1,1,1,0})].scoreW=Constant.qFOUR;
		patternList[getNumFromArray(new int[]{1,1,0,1,1,0})].scoreW=Constant.qFOUR;
		patternList[getNumFromArray(new int[]{1,1,0,1,1,0})].scoreW=Constant.qFOUR;
		patternList[getNumFromArray(new int[]{1,1,0,1,1,0})].scoreW=Constant.qFOUR;
	}
}

class OnePattern{
	int line[];
	int scoreW,scoreB;
	OnePattern(int line[]){
		this.line=new int[6];
		for (int i=0;i<6;i++){
			this.line[i]=line[i];
		}
	}
}

class Constant{
	static final int FIVE=400;
	static final int FOUR=400;
	static final int dFOUR=400;
	static final int qFOUR=400;
	static final int THREE=400;
	static final int dTHREE=400;
	static final int qTHREE=400;
	static final int TWO=400;
	static final int dTWO=400;
}