package SoftAnalysisOfGenomicMethylationOfSingleCell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * If multiple mapping, �������Ǹ���repeatmasker���κΣ�/CNV/segmental dups��һ��overlap�ٵ��Ǹ�reads
 * @author wuxuehong
 * 2012-5-18
 */
public class MultipleMapFilter {

	private int chrLen;    //Ⱦɫ�峤��
	private String readsFile;
	private String repeatFile;
	private String segmentFile;
	private String CNVFile;
	private String outFile;
	private byte[] flag;
	private int style;
	
	public MultipleMapFilter(int chrLen, String readsFile, String repeatFile, String segmentFile, String CNVFile, String outFile, int style){
		this.chrLen = chrLen;
		this.repeatFile = repeatFile;
		this.readsFile = readsFile;
		this.outFile = outFile;
		this.CNVFile = CNVFile;
		this.segmentFile = segmentFile;
		flag = new byte[chrLen];
		this.style = style;
		
		readsRepeats();//��ȡreapeats
		readsFilter(); //���� 
	}
	
	/**
	 * ��ȡrepeats������Ϣ
	 * @param repeatFile
	 */
	private void readsRepeats(){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(repeatFile)));
			String str = br.readLine(); //���Ե�һ��
			str = br.readLine();
			Scanner s = null;
			int start ,end ,tempStart, tempEnd;
			String family;
			String classr;
			String name;
			while(str != null){
				s = new Scanner(str);
				for(int i=0;i<6;i++)s.next();
				if(style == Main.CRICK){
					tempStart = s.nextInt();
					tempEnd = s.nextInt();
					start = chrLen-tempEnd+1;
					end = chrLen-tempStart+1;
				}else{
					start = s.nextInt();
					end = s.nextInt();
				}
				for(int i=0;i<2;i++)s.next();
				name = s.next();
				classr = s.next();
				family = s.next();
				//����һ������repeats
				if(family.equals("Alu") || family.equals("Simple_repeat") || family.equals("ERVL-MaLR") || family.equals("Low_complexity") || family.equals("ERV1") || family.equals("ERVL") || name.equals("L1HS") || name.startsWith("L1M") || name.startsWith("L1P")){
					for(int i=start;i<=end;i++) flag[i] = 1;
				}
				str = br.readLine();
			}
			
			//��ȡsegmental duplicate repeats
			br = new BufferedReader(new FileReader(new File(segmentFile)));
			str = br.readLine(); //���Ե�һ��
			str = br.readLine();
			while(str != null){
				s = new Scanner(str);
				s.next();s.next();
				if(style == Main.CRICK){
					tempStart = s.nextInt();
					tempEnd = s.nextInt();
					start = chrLen-tempEnd+1;
					end = chrLen-tempStart+1;
				}else{
					start = s.nextInt();
					end = s.nextInt();
				}
				for(int i=start;i<=end;i++)flag[i] = 1;
				str = br.readLine();
			}
			br.close();
			
			//��ȡCNV repeats
			br = new BufferedReader(new FileReader(new File(CNVFile)));
			str = br.readLine();
			str = br.readLine();
			while(str != null){
				s = new Scanner(str);
				s.next();s.next();
				if(style == Main.CRICK){
					tempStart = s.nextInt();
					tempEnd = s.nextInt();
					start = chrLen-tempEnd+1;
					end = chrLen-tempStart+1;
				}else{
					start = s.nextInt();
					end = s.nextInt();
				}
				for(int i=start;i<=end;i++)flag[i] = 1;
				str = br.readLine();
			}
			br.close();       
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void readsFilter(){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(readsFile)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outFile)));
			String str = br.readLine();
			bw.write(str);
			bw.newLine();
			str = br.readLine();
			Scanner s = null;
			Reads last;
			Reads cur;
			List<Reads> list = new ArrayList<Reads>();
			while(str != null){
				s = new Scanner(str);
				cur = new Reads();
				cur.readid = s.next();
				
				s.next();s.next();
				cur.start = s.nextInt();
				cur.end = s.nextInt();
				cur.len = s.nextInt();
				s.next();s.next();
				cur.cigra = s.next();
				cur.str = str;
				
				if(list.size() == 0){//�����ǰ���Ƚ϶���Ϊnull ��ֱ�ӽ���ǰreads������������� (һ������ڳ�ʼ��״̬�� ����������Ϊnull)
					list.add(cur);
				}else if (list.size() != 0 && isSame(cur.readid, list)){ //�����ǰ���������з�null ���ҵ�ǰreads������ͬ �����֮
					               
					boolean addCur = true; //�Ƿ�Ҫ����ǰreads ��ӵ�����������    ���ҽ�����ǰreads�������д�����reads�ȽϺ�û��ɾ�� �Ż���ӵ�����������
					for(int i=0;i<list.size();i++){
						last = list.get(i);
						//��ȡ��һ��reads��ϸƥ�����
						char[] lastc = getChars(last.cigra, last.len);
						char[] curc = getChars(cur.cigra, cur.len);
						
						if(last.len != cur.len){
							System.out.println(last.str);
							System.out.println(cur.str);
							System.err.println("warning.............");
						}
						
						int lastC=0,curC=0;
						for(int j=0;j<lastc.length;j++){
							if(lastc[j] == 'M' && flag[j+last.start]==1)lastC++;
						}
						for(int j=0;j<curc.length;j++){
							if(curc[j] == 'M' && flag[j+cur.start]==1)curC++;
						}
						  
					  //��ʼ�� �Ƿ�Ҫɾ����һ��reads ���ǵ�ǰreads   Ĭ��Ϊ����ɾ��
					  boolean lastDel = false, curDel = false;
					  
					  //��repeat overlap ��� ��ɾ��
					  if(lastC<curC){
						    curDel = true;
					  }else{
						  lastDel = true;
					  }
					  if(lastDel) //ɾ����һreads
						  list.remove(last);
					  if(curDel){  //ɾ����ǰreads
						  addCur = false; //��ʾ ���轫��ǰreads��ӵ�������������
						  break;//һ��ɾ����ǰreads������ѭ��
					  }
					}
					if(addCur) list.add(cur);
				}else if (list.size() != 0 && !isSame(cur.readid, list)){  //�����ǰ���������з�null ���ҵ�ǰreads���䲻ͬ  ��������������������� �����ҽ���ǰreads�����������
					for(int i=0;i<list.size();i++){
						bw.write(list.get(i).str);
						bw.newLine();
					}
					list.clear();
					//����ǰreads�������������
					list.add(cur);
				}
				str = br.readLine();
			}
			bw.flush();
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * �Ƚϵ�ǰreadsid�Ƿ���֮ǰ������readsid��ͬ
	 * @param cur
	 * @param list
	 * @return
	 */
	private boolean isSame(String cur, List<Reads> list){
		String last = list.get(0).readid;
		last = last.substring(0, 14);
		cur = cur.substring(0, 14);
		return last.equals(cur);
	}
	
	class Reads{
		String readid = null;  //reads ID
		int start = 0;   //��ʼλ��
		int end = 0;     //����λ��
		int len = 0;     //reads����
		String cigra = null;  //ƥ����Ϣ
		String str = null;   //reads��Ϣ
	}
	/**
	 * ��ȡÿ��reads��ƥ������
	 * @param cigra
	 * @param readslen
	 * @return
	 */
	public char[] getChars(String cigra,int readslen){
		int totalD = 0;
	  try{
		char[] c = cigra.toCharArray();
		char[] r = new char[readslen];
		int index = 0;
		int count = 0;
 		for(int i=0;i<c.length;i++){
			if(c[i]>='0'&&c[i]<='9'){
				count = count*10+c[i]-'0';
			}else if(c[i]=='M'){
				for(int j=index;j<index+count;j++)r[j]='M';
				index+=count;
				count=0;
			}else if(c[i]=='D'){
				//for(int j=index;j<index+count;j++)r[j]='D';
				//index+=count;
				count=0;
			}else if(c[i]=='I'){
				for(int j=index;j<index+count;j++)r[j]='M';
				index+=count;
				count=0;
			}else if(c[i]=='S'){
				for(int j=index;j<index+count;j++)r[j]='S';
				index+=count;
				count=0;
			}else 
				System.out.println("exception !!!!");
		}
		return r;
	  }catch(Exception e){
		  System.out.println(cigra+"\t"+readslen);
		  System.out.println("Total D$$$$$$$$$$$$$$$:"+totalD);
		  return null;
	  }
	}
	
}
