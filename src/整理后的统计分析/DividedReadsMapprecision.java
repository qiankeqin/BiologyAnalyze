package ������ͳ�Ʒ���;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import util.FileStreamUtil;

/**
 * ����������б��ָ�ɵ�Сreads �У�
 * Ψһmatch��reads,ռ����Сreads �ı��ʡ�
 * ��Ҫ���б��ָ�ɵ�Сreads �ľ������֣���Ψһmatch�ľ������֡�
 * PDF���г�������
 * @author wuxuehong
 * 2012-2-24
 */
public class DividedReadsMapprecision {

	/**
	 * bwa�㷨֮�� ���ƥ����
	 */
	Map<String,Integer> mapped = new HashMap<String,Integer>();
	
	/**
	 * δƥ���reads�ָ��õ���������
	 */
	private Set<String> dividereads = new HashSet<String>();
	
	private Map<String, ReadsVo> readsMap = new HashMap<String, ReadsVo>();
	
	private Set<String> uniqueReads = new HashSet<String>();
	/**
	 * ��ȡbwa�㷨ƥ����reads
	 * @param filename
	 */
	public void readMaxprecisionReads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			Scanner s = null;
			String readid ;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				mapped.put(readid, 0);
				str = br.readLine();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡδƥ���reasds�ָ��õ���������
	 */
	public void readDividedUnmappedReads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine().trim();
			Scanner s = null;
			String readid = null ;
			while(str != null){
				if(str.startsWith(">")){
					readid = str.substring(1, str.indexOf("_"));
					if(mapped.get(readid) == null){  //˵������reads��û��map�� ����Ҫ����
						dividereads.add(str.substring(1));
					}
				}
				str = br.readLine();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ�ָ��Ψһƥ���reads
	 * @param filename
	 */
	public void readMappedDividedReads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine().trim();
			Scanner s = null;
			String readid = null ;
			ReadsVo reads;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				if(mapped.get(readid.substring(0, readid.indexOf('_')))==null){ //˵������reads��û��map�� ��Ҫ����
					reads = readsMap.get(readid);
					if(reads == null){
						reads = new ReadsVo();
						reads.readid = readid;
						reads.count = 1;
						readsMap.put(readid, reads);
					}else{
						reads.count++;
					}
				}
				str = br.readLine();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DividedReadsMapprecision(){
		readMaxprecisionReads("maxprecision/crick_CT.txt");
		readMaxprecisionReads("maxprecision/crick_GA.txt");
		readMaxprecisionReads("maxprecision/watson_CT.txt");
		readMaxprecisionReads("maxprecision/watson_GA.txt");
System.out.println("bwa mapped reads:"+mapped.size());
	
		readDividedUnmappedReads("unmappble/crick_CT.txt");
		readDividedUnmappedReads("unmappble/crick_GA.txt");
		readDividedUnmappedReads("unmappble/watson_CT.txt");
		readDividedUnmappedReads("unmappble/watson_GA.txt");
System.out.println("total reads after division:"+dividereads.size());

		readMappedDividedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/crick_CT.txt");
		readMappedDividedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/crick_GA.txt");
		readMappedDividedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/watson_CT.txt");
		readMappedDividedReads("unmappble/bwa/FormatConvert/Fusion/maxprecision/watson_GA.txt");
		
		Set<String> test = new HashSet<String>();
		Iterator<ReadsVo> it = readsMap.values().iterator();
		ReadsVo read;
		while(it.hasNext()){
			read = it.next();
			if(read.count == 1){
				uniqueReads.add(read.readid);
				test.add(read.readid.substring(0, read.readid.indexOf('_')));
			}
		}
System.out.println("unique mapped reads:"+uniqueReads.size());
System.out.println("test result:"+test.size());

	System.out.println("precision:"+(float)(uniqueReads.size())/(float)(dividereads.size()));
	}
	
	public static void main(String args[]){
		new DividedReadsMapprecision();
	}
	
	class ReadsVo{
		String readid;
		int count = 0;
	}
}
