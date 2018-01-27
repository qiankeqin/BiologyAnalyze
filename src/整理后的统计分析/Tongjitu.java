package ������ͳ�Ʒ���;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import util.FileStreamUtil;
/**
 * ͳ��ÿ��Ⱦɫ���� ��ƥ���reads��Ŀ
 * @author wuxuehong
 * 2012-2-12
 */
public class Tongjitu {
	
//	byte[] flag = new byte[24]; 
	
	private Map<String,byte[]> reads2chr = new HashMap<String,byte[]>();
	
//	private Map<String,Set<String>> chr2reads = new HashMap<String,Set<String>>();
	
	/**
	 * ��ȡ�Ѿ�ƥ���reads���
	 * @param filename
	 */
	public void readMappedReads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = null;
			str = br.readLine();
			if(str.startsWith("ReadID")) str = br.readLine();
			Scanner s = null;
			String readid;
			String chr;
			int chrid;
			byte[] b;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				s.next();
				chr = s.next();
				if(chr.substring(3).equals("X"))
					chrid = 23;
				else
					chrid = Integer.parseInt(chr.substring(3));
				
				b = reads2chr.get(readid);
				if(b==null){
					b = new byte[24];
					b[chrid] = 1;
					reads2chr.put(readid, b);
				}else{
					b[chrid] = 1;
				}
				str = br.readLine();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡreads�ָ���ƥ����
	 */
	public void readMappedDividedReads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = null;
			str = br.readLine();
			if(str.startsWith("ReadID")) str = br.readLine();
			Scanner s = null;
			String readid;
			String chr;
			int chrid;
			byte[] b;
			while(str != null){
				s = new Scanner(str);
				readid = s.next().substring(0,14);
				s.next();
				chr = s.next();
				if(chr.substring(3).equals("X"))
					chrid = 23;
				else
					chrid = Integer.parseInt(chr.substring(3));
				
				b = reads2chr.get(readid);
				if(b==null){
					b = new byte[24];
					b[chrid] = 1;
					reads2chr.put(readid, b);
		System.out.println("�������������ͱ�ʾ����������");
				}else{
					b[chrid] = 1;
				}
				str = br.readLine();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡδƥ���reads �ֶκ�ƥ����
	 * @param filename
	 */
	public void readunMappedDividedReads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = null;
			str = br.readLine();
			if(str.startsWith("ReadID")) str = br.readLine();
			Scanner s = null;
			String readid;
			String chr;
			int chrid;
			byte[] b;
			while(str != null){
				s = new Scanner(str);
				readid = s.next().substring(0,14);
				s.next();
				chr = s.next();
				if(chr.substring(3).equals("X"))
					chrid = 23;
				else
					chrid = Integer.parseInt(chr.substring(3));
				
				b = reads2chr.get(readid);
				if(b==null){
					b = new byte[24];
					b[chrid] = 1;
					reads2chr.put(readid, b);
				}else{
					b[chrid] = 1;
				}
				str = br.readLine();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	int[] chr = new int[24];
	public Tongjitu() {
		// TODO Auto-generated constructor stub
		
		readMappedReads("crick_CT.txt");
		readMappedReads("crick_GA.txt");
		readMappedReads("watson_CT.txt");
		readMappedReads("watson_GA.txt");
		
		readMappedDividedReads("maxprecision/divide/bwa/FormatConvert/Fusion/maxprecision/crick_CT.txt");
		readMappedDividedReads("maxprecision/divide/bwa/FormatConvert/Fusion/maxprecision/crick_GA.txt");
		readMappedDividedReads("maxprecision/divide/bwa/FormatConvert/Fusion/maxprecision/watson_CT.txt");
		readMappedDividedReads("maxprecision/divide/bwa/FormatConvert/Fusion/maxprecision/watson_GA.txt");
		
		System.out.println("mapped reads:"+reads2chr.size());
		
		readunMappedDividedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/crick_CT.txt");
		readunMappedDividedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/crick_GA.txt");
		readunMappedDividedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/watson_CT.txt");
		readunMappedDividedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/watson_GA.txt");
		
		System.out.println("total matched reads:"+reads2chr.size());
		
		Iterator<byte[]> it = reads2chr.values().iterator();
		byte[] b;
		while(it.hasNext()){
			b = it.next();
			for(int i=0;i<b.length;i++){
				if(b[i] == 1){
					chr[i]++;
				}
			}
		}
		
		for(int i=1;i<24;i++){
			System.out.println("chr"+i+"\t"+chr[i]);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Tongjitu();
	}

}
