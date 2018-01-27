package HummanGeneDeal_step1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import util.FileStreamUtil;

/**
 * �ļ��ں�
 * ��23���ļ��ںϵ�һ���ļ���
 * ������
 * @author wuxuehong
 * 2012-1-13
 */
public class FileFusion {
	
	/**
	 * ��ȡ�ļ����ݵ���һ���ļ�
	 * @param filename
	 * @param bw
	 */
	public void readAndOutput(String filename, BufferedWriter bw){
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine(); //���Ե�һ��
			str = br.readLine();
			while(str != null){
				bw.write(str);
				bw.newLine();
				str = br.readLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public FileFusion() {
		try{
			BufferedWriter bw = FileStreamUtil.getBufferedWriter("E:/morehouse/o6/FormatConvert/Fusion/crick_CT.txt");
			for(int i=1;i<=22;i++){
				readAndOutput("E:/morehouse/o6/FormatConvert/crick_CT_Chr"+i+".txt", bw);
			}
			readAndOutput("E:/morehouse/o6/FormatConvert/crick_CT_ChrX.txt", bw);
			bw.flush();
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new FileFusion();
	}

}
