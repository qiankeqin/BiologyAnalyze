package ������ͳ�Ʒ���;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import util.FileStreamUtil;

/**
 * ƥ���ʷ���
 * ��Ҫ���㣺
 * 1���ܵ�reads��Ŀ
 * 2��ƥ���reads��Ŀ
 * 3��Ψһƥ���reads��Ŀ
 * 4������reads���Ⱥ�
 * 5��ƥ���reads���Ⱥ�
 * 6��ƥ���reads bp����
 * 7���ӷָ��ƥ��reads bp����
 * 8���ӷָ��Լ�δƥ��reads������ƥ���reads bp����
 * @author wuxuehong
 * 2012-2-5
 */
public class MapPrecision {
	
	int totalReads = 0; //�ܵ�reads��Ŀ
	int totalMappedReads = 0; //ƥ���reads��Ŀ
	int totalUniqueMappedReads = 0; //Ψһƥ���reads��Ŀ
	int totalReadsLen = 0; //����reads����
	int totalMappedReadsLen = 0; //ƥ���reads���Ⱥ�
	int totalMappedReadsBPs = 0; //ƥ���reads bp����
	int totalMappedReadsBPs_divide = 0;//�ӷָ��ƥ��reads bp����
	int totalMappedReadsBPs_divide_unmap = 0; //�ӷָ��Լ�δƥ��reads������ƥ���reads bp����

	Map<String,Reads> mapReads = new HashMap<String,Reads>();      //����ƥ����
	Map<String,Integer> Divide_mapReads = new HashMap<String,Integer>(); //�ָ��ƥ����
	Map<String,Integer> Unmap_mapReads = new HashMap<String,Integer>();  //δƥ��ֶκ�ƥ����
	
