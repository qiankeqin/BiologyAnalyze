package reads�ָ��100bp�Ķ�reads;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import util.FileStreamUtil;

/**
 * reads�ָ�
 * @author wuxuehong
 * 2011-11-25
 */
public class Main {
	
	private int eachCount = 50;
	
	/**
	 * 
	 * @param inputFile
	 * @param outFile
	 * @throws IOException 
	 */
	public void readAndOutput(String inputFile, String outFile) throws IOException{
		BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
		BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
		String str = br.readLine();
		Scanner s = null;
		String readID = null;   //read id
		String readInfo = null ;  //
		StringBuffer sb = new StringBuffer(""); //���������reads sequence
		while(str != null){
			if(str.startsWith(">")){
				if(sb.toString().length()!=0){
					List<String> subs = getSubString(100, sb.toString()); //�и�����  ����100bp
					for(int i=0;i<subs.size();i++){
						bw.write(readID+"-"+(i+1)+""+readInfo);
						bw.newLine();
						bw.write(subs.get(i));
						bw.newLine();
					}
				}
				s = new Scanner(str);
				readID = s.next(); //
				readInfo = str.substring(readID.length());
				sb = new StringBuffer("");
			}else{
				sb.append(str);
			}
			str = br.readLine();
		}
		//���һ��
		if(sb.toString().length()!=0){
			List<String> subs = getSubString(100, sb.toString());
			for(int i=0;i<subs.size();i++){
				bw.write(readID+"-"+(i+1)+""+readInfo);
				bw.newLine();
				bw.write(subs.get(i));
				bw.newLine();
			}
		}
		br.close();
		bw.flush();
		bw.close();
	}
	
	/**
	 * ���ַ����и�
	 * �и��ÿ���ַ���֮�䶼��overlap
	 * @param length  �и��ÿ���ַ�����󳤶�
	 * @param seq  �ַ���
	 * @return
	 */
	public List<String> getSubString(int length,String seq){
		List<String> subs = new ArrayList<String>();
		Random r = new Random();
		int start = 0;
		int ran = 0;
		String temp = "";
		while(seq.substring(start).length() >length){
			temp = seq.substring(start, start+length);
			subs.add(temp); 
			ran = r.nextInt(length);
			if(ran==0) ran++;
			start = start+ran;
		}
		subs.add(seq.substring(start));
		return subs;
	}
	
	public Main() throws IOException{
		readAndOutput("E:/�о�������/mouse data/rfsc-1.GAC.454Reads_G2A.fa", "E:/�о�������/mouse data/mouse�ֶ�/rfsc-1.GAC.454Reads_G2A.fa");
	}
	
	public static void main(String args[]) throws IOException{
		new Main();
	}

}
