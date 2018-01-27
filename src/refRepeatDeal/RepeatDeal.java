package refRepeatDeal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import util.FileStreamUtil;

public class RepeatDeal {

	public RepeatDeal() throws IOException {
		// TODO Auto-generated constructor stub
		inputAndOutput("E:/�о�������/mouse data/mouse�ֶ�+repeat����/rchr20_new.fa",
				"E:/�о�������/mouse data/mouse�ֶ�+repeat����/rchr20.fa");
	}
	
	/**
	 * ��Сд��ĸ����X
	 * @param str
	 * @return
	 */
	public String repeatDeal(String str){
	    str = str.replaceAll("a", "X");
	    str = str.replaceAll("c", "X");
	    str = str.replaceAll("g", "X");
	    str = str.replaceAll("t", "X");
		return str;
	}
	
	/**
	 * �ļ��������
	 * @param inputFile
	 * @param outFile
	 * @throws IOException
	 */
	public void inputAndOutput(String inputFile, String outFile) throws IOException{
		BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
		BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
		String str = br.readLine(); //���Ե�һ��
		str = br.readLine();
		while(str != null){
			bw.write(repeatDeal(str));
			bw.newLine();
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
			new RepeatDeal();
	}

}
