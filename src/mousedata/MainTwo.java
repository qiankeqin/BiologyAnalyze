package mousedata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import util.FileStreamUtil;

/**
 * �����е�read�ϲ������һ��read��z���ڲ�ֵͬ����ƥ������ȡmatch/�ܳ������Ǹ�������÷���ͬ ��ȡzֵ���ġ�
 * @author wuxuehong
 * 2011-11-20
 */
public class MainTwo {
	
	Map<String, ReadVo> readMap = new HashMap<String, ReadVo>();
	String header = "ReadID	RefID	Starting position of ref in the alignment	End position of ref in the alignment	Length of read	Length of ref	Match/Reads	Ciagr	misMatch/Match	Mapping strand of ref";

	public MainTwo() throws IOException {
		// TODO Auto-generated constructor stub
		inputAndOutput("f_rfsc-2_C2T_Crick_aln", "f_rfsc-2_C2T_Crick_aln");
	}
	
	public void  inputAndOutput(String inpuFile, String outFile) throws IOException{
		for(int i=1;i<11;i++){
			System.out.println("reading:"+i);
			readReads("E:/�о�������/mouse data/mousez=1-50/z"+i+"/"+inpuFile);
			System.out.println(readMap.size());
		}
		System.out.println(readMap.size());
		List<ReadVo> list = new ArrayList<ReadVo>(readMap.values());
		Collections.sort(list);
		writeReads("E:/�о�������/mouse data/mousez=1-50/z1-50/"+outFile, list);
	}
	
	public void writeReads(String outFile,List<ReadVo> list) throws IOException{
		BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
		bw.write(header);
		bw.newLine();
		ReadVo read = null;
		for(int i=0;i<list.size();i++){
			read = list.get(i);
			bw.write(read.getSeq());
			bw.newLine();
		}
		bw.flush();
		bw.close();
		}
	
	public void readReads(String filename) throws IOException{
		BufferedReader br = FileStreamUtil.getBufferedReader(filename);
		String str = br.readLine(); //get rid of the first line.
		str = br.readLine();
		Scanner s = null;
		String id;
		float match;
		ReadVo read = null;
		while(str != null){
			s = new Scanner(str);
			id = s.next();  //��ȡreadID
			for(int i=0;i<5;i++) s.next(); //���Խ�����5��Ԫ��
			match = Float.parseFloat(s.next()); //��ȡ��match/read��ֵ
			read = readMap.get(id);
			if(read==null){   //���map�в����ڸö���
				read = new ReadVo();  //��������
				read.setMathcs(match);
				read.setReadID(id);
				read.setSeq(str);
				readMap.put(id, read);  //����map
			}else{                     //���������� �� �Ƚ�matchֵ
				if(match>=read.getMathcs()){ 
					read.setMathcs(match);
					read.setReadID(id);
					read.setSeq(str);
				}
			}
			str = br.readLine();
		}
		br.close();
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new MainTwo();
	}

}
