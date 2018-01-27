package statics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import util.FileStreamUtil;

/**
 * ƥ���reads��/�ܵ�reads��Ŀ
 * @author wuxuehong
 * 2012-1-3
 */
public class MapPrecision {
	
	//�ܵ�reads��Ŀ
	int totalCount = 0;
	//�ܵ�reads����
	int totalReadsLength = 0;
	//ƥ���reads����                             //ֱ����
	int totalMappedReadsLength = 0;
	//ƥ���reads bp����                 //reads���ȳ���ƥ����
	int totalMappedReadsBps = 0;
	
	List<String> treads = new ArrayList<String>();
	
	/***************************δ����repeat����ͳ��**********************************/
	//����ͳ��Ψһmap��reads��Ŀ     ����map��ref��һ��λ�õ�
	Map<String,ReadVo> uniqueMapr = new HashMap<String,ReadVo>(); 
	//����ͳ�ƶ��map��reads��Ŀ
	Map<String,Integer> multiMapr = new HashMap<String,Integer>();
	//����ͳ���ܵ�map��reads��Ŀ   
	Set<String> sreadsr = new HashSet<String>();
	
	//����ͳ��Ψһmap��reads��Ŀ     ����map��ref��һ��λ�õ�
	Map<String,Integer> uniqueMap = new HashMap<String,Integer>(); 
	
