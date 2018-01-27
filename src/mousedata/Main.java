package mousedata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import util.FileStreamUtil;

/**
 * ��ref�����д�д��ĸ��Ҳ��Сд��ĸ��Сд��ĸ����repeat����
 * �������ڻ���Ҫ���ǰ�æ��readsƥ��Ľ���ٹ���һ�£�
 * ��ȫ������repeat����reads���˵��������ǣ�
 * ���ÿ��reads��ref���Ӧ���Ƿ����Сд��
 * ֻҪ�д�д��ĸ��������z=1��z=100�Ľ���������¡�
 * @author wuxuehong
 * 2011-11-15
 */
public class Main {
	
	private static final int WATSON = 0x1 ;
	private static final int CRICK = 0x2;
	
	/**
	 * for watson
	 * @param leftmost
	 * @return
	 */
	public long getStartPointerWatson(long leftmost){
		long start = 0;
		leftmost--;
		start += 81; //��һ�� ���ݹ���
		long hang = leftmost/50;   //50��ÿ�е��ַ�����
		long mod = leftmost%50; //���һ���ַ�����
		start += 52*hang;
		start = start+mod; 
		return start;
	}
	
	/**
	 * for crick
	 * @param leftmost
	 * @return
	 */
	public long getStartPointerCrick(long leftmost){
		long start = 0;
		leftmost--;
		start += 81; //��һ�� ���ݹ���
		start += 18;  //��һ�в���50�ַ���
		leftmost -= 16;
		long hang = leftmost/50;   //50��ÿ�е��ַ�����
		long mod = leftmost%50; //���һ���ַ�����
		start += 52*hang;
		start = start+mod; 
		return start;
	}
	
