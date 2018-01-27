package Task20120228;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import mousedata.Main;
import util.FileStreamUtil;

public class RefUtil {
	
	public static final int POS = 1;
	public static final int NEG = 0;
	//1-22 X, Y
	static long[] locat = {249250621,243199373,198022430,191154276,180915260,171115067,
			159138663,146364022,141213431,135534747,135006516,133851895,
			115169878,107349540,102531392,90354753,81195210,78077248,
			59128983,63025520,48129895,51304566,155270560,59373566};
	/**
	 * for watson
	 * @param leftmost
	 * @return
	 */
	public static long getStartPointerWatson(long leftmost, int firstLine, int eachLine){
		long start = 0;
		leftmost--;
		start += (firstLine+2); //��һ�� ���ݹ���
		long hang = leftmost/eachLine;   //50��ÿ�е��ַ�����
		long mod = leftmost%eachLine; //���һ���ַ�����
		start += (eachLine+2)*hang;
		start = start+mod; 
		return start;
	}
	/**
	 * for crick
	 * @param leftmost
	 * @return
	 */
	public static long getStartPointerCrick(long leftmost, int firstLine, int eachLine, int offset){
		long start = 0;
		leftmost--;
		start += (firstLine+2); //��һ�� ���ݹ���
		start += (offset+2);  //��һ�в���50�ַ���
		leftmost -= offset;
		long hang = leftmost/eachLine;   //50��ÿ�е��ַ�����
		long mod = leftmost%eachLine; //���һ���ַ�����
		start += (eachLine+2)*hang;
		start = start+mod; 
		return start;
	}
	
	
	/**
	 * 
	 * @param style  WATSON OR CRICK deal?
	 * @param firstLine ��һ���ַ����ĳ���
	 * @param eachLine  ÿ���ַ����ĳ���
	 * @param offset  CRICKʱ ��һ�в���50���ַ����ַ�����
	 * @param ref  reference
	 * @param sam   �������ļ�
	 * @param output   ����ļ�
	 * @throws IOException 
	 */
	public static String refReadsAndFilterRuelst(String ref,int start, int end, int type) throws IOException{
		 RandomAccessFile  rf  = null;
		 rf = FileStreamUtil.getRAF(ref);
		 int firstLine = rf.readLine().length();
		 int offset = rf.readLine().length();
		 rf.seek(0);
		 if(type == POS)
			 rf.seek(getStartPointerWatson(start,firstLine,50));
		 else if(type == NEG)
			 rf.seek(getStartPointerCrick(start, firstLine,50,offset));
		 int len = end-start+1;
		 char[] c = new char[len];
		 
		 for(int i=0;i<len;){
			 int a = rf.read();
			 if (a=='\n'||a=='\r'){
				 continue;
			 }
			 c[i] = (char)a;
			 i++;
		 }
		 return String.valueOf(c);
	}
	
	public static void main(String args[]) throws IOException{
		String file = "D:/recover/�о�������/personal_data/repeatchr1-22/WC/W_hg19_Chr1.txt";
		String file2 = "D:/recover/�о�������/personal_data/repeatchr1-22/GeneNoRepeat/watson_Chr1.txt";
//		System.out.println(refReadsAndFilterRuelst(file, 31876701,31876710));
		
//		System.out.println("ATATAGATATATAGATATATAGATGTATATATAGATTTATATAGATATATATTTATAAGGATATATATAGATAATATAGATATATATAGATATAGGTATAGATATATAGATATATAGATGTATATATAGATATAGATATATATAAGGTATATAGGGGATA".length());
//		String file = "D:/recover/�о�������/personal_data/repeatchr1-22/GeneNoRepeat/watson_Chr1.txt";
//		System.out.println(refReadsAndFilterRuelst(file2, 38185342,38185427));
		//GGAGTATAGTGGTGTGATTATAGTTTATTGTAGTTTTAATTTTTAGGT
		//GGAGTATAGTGGTATGATTATAGTTTATTGTAGTTTTAATTTTTAGGT
		//GGAGTACAGTGGCGTGATCACAGCTTACTGTAGCCTCAACCTCCAGGC
		//GGAGTACAGTGGCGTGATCACAGCTTACTGTAGCCTCAACCTCCAGGC
//		ATATAGATATATAGATATATAGATGTATATATAGATTTATATAGATATATATTTATAAGGATATATATAGATAATATAGATATATATAGATATAGGTATAGATATATAGATATATAGATGTATATATAGATATAGATATATATAAGGTATATAGGGGATA\
//		ATATATATATGTATATATATAGATATAGATATATATATATATATGTGTATATATAGATATAGATATATATATATATATTTAAGGAAGTTTGGGGTAGTGGTTAGGTAGAATTAGAAGTAAGTAGATAGAGAAGAGGGGTTTGAGGTAGGAAGATTGAGGG
		
//		AAGATCAGATAGTTGTAGATATGCGGCGTTATTTCTGAGGGCTCTGTTCTGTTCCATTGATCTATATCTCTGTTTTGGTACCAGTACCATGCTGTTTTTGGTTACTGTAGCCTTGTAGTATAGTTTGAAGTCAGGTAGTGTGATGCCTCC
//		AAGATTAGATAGTTGTAGATATGTGGTGTTATTTTTGAGGGTTTTGTTTTGTTTTATTGATTTATATTTTTGTTTTGGTATTAGTATTATGTTGTTTTTGGTTATTGTAGTTTTGTAGTATAGTTTGAAGTTAGGTAGTGTGATGTTTTT
//	    AAGATTAGATGATTGTAGATATGTGGTGTTGTTTTTGAGGTTTTTGTTTTGTTTTAATGGTTTATATTTTTGTTTTGGTATTAGTATTATGTTTTTTTTGGTTATTGTAGTTTTGTAGTATAGTTTGAAGTTAGGTAGTGTGATGTTTTT

		//GGAGAAGTGTGTGTGTGTGTGTGTGTGTGTGTGTGTGTGTTTGGTAGTA
		//GGAGAAGTGTGTGTGTGTGTGTGTGTGTGTGTGTGTGTGTGTTGTAGTA
		
		
	}
}
