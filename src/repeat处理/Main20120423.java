package repeat����;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream.GetField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 1���ϲ�I,D 
 * 2������reads M��λ�ü���ȥ����alu  �� simple repeats  �������alu ����simple repeats match�ϵĲ�λ�������죬�������С��20bp
 *    �ķ�alu��simple repeats��readsҲȥ��
 *  3�����line����(line��repeat��һ��)
 * @author wuxuehong
 * 2012-4-23
 */
public class Main20120423 {

	byte[] flag = new byte[51304567]; 
	byte[] flag2 = new byte[51304567];
	private int length = 51304566;
	
	String[] line = new String[1000];
	Map<String,Integer> map = new HashMap<String,Integer>();
	/**
	 * W+
	 * @param filename
	 */
	public void readRepeatPos(String filename){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String str = br.readLine(); //���Ե�һ��
			str = br.readLine();
			Scanner s = null;
			int start ,end;
			String family;
			String classr;
			String name;
			int index = 5;
			while(str != null){
				s = new Scanner(str);
				for(int i=0;i<6;i++)s.next();
				start = s.nextInt();
				end = s.nextInt();
				for(int i=0;i<2;i++)s.next();
				name = s.next();
				classr = s.next();
				family = s.next();
				//����� alu ����simplerepeat �����Ϊ1
				if(family.equals("Alu") || family.equals("Simple_repeat")){
					for(int i=start;i<=end;i++)
						flag[i] = 1;
				}else{//���������reapeat�����Ϊ2
					for(int i=start;i<=end;i++)
						flag[i] = 2;
				}
				if(classr.equals("LINE")){
					Integer intvalue = map.get(name);
					if(intvalue != null){
						for(int i=start;i<=end;i++)
							flag[i] = intvalue.byteValue();	
					}else{
						index++;
						for(int i=start;i<=end;i++){
							flag[i] = (byte)index;
						}
						line[index] =  name;
						map.put(name, index);
					}
				}
				str = br.readLine();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * C+
	 * @param filename
	 */
	public void readRepeatNeg(String filename){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String str = br.readLine(); //���Ե�һ��
			str = br.readLine();
			Scanner s = null;
			int start ,end, startTemp, endTemp;
			String family;
			String classr,name;
			int index = 5;
			while(str != null){
				s = new Scanner(str);
				for(int i=0;i<6;i++)s.next();
				startTemp = s.nextInt();
				endTemp = s.nextInt();
				
				start = length-endTemp+1;
				end = length-startTemp+1;
				
				for(int i=0;i<2;i++)s.next();
				name = s.next();
				classr = s.next();
				family = s.next();
				//����� alu ����simplerepeat �����Ϊ1
				if(family.equals("Alu") || family.equals("Simple_repeat")){
					for(int i=start;i<=end;i++)
						flag[i]= 1;
				}else{ //���������reapeat�����Ϊ2
					for(int i=start;i<=end;i++)
						flag[i] = 2;
				}
				
				if(classr.equals("LINE")){
					Integer intvalue = map.get(name);
					if(intvalue != null){
						for(int i=start;i<=end;i++)
							flag[i] = intvalue.byteValue();	
					}else{
						index++;
						for(int i=start;i<=end;i++){
							flag[i] = (byte)index;
						}
						line[index] =  name;
						map.put(name, index);
					}
				}
				
				str = br.readLine();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void filterReads(String filename, String outFile){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outFile)));
			String str = br.readLine();
			if(str.startsWith("ReadID")){
				bw.write("Merge\t");
				bw.write("LineClass\t");
				bw.write(str);
				bw.newLine();
				str = br.readLine();
			}
			Scanner s = null;
			int start, end, len;
			String cigra;
			String readid;
			int maxLen = 20;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();s.next();s.next();
				start = s.nextInt();
				end = s.nextInt();
				len = s.nextInt();
				s.next();s.next();
				cigra = s.next();
				char[] c = getChars(cigra,  len);
				
				for(int i=0;i<c.length;i++){
					if(c[i]=='M' ){
						flag2[start+i] = 'M';
					}
				}
				//����ǰreads�� �ǲ��Ƕ���alu ����simple repeat   ����� ����˵�
				boolean filter = true;
				int count = 0;
				for(int i=start;i<=end;i++){
					//����Ǵ�д��ĸ����� ��д��ĸ���� ���ұ�����ǰreads����alu����simple repeat
					if(flag[i] == 0 && flag2[i] == 'M'){
						count++;
					}
					//������ǰreads����alu����simple repeat
					if(flag[i] != 1 && flag2[i] == 'M')
						filter = false;
				}
				//���û�й��� ��˵��������repeat���߲�ȫ�� alu simple repeat
				if(!filter){
					//Ҫô��д��ĸ���ڵ���20  Ҫô��ȫ�������������
					if(count>=maxLen || count == 0){
						
						bw.write(merge(c)+"\t");
						boolean isline = false;
						for(int i=start;i<=end;i++){
							if(flag[i]>=5 && flag2[i]=='M')/////////////////////////////////ע�����������
							{
								isline = true;              /////////////////////////////////������Ϊ��Ϊһ������������ ��˵������reads��������
								bw.write(line[flag[i]]+"\t");
								break;
							}
						}
						if(!isline)bw.write(" \t");
						bw.write(str);
						bw.newLine();
					}else{
						//System.out.println("��д��ĸ����:"+count);
					}
				}else{
					//System.out.println("I am alu or simple repeat reads!");
				}
				
				//�ⲻ����Ҫ ��Ҫ��λ�ñ�ǻ�ԭ
				for(int i=0;i<c.length;i++){
					if(c[i]=='M' ){
						flag2[start+i] = 0;
					}
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
	
	
	public Main20120423(){
		String path = "K:/bwa/z=50/01/";
		readRepeatPos("K:/bwa/chr22-repeats.txt");
		filterReads(path+"Chr22_w+.txt", path+"Chr22_w+_filter.txt");
		
	}
	
	
	public String merge(char[] cigra){
		StringBuffer sb = new StringBuffer();
		char upchar = 'A';
		int count = 0;
		for(int i=0;i<cigra.length;i++){
			char cur = cigra[i];
			if(cur != upchar){
				if(count != 0){
					sb.append(count+""+upchar);
				}
				upchar = cur;
				count = 1;
			}else{
				count++;
			}
		}
		sb.append(count+""+upchar);
		return sb.toString();
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
	
	
	public static void main(String args[]){
		System.out.println("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111".length());
		System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM".length());
//		Main20120423 main = new Main20120423();
//		String cigra = "3S26M1I88M1I13M1I37M1I19M16S";
//		String out = String.valueOf(main.getChars(cigra, 206));
//		System.out.println(out);
//		char[] c = out.toCharArray();
//		System.out.println(main.merge(c));
	}
	
}
