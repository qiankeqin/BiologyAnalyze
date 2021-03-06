package statics;

import java.io.BufferedReader;
import java.util.Scanner;

import util.FileStreamUtil;


/**
 * 统计ref中所有CG 的甲基化率
 * 即： 甲基化的CG个数 除以ref中总的CG个数
 * @author wuxuehong
 * 2012-1-11
 */
public class CpG {
	
	//#CGr2-Ref中CG的个数:39039314
	//#CGr2-Ref中CA的个数:218299536
	private int totalCg = 218299536;
	private int mCg = 0;
	
	//consisitent率超过0.5则认为该位置上甲基化了
	private float door = 0.5f;

	//读取计算了consistent率的文件
	public void readCg(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			Scanner s = null;
			float m;
			while(str != null){
				s = new Scanner(str);
				s.next();s.next();s.next();s.next();
				m = Float.parseFloat(s.next());
				if(m>=door)
					mCg++;
				str = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public CpG() {
		for(int i=1;i<=22;i++){
			System.out.println("reading:"+i);
			readCg("E:/ca2/hg19_Chr"+i+".txt");
		}
		readCg("E:/ca2/hg19_ChrX.txt");
		System.out.println("m Ca:"+mCg);
		System.out.println("total Ca:"+totalCg);
		System.out.println((float)mCg/(float)totalCg);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CpG();
	}

}
