package ͳ�Ʒ���;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import util.FileStreamUtil;

/**
 * ref��ÿ��CG��depth:ͳ��                                                                                                   
 * @author wuxuehong
 * 2012-1-8
 */
public class Depth {
	
	
	private int totalCg = 0; //����depth��CG�ĸ���
	private int totalDepth = 0; //�������֮��
	private int N = 1000;
	//1-22 X, Y
	long[] locat = {249250621,243199373,198022430,191154276,180915260,171115067,
			159138663,146364022,141213431,135534747,135006516,133851895,
			115169878,107349540,102531392,90354753,81195210,78077248,
			59128983,63025520,48129895,51304566,155270560,59373566};
	
	/**
	 * ��ȡƥ����reads��Ϣ
	 * ��watson��ƥ��Ľ��  ֱ�Ӽ���
	 * @param filename   ƥ����
	 * @param b          ��ʶ���
	 * @param length     Ⱦɫ�峤��
	 */
	public void readMappedReads_Watson(String filename,byte[] b){
		String str = null;
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			str = br.readLine();
			Scanner s = null;
			str = br.readLine();
			int start,end;
			while(str != null){
				s = new Scanner(str);
				s.next();
				s.next();
				s.next();
				start = s.nextInt();
				end = s.nextInt();
				for(int i=start;i<=end;i++)b[i]++; //�������
				str = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(str);
		}
	}
	
	/**
	 * ��ȡ��Crickƥ����reads
	 * ע�⣺��ƥ������Ӧ��refλ����Ϣ����ԭʼrefλ����Ϣ����  ������ת����ԭʼλ����Ϣ
	 * @param filename
	 * @param b
	 * @param length
	 */
	public void readMappedReads_Crick(String filename,byte[] b, long length){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			Scanner s = null;
			str = br.readLine();
			int start,end;
			while(str != null){
				s = new Scanner(str);
				s.next();
				s.next();
				s.next();
				start = s.nextInt();
				end = s.nextInt();
				for(int i=start;i<=end;i++)b[(int) (length+1-i)]++; //�������  
				str = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡȾɫ��������Ϣ
	 * ��ȡCG��λ��
	 * @param chrFile
	 */
	public void readChr(String chrFile,byte[] b){
		 int length = 0;
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(chrFile);
			br.readLine(); //���Ե�һ��
			String str = br.readLine();
			char[] ch;
			boolean furtherC = false; //��һ��ĩβ�Ƿ�ΪC
			int position = 0;  //CG��λ��
			while(str != null){
				if(furtherC) str = "C"+str;
				ch = str.toUpperCase().toCharArray();
				for(int i=0;i<ch.length-1;i++){
					if(ch[i]=='C'&&ch[i+1]=='G'){  //����� CGλ��
						 position = length+(i+1); //��ȡ��C��λ����Ϣ
						 if(b[position]>0){  //�����CGλ���ϵ���ȴ���0��������ļ�
							 totalCg++;  //CG�����ͼ�1
							 totalDepth+=b[position]; //����ۼ�
						 }
					}
				}
				if(ch[ch.length-1]=='C') { //����ַ�����β��C  �򳤶�--
					furtherC =  true;   
				    length--;
				}else
					furtherC = false;
				length+=str.length();
				str = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Depth() {
		// TODO Auto-generated constructor stub
		for(int i=1;i<=22;i++){
			System.out.println("Reading chr:"+i);
			byte[] b = new byte[(int)locat[i-1]+N];
			readMappedReads_Crick("../crick_CT_Chr"+i+".txt",b,locat[i-1]);
			readMappedReads_Crick("../crick_GA_Chr"+i+".txt",b,locat[i-1]);
			readMappedReads_Watson("../watson_CT_Chr"+i+".txt", b);
			readMappedReads_Watson("../watson_GA_Chr"+i+".txt", b);
			readChr("HumanGeneRepeat/hg19_Chr"+i+".txt", b);
		}
		
		System.out.println("Reading chr:X");
		byte[] b = new byte[(int)locat[22]+N];
		readMappedReads_Crick("../crick_CT_ChrX.txt",b,locat[22]);
		readMappedReads_Crick("../crick_GA_ChrX.txt",b,locat[22]);
		readMappedReads_Watson("../watson_CT_ChrX.txt",b);
		readMappedReads_Watson("../watson_GA_ChrX.txt",b);
		readChr("HumanGeneRepeat/hg19_ChrX.txt", b);
		
		System.out.println("Ref������CG depth֮��:"+totalDepth);
		System.out.println("Ref����ȴ���0��CG����:"+totalCg);
		System.out.println("Average Depth:"+(float)totalDepth/(float)totalCg);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Depth();
	}

}
