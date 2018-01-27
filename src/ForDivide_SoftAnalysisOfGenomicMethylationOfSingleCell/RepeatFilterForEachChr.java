package ForDivide_SoftAnalysisOfGenomicMethylationOfSingleCell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
(1). ����repeat masker���ͣ��������reads��λ��������alu, simple repeats, ERVL-MaLR��low-complexity����ȥ����reads.
(2). �ϲ�I��D: D������I��������34M1D66M=100M, 12M2I56M=70M��
(3).����reads��M��λ��ȥ����alu, simple repeats��ERVL-MaLR��low-complexity, �����������죬�������С��20bp�ķ�alu, simple repeats��ERVL-MaLR��low-complexity��readsҲȥ����
(4). ���line���ͣ�line��repeat��һ�֣����ڣ�1�����˺��ļ�Z=50�ļ��зֱ��г�1�С�����M��λ��filter L1HS, L1MXX and L1PXX ���͵�reads�������������죬�������С��20bp�ķ�L1HS, L1MXX and L1PXX��readsҲȥ����
(5). ����segmental duplicate data, ����M��λ�ù��˵�segmental duplicate�������������죬�������С��20bp�ķ�segmental duplicate��readsҲȥ����
(6). ��ucsc�У�tables����CNV������M��λ�ù��˵�CNV�������������죬�������С��20bp�ķ�CNV��readsҲȥ����������CNV: ucsc---tables: clade: mammal; genome: human; assembly: Feb. 2009 (GRCh37/hg19); group: variation and repeats; track: DGV struct Var; table: dgv; region��genome��
 * @author wuxuehong
 * 2012-5-13
 */
public class RepeatFilterForEachChr {
	
	private int chrLen;    //Ⱦɫ�峤��
	private String readsFile;
	private String repeatFile;
	private String segmentFile;
	private String CNVFile;
	private String outFile;
	private byte[] flag;
	private byte[] flag2;
	private String[] line = new String[1000];
	private Map<String,Integer> map = new HashMap<String,Integer>();
	private int style;
	
	
	public RepeatFilterForEachChr(int chrLen, String readsFile, String repeatFile, String segmentFile, String CNVFile, String outFile, int style){
		this.chrLen = chrLen;
		this.repeatFile = repeatFile;
		this.readsFile = readsFile;
		this.outFile = outFile;
		this.CNVFile = CNVFile;
		this.segmentFile = segmentFile;
		this.style = style;
		flag = new byte[chrLen];
		flag2 = new byte[chrLen];
		readsRepeats();
		filterReads();
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
			int index = 5;
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
				if(family.equals("Alu") || family.equals("Simple_repeat") || family.equals("ERVL-MaLR") || family.equals("Low_complexity") || family.equals("ERV1") || family.equals("ERVL")){
					for(int i=start;i<=end;i++) flag[i] = 1;
				}
				
				//���LINE����
				//��L1HS  L1MXX  L1PXX λ�ñ��Ϊ1  (�������ڹ���)
				//����ı��Ϊ5+  
				if(classr.equals("LINE")){
					if(name.equals("L1HS") || name.startsWith("L1M") || name.startsWith("L1P")){
						for(int i=start;i<=end;i++)flag[i] = 1;
					}else{
						Integer intvalue = map.get(name);
						if(intvalue != null){
							for(int i=start;i<=end;i++)
								flag2[i] = intvalue.byteValue();	
						}else{
							index++;
							for(int i=start;i<=end;i++){
								flag2[i] = (byte)index;
							}
							line[index] = name;
							map.put(name, index);
						}
					}
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
	
	private void filterReads(){
		String str = null;
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(readsFile)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outFile)));
			String head = br.readLine();
			bw.write(head);
			bw.newLine();
			str = br.readLine();
			int start,end,len;
			String cigra;
			Scanner s = null;
			int maxLen = 20;
			while(str != null){
				s = new Scanner(str);
				s.next();s.next();s.next();
				start = s.nextInt();  //ƥ��Ŀ�ʼλ��
				end = s.nextInt();   //ƥ��Ľ���λ��
				len = s.nextInt();   //reads����
				s.next();s.next();
				cigra = s.next();  //ƥ����ϸ��Ϣ
				
				boolean filter = true; //��ʼ����reads��Ҫ�����˵�
				//�鿴����reads�Ƿ������� repeat����  �������������˵� ������ʱ����
				for(int i=start;i<=end;i++){
					if(flag[i] != 1) {
						filter = false;
						break;
					}
				}
				
				char[] c = getChars(cigra, len);
				//���readsû��ȫ������repeat����  ��鿴 reads M �Ĳ����Ƿ�������repeat����
				
				try{
				if(!filter){
					filter = true; //��Ȼ�����ʼΪ��Ҫ���˵���
					int count = 0; //����M�Ĳ���û������
					for(int i=start;i<=end;i++){
						if(c[i-start] == 'M' && flag[i] != 1){ //ͳ��reads�� M���ַ� alu simple repeats,...����repeats ��reads����
							count++;
						}
					}
					if(count>=maxLen)filter = false;
				}
				}catch (Exception e) {
					System.err.println(str);
					str = br.readLine();
					continue;
				}
				
				//���reads M������Ȼû����������repeats�������� �������reads  ���ұ�ǳ�LINE����
				if(!filter){
					bw.write(str+"\t");
					bw.write(merge(c)+"\t");
					int cur = -1;
					boolean isline = true;  //Ĭ��ΪLine����
					boolean first = true; //�Ƿ��һ������M
					for(int i=start;i<=end;i++){
						if(flag2[i] >= 5 && c[i-start] == 'M'){    //��־λ���ڵ���5�Ĳ���line����
							if(first){
								cur = flag2[i];
								first = false;
							}else if(flag2[i] != cur){
								isline = false;   //�����reads�в���ͬһ������
								break;
							}
						}
						if(flag2[i] < 5){
							isline = false;
							break;
						}
					}
					if(isline) bw.write(line[cur]);
					bw.newLine();
				}
				str = br.readLine();
			}
			bw.flush();
			bw.close();
			br.close();
		}catch (Exception e) {
			System.err.println(str);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ��readsƥ�����ϸ��Ϣת���� �ַ�����  
	 * ����D����
	 * Iת����M
	 * @param cigra
	 * @param readslen
	 * @return
	 */
	private char[] getChars(String cigra,int readslen){
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
	
	private String merge(char[] cigra){
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

}
