import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import util.FileStreamUtil;
import java.util.*;
/**
 * �����ȫδƥ���reads
 * @author wuxuehong
 * 2012-2-12
 */
public class UnmappedReads {
	
	private Map<String,Seq> readid2seq = new HashMap<String,Seq>();
	private Set<String> mappedreadid = new HashSet<String>();
	private Set<String> unmappedreaid = new HashSet<String>();
	
	class Seq{
		String title;
		StringBuffer content = new StringBuffer();
	}
	/**
	 * ��ȡԭʼreads��Ϣ
	 * @param filename
	 */
	public void readOriginreads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			String readid="",seq="";
			Seq se = null;
			while(str != null){
				if(str.startsWith(">")){
					se = new Seq();
					readid = str.substring(1,str.indexOf("rank")-1);
					readid2seq.put(readid, se);
					se.title = str;
				}else{
					se.content.append(str+"\n");
				}
				str = br.readLine();
			}
			br.close();
	System.out.println("original reads size:"+readid2seq.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * �Ѿ�ƥ���readss
	 * @param filename
	 */
	public void readMappedReads(String filename){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			if(str.startsWith("ReadID"))str = br.readLine();
			Scanner s = null;
			String readid; //id
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				mappedreadid.add(readid);
				str = br.readLine();
			}
			br.close();
//			System.out.println("total mapped reads size:"+mappedreadid.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * δƥ��reads�ָ��ƥ����
	 * @param filename
	 */
	public void readUnmappedReads(String filename){
			try{
				BufferedReader br = FileStreamUtil.getBufferedReader(filename);
				String str = br.readLine();
				Scanner s = null;
				String readid = "";
				while(str != null){
					s = new Scanner(str);
					readid = s.next();
					readid = readid.substring(0,readid.indexOf('_'));
					unmappedreaid.add(readid);
					str = br.readLine();
				}
				br.close();
			}catch(IOException e){
				e.printStackTrace();
			}
	}
	
	

	public UnmappedReads() {
		// TODO Auto-generated constructor stub
		String base = "E:/�о�������/personal_data/o1.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/";
		readOriginreads(base+"o1.GAC.454Reads.fa");
		
		readMappedReads(base+"maxprecision/crick_CT.txt");
		readMappedReads(base+"maxprecision/crick_GA.txt");
		readMappedReads(base+"maxprecision/watson_CT.txt");
		readMappedReads(base+"maxprecision/watson_GA.txt");
		
		readUnmappedReads(base+"unmappble/bwa/FormatConvert/Fusion/maxprecision/crick_CT.txt");
		readUnmappedReads(base+"unmappble/bwa/FormatConvert/Fusion/maxprecision/crick_GA.txt");
		readUnmappedReads(base+"unmappble/bwa/FormatConvert/Fusion/maxprecision/watson_CT.txt");
		readUnmappedReads(base+"unmappble/bwa/FormatConvert/Fusion/maxprecision/watson_GA.txt");
		
		Set<String> all = readid2seq.keySet();
		all.removeAll(mappedreadid);
		all.removeAll(unmappedreaid);
		
		try{
			BufferedWriter bw = FileStreamUtil.getBufferedWriter(base+"unmapped/unmapped.txt");
			Iterator<String> it = all.iterator();
			Seq readid = null;
			while(it.hasNext()){
				readid = readid2seq.get(it.next());
				bw.write(readid.title);
				bw.newLine();
				bw.write(readid.content.toString());
			}
			bw.flush();
			bw.close();
		}catch(Exception e){}
		
		System.out.println(mappedreadid.size());
		mappedreadid.addAll(unmappedreaid);
		System.out.println("unmapped reads:"+readid2seq.size());
		System.out.println("mapped reads:"+mappedreadid.size());
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UnmappedReads();
	}

}
