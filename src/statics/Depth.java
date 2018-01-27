package statics;

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
		int len = b.length;
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
				for(int i=start;i<=end&&i<len;i++)b[i]++; //�������
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
			int len = b.length;
			str = br.readLine();
			int start,end,temp;
			while(str != null){
				s = new Scanner(str);
				s.next();
				s.next();
				s.next();
				start = s.nextInt();
				end = s.nextInt();
				temp = start;
				start = (int) (length+1-end);
				end = (int) (length+1-temp);
				for(int i=start;i<=end&&i<len;i++)b[i]++; //�������  
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
	public void readChr(String chrFile, String outFile,byte[] b){
		 int length = 0;
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(chrFile);
			BufferedWriter bw = new BufferedWriter(new FileWriter(outFile, true));
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
							 bw.write("CG\t"+position+"\t"+b[position]);
							 bw.newLine();
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
			bw.flush();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Depth() {
		// TODO Auto-generated constructor stub
		for(int i=1;i<=22;i++){
			byte[] b = new byte[(int)locat[i-1]+1];
			readMappedReads_Crick("E:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/crick_CT_Chr"+i+".txt",
				 b,locat[i-1]);
			readMappedReads_Crick("E:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/crick_GA_Chr"+i+".txt",
				 b,locat[i-1]);
			
			readMappedReads_Watson("E:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/watson_CT_Chr"+i+".txt",
				 b);
			readMappedReads_Watson("E:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/watson_GA_Chr"+i+".txt",
				 b);
			
			readChr("E:/�о�������/personal_data/repeatchr1-22/HumanGeneRepeat/hg19_Chr"+i+".txt", "e:/cg/hg19_Chr"+i+".txt", b);
			System.out.println(i+"\t\t"+totalCg+"\t"+totalDepth);
		}
		byte[] b = new byte[(int)locat[22]+1];
		readMappedReads_Crick("E:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/crick_CT_ChrX.txt",
			 b,locat[22]);
		readMappedReads_Crick("E:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/crick_GA_ChrX.txt",
			 b,locat[22]);
		
		readMappedReads_Watson("E:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/watson_CT_ChrX.txt",
			 b);
		readMappedReads_Watson("E:/�о�������/personal_data/O6.GAC.454Reads/bwaƥ����/FormatConvert/watson_GA_ChrX.txt",
			 b);
		
		readChr("E:/�о�������/personal_data/repeatchr1-22/HumanGeneRepeat/hg19_ChrX.txt", "e:/cg/hg19_ChrX.txt", b);
		System.out.println("X\t\t"+totalCg+"\t"+totalDepth);
		
		System.out.println((float)totalDepth/(float)totalCg);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Depth();
	}

}
