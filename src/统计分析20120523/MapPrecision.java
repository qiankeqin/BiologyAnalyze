package ͳ�Ʒ���20120523;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * 
 * ���⣺
 * ����reads��ƥ�䲿��bp���ܺ� and percentage:
 * ���reads������: ��bwa������İ���Multiple ƥ���reads
 * ���� �� ���˺��
 * ���һ��reads�� chr1��ƥ����30bp  ��chr4��ƥ����40bp  ��chr14��ƥ����60bp ��ô ����readsƥ���˶���bp
 * @author wuxuehong
 *
 * 2012-5-23                                                                                       
 */
public class MapPrecision {
	
	private int totalReadsNum = 0 ; //�ܵ�reads��Ŀ
	private int totalMappedReadsNum = 0 ; //�ܵ�matched reads��Ŀ
	private int totalUnMappedReadsNum = 0; //�ܵ�δmatched reads��Ŀ
	private int totalUniqueMappedReadsNum = 0; //�ܵ�Ψһmatched reads��Ŀ
	
	private int totalReadsLen = 0; //����reads���Ⱥ�
	private int totalMappedReadsLen = 0; //����ƥ��reads���Ⱥ�
	private int totalMappedReadsBp = 0 ;//����ƥ��reads ƥ�䲿��Bp���͡�(�����и���)
	
	private Set<ReadsVo> reads = new HashSet<ReadsVo>();//bwa ��ƥ��reads
	private Set<ReadsVo> uniques = new HashSet<ReadsVo>();//Ψһƥ���reads
	private Map<String,Integer> divides = new HashMap<String, Integer>(); //�ָ��readsƥ�����
	
