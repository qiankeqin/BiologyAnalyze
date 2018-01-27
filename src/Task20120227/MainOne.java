package Task20120227;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Scanner;

import util.FileStreamUtil;

/**
 * �������ϰ�ĵķ�����Qq�������ˡ�
˵�������ڷָ�ǰ�ĵ�3��ͼ�͵�4��ͼ���ı�һ��
������ 2012-2-28 01:00:46
�����겻��reads�ĳ��ȣ�
������ 2012-2-28 01:02:25
��reads�ֳ����䣬����40-80,80-120,120-160�ȣ������������㶨��

Y����ÿ��������mapped ��reads��/ÿһ��������reads����

 * @author wuxuehong
 * 2012-2-28
 */
public class MainOne {
	
	int total[] = new int[1000];
	int map[] = new int[1000];
	int umap[] = new int[1000];
	
	public void readFile(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			Scanner s = null;
			int index;
			while(str != null){
				s = new Scanner(str);
				index = s.nextInt();
				total[index] = s.nextInt();
				map[index] = s.nextInt();
				umap[index] = s.nextInt();
				str = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void writeFile(String filename,int period){
		try{
			BufferedWriter bw = FileStreamUtil.getBufferedWriter("matched_period_chart_out.txt");
			for(int i=40;i<1000;){
				int index = i;
				int totalt = 0;
				int totalm = 0;
				int totalu = 0;
				double aa = 0;
				double bb = 0;
				double cc = 0;
				System.out.print("["+i+","+(i+period)+")\t");
				bw.write("["+i+","+(i+period)+")\t");
				for(int j=index;j<index+period && j<1000;j++){
					i++;
					totalt+=total[j];
					totalm+=map[j];
					totalu+=umap[j];
				}
				System.out.println(totalt+"\t"+totalm+"\t"+totalu+"\t"+(double)totalm/(double)totalt+"\t"+(double)totalu/(double)totalt+"\t"+(double)totalu/(double)totalm);
				bw.write(totalt+"\t"+totalm+"\t"+totalu+"\t"+(double)totalm/(double)totalt+"\t"+(double)totalu/(double)totalt+"\t"+(double)totalu/(double)totalm);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public MainOne(String args[]){
		if(args.length != 1)
		{
			System.out.println("error paramaters!");
			return;
		}
		int period = Integer.parseInt(args[0]);
		readFile("matched_chart_out.txt");
		writeFile("matched_period_chart_out.txt", period);
	}
	
	public static void main(String args[]){
		new MainOne(args);
	}
 
}
