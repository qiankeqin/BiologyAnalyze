package ���ݴ����Լ��ȶ�;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import util.FileStreamUtil;

public class ReferenceDeal {
	
	/**
	 * A-T C-G����  ��C����T
	 * @param ch
	 * @return
	 */
	public char getChar(char ch){
		if(ch=='A') return 'T';
		if(ch=='T') return 'A';
		if(ch=='G') return 'T';
		if(ch=='C') return 'G';
		return ch;
	}
	
	/**
	 * �����ȡ����ȡ��Ӧ��ֵ��A-T��G-C����      ��Cת����T
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
	 * Crick ��ת��
	 * @param inputFile
	 * @param outFile
	 */
	public void Origin2Crick(String inputFile,String outFile){
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
	/**
	 * watson ��ת��
	 * @param inputFile
	 * @param outFile
	 * @throws IOException
	 */
	public void origin2Watson(String inputFile,String outFile)throws IOException{
		BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
		BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
		String str = br.readLine();
		while(str != null){
			str = str.toUpperCase();  //��д
			str = str.replaceAll("C", "T");
			bw.write(str);
			bw.newLine();
			str = br.readLine();
		}
		bw.flush();
		bw.close();
		br.close();
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
			ReferenceDeal rd = new ReferenceDeal();
	}
	
	public ReferenceDeal(){
		//1-22
		String inputFile,watsonFile,crickFile;
		for(int i=1;i<=22;i++){
			inputFile = "E:/�о�������/personal data/repeat chr1-22/HumanGeneRepeat/hg19_Chr"+i+".txt";
			watsonFile = "E:/�о�������/personal data/repeat chr1-22/GeneNoRepeat/watson_Chr"+i+".txt";
			crickFile = "E:/�о�������/personal data/repeat chr1-22/GeneNoRepeat/crick_Chr"+i+".txt"; 
			
			try{
				System.out.println(crickFile+"\t is beginning....");
				Origin2Crick(inputFile, crickFile);
				System.out.println(crickFile+"\tfinished!");
				System.out.println(watsonFile+"\t is beginning....");
				origin2Watson(inputFile, watsonFile);
				System.out.println(watsonFile+"\tfinished!");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		try{
			inputFile = "E:/�о�������/personal data/repeat chr1-22/HumanGeneRepeat/hg19_ChrX.txt";
			watsonFile = "E:/�о�������/personal data/repeat chr1-22/GeneNoRepeat/watson_ChrX.txt";
			crickFile = "E:/�о�������/personal data/repeat chr1-22/GeneNoRepeat/crick_ChrX.txt";
			System.out.println(crickFile+"\t is beginning....");
			Origin2Crick(inputFile, crickFile);
			System.out.println(crickFile+"\tfinished!");
			System.out.println(watsonFile+"\t is beginning....");
			origin2Watson(inputFile, watsonFile);
			System.out.println(watsonFile+"\tfinished!");
			
			inputFile = "E:/�о�������/personal data/repeat chr1-22/HumanGeneRepeat/hg19_ChrY.txt";
			watsonFile = "E:/�о�������/personal data/repeat chr1-22/GeneNoRepeat/watson_ChrY.txt";
			crickFile = "E:/�о�������/personal data/repeat chr1-22/GeneNoRepeat/crick_ChrY.txt";
			System.out.println(crickFile+"\t is beginning....");
			Origin2Crick(inputFile, crickFile);
			System.out.println(crickFile+"\tfinished!");
			System.out.println(watsonFile+"\t is beginning....");
			origin2Watson(inputFile, watsonFile);
			System.out.println(watsonFile+"\tfinished!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
