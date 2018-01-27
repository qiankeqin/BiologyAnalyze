package repeat����;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A��B��һ��reads��ͬת�����ɵ� ��Դ��a��b�� A matched�ĳ��ȴ���B�� A��B��overlap�� Bû�б�overlap�Ĳ���<=6bp.��ɾ��B
 * @author wuxuehong
 * 2012-5-4
 */
public class Main20120504 {
	
	byte[] flag = new byte[51304567]; 
	
	public void readsFilter(String inputFile, String outFile){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outFile)));
			String str = br.readLine(); //���ӵ�һ�б�ͷ
			str = br.readLine();
			Scanner s = null;
			Reads last;
			Reads cur;
			List<Reads> list = new ArrayList<Reads>();
			while(str != null){
				s = new Scanner(str);
				cur = new Reads();
				cur.readid = s.next();
				
				if(cur.readid.equals("HILT7XA01A02BKa")){
					System.out.println("abc");
				}
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
						
						//����һ��reads M�Ĳ�����Ϊ1
						  for(int j=0;j<lastc.length;j++){
							  if(lastc[j] == 'M'){
								  flag[j+last.start] = 1;
							  }
						  }
						  //����ǰreads M�Ĳ�����Ϊ2
						  for(int j=0;j<curc.length;j++){
							  if(curc[j] == 'M'){
								  flag[j+cur.start] += 2;
							  }
						  }
						  
						  //�ֱ������һ��reads �Լ���ǰreads M�Ĳ���û�б�overlap����   
						  int lastCount = 0, curCount = 0;
						  for(int j=last.start;j<=last.end;j++){
							  if(flag[j] == 1) lastCount++;
						  }
						  for(int j=cur.start;j<=cur.end;j++){
							  if(flag[j] == 2) curCount++;
						  }
						  
						  //��ʼ�� �Ƿ�Ҫɾ����һ��reads ���ǵ�ǰreads   Ĭ��Ϊ����ɾ��
						  boolean lastDel = false, curDel = false;
						  //�ҷ�overlap��С��   ���û�б�overlapp�Ĳ���С��6�� ��ôɾ��   
						  if(lastCount<=curCount){
							  if(lastCount <=6) lastDel = true;
						  }else{
							  if(curCount <=6) curDel = true;
						  }
						  //��flag��־λ ��Ϊ0 ��ʼֵ
						  for(int j=last.start;j<=last.end;j++){
							  flag[j] = 0;
						  }
						  for(int j=cur.start;j<=cur.end;j++){
							  flag[j] = 0;
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
			br.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Main20120504(){
		String base = "K:/bwa/z=50/01/z=50alusimpleFilter_line01/L1HS_filter/dupsfilter/LAPX_filter/L1PA3L1M4c_filter/CNV_filter/";
		readsFilter(base+"Chr22_w+_CNV_filter.txt", base+"ABfilter/Chr22_w+_AB_filter.txt");
	}
	
	public static void main(String args[]){
		new Main20120504();
	}
	
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
		last = last.substring(0, last.length()-1);
		cur = cur.substring(0, cur.length()-1);
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
}