	class Reads{
		int times; //���ִ���
//		String readid; //reads ID
		int len; //reads ����
		float matchics; //ƥ����
	}
	/**
	 * ��ȡԭʼReads����
	 * @param filename
	 */
	public void readOriginalReads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			while(str != null){
				if(str.startsWith(">")){
					totalReads++;    //reads������1
					totalReadsLen+=(Integer.parseInt(str.substring(str.lastIndexOf('=')+1))); //reads���ȵ���
				}
				str = br.readLine();
			}
			br.close();
		}catch(IOException E){
			E.printStackTrace();
		}
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
	 * ��ȡ����ƥ����  �Լ��ָ��ƥ����
	 * @param fileanem1
	 * @param filename2
	 */
	public void readMapedReadsAndDivideMappedReads(String fileanem1,String filename2,Map<String,Integer> map){
		try{
			BufferedReader br1 = FileStreamUtil.getBufferedReader(fileanem1);
			String str = br1.readLine();
			if(str.startsWith("ReadID"))str = br1.readLine();
			Scanner s = null;
			String readid;
			int len;
			float matchics;
			int i;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();  //read id
				for(i=0;i<4;i++) s.next();
				len = s.nextInt();  //reads length
				s.next();
				matchics = Float.parseFloat(s.next()); //ƥ����
				if(map.get(readid)==null){
					map.put(readid, (int)(len*matchics));
				}
				str = br1.readLine();
			}
			br1.close();
			
			//��ȡ�ָ�����ƥ����
			BufferedReader br2 = FileStreamUtil.getBufferedReader(filename2);
			str = br2.readLine();
			Integer temp ;
			while(str != null){
				s = new Scanner(str);
				readid = s.next().substring(0, 14);
				for(i=0;i<4;i++) s.next();
				len = s.nextInt();  //reads length
				s.next();
				matchics = Float.parseFloat(s.next()); //ƥ����
				
				temp = map.get(readid);
				if(temp!=null){
					map.put(readid, temp.intValue()+(int)(len*matchics));
				}
				str = br2.readLine();
			}
			br2.close();
		}catch(IOException e){ 
			e.printStackTrace();
		}
	}
	/**
	 * �ۺ��������������ƥ����  �Ӷ����� ���Ϸָ������ƥ��bp��
	 * @param map
	 */
	public void calMaxMappedBps(Map<String,Integer> map){
		String readid;
		Integer value;
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			readid = it.next();
			value =  Divide_mapReads.get(readid);
			if(value==null || (value!=null && map.get(readid).intValue()>value.intValue())){
				Divide_mapReads.put(readid, map.get(readid));
			}
		}
	}
	/**
	 * ��ȡ�ָ������reads�����ƥ����
	 * @param filename
	 */
	public void readWithDivideMappedReads(){
		//CT��watson��ƥ�������  readsƥ��bp��
		Map<String,Integer> watsonCTmap = new HashMap<String,Integer>();
		Map<String,Integer> watsonGAmap = new HashMap<String,Integer>();
		Map<String,Integer> crickCTmap = new HashMap<String,Integer>();
		Map<String,Integer> crickGAmap = new HashMap<String,Integer>();
		readMapedReadsAndDivideMappedReads("maxprecision/watson_CT.txt", 
				"maxprecision/divide/bwa/FormatConvert/Fusion/maxprecision/watson_CT.txt", watsonCTmap);
		readMapedReadsAndDivideMappedReads("maxprecision/watson_GA.txt", 
				"maxprecision/divide/bwa/FormatConvert/Fusion/maxprecision/watson_GA.txt", watsonGAmap);
		readMapedReadsAndDivideMappedReads("maxprecision/crick_CT.txt", 
				"maxprecision/divide/bwa/FormatConvert/Fusion/maxprecision/crick_CT.txt", crickCTmap);
		readMapedReadsAndDivideMappedReads("maxprecision/crick_GA.txt", 
				"maxprecision/divide/bwa/FormatConvert/Fusion/maxprecision/crick_GA.txt", crickGAmap);
		
		calMaxMappedBps(watsonCTmap);
		calMaxMappedBps(watsonGAmap);
		calMaxMappedBps(crickCTmap);
		calMaxMappedBps(crickGAmap);
		
		Iterator<Integer> it = Divide_mapReads.values().iterator();
		while(it.hasNext()){
			totalMappedReadsBPs_divide += it.next();
		}
	}
	
	
	/**
	 * ��ȡδƥ��reads �ֶη�����Ľ��
	 * @param filename
	 */
	public void readUnmappedReads(String filename){
		try{
			BufferedReader br1 = FileStreamUtil.getBufferedReader(filename);
			String str = br1.readLine();
			if(str.startsWith("ReadID"))str = br1.readLine();
			Scanner s = null;
			String readid;
			int len;
			float matchics;
			int i;
			Integer temp;
			int bps;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();  //read id
				for(i=0;i<4;i++) s.next();
				len = s.nextInt();  //reads length
				s.next();
				matchics = Float.parseFloat(s.next()); //ƥ����
				bps = (int)(len*matchics); 
					
				temp = Unmap_mapReads.get(readid);
				if(temp == null || (temp != null && bps>temp.intValue())){
					Unmap_mapReads.put(readid, bps);
				}
				str = br1.readLine();
			}
			br1.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	
	
	public MapPrecision() {
		//��һ��     ��ȡԭʼ���ݼ���
		readOriginalReads("CT.txt");
		System.out.println("�ܵ�reads��:"+totalReads);
		System.out.println("����reads���Ⱥ�:"+totalReadsLen);
		
		//�ڶ���    ��ȡbwa�����������
		readMppedReads("maxprecision/crick_CT.txt");
		readMppedReads("maxprecision/crick_GA.txt");
		readMppedReads("maxprecision/watson_CT.txt");
		readMppedReads("maxprecision/watson_GA.txt");
		totalMappedReads = mapReads.size();
		Iterator<Reads> it = mapReads.values().iterator();
		Reads read;
		while(it.hasNext()){
			read = it.next();
			totalMappedReadsLen += read.len;
			totalMappedReadsBPs += (read.len*read.matchics);
			if(read.times==1)totalUniqueMappedReads++;
		}
		System.out.println("ƥ���reads��:"+totalMappedReads+"\t"+(float)totalMappedReads/(float)totalReads);
		System.out.println("Ψһƥ���reads��:"+totalUniqueMappedReads+"\t"+(float)totalUniqueMappedReads/(float)totalReads);
		System.out.println("ƥ���reads���Ⱥ�:"+totalMappedReadsLen+"\t"+(float)totalMappedReadsLen/(float)totalReadsLen);
		System.out.println("ƥ���reads bp����:"+totalMappedReadsBPs+"\t"+(float)totalMappedReadsBPs/(float)totalReadsLen);
		
		//������   ��bwa������������ϼ��Ϸָ���ļ���
//		readWithDivideMappedReads();
//		System.out.println("���Ϸָ������ƥ���reads bp����:"+totalMappedReadsBPs_divide+"\t"+(float)totalMappedReadsBPs_divide/(float)totalReadsLen);
		
		//���Ĳ�  �����ϻ����϶�unmappreads�ֶ�ƥ�䴦���ļ���
//		readUnmappedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/watson_CT.txt");
//		readUnmappedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/watson_GA.txt");
//		readUnmappedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/crick_CT.txt");
//		readUnmappedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/crick_GA.txt");
//		Iterator<Integer> it2 = Unmap_mapReads.values().iterator();
//		while(it2.hasNext()){
//			totalMappedReadsBPs_divide_unmap += it2.next();
//		}
//		totalMappedReadsBPs_divide_unmap+=totalMappedReadsBPs_divide;
//		System.out.println("���Ϸָ�Լ�δƥ��Reads�ֶη�����ƥ���reads bp���ͣ�"+totalMappedReadsBPs_divide_unmap+"\t"+(float)totalMappedReadsBPs_divide_unmap/(float)totalReadsLen);;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MapPrecision();
	}

}