	private Map<String,ReadsVo> map = new HashMap<String,ReadsVo>(); //���˺��reads
	/**
	 * ��ȡԭʼreads������Ϣ
	 * @param filename
	 */
	public void readOriginalReads(String filename){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String str = br.readLine();
			while(str != null){
				if(str.startsWith(">")){
					totalReadsNum++;
					totalReadsLen+=(Integer.parseInt(str.substring(str.lastIndexOf('=')+1))); //reads�����ۼ�
				}
				str = br.readLine();
			}
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡreadsƥ��� ��  bwa���
	 * @param filename
	 */
	public void readMappedReads(String filename){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String str = br.readLine();
			if(str.startsWith("ReadID"))str = br.readLine();
			Scanner s = null;
			while(str != null){
				s = new Scanner(str);
				ReadsVo r = new ReadsVo();
				r.readid = s.next().substring(0,14);
				for(int i=0;i<4;i++)s.next();
				r.len = s.nextInt();
				s.next();
				r.matchics = Float.parseFloat(s.next());
				r.cigra = s.next();
				reads.add(r);
				str = br.readLine();
			}
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡbwa ƥ���  �����Ҿ������˺��reads
	 * @param filename
	 */
	public void readMpapedFilterReads(String filename){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String str = br.readLine();
			if(str.startsWith("ReadID"))str = br.readLine();
			Scanner s = null;
			String readid,cigra;
			int len;
			float matchics;
			while(str != null){
				s = new Scanner(str);
				readid = s.next().substring(0,14);
				for(int i=0;i<4;i++)s.next();
				len = s.nextInt();
				s.next();
				matchics = Float.parseFloat(s.next());
				cigra = s.next();
				ReadsVo r = map.get(readid);
				if(r == null){
					r = new ReadsVo();
					r.times = 1;
					r.readid = readid;
					r.len = len;
					s.next();
					r.matchics = matchics;
					r.cigra = cigra;
					byte[] flag = new byte[r.len];        //��¼��reads��ÿ��λ��ƥ�����
					char[] ch = Util.getChars(r.cigra,r.len);   //��ȡ��readsÿ��λ��ƥ�����
					for(int i=0;i<ch.length;i++){         //һ�����ض�λ��ƥ���� ��ô�ͽ���λ�ü����1
						if(ch[i] == 'M') flag[i]++;
					}
					r.flag = flag;
					map.put(readid, r);
				}else{
					byte[] flag = r.flag;
					char[] ch = Util.getChars(cigra,r.len);
					for(int i=0;i<ch.length;i++){
						if(ch[i] == 'M') flag[i]++;
					}
					r.times++;
				}
				str = br.readLine();
			}
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����Ψһƥ���reads��Ŀ
	 */
	public void calculate(){
		//����Ψһƥ���reads��Ŀ
		Iterator<ReadsVo> it = map.values().iterator();
		while(it.hasNext()){
			ReadsVo r = it.next();
			byte[] b = r.flag;
			boolean unique = true;
			for(int i=0;i<b.length;i++){   
				if(b[i]>1){        //һ����reads��ĳһ��λ�� ƥ�������� ������������ ��ô ��reads�Ͳ���Ψһƥ�����
					unique = false;
					break;
				}
			}
			if(unique) {
				totalUniqueMappedReadsNum++;    //Ψһƥ���reads��
				totalMappedReadsLen+=r.len;    //ƥ���reads���Ⱥ�
				uniques.add(r);
				
				int length = (int)(r.len*r.matchics);
				totalMappedReadsBp += length;
				if(divides.get(r.readid)!=null)totalMappedReadsBp+=divides.get(r.readid).intValue();
			}
		}
		
	}
	
	/**
	 * ��ȡ�ָ��ƥ���reads
	 * @param filename
	 */
	public void readsDivideMappedReads(String filename){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String str = br.readLine();
			str = br.readLine();
			int len;
			float matchics;
			String readid;
			Scanner s = null;
			while(str != null){
				s = new Scanner(str);
				readid = s.next().substring(0,14);
				for(int i=0;i<4;i++)s.next();
				len = s.nextInt();
				s.next();
				matchics = Float.parseFloat(s.next());
				int value = (int)(len*matchics);
				Integer in = divides.get(readid);
				if(in == null){
					divides.put(readid, value);
				}else{
					divides.put(readid, in.intValue()+value);
				}
				str = br.readLine();
			}
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public MapPrecision(){
		readOriginalReads("E:/morehouse/o1o6reads/o6-a.txt");
		String filename = null;
		for(int i=1;i<=23;i++){
System.out.println(i);
			filename = "Chr"+i+"_c+.txt";
			if(i==23)filename = "ChrX_c+.txt";
			readMappedReads("E:/morehouse/z=50/06/"+filename);
			filename = "Chr"+i+"_w+.txt";
			if(i==23)filename = "ChrX_w+.txt";
			readMappedReads("E:/morehouse/z=50/06/"+filename);
		}
		
		for(int i=1;i<=23;i++){
System.out.println(i);
			filename = "Chr"+i+"_c+.txt";
			if(i==23)filename = "ChrX_c+.txt";
			readMpapedFilterReads("E:/morehouse/z=50/06/MUTIMAP_FILTER/"+filename);
			filename = "Chr"+i+"_w+.txt";
			if(i==23)filename = "ChrX_w+.txt";
			readMpapedFilterReads("E:/morehouse/z=50/06/MUTIMAP_FILTER/"+filename);
		}
		
		for(int i=1;i<=23;i++){
System.out.println(i);
			filename = "Chr"+i+"_c+.txt";
			if(i==23)filename = "ChrX_c+.txt";
			readMpapedFilterReads("E:/morehouse/z=50/divide/06/FormatConvert/MUTIMAP_FILTER/"+filename);
			filename = "Chr"+i+"_w+.txt";
			if(i==23)filename = "ChrX_w+.txt";
			readMpapedFilterReads("E:/morehouse/z=50/divide/06/FormatConvert/MUTIMAP_FILTER/"+filename);
		}
		
		calculate();
		
		totalMappedReadsNum = reads.size();
		System.err.println("ƥ���reads��Ŀ:"+totalMappedReadsNum);
		System.err.println("�ܵ�reads��Ŀ��"+totalReadsNum);
		System.err.println("ƥ���reads��Ŀ/�ܵ�reads��Ŀ:"+((float)totalMappedReadsNum/(float)totalReadsNum));
		totalUnMappedReadsNum  = totalReadsNum-totalMappedReadsNum;
		System.err.println("��ȫδƥ���reads��Ŀ:"+totalUnMappedReadsNum);
		System.err.println("δƥ���reads��Ŀ/�ܵ�reads��Ŀ:"+((float)totalUnMappedReadsNum/(float)totalReadsNum));
		System.err.println("Ψһƥ���reads��Ŀ:"+totalUniqueMappedReadsNum);
		System.err.println("Ψһƥ��reads��Ŀ/�ܵ�reads��Ŀ"+((float)totalUniqueMappedReadsNum/(float)totalReadsNum));
		System.out.println();
		
		System.err.println("ƥ���reads����:"+totalMappedReadsLen);
		System.err.println("��dreads����:"+totalReadsLen);
		System.err.println("ƥ���reads����/�ܵ�reads����:"+((float)totalMappedReadsLen/(float)totalReadsLen));
		
		System.out.println();
		System.err.println("ƥ���reads bp���ܺ�:"+totalMappedReadsBp);
	}
	
	public static void main(String args[]){
		new MapPrecision();
	}
	

	
}