	/**
	 * 
	 * @param ref
	 * @param sam
	 * @param output
	 * @throws IOException
	 */
	public void refReadsAndFilterRuelst(int style, String ref, String sam, String output) throws IOException{
		 RandomAccessFile  rf  = null;
		 rf = FileStreamUtil.getRAF(ref);
//		 rf.seek(getStartPointerWatson(51));
//		 for(int i=0;i<10;){
//			 int a = rf.read();
//			 if (a=='\n'||a=='\r'){
//				 continue;
//			 }
//			 i++;
//			 System.out.print((char)a);
//		 }
//		 System.out.println();
//		 
//		 System.out.println("CTATTaaacgagttcaacataaaaagacattt".length());
//		 if(1==1)return;
		 
		 BufferedReader br = FileStreamUtil.getBufferedReader(sam);
		 BufferedWriter bw = FileStreamUtil.getBufferedWriter(output);
		 
		 String str = br.readLine(); //ignorance the first line
		 bw.write(str);
		 bw.newLine();
		 
		 Scanner s = null;
		 str = br.readLine();
		 long leftmost = 0;
		 long length = 0;
		 int c = -1;
		 boolean avi = false;
		 while(str != null){
//	System.out.println(str);
			 s = new Scanner(str);
			 s.next();s.next();
			 leftmost = Long.parseLong(s.next()); //��ȡƥ��ref�е�λ��
			 for(int i=0;i<5;i++)s.next();
			 length = s.next().length();  //��ȡseq����
			 
			 switch(style){
			 case WATSON:  
				 rf.seek(getStartPointerWatson(leftmost));  //��ȡ��ref�ļ��е�λ��
				 break;
			 case CRICK:
				 rf.seek(getStartPointerCrick(leftmost));  //��ȡ��ref�ļ��е�λ��
				 break;
			 }
//			 rf.seek(getStartPointerWatson(leftmost));  //��ȡ��ref�ļ��е�λ��
//			 rf.seek(getStartPointerCrick(leftmost)); //crick
			 c = -1;
			 avi = false;
			 for(int i=0; i<length; ){
				 c = rf.read();
				 if (c=='\n'||c=='\r'){
					 continue;
				 }
				 if(c>='A'&&c<='Z'){
					 avi = true;
					 break;
				 }
				 i++;
			 }
			 if(avi){  //���ƥ���seq  strand�д�д��ĸ�����
				 bw.write(str);
				 bw.newLine();
			 }
			str = br.readLine();
		 }
		 rf.close();
		 br.close();
		 bw.flush();
		 bw.close();
	}
	
	
	/**
	 * �ַ�������
	 * @param str
	 * @return
	 */
	public String convert(String str){
		char[] c = str.toCharArray();
		int len = c.length;
		int first = 0;
		int last = len-1;
		char temp ;
		while(first<last){
			temp = c[first];
			c[first] = c[last];
			c[last] = temp;
			first++;
			last--;
		}
		return String.valueOf(c);
	}
	/**
	 * ���ļ������ݷ������
	 * @param inputFile
	 * @param outFile
	 */
	public void reserveFile(String inputFile,String outFile){
		 RandomAccessFile  rf  = null;
		 BufferedWriter bw = null; 
	    try {
	      rf = FileStreamUtil.getRAF(inputFile);
	      bw = FileStreamUtil.getBufferedWriter(outFile);
	      long len = rf.length();
	      long start = rf.getFilePointer();
	      System.out.println(start);
	      rf.seek(0);
	      bw.write(rf.readLine());
	      bw.newLine();
	      long nextend = start + len - 1;
	      String line;
	      rf.seek(nextend);
	      int c = -1;
	      while (nextend > start) {
	        c = rf.read();
	        if (c == '\n' || c == '\r') {
	          line = rf.readLine();
	        if(line == null)
	        {
	        	nextend -= 2;
	        	rf.seek(nextend);
	        	continue;
	        }
	          bw.write(convert(line)); 
	          bw.newLine();
	          nextend--;
	        }
	        nextend--;
	        rf.seek(nextend);
	        if (nextend == 0) {// ���ļ�ָ�������ļ���ʼ���������һ��
	          System.out.println(rf.readLine());
	        }
	      }
	      bw.flush();
	      bw.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      try {
	        if (rf != null)
	          rf.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	}
	
	
	//watson ----- rchr20_new.fa
	//crick ------ rchr20_new_reserve.fa
	
	public Main() throws IOException{
		
		for(int i=1;i<=1;i++){
			reserveFile("E:/�о�������/personal data/repeat chr1-22/HumanGeneRepeat/hg19_Chr"+i+".txt",
					"E:/�о�������/personal data/repeat chr1-22/GeneWithRepeat_reserve/hg19_Chr"+i+".txt");
		}
//		reserveFile("E:/�о�������/personal data/repeat chr1-22/HumanGeneRepeat/hg19_ChrX.txt",
//				"E:/�о�������/personal data/repeat chr1-22/GeneWithRepeat/hg19_ChrX.txt");
//		reserveFile("E:/�о�������/personal data/repeat chr1-22/HumanGeneRepeat/hg19_ChrY.txt",
//				"E:/�о�������/personal data/repeat chr1-22/GeneWithRepeat/hg19_ChrY.txt");
//		refReadsAndFilterRuelst(WATSON, "E:/�о�������/personal data/hg19_Chr22.txt", 
//				"E:/�о�������/personal data/persion new data/z=1/readsC2T_watson.txt", 
//		"E:/�о�������/personal data/persion new data/z=1/readsC2T_watson");
//		refReadsAndFilterRuelst(WATSON,"E:/�о�������/personal data/hg19_Chr22.txt", 
//				"E:/�о�������/personal data/persion new data/z=1/readsG2A_watson.txt", 
//		"E:/�о�������/personal data/persion new data/z=1/readsG2A_watson");
//		
//		refReadsAndFilterRuelst(CRICK, "E:/�о�������/personal data/hg19_Chr22_reserve.txt", 
//				"E:/�о�������/personal data/persion new data/z=1/readsC2T_crick.txt", 
//		"E:/�о�������/personal data/persion new data/z=1/readsC2T_crick");
//		refReadsAndFilterRuelst(CRICK,"E:/�о�������/personal data/hg19_Chr22_reserve.txt", 
//				"E:/�о�������/personal data/persion new data/z=1/readsG2A_crick.txt", 
//		"E:/�о�������/personal data/persion new data/z=1/readsG2A_crick");
		
//		reserveFile("E:/�о�������/personal data/hg19_Chr22.txt", "E:/�о�������/personal data/hg19_Chr22_reserve.txt");
		//Watson
//		for(int i=50;i<=50;i++){
//		refReadsAndFilterRuelst("E:/�о�������/mouse data/rchr20_new.fa", 
//				"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-2_C2T_Watson_aln.txt", 
//				"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-2_C2T_Watson_aln");
//		refReadsAndFilterRuelst("E:/�о�������/mouse data/rchr20_new.fa", 
//				"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-2_G2A_Watson_aln.txt", 
//				"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-2_G2A_Watson_aln");
//		
//		}
		//or not and!!!
		//Crick
		
//			for(int i=50;i<=50;i++){
//		System.out.println("**********"+i);
//				refReadsAndFilterRuelst("E:/�о�������/mouse data/rchr20_new_reserve.fa", 
//						"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-1_C2T_Crick_aln.txt", 
//						"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-1_C2T_Crick_aln");
//				refReadsAndFilterRuelst("E:/�о�������/mouse data/rchr20_new_reserve.fa", 
//						"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-1_G2A_Crick_aln.txt", 
//						"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-1_G2A_Crick_aln");
//				refReadsAndFilterRuelst("E:/�о�������/mouse data/rchr20_new_reserve.fa", 
//						"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-2_C2T_Crick_aln.txt", 
//						"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-2_C2T_Crick_aln");
//				refReadsAndFilterRuelst("E:/�о�������/mouse data/rchr20_new_reserve.fa", 
//						"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-2_G2A_Crick_aln.txt", 
//						"E:/�о�������/mouse data/z=1-50/z"+i+"/f_rfsc-2_G2A_Crick_aln");
//			}
//		reserveFile("E:\\�о�������\\mouse data\\rchr20_new.fa", "E:\\�о�������\\mouse data\\rchr20_new_reserve.fa");
	}
	public static void main(String args[]) throws IOException{
		new Main();
	}

}
