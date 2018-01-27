package GO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * ����known.txt�е���֪�����ʸ����� 
 * ȥ ��̬�����ʸ����� �Լ���̬�����ʸ������зֱ�Ѱ��һ�����ƥ�����������
 * @author wuxuehong
 *
 * 2012-3-27
 */
public class BestMatch {
	
	public void readAndOutput(String inputFile, String outFile){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outFile)));
			String str =  br.readLine();
			int index = 1;
			while(str != null){
				if(str.startsWith("Complex")){
					bw.write("Complex\t"+(index++));
					bw.newLine();
				}else{
					bw.write(str);
					bw.newLine();
				}
				str = br.readLine();
			}
			br.close();
			bw.flush();
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	String base = "G:/personal/�ҵ�����/BMC/BMCʵ�鲹��/BMCʵ����/Response2Nature/";
	public BestMatch() {
		// TODO Auto-generated constructor stub
		readAndOutput(base+"nature.txt", base+"out.txt");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BestMatch();
	}

}
