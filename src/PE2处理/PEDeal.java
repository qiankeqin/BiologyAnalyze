package PE2����;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLClientInfoException;

import util.*;

public class PEDeal {
	
	
	/**
	 * ����    ���д���  fastq��ʽ
	 * @param inputFile
	 * @param outFile
	 * @throws IOException
	 */
	public void inputAndOutputByorder(String inputFile, String outFile) throws IOException{
		BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
		BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
		String str = br.readLine();
		while(str != null){
			if(str.startsWith("@")){
				bw.write(str);        //����ID
				bw.newLine();
				//���д���
				str = br.readLine();   //����\
				
				//���д���ʼ
//				str = SeqUtil.C2T(str);  //C-T
//				str = SeqUtil.G2A(str);  //G-A
//				str = SeqUtil.reserve(str);  str = SeqUtil.C2T(str); //���� C->T
//				str = SeqUtil.reserve(str);  str = SeqUtil.G2A(str);  //���� G->A
//				str = SeqUtil.complement(str);  str = SeqUtil.C2T(str);  //����C->T
//				str = SeqUtil.complement(str);  str = SeqUtil.G2A(str);  //����G->A
//				str = SeqUtil.reserve(str);  str = SeqUtil.complement(str); str = SeqUtil.C2T(str); //����  ���� C->T
				str = SeqUtil.reserve(str);  str = SeqUtil.complement(str); str = SeqUtil.G2A(str); //���� ����G->A
//				���д������
				
				bw.write(str);
				bw.newLine();
				
				bw.write(br.readLine());  //���е�������Ϣ
				bw.newLine();
				bw.write(br.readLine());  //������������
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
			PEDeal pd = new PEDeal();
			pd.inputAndOutputByorder("E:/�о�������/PE1PE2/Crick/PE_1.fastq",
					"E:/�о�������/PE1PE2/Crick/PE_1_RCG2A.fastq");
			//test
//			pd.inputAndOutputByorder("d:/test.txt", "d:/reserve_com_C2T.TXT");
	}

}
