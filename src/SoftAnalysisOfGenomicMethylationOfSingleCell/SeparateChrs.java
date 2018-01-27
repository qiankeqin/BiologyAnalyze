package SoftAnalysisOfGenomicMethylationOfSingleCell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * ���������������������: repeatmasker, CNV, SD�ֱ���ÿ��Ⱦɫ��ָ���� 
 * @author wuxuehong
 * 2012-5-13
 */ 
public class SeparateChrs {
	
	public static void main(String args[]){
		String base = "E:/morehouse/download/";
		separateSD(base+"SD.txt", base+"SD/");
		separateCNV(base+"CNV.txt", base+"CNV/");
		separateRepeats(base+"repeatmasker.txt", base+"REPEAT/");
	}
	
	/**
	 * �ָ�segmental duplicate data,
	 */
	public static void separateSD(String inputFile, String outFilePath){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
			Map<String,BufferedWriter> map = new HashMap<String,BufferedWriter>();
			String head = br.readLine();
			String str = br.readLine();
			Scanner s = null;
			String chr;
			BufferedWriter bw;
			while(str != null){
				s = new Scanner(str);
				s.next();
				chr = s.next();
				bw = map.get(chr);
				if(bw == null){
					bw = new BufferedWriter(new FileWriter(new File(outFilePath+chr+".txt")));
					bw.write(head);
					bw.newLine();
					map.put(chr, bw);
				}
				bw.write(str);
				bw.newLine();
				str = br.readLine();
			}
			Iterator<BufferedWriter> it = map.values().iterator();
			while(it.hasNext()){
				bw = it.next();
				bw.flush();
				bw.close();
			}
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ָ�CNV
	 */
	public static void separateCNV(String inputFile, String outFilePath){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
			Map<String,BufferedWriter> map = new HashMap<String,BufferedWriter>();
			String head = br.readLine();
			String str = br.readLine();
			Scanner s = null;
			String chr;
			BufferedWriter bw;
			while(str != null){
				s = new Scanner(str);
				s.next();
				chr = s.next();
				bw = map.get(chr);
				if(bw == null){
					bw = new BufferedWriter(new FileWriter(new File(outFilePath+chr+".txt")));
					bw.write(head);
					bw.newLine();
					map.put(chr, bw);
				}
				bw.write(str);
				bw.newLine();
				str = br.readLine();
			}
			Iterator<BufferedWriter> it = map.values().iterator();
			while(it.hasNext()){
				bw = it.next();
				bw.flush();
				bw.close();
			}
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ָ�Repepats
	 */
	public static void separateRepeats(String inputFile, String outFilePath){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
			Map<String,BufferedWriter> map = new HashMap<String,BufferedWriter>();
			String head = br.readLine();
			String str = br.readLine();
			Scanner s = null;
			String chr;
			BufferedWriter bw;
			while(str != null){
				s = new Scanner(str);
				for(int i=0;i<5;i++)s.next();
				chr = s.next();
				bw = map.get(chr);
				if(bw == null){
					bw = new BufferedWriter(new FileWriter(new File(outFilePath+chr+".txt")));
					bw.write(head);
					bw.newLine();
					map.put(chr, bw);
				}
				bw.write(str);
				bw.newLine();
				str = br.readLine();
			}
			Iterator<BufferedWriter> it = map.values().iterator();
			while(it.hasNext()){
				bw = it.next();
				bw.flush();
				bw.close();
			}
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
