package ͳ�Ʒ���;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.sql.Ref;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import HumanGenDeal.ReadVo;

import util.FileStreamUtil;

/**
 * Coverage:Ref������ƥ����reads����/ref���ܳ���
 * @author wuxuehong
 * 2012-1-8
 */
public class Coverage {

	private final static int N = 1000;
	//1-22 X, Y
	long[] locat = {249250621,243199373,198022430,191154276,180915260,171115067,
			159138663,146364022,141213431,135534747,135006516,133851895,
			115169878,107349540,102531392,90354753,81195210,78077248,
			59128983,63025520,48129895,51304566,155270560,59373566};

	//ÿ��Ⱦɫ���ϱ�ƥ��ĳ���
	long[] mapped = new long[23];
	Map<String,ReadVo> rmap = new HashMap<String, ReadVo>();
	private final static int WATSON = 0;
	private final static int CRICK = 1;
	/**
	 * 
	 * @param inputFile
	 * @param flag  0-Watson 1-Crick
	 */
	public void readAndOutput(String inputFile, int flag){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
			String str = br.readLine();
			if(str.startsWith("ReadID")){  //��������� ����Ե�һ��
				str = br.readLine();
			}
			String readid = null;
			Scanner s = null;
			int i;
			float value;
			ReadVo read;
			int start,end,len;
			String cigra,chr;
			while(str != null){
				s = new Scanner(str);
				readid = s.next(); //readid
				s.next();
				chr = s.next();
				start = s.nextInt(); //��ʼλ��
				end = s.nextInt(); //����λ��
				len = s.nextInt(); //reads����
				s.next(); 
				value = Float.parseFloat(s.next());  //ƥ����
				cigra = s.next();
				read = rmap.get(readid);
				if(read == null || (read!=null && value > read.getMathcs())){  //���read�ǿ� ����read��ƥ���ʽ�С  �����map
					read = new ReadVo();
					read.setReadID(readid);
				//	read.setSeq(str);
					read.setMathcs(value);
					read.setEnd(end); 
					read.setStart(start);
					read.setReadLength(len);
					read.setChr(chr);        //��־Ⱦɫ��
					read.setCiagr(cigra);    //
					read.setRefID(flag+"");  //���ø��ֶ�  ������־ ��reads����watson�� ������Crick��ƥ��
					rmap.put(readid, read);
				}
				str = br.readLine();
			}
			br.close();
//			System.out.println(rmap.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Coverage() {
		// TODO Auto-generated constructor stub
		readAndOutput("maxprecision/watson_CT.txt", WATSON);
		readAndOutput("maxprecision/watson_GA.txt", WATSON);
		readAndOutput("maxprecision/crick_CT.txt", CRICK);
		readAndOutput("maxprecision/crick_GA.txt", CRICK);
		System.out.println("Total reads:"+rmap.size());
		
		
		byte[]b = null;
		int total = 0;//��ƥ���ref����
		for(int i=0;i<23;i++){
			b = new byte[(int) (locat[i]+N)];
			Iterator<ReadVo> it = rmap.values().iterator();
			ReadVo read;
			int index;
			while(it.hasNext()){
				read = it.next();
				//get the index of chr
				if(read.getChr().substring(3).equals("X"))
					index = 23;
				else
					index = Integer.parseInt(read.getChr().substring(3));
				if(index-1 == i){
					if(read.getRefID().equals(WATSON+"")){ //watson ��
						for(int m=read.getStart();m<=read.getEnd();m++)b[m]=1;
					}else if(read.getRefID().equals(CRICK+"")){ //crick��
						for(int n=read.getStart();n<=read.getEnd();n++)b[(int) (locat[index-1]+1-n)]=1;
					}else{
						System.out.println("��������");
					}
				}
			}
			for(int m=0;m<locat[i]+N;m++){
				if(b[m]==1)total++;
			}
			System.out.println("chr:"+(i+1)+"\ttotal:"+total);
		}
		
		long total2 = 0;
		for(int i=0;i<23;i++)
			total2+=locat[i];
		
		System.out.println("Reference coverd length:"+total);
		System.out.println("Reference total length:"+total2);
		System.out.println("Converage:"+(double)total/(double)total2);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Coverage();
	}

}
