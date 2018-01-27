package ͳ�Ʒ���;

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
		readReads("E:/�о�������/personal_data/1999.GAC.454Reads/1999.GAC.454Reads.fa");
		System.out.println("Total reads:"+totalCount);
		System.out.println("Total reads length:"+totalReadsLength);
		
		/*****************δ����repeat�� mapped readsͳ��*********************/
		for(int i=1;i<=22;i++){
			readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_CT_Chr"+i+".txt");
			readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_GA_Chr"+i+".txt");
			readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_CT_Chr"+i+".txt");
			readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_GA_Chr"+i+".txt");
			System.out.println(i+"\t\t"+totalMappedReadsLength+"\t\t"+totalMappedReadsBps);
		}
		readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_CT_ChrX.txt");
		readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_GA_ChrX.txt");
		readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_CT_ChrX.txt");
		readMappedReadsWithRepeat("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_GA_ChrX.txt");
		
		System.out.println("**********************With repeat********************");
		System.out.println("Multiple mapped reads:"+multiMapr.size());
		System.out.println("Uniquely Mapped reads :"+uniqueMapr.size()+"\t\tpercent:"+(float)uniqueMapr.size()/(float)totalCount);
		System.out.println("Mapped reads:"+uniqueMap.size());
		System.out.println("Mapped reads :"+sreadsr.size()+"\t\tpercent:"+(float)sreadsr.size()/(float)totalCount);
		System.out.println("Mapped reads length:"+totalMappedReadsLength+"\t\tpercent:"+(float)totalMappedReadsLength/(float)totalReadsLength);
		System.out.println("Mapped reads bp:"+totalMappedReadsBps+"\t\tpercent:"+(float)totalMappedReadsBps/(float)totalReadsLength);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MapPrecision(args);
	}

}
