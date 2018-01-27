import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import util.FileStreamUtil;
public class T {

	private Set<String> readss = new HashSet<String>();
	
	private Map<String,Reads> map1 = new HashMap<String,Reads>();
	private Map<String,Reads> map2 = new HashMap<String,Reads>();
	private Map<String,Reads> map3 = new HashMap<String,Reads>();
	private Map<String,Reads> map4 = new HashMap<String,Reads>();
	
	class Reads{
		String readid;
		int[] count = new int[24];
		int getMappedBP(){
			int total = 0;
			for(int i=0;i<24;i++){
				total+=count[i];
			}
			return total;
		}
	}
	public void readMaxprecision(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			if(str.startsWith("ReadID"))str = br.readLine();
			Scanner s = null;
			String readid; //id
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				readss.add(readid);
				str = br.readLine();
			}
			br.close();
			System.out.println("total reads size:"+readss.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ������������ƥ����
	 * @param filename
	 */
	public void readMppedReads(String filename,Map<String,Reads> map){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			if(str.startsWith("ReadID"))str = br.readLine();
			Scanner s = null;
			String readid,chr; //id
			int len;
			float matchics;
			int chrindex;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				s.next();
				chr = s.next();
				chrindex = Integer.parseInt(chr.substring(3)); //��ƥ���Ⱦɫ����
				s.next();s.next();
				len = s.nextInt(); //length
				s.next();
				matchics = Float.parseFloat(s.next());
				
				Reads read = map.get(readid);
				if(read == null){
					read = new Reads();
					read.readid = readid;
					read.count[chrindex] = (int)(len*matchics);
					map.put(readid, read);
				}else{                //����ڶ�ӦȾɫ����ƥ���Bp��Ŀ���� ��ô���滻
		System.out.println("���ûִ��!");
					int count = (int)(len*matchics);
					if(count>read.count[chrindex]){
						read.count[chrindex] = count;
					}
				}
				str = br.readLine();
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void readDivideMappedReads(String filename,Map<String,Reads> map){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			if(str.startsWith("ReadID"))str = br.readLine();
			Scanner s = null;
			String readid,chr;
			int len;
			float matchics;
			int chrindex;
			while(str != null){
				s = new Scanner(str);
				readid = s.next(); //readid
				readid = readid.substring(0,readid.indexOf('_')); //
				s.next();
				chr = s.next();
				chrindex = Integer.parseInt(chr.substring(3)); //chr index
				s.next();s.next();
				len = s.nextInt();
				s.next();
				matchics = Float.parseFloat(s.next());
				
				Reads read = map.get(readid);
				if(read!=null){
					
				}else{
					System.out.println("ִ����ͱ�ʾ������!");
				}
				
			}
		}catch(IOException e){
			
		}
	}
	
	public T() {
		// TODO Auto-generated constructor stub
//		readMppedReads("E:\\�о�������\\personal_data\\o1.GAC.454Reads\\bwaƥ����\\FormatConvert\\Fusion\\maxprecision\\crick_CT.txt");
//		readMppedReads("E:\\�о�������\\personal_data\\o1.GAC.454Reads\\bwaƥ����\\FormatConvert\\Fusion\\maxprecision\\crick_GA.txt");
//		readMppedReads("E:\\�о�������\\personal_data\\o1.GAC.454Reads\\bwaƥ����\\FormatConvert\\Fusion\\maxprecision\\watson_CT.txt");
//		readMppedReads("E:\\�о�������\\personal_data\\o1.GAC.454Reads\\bwaƥ����\\FormatConvert\\Fusion\\maxprecision\\watson_GA.txt");
//		
//		System.out.println(ss.size());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new T();
		
		String str = "ATCTCTCCATGAACAATCTGAATTAGAATATTTCTTGAATTTCTTGAAAA";
		System.out.println(str.length());
	}
	

}
