package HummanGeneDeal_step1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import util.FileStreamUtil;

/**
 * ������ bwaƥ���Ľ��  ����ƥ����� ��ƥ��� ��δƥ��Ĳ��ֲ�ֿ���
 *��δƥ��Ĳ���������bwa�㷨 ����ƥ��
 *ƥ������� ��Ҫ��ƥ����Ϣ�ںϵ�ԭ����ƥ������ 
 *
 * @author wuxuehong
 * 2012-1-3
 */
public class DivideFusion {
	
	//ͬһ��seq��Ӧ���ܶ��ֲ�ͬƥ����
	public Map<String, List<String>> seq2reads = new HashMap<String, List<String>>();

	public DivideFusion() {
		// TODO Auto-generated constructor stub
		
		readMisMatchFile("E:/�о�������/personal data/ocsc1.GAC.454Reads/divided/mismatch/crick_CT.txt");
		readAndOutput("E:/�о�������/personal data/ocsc1.GAC.454Reads/divided/divided/crick_CT_divide.txt",
				         "E:/�о�������/personal data/ocsc1.GAC.454Reads/divided/crick_CT_divide.txt");
	}
	
	/**
wu	 * �ָ����ƥ���reads����ƥ������Ϣ
	 * @param mismatchFile
	 */
	public void readMisMatchFile(String mismatchFile){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(mismatchFile);
			String str = br.readLine(); //���Ե�һ��
			Scanner s = null;
			str = br.readLine();
			while(str != null){
				s = new Scanner(str);
				for(int i=0;i<9;i++) s.next();
				String seq = s.next();
				List<String> reads = seq2reads.get(seq);
				if(reads == null){
					reads = new ArrayList<String>();
					reads.add(str);
					seq2reads.put(seq, reads);
				}else{
					reads.add(str);
				}
				str = br.readLine();
			}
			br.close();
		System.out.println("seq2reads size:"+seq2reads.size());
		}catch(IOException e){}
	}

	public void readAndOutput(String originMapFile, String outFile){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(originMapFile);
			BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
			String str = br.readLine();
			Scanner s = null;
			String readid = null;
			String seq;
			while(str != null){
				s = new Scanner(str);
				if(s.hasNext()){   
					readid = s.next();
					if(readid.contains("_")){  //�������������� ��˵���ǲ�ֺ��
						s.next();
						seq = s.next(); 
						if(!s.hasNext()){  //���û�н�������Ԫ��  ��˵����reads��mismatch��
							List<String> readss = seq2reads.get(seq);
							if(readss==null || readss.size()==0){
								bw.write(str+"\tunmappable");
								bw.newLine();
							}else{
								for(int i=0;i<readss.size();i++){
									bw.write(readid+"\t"+readss.get(i).substring(17));
									bw.newLine();
								}
							}
							str = br.readLine();
							continue;
						}
					}
				}
				bw.write(str);
				bw.newLine();
				str = br.readLine();
			}
			br.close();
			bw.flush();
			bw.close();
		}catch(IOException e){
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new DivideFusion();
	}

}
