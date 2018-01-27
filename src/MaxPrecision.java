import java.io.BufferedReader;
import java.io.BufferedWriter;
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
 * bwa �㷨�������Ľ��  ����readsƥ������ߵ����
 * @author wuxuehong
 * 2012-2-2
 */
public class MaxPrecision {
	
	/**
	 * 
	 * @param inputFile    bwa������� ���Ұ���map�ʴӴ�С����������
	 * @param outFile      bwa��������� ����readsƥ�������
	 */
	public void readAndOutput(String inputFile, String outFile){
		try{
			Map<String,ReadVo> rmap = new HashMap<String, ReadVo>();
			BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
			BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
			String str = br.readLine();
			if(str.startsWith("ReadID")){  //��������� ����Ե�һ��
				str = br.readLine();
			}
			String readid = null;
			Scanner s = null;
			int i;
			float value;
			ReadVo read;
			while(str != null){
				s = new Scanner(str);
				readid = s.next();
				for(i=0;i<6;i++)s.next();
				value = Float.parseFloat(s.next());  //ƥ����
				read = rmap.get(readid);
				if(read == null || (read!=null && value > read.getMathcs())){  //���read�ǿ� ����read��ƥ���ʽ�С  �����map
					read = new ReadVo();
					read.setReadID(readid);
					read.setSeq(str);
					read.setMathcs(value);
					rmap.put(readid, read);
				}
				str = br.readLine();
			}
			br.close();
			
			List<ReadVo> list = new LinkedList<ReadVo>(rmap.values());
			Collections.sort(list);
			for(i=0;i<list.size();i++){
				read = list.get(i);
				bw.write(read.getSeq());
				bw.newLine();
			}
			bw.flush();
			bw.close();
			System.out.println(rmap.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public MaxPrecision(){
		readAndOutput("watson_CT.txt",
				"maxprecision/watson_CT.txt");
		readAndOutput("watson_GA.txt",
		"maxprecision/watson_GA.txt");
		readAndOutput("crick_CT.txt",
		"maxprecision/crick_CT.txt");
		readAndOutput("crick_GA.txt",
		"maxprecision/crick_GA.txt");
	}
	
	public static void main(String args[]){
		new MaxPrecision();
	}

}
