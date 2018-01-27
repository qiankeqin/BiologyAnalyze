package ͳ�Ʒ���;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import util.FileStreamUtil;

/**
 * Consisitent��
 * ����REF��ÿ��CG��ƥ���reads ����Ŀ�У� �׻����ı���
 * @author wuxuehong
 * 2012-1-10
 */
public class Consisitent {
	
	//CA consistent: 0.5961467
	
	private int totalCg = 0; //����depth��CG�ĸ���
	private int totalDepth = 0; //�������֮��
	private int N = 1000;
	private String p;
	private char pp;
	
	private float totalConsistent = 0; //�ܵ�consisitent�ʺ�
	
	//reads ID��seqӳ��
	private Map<String, String> id2seq = new HashMap<String, String>();
	//1-22 X, Y
	long[] locat = {249250621,243199373,198022430,191154276,180915260,171115067,
			159138663,146364022,141213431,135534747,135006516,133851895,
			115169878,107349540,102531392,90354753,81195210,78077248,
			59128983,63025520,48129895,51304566,155270560,59373566};
	
	/**
	 * ��ȡԭʼreads��Ϣ
	 * @param filename
	 */
	public void readOringinalReads(String filename){
		try{
			String readid="", seq="";
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			while(str != null){
				if(str.startsWith(">")){
					if(readid.length()!=0){
						id2seq.put(readid, seq);
					}
					seq = "";
					readid = str.substring(1,str.indexOf("rank")-1); 
				}else{
					seq = seq+str;
				}
				str = br.readLine();
			}
			id2seq.put(readid, seq);
			br.close();
	System.out.println("Reads size:"+id2seq.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡƥ����reads��Ϣ
	 * ��watson��ƥ��Ľ��  ֱ�Ӽ���
	 * @param filename   ƥ����
	 * @param cg          ��ʶcgλ�õ����
	 * @param total       ��ʾ����λ�õ����
	 * @param length     Ⱦɫ�峤��
	 */
	public void readMappedReads_Watson(String filename,byte[] cg, byte[] total){
		String str = null;
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			str = br.readLine();
			Scanner s = null;
			str = br.readLine();
			int start,end;
			String seq;  //����
			String readid; 
			char[] c;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				s.next();
				s.next();
				start = s.nextInt();
				end = s.nextInt();
				seq = id2seq.get(readid);
				
				c = seq.toCharArray();
				for(int i=start;i<=end;i++){
					if( (i-start+1<seq.length()) && c[i-start]=='C' && c[i-start+1]==pp){ //ֻ����CG�����
									cg[i]++; //����CGλ�����
									total[i]++;
					}
					if( (i-start+1<seq.length()) && c[i-start]=='T' && c[i-start+1]==pp) //����TG�����
							       total[i]++;   //��������λ�����
				}
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
	public void readMappedReads_Crick(String filename,byte[] cg,byte[] total, long length){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			Scanner s = null;
			str = br.readLine();
			int start,end,temp;
			String seq,readid;
			char[] c;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				s.next();
				s.next();
				start = s.nextInt();
				end = s.nextInt();
				seq = id2seq.get(readid);
				
				c = seq.toCharArray();
				for(int i=start;i<=end;i++){
					if( (i-start+1<seq.length()) && c[i-start]=='C' && c[i-start+1]==pp){ //ֻ����CG�����
								cg[(int) (length+1-i)]++; //����CGλ�����
								total[(int) (length+1-i)]++;
					}
					if( (i-start+1<seq.length()) && c[i-start]=='T' && c[i-start+1]==pp) //ֻ����TG�����
								total[(int) (length+1-i)]++;   //����TGλ�����  
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
	public void readChr(String chrFile, String outFile,byte[] total, byte[] cg){
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
					if(ch[i]=='C'&&ch[i+1]==pp){  //����� CGλ��
						 position = length+(i+1); //��ȡ��C��λ����Ϣ
						 if(total[position]>0){  //�����CGλ���ϵ���ȴ���0��������ļ�
							 totalCg++;  //CG�����ͼ�1
							 totalDepth+=total[position]; //����ۼ�
							 
							 totalConsistent += (float)cg[position]/(float)total[position];
							 bw.write("C"+pp+"\t"+position+"\t"+total[position]+"\t"+cg[position]+"\t"+(float)cg[position]/(float)total[position]);
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


	public Consisitent(String args[]) {
		if(args.length!=2){
			System.out.println("��������������ԭʼreads�ļ�!�Լ�CG/CAѡ��");
			System.out.println("java -jar -Xms1024m -Xmx1024m -jar Consistent.jar reads.fa CA/CG");
			return;
		}
		String readsFile = args[0];
		pp = args[1].charAt(1);
		if(!(pp=='G'||pp=='A')){
			System.out.println("��������!");
			System.out.println("java -jar -Xms1024m -Xmx1024m -jar Consistent.jar reads.fa CA/CG");
			return;
		}
		readOringinalReads(readsFile);
		
		File f = new File("C"+pp+"_consistent");
		if(!f.exists())f.mkdir();
		for(int i=1;i<=22;i++){
			System.out.println("Reading chr"+i);
			byte[] total = new byte[(int)locat[i-1]+N];
			byte[] cg = new byte[(int)locat[i-1]+N];
			readMappedReads_Crick("../crick_CT_Chr"+i+".txt",cg,total,locat[i-1]);
			readMappedReads_Crick("../crick_GA_Chr"+i+".txt",cg,total,locat[i-1]);
			readMappedReads_Watson("../watson_CT_Chr"+i+".txt", cg,total);
			readMappedReads_Watson("../watson_GA_Chr"+i+".txt", cg,total);
			readChr("HumanGeneRepeat/hg19_Chr"+i+".txt", "C"+pp+"_consistent/hg19_Chr"+i+".txt", total,cg);
			total = null;
			cg = null;
		}
		
		System.out.println("Reading chrX");
		byte[] total = new byte[(int)locat[22]+N];
		byte[] cg = new byte[(int)locat[22]+N];
		readMappedReads_Crick("../crick_CT_ChrX.txt",cg,total,locat[22]);
		readMappedReads_Crick("../crick_GA_ChrX.txt",cg,total,locat[22]);
		readMappedReads_Watson("../watson_CT_ChrX.txt",cg,total);
		readMappedReads_Watson("../watson_GA_ChrX.txt",cg,total);
		readChr("HumanGeneRepeat/hg19_ChrX.txt", "C"+pp+"_consistent/hg19_ChrX.txt", total,cg);
		
		System.out.println("Total consistent:"+totalConsistent);
		System.out.println("Total C"+pp+":"+totalCg);
//		System.out.println("Total Depth:"+totalDepth);
//		System.out.println("Average Depth:"+(float)totalDepth/(float)totalCg);
		System.out.println("Average Consistent:"+totalConsistent/totalCg);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Consisitent(args);
	}

}
