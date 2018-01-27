package repeat����;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

import util.FileStreamUtil;

/**
 * reads ����  
 * ���˵�reads�е�repeat��
 * @author wuxuehong
 * 2011-12-21
 */
public class ReadsDeal {

	byte[] flag = new byte[60000000];
	
	/**
	 * mark the flag
	 * @param inputFile
	 * @throws IOException
	 */
	public void readRepeat(String inputFile)throws IOException{
		BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
		String str = br.readLine();
		Scanner s = null;
		int start,end;
		while(str != null)
		{
			s = new Scanner(str);
			s.next();
			start = s.nextInt();
			end = s.nextInt();
			for(int i=start; i<=end; i++) flag[i] = 1;   //��repeat�����ϱ�ʶ  1
			str = br.readLine();
		}
		br.close();
	}
	
	/**
	 * 
	 * @param inputFile
	 * @param outFile
	 */
	public void readReadsAndOutput(String inputFile, String outFile)throws IOException{
		BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
		BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
		String str = br.readLine(); //���Ե�һ��
		bw.write(str);
		bw.newLine();
		Scanner s = null;
		str = br.readLine();
		int start,end;
		boolean avi = false;  //��ʼ��Ϊfalse  ����read����������
		while(str != null){
			s = new Scanner(str);
			avi = false;
			s.next();s.next();
			start = s.nextInt();
			end = s.nextInt();
			for(int i=start;i<=end;i++){
				if(flag[i]==0){  //�������˷�repeat���Ļ���
					avi = true;  //��read��Ҫ���
					break;
				}
			}
			if(avi){  //read ���з�repeat����  �����
				bw.write(str);
				bw.newLine();
			}
			str = br.readLine();
		}
		br.close();
		bw.flush();
		bw.close();
	}
	
	public ReadsDeal() throws IOException{
		// TODO Auto-generated constructor stub
		for(int i=0;i<60000000;i++)flag[i] = 0;
		readRepeat("E:/�о�������/personal data/persion new data/repeat.txt");
System.out.println("1");
		readReadsAndOutput("E:/�о�������/personal data/persion new data/z=1/readsC2T_crick.txt", 
				"E:/�о�������/personal data/persion new data/z=1/f_readsC2T_crick.txt");
		System.out.println("2");
		readReadsAndOutput("E:/�о�������/personal data/persion new data/z=1/readsC2T_watson.txt", 
				"E:/�о�������/personal data/persion new data/z=1/f_readsC2T_watson.txt");
		System.out.println("3");
		readReadsAndOutput("E:/�о�������/personal data/persion new data/z=1/readsG2A_crick.txt", 
				"E:/�о�������/personal data/persion new data/z=1/f_readsG2A_crick.txt");
		readReadsAndOutput("E:/�о�������/personal data/persion new data/z=1/readsG2A_watson.txt", 
				"E:/�о�������/personal data/persion new data/z=1/f_readsG2A_watson.txt");
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
			try {
				new ReadsDeal();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
