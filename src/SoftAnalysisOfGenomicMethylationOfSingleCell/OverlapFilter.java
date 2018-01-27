package SoftAnalysisOfGenomicMethylationOfSingleCell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.cert.CertPathValidatorException.Reason;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * A��B��һ��read�Ĳ�ͬת�����ɵģ���Դ��A��B, A matched�ĳ��ȴ���B, A��B��overlap, Bû�б�overlap�Ĳ���<=15bp,��ɾ��B
 * @author wuxuehong
 * 2012-5-13
 */
public class OverlapFilter {

	private int chrLen;    //Ⱦɫ�峤��
	private String readsFile;
	private String outFile;
	private List<Read> reads = new ArrayList<Read>();
	private String head;
	
	public OverlapFilter(int chrLen,String readsFile, String outFile){
		this.chrLen = chrLen;
		this.readsFile = readsFile;
		this.outFile = outFile;
		sortReads();
		readsFilter();
	}
	
	private void sortReads(){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(readsFile)));
			head = br.readLine();
			String str = br.readLine();
			Scanner s = null;
			String readid;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				Read r = new Read();
				r.readid = readid;
				r.str = str;
				reads.add(r);
				str = br.readLine();
			}
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(reads);
	}
	
	private void readsFilter(){
		try{
			Iterator<Read> it = reads.iterator();
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outFile)));
			bw.write(head);
			bw.newLine();
			Scanner s = null;
			Reads last;
			Reads cur;
			List<Reads> list = new ArrayList<Reads>();
			Read read;
			while(it.hasNext()){
				read = it.next();
				s = new Scanner(read.str);
				cur = new Reads();
				cur.readid = s.next();
				
				s.next();s.next();
				cur.start = s.nextInt();
				cur.end = s.nextInt();
				cur.len = s.nextInt();
				s.next();s.next();
				cur.cigra = s.next();
				cur.str = read.str;
				
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
						byte[] flag = new byte[last.len];
						//����һ��reads M�Ĳ�����Ϊ1
						  for(int j=0;j<last.len;j++){
							  if(lastc[j] == 'M'){
								  flag[j] = 1;
							  }
						  }
						  //����ǰreads M�Ĳ�����Ϊ2
						  for(int j=0;j<last.len;j++){
							  if(curc[j] == 'M'){
								  flag[j] += 2;
							  }
						  }
						  
						  //�ֱ������һ��reads �Լ���ǰreads M�Ĳ���û�б�overlap����   
						  int lastCount = 0, curCount = 0;
						  for(int j=0;j<last.len;j++){
							  if(flag[j] == 1) lastCount++;
						  }
						  for(int j=0;j<last.len;j++){
							  if(flag[j] == 2) curCount++;
						  }
						  
						  //��ʼ�� �Ƿ�Ҫɾ����һ��reads ���ǵ�ǰreads   Ĭ��Ϊ����ɾ��
						  boolean lastDel = false, curDel = false;
						  //�ҷ�overlap��С��   ���û�б�overlapp�Ĳ���С��6�� ��ôɾ��   
						  if(lastCount<=curCount){
							  if(lastCount <=20) lastDel = true;
						  }else{
							  if(curCount <=20) curDel = true;
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
			}
			bw.flush();
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
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
	
	class Read implements Comparable<Read>{
		String readid;
		String str;
		@Override
		public int compareTo(Read o) {
			return this.readid.compareTo(o.readid);
		}
	}
	
	public static void main(String args[])
	{
		String last = "HILT7XA01A00AKb";
		last = last.substring(0, 14);
		System.out.println(last);
	}
	
	
}
