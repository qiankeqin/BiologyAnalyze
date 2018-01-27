package WCNegative;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import util.FileStreamUtil;

public class Main {
	
	
	/**
	 * A-T C-G���� 
	 * @param ch
	 * @return
	 */
	public char getChar(char ch){
		if(ch=='A') return 'T';
		if(ch=='T') return 'A';
		if(ch=='G') return 'C';
		if(ch=='C') return 'G';
		return ch;
	}
	/**
	 * �����ȡ����ȡ��Ӧ��ֵ��A-T��G-C����   
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
			c[first]=getChar(c[first]);
			c[last]=getChar(c[last]);
			temp = c[first];
			c[first] = c[last];
			c[last] = temp;
			first++;
			last--;
		}
		return String.valueOf(c);
	}
	/**
	 * W+���ķ�������������W-
	 * W+--------�����һ���------------>W-
	 * ����:����ref�����ȡ
	 * ����:A-T C-G����
	 * 
	 * C+���ķ�������������C-
	 * C+--------�����һ���------------>C-
	 * ����:����ref�����ȡ
	 * ����:A-T C-G����
	 * @param inputFile
	 * @param outFile
	 */
	public void W2WorC2C(String inputFile,String outFile){
		 RandomAccessFile  rf  = null;
		 BufferedWriter bw = null; 
	    try {
	      //����ļ���ȡ����
	      rf = FileStreamUtil.getRAF(inputFile);
	      //�ļ����
	      bw = FileStreamUtil.getBufferedWriter(outFile);
	      //�ļ�����
	      long len = rf.length();
	      //�ļ���ʼλ��
	      long start = rf.getFilePointer();
	      //���ļ�ͷ��ʼ��ȡ
	      rf.seek(0);
	      //��ȡ��һ��  ���Ե�һ��
	      bw.write(rf.readLine());
	      bw.newLine();
	      //�ļ�����λ��
	      long nextend = start + len - 1;
	      String line;
	      //ָ��ָ���ļ�����λ��
	      rf.seek(nextend);
	      int c = -1;
	      while (nextend > start) {
	    	rf.seek(nextend);
	        c = rf.read();
	        if (c == '\n' || c == '\r') {   //��ȡ���س����߻��е�ʱ�� ���������ݶ�ȡ����
	          line = rf.readLine();
			  if(line==null){
				  System.out.println(inputFile+"\t\t"+line+"\t\t"+rf.getFilePointer());
				  nextend-=2;
			      continue;
			  }
			  //��һ�����ݷ��� ��������ļ�
	          bw.write(convert(line)); 
	          bw.newLine();
	          nextend-=50;  //ָ��λ�û���50(һ���ַ����ĳ���)
	        }
	        nextend--;
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
	
	public Main(){
		new Thread1().start();
		new Thread2().start();
	}
	
	class Thread1 extends Thread{
		public void run() {
			String basedir = "D:/recover/�о�������/personal_data/repeatchr1-22/";
			for(int i=12;i>=1;i--){
				System.out.println("current:"+i);
				W2WorC2C(basedir+"WC+/crick_Chr"+i+".txt", basedir+"WC-/crick_Chr"+i+".txt");
						}
				System.out.println("current:X");
				W2WorC2C(basedir+"WC+/crick_ChrX.txt", basedir+"WC-/crick_ChrX.txt");
		}
	}
	
	class Thread2 extends Thread{
		public void run() {
			String basedir = "D:/recover/�о�������/personal_data/repeatchr1-22/";
			for(int i=11;i>=1;i--){
				System.out.println("current:"+i);
				W2WorC2C(basedir+"WC+/watson_Chr"+i+".txt", basedir+"WC-/watson_Chr"+i+".txt");
						}
				System.out.println("current:X");
				W2WorC2C(basedir+"WC+/watson_ChrX.txt", basedir+"WC-/watson_ChrX.txt");
		}
	}
	
	public static void main(String args[]){
		new Main();
	}

}
