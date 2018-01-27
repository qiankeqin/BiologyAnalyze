package ���ݴ����Լ��ȶ�;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import util.FileStreamUtil;


/**
 *  ����ƥ��Ĳ����Ѿ�����mismatch/match�����reads��Ϣ
 *  
 *  ������������������reads���
   1.read>200bp
   2 non-mapped portion<50bp 
 * @author wuxuehong
 * 2011-11-8
 */
public class MainTwo {

	public MainTwo() throws IOException {
		// TODO Auto-generated constructor stub
		readAndOutput("E:/�о�������/dataDeal/20111031/���matched���������read/aln_GA_watson.txt", 
				"E:/�о�������/dataDeal/20111031/���matched���������read/��һ��������/aln_GA_watson.txt");
		
		readAndOutput("E:/�о�������/dataDeal/20111031/���matched���������read/aln_CT_watson.txt", 
		"E:/�о�������/dataDeal/20111031/���matched���������read/��һ��������/aln_CT_watson.txt");
		
		readAndOutput("E:/�о�������/dataDeal/20111031/���matched���������read/aln_CT_crick.txt", 
		"E:/�о�������/dataDeal/20111031/���matched���������read/��һ��������/aln_CT_crick.txt");
		
		readAndOutput("E:/�о�������/dataDeal/20111031/���matched���������read/aln_GA_crick.txt", 
		"E:/�о�������/dataDeal/20111031/���matched���������read/��һ��������/aln_GA_crick.txt");
	}
	
	/**
	 * ����CIAGR �ַ���  ������ ƥ��ĸ���
	 * @param ciagr
	 * @return
	 */
	public int parseCiagr(String ciagr){
		int total = 0;
		int temp = 0;
		char[]  c = ciagr.toCharArray();
		int len = c.length;
		for(int i=0;i<len; i++){
			if(c[i]=='M'){
				total += temp;
			}
			if(c[i]>='0'&&c[i]<='9'){
				temp = temp*10+c[i]-'0';
			}else{
				temp = 0;
			}
		}
		return total;
	}
	
	public void readAndOutput(String inputFile, String outputFile) throws IOException{
		BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
		BufferedWriter bw = FileStreamUtil.getBufferedWriter(outputFile);
		String str = br.readLine();  //��ȡ��һ�� ����
		bw.write(str);
		bw.newLine();
		str = br.readLine();
		Scanner s = null;
		String seq,ciagr;
		int match = 0, len = 0;
		while(str != null){
			s = new Scanner(str);
			for(int i=0;i<7;i++) s.next();
			ciagr = s.next();
			seq = s.next();
			len = seq.length();          //��ȡread length
			match = parseCiagr(ciagr);   //����ciagr  ��ȡmatch�ĸ���
			if(len>200 && len-match<50){
				bw.write(str);
				bw.newLine();
			}
			str = br.readLine();
		}
		br.close();
		bw.flush();
		bw.close();
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
			MainTwo mt = new MainTwo();
//			System.out.println(mt.parseCiagr("29M1I11M1I15M1I5M1I38M1D12M1D50M1I16M1I11M1D18M31S"));
//			System.out.println("TATATATATATATATATATAATATATCTATATATCTATATATATATATCTATATATATTATATAATATATATATAATATCTATATATATATATATATATATAATATATCTATATTATATAATATATATATATATATAATATATATATATATATAATATATATATAATATATATATATATATAATATCTCTATATTTATATATATATATATAATATATATATATATATATAATATATATATAT".length());
	}

}
