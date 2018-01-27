package statics;

import java.io.BufferedReader;
import java.util.Scanner;

import util.FileStreamUtil;

/**
 * Coverage:Ref������ƥ����reads����/ref���ܳ���
 * @author wuxuehong
 * 2012-1-8
 */
public class Coverage {

	private final static int N = 250000000;
	//1-22 X, Y
	long[] locat = {249250621,243199373,198022430,191154276,180915260,171115067,
			159138663,146364022,141213431,135534747,135006516,133851895,
			115169878,107349540,102531392,90354753,81195210,78077248,
			59128983,63025520,48129895,51304566,155270560,59373566};

	//ÿ��Ⱦɫ���ϱ�ƥ��ĳ���
	long[] mapped = new long[23];
	
	/**
	 * ��ȡƥ����reads��Ϣ
	 * ��watson��ƥ��Ľ��  ֱ�Ӽ���
	 * @param filename   ƥ����
	 * @param b          ��ʶ���
	 * @param length     Ⱦɫ�峤��
	 */
	public void readMappedReads_Watson(String filename,byte[] b){
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
				for(int i=start;i<=end;i++)b[i]=1;
				str = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
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
				for(int i=start;i<=end;i++)b[i]=1;  
				str = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public Coverage() {
		// TODO Auto-generated constructor stub
		for(int i=1;i<=22;i++){
			byte[] b = new byte[N];
			int count = 0;
			readMappedReads_Crick("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_CT_Chr"+i+".txt",
				 b,locat[i-1]);
			readMappedReads_Crick("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_GA_Chr"+i+".txt",
				 b,locat[i-1]);
			
			readMappedReads_Watson("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_CT_Chr"+i+".txt",
				 b);
			readMappedReads_Watson("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_GA_Chr"+i+".txt",
				 b);
			for(int j=0;j<=locat[i-1];j++)if(b[j]==1)count++;
			b = null;
			mapped[i-1]=count;
			System.out.println(i+"\t\t\t\t\t"+count);
		}
		
		byte[] b = new byte[N];
		int count = 0;
		readMappedReads_Crick("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_CT_ChrX.txt",
				b,locat[22]);
		readMappedReads_Crick("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/crick_GA_ChrX.txt",
				b,locat[22]);
		readMappedReads_Watson("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_CT_ChrX.txt",
				b);
		readMappedReads_Watson("E:/�о�������/personal_data/1999.GAC.454Reads/bwaƥ����/FormatConvert/watson_GA_ChrX.txt",
				b);
		for(int j=0;j<=locat[22];j++)if(b[j]==1)count++;
		b = null;
		mapped[22]=count;
		
		int total = 0;
		for(int i=0;i<23;i++){
			total+=mapped[i];
		}
		
		long total2 = 0;
		for(int i=0;i<23;i++)
			total2+=locat[i];
		System.out.println();
		System.out.println("total:"+total);
		System.out.println("total2:"+total2);
		System.out.println("total/total2:"+(double)total/(double)total2);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Coverage();
	}

}