	//ƥ���reads����
	Map<String,Integer> uniquereads = new HashMap<String,Integer>();
	
	
	class ReadVo{
		String readid;
		String chrid;
		String start;
	}
	/**
	 * ��ȡԭʼreads��Ϣ
	 * @param filename
	 */
	public void readReads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			while(str != null){
				if(str.startsWith(">")) {
					totalCount++;
					totalReadsLength+=(Integer.parseInt(str.substring(str.lastIndexOf('=')+1))); //reads�����ۼ�
				}
				str = br.readLine();
			}
			br.close();
		}catch(IOException e){e.printStackTrace();}
	}
	
	/**
	 * ��ȡmap��reads��Ϣ(δ����repeat��)
	 * ����map��Ŀ
	 * @param filename
	 */
	public void readMappedReadsWithRepeat(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine(); //���Ե�һ��
			Scanner s = null;
			str = br.readLine();
			String readid,chrid,start;
			float matchics;
			int length;
			ReadVo r;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				s.next();
				chrid = s.next();
				start = s.next();
				r = uniqueMapr.get(readid); 
				if(r!=null){
					if(!r.chrid.equals(chrid) || !r.start.equals(start)){  //readid��ͬ ���ƥ���ref��λ�ò�һ������λһƥ��
						uniqueMapr.remove(readid);
						multiMapr.put(readid, 1);
						}
				}else{
					r = new ReadVo();
					r.readid = readid;
					r.chrid = chrid;
					r.start = start;
					if(multiMapr.get(readid)==null)
						uniqueMapr.put(readid, r);
				}
				sreadsr.add(readid);
				
				if(uniqueMap.get(readid) == null){
					s.next();
					length = s.nextInt();
					totalMappedReadsLength += length;  //�ۼ�mapped reads�ĳ���
					s.next();
					matchics = Float.parseFloat(s.next());
					totalMappedReadsBps += (length*matchics); //�ۼ�mapped reads bp����
					uniqueMap.put(readid, 1);
				}
				str = br.readLine();
			}
			br.close();
		}catch(IOException e){
				e.printStackTrace();
		}
	}

	public MapPrecision(String args[]) {
//		readReads("E:/�о�������/personal_data/1999.GAC.454Reads/1999.GAC.454Reads.fa");
//		System.out.println("Total reads:"+totalCount);
//		System.out.println("Total reads length:"+totalReadsLength);
//		
//		/*****************δ����repeat�� mapped readsͳ��*********************/
//		for(int i=1;i<=22;i++){
//			readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_CT_Chr"+i+".txt");
//			readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_GA_Chr"+i+".txt");
//			readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_CT_Chr"+i+".txt");
//			readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_GA_Chr"+i+".txt");
//			System.out.println(i+"\t\t"+totalMappedReadsLength+"\t\t"+totalMappedReadsBps);
//		}
//		readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_CT_ChrX.txt");
//		readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_GA_ChrX.txt");
//		readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_CT_ChrX.txt");
//		readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_GA_ChrX.txt");
//		
//		System.out.println("**********************With repeat********************");
//		System.out.println("Multiple mapped reads:"+multiMapr.size());
//		System.out.println("Uniquely Mapped reads :"+uniqueMapr.size()+"\t\tpercent:"+(float)uniqueMapr.size()/(float)totalCount);
//		System.out.println("Mapped reads:"+uniqueMap.size());
//		System.out.println("Mapped reads :"+sreadsr.size()+"\t\tpercent:"+(float)sreadsr.size()/(float)totalCount);
//		System.out.println("Mapped reads length:"+totalMappedReadsLength+"\t\tpercent:"+(float)totalMappedReadsLength/(float)totalReadsLength);
//		System.out.println("Mapped reads bp:"+totalMappedReadsBps+"\t\tpercent:"+(float)totalMappedReadsBps/(float)totalReadsLength);
	
		readMappedBPs("j:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/crick_CT.txt",uniqueMap1);
		readDividedBPs("j:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/divide/bwa/FormatConvert/Fusion/crick_CT.txt",uniqueMap1);
System.out.println(uniqueMap1.size());
		readMappedBPs("j:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/crick_GA.txt",uniqueMap2);
		readDividedBPs("j:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/divide/bwa/FormatConvert/Fusion/crick_GA.txt",uniqueMap2);
System.out.println(uniqueMap2.size());
		readMappedBPs("j:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/watson_CT.txt",uniqueMap3);
		readDividedBPs("j:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/divide/bwa/FormatConvert/Fusion/watson_CT.txt",uniqueMap3);
System.out.println(uniqueMap3.size());
		readMappedBPs("j:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/watson_GA.txt",uniqueMap4);
		readDividedBPs("j:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/divide/bwa/FormatConvert/Fusion/watson_GA.txt",uniqueMap4);
System.out.println(uniqueMap4.size());
		Iterator<String> it = uniqueMap1.keySet().iterator();
		String readid = null;
		while(it.hasNext()){
			readid = it.next();
			if(uniqueMap.get(readid)==null || uniqueMap1.get(readid)>uniqueMap.get(readid)){
				uniqueMap.put(readid, uniqueMap1.get(readid));
			}
		}
		
		it = uniqueMap2.keySet().iterator();
		while(it.hasNext()){
			readid = it.next();
			if(uniqueMap.get(readid)==null || uniqueMap2.get(readid)>uniqueMap.get(readid)){
				uniqueMap.put(readid, uniqueMap2.get(readid));
			}
		}
		
		it = uniqueMap3.keySet().iterator();
		while(it.hasNext()){
			readid = it.next();
			if(uniqueMap.get(readid)==null || uniqueMap3.get(readid)>uniqueMap.get(readid)){
				uniqueMap.put(readid, uniqueMap3.get(readid));
			}
		}
		
		it = uniqueMap4.keySet().iterator();
		while(it.hasNext()){
			readid = it.next();
			if(uniqueMap.get(readid)==null || uniqueMap4.get(readid)>uniqueMap.get(readid)){
				uniqueMap.put(readid, uniqueMap4.get(readid));
			}
		}
		
		Iterator<Integer> it2 = uniqueMap.values().iterator();
		while(it2.hasNext()){
			totalMappedReadsBps += it2.next();
		}
		System.out.println(totalMappedReadsBps);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MapPrecision(args);
	}

	private Map<String,Integer> divideMap = new HashMap<String,Integer>();
	public void readDividedBPs(String filename, Map<String,Integer> map){
		  divideMap.clear();
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();  //���Ե�һ��
			str = br.readLine();
			Scanner s = null;
			int len = 0;
			float matchics = 0;
			String readid = null;
			int addition=0;
			Integer value;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				if(divideMap.get(readid)==null){
					for(int i=0;i<4;i++) s.next();
					len = s.nextInt();
					s.next();
					matchics = Float.parseFloat(s.next());
					addition = (int) (len*matchics);
					divideMap.put(readid, addition);
					
					readid = readid.substring(0,14); //get real reads ID
					value = map.get(readid);
					if(value != null){
						map.put(readid, value+addition);
					}else
						System.out.println("***************************88");
				}
				str = br.readLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ�Ѿ�mapped��reads��map��bp��Ŀ
	 * @param filename
	 */
	
	private Map<String,Integer> uniqueMap1 = new HashMap<String,Integer>();
	private Map<String,Integer> uniqueMap2 = new HashMap<String,Integer>();
	private Map<String,Integer> uniqueMap3 = new HashMap<String,Integer>();
	private Map<String,Integer> uniqueMap4 = new HashMap<String,Integer>();
	public void readMappedBPs(String filename, Map<String,Integer> map){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();  //���Ե�һ��
			str = br.readLine();
			Scanner s = null;
			int len = 0;
			float matchics = 0;
			String readid = null;
			int addition=0;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				for(int i=0;i<4;i++) s.next();
				len = s.nextInt();
				s.next();
				matchics = Float.parseFloat(s.next());
				addition = (int) (len*matchics);
				if(map.get(readid)==null || addition>map.get(readid)){
					map.put(readid, addition);
				}
				str = br.readLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
