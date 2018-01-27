package HumanGenDeal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import util.FileStreamUtil;

public class ReadDeal {

	public ReadDeal() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 454CT����454reads�е�Cת����T
	 * @param inputFile
	 * @param outFile
	 * @throws IOException
	 */
	public void C2T(String inputFile,String outFile) throws IOException{
		BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
		BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
		String str = br.readLine();
		while(str != null){
			if(!str.startsWith(">")){
				str = str.replaceAll("C", "T");
			}
			bw.write(str);
			bw.newLine();
			str = br.readLine();
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	/**
	 * 454GA����454reads�е�Gת����A
	 * @param inputFile
	 * @param outFile
	 * @throws IOException
	 */
	public void G2A(String inputFile,String outFile)throws IOException{
		BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
		BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
		String str = br.readLine();
		while(str != null){
			if(!str.startsWith(">")){
				str = str.replaceAll("G", "A");
			}
			bw.write(str);
			bw.newLine();
			str = br.readLine();
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	public static void main(String args[]){
		ReadDeal rd = new ReadDeal();
		try {
			rd.C2T("E:\\�о�������\\personal_data\\o1.GAC.454Reads\\bwaƥ����\\FormatConvert\\Fusion\\unmapped\\unmapped.txt",
					"E:\\�о�������\\personal_data\\o1.GAC.454Reads\\bwaƥ����\\FormatConvert\\Fusion\\unmapped\\CT.txt");
			rd.G2A("E:\\�о�������\\personal_data\\o1.GAC.454Reads\\bwaƥ����\\FormatConvert\\Fusion\\unmapped\\unmapped.txt", 
					"E:\\�о�������\\personal_data\\o1.GAC.454Reads\\bwaƥ����\\FormatConvert\\Fusion\\unmapped\\GA.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
