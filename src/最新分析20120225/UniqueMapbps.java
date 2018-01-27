package ���·���20120225;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import util.FileStreamUtil;


/**
 * �ָ�ǰΨһƥ���bp�ܳ����ָ���ڷָ��Сreads�У�Ψһƥ���bp�ܳ�
 * @author wuxuehong
 * 2012-2-25
 */
public class UniqueMapbps {
	
	Map<String,Reads> mapReads = new HashMap<String,Reads>();      //����ƥ����
	
	Map<String,Reads> divedeMap = new HashMap<String,Reads>(); //�ָ��ƥ����
	/*****************************�ָ�ǰΨһƥ���bp�ܳ�******************************************/
	class Reads{
		int times = 0; //���ִ���
		int len; //reads ����
		float matchics; //ƥ����
	}
	
	/**
	 * ��ȡ������������ƥ����
	 * @param filename
	 */
	public void readMppedReads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			if(str.startsWith("ReadID"))str = br.readLine();
			Scanner s = null;
			String readid; //id
			int len = 0;  //����
			float matchics; //ƥ����
			int i;
			Reads read;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				for(i=0;i<4;i++)s.next();
				len = s.nextInt();
				s.next();
				matchics = Float.parseFloat(s.next());
				read = mapReads.get(readid);
				if(read == null ){
					read = new Reads();
					read.times = 1;
					read.len = len;
					read.matchics = matchics;
					mapReads.put(readid, read);
				}else{
					read.times++;
					if(matchics > read.matchics)read.matchics = matchics;
				}
				str = br.readLine();
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ�ָ�� ƥ���reads
	 * @param filename
	 */
	public void readMappedReads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			Scanner s = null;
			String readid;
			Reads reads;
			int len = 0;  //����
			float matchics; //ƥ����
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				for(int i=0;i<4;i++)s.next();
				len = s.nextInt();
				s.next();
				matchics = Float.parseFloat(s.next());
				
				if(mapReads.get(readid.substring(0, 14)) == null){  //˵�������ָ��reads ��δmap�� ��Ҫ����
					reads = divedeMap.get(readid);
					if(reads == null){
						reads = new Reads();
						reads.len = len;
						reads.matchics = matchics;
						reads.times = 1;
						divedeMap.put(readid, reads);
					}else{
						reads.times ++;
					}
				}
				str = br.readLine(); 
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public UniqueMapbps(){
		System.out.println("*******************************�ָ�ǰ***************************************");
		//   ��ȡbwa�����������   δ�ָ�ǰreads
		readMppedReads("maxprecision/crick_CT.txt");
		readMppedReads("maxprecision/crick_GA.txt");
		readMppedReads("maxprecision/watson_CT.txt");
		readMppedReads("maxprecision/watson_GA.txt");
		
		int uniqueMap = 0, totalMaps = 0;  //Ψһƥ���reads��Ŀ�� Ψһƥ���bp�ܳ�
		Iterator<Reads> it = mapReads.values().iterator();
		while(it.hasNext()){
			Reads read = it.next();
			if(read.times == 1) {
				uniqueMap++;
				totalMaps += (read.len*read.matchics);
			}
		}
		System.out.println("ƥ���reads����:"+mapReads.size());
		System.out.println("δ�ָ�ǰΨһƥ���reads����:"+uniqueMap);
		System.out.println("δ�ָ�ǰΨһƥ���reads bp����:"+totalMaps);
		
		
		System.out.println("*******************************�ָ��***************************************");
		readMappedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/crick_CT.txt");
		readMappedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/crick_GA.txt");
		readMappedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/watson_CT.txt");
		readMappedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/watson_GA.txt");
		
		uniqueMap = 0;totalMaps = 0;
		it = divedeMap.values().iterator();
		while(it.hasNext()){
			Reads read = it.next();
			if(read.times == 1) {
				uniqueMap++;
				totalMaps += (read.len*read.matchics);
			}
		}
		System.out.println("ƥ���reads����:"+divedeMap.size());
		System.out.println("�ָ��Ψһƥ���reads����:"+uniqueMap);
		System.out.println("�ָ��Ψһƥ���reads bp����:"+totalMaps);
	}

	
	public static void main(String args[]){
		new UniqueMapbps();
	}
}
