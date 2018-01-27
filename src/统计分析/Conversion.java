package ͳ�Ʒ���;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

import util.FileStreamUtil;

/**
 * �ֱ����C�� G����Conversion
 * 
 * ���㷽ʽ: C��  (#C-#CpG)/(reads��Cover��ref����)
 *           G��  (#G-#GpC)/(reads��Cover��ref����)
 * �ֱ���� Cover ����  �Լ�Cover bp�����������
 * @author wuxuehong
 * 2012-2-3
 */
public class Conversion {
	
	private final static int offset = 1000;
	private final static int LEN = 1; //reads length cover
	private final static int BP = 0;  //reads bp cover
	//1-22 X, Y
	long[] locat = {249250621,243199373,198022430,191154276,180915260,171115067,
			159138663,146364022,141213431,135534747,135006516,133851895,
			115169878,107349540,102531392,90354753,81195210,78077248,
			59128983,63025520,48129895,51304566,155270560,59373566};

	//ÿ��Ⱦɫ���ϱ�ƥ��ĳ���
	long[] mapped = new long[23];
	int[] c = new int[23];
	int[] g = new int[23];
	int[] cg = new int[23];
	int[] gc = new int[23]; 
	
	/**
	 * ��ȡƥ����reads��Ϣ
	 * ��watson��ƥ��Ľ��  ֱ�Ӽ���
	 * @param filename   ƥ����
	 * @param b          ��ʶ���
	 * @param length     Ⱦɫ�峤��
	 */
	public void readMappedReads_Watson(String filename,byte[] b,int type){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			Scanner s = null;
			str = br.readLine();
			int start,end,len;
			String cigra;
			char[] f;
			while(str != null){
				s = new Scanner(str);
				s.next();
				s.next();
				s.next();
				start = s.nextInt();
				end = s.nextInt();
				len = s.nextInt();
				s.next();
				s.next();
				cigra = s.next();
				
				switch(type){
					case LEN:  //cover length
						for(int i=start;i<=end;i++)b[i]=1;
						break; 
					case BP:  //cover bp
						f = getChars(cigra, len*2);
						for(int i=start;i<=end;i++){
							if(f[i-start]=='M')
								b[i]=1;
						}
				}
				str = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
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
				for(int j=index;j<index+count;j++)r[j]='D';
				index+=count;
				totalD+=count;
				count=0;
			}else if(c[i]=='I'){
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
	 * ��ȡ��Crickƥ����reads
	 * ע�⣺��ƥ������Ӧ��refλ����Ϣ����ԭʼrefλ����Ϣ����  ������ת����ԭʼλ����Ϣ
	 * @param filename
	 * @param b
	 * @param length
	 */
	public void readMappedReads_Crick(String filename,byte[] b, long length,int type){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			Scanner s = null;
			str = br.readLine();
			int start,end,temp,len;
			String cigra;
			char[] f;
			while(str != null){
				s = new Scanner(str);
				s.next();
				s.next();
				s.next();
				start = s.nextInt();
				end = s.nextInt();
				len = s.nextInt();
				s.next();
				s.next();
				cigra = s.next();
				
				switch(type){
					case LEN:
						for(int i=start;i<=end;i++)b[(int) (length+1-i)]=1;
						break;
					case BP:
						f = getChars(cigra, len*2);
						for(int i=start;i<=end;i++){
							if(f[i-start]=='M'){
								temp = (int) (length+1-i);
								b[temp] = 1;
							}
						}
				}
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
	public void readChr(String chrFile, byte[] b, int index){
		 int length = 0;
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(chrFile);
			br.readLine(); //���Ե�һ��
			String str = br.readLine();
			char[] ch;
			boolean furtherC = false; //��һ��ĩβ�Ƿ�ΪC
			boolean furtherG = false; //��һ��ĩβ�Ƿ�ΪG
			int position = 0;  //CG��λ��
			while(str != null){
				if(furtherC) str = "C"+str;
				if(furtherG) str = "G"+str;
 				ch = str.toUpperCase().toCharArray();
				for(int i=0;i<ch.length-1;i++){
					if(ch[i]=='C'){
						position = length+(i+1); //��ȡ��C��λ����Ϣ
						if(b[position]==1){   //�����cover��
								c[index]++;
								if(ch[i+1]=='G' && b[position+1]==1){
									cg[index]++;
								}
						}
					}else if(ch[i]=='G'){
						position = length+(i+1);
						if(b[position]==1){
								g[index]++;
								if(ch[i+1]=='C' && b[position+1]==1){
									gc[index]++;
								}
						}
					}
				}
				
				if(ch[ch.length-1]=='C') { //����ַ�����β��C  �򳤶�--
					furtherC =  true;   
				    length--;
				}else
					furtherC = false;
				
				if(ch[ch.length-1]=='G'){
					furtherG = true;
					length--;
				}else
					furtherG = false;
				
				length+=str.length();
				str = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Conversion(String args[]) {
		// TODO Auto-generated constructor stub
		if(args.length!=1){
			System.out.println("��������������reads cover����0-length,1-bp");
			return;
		}
		int type = 0;
		try{
			type = Integer.parseInt(args[0]);
			if(!(type==1||type==0)){
				throw new Exception();
			}
		}catch(Exception e){
			System.out.println("��������������reads cover����0-length,1-bp");
			return;
		}
		for(int i=1;i<=22;i++){
	System.out.println(i);
			byte[] b = new byte[(int) (locat[i-1]+offset)];
			int count = 0;
			readMappedReads_Watson("../watson_CT_Chr"+i+".txt", b,type);
			readMappedReads_Watson("../watson_GA_Chr"+i+".txt", b,type);
			readMappedReads_Crick("../crick_CT_Chr"+i+".txt", b, locat[i-1],type);
			readMappedReads_Crick("../crick_GA_Chr"+i+".txt", b, locat[i-1],type);
			for(int j=0;j<=locat[i-1];j++)if(b[j]==1)count++;
			mapped[i-1]=count;
			readChr("HumanGeneRepeat/hg19_Chr"+i+".txt", b, i-1);
			b = null;
		}
		
		byte[] b = new byte[(int) (locat[22]+offset)];
		int count = 0;
		readMappedReads_Watson("../watson_CT_ChrX.txt", b,type);
		readMappedReads_Watson("../watson_GA_ChrX.txt", b,type);
		readMappedReads_Crick("../crick_CT_ChrX.txt", b, locat[22],type);
		readMappedReads_Crick("../crick_GA_ChrX.txt", b, locat[22],type);
		for(int j=0;j<=locat[22];j++)if(b[j]==1)count++;
		mapped[22]=count;
		readChr("HumanGeneRepeat/hg19_ChrX.txt", b, 22);
		b = null;
		
		int total = 0;
		int totalc = 0;
		int totalg = 0;
		int totalcg = 0;
		int totalgc = 0;
		
		for(int i=0;i<23;i++){
			total+=mapped[i];
			totalc+=c[i];
			totalg+=g[i];
			totalcg+=cg[i];
			totalgc+=gc[i];
		}
		
		if(type==LEN){
			System.out.println("����ƥ��reads������bp cover ref����:");
		}else
			System.out.println("����ƥ��reads��ƥ���˵�bp cover ref����:");
		System.out.println("total coverd ref length:"+total);
		System.out.println("total coverd ref c length:"+totalc);
		System.out.println("total coverd ref g length:"+totalg);
		System.out.println("total coverd ref cg length:"+totalcg);
		System.out.println("total coverd ref gc length:"+totalgc);
		
		System.out.println("c conversion:"+(float)(totalc-totalcg)/(float)total);
		System.out.println("g conversion:"+(float)(totalg-totalgc)/(float)total);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Conversion c = 	new Conversion(args);
//		System.out.println(Arrays.toString(c.getChars("6S104M1I16M315S",442)));
	}

}
