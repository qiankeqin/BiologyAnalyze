package ��Ⱦɫ�����ݽ��д���;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import util.FileStreamUtil;

public class Main {

	public Main() throws IOException {
		// TODO Auto-generated constructor stub
		readAndOutput("E:\\�о�������\\Ⱦɫ�������Լ���������\\hchr22.fa", "E:\\�о�������\\Ⱦɫ�������Լ���������\\hchr22_filter.fa");
	}
	
	
	/**
	 * ת���ַ��� �����з�CG������C ����T    ���һ���ַ�������
	 * @param str
	 * @return
	 */
	public String convert(String str){
		char[] c = str.toCharArray();
		int l = c.length;
		for(int i=0;i<l-1;i++){
			if(c[i]=='C' && c[i+1]!='G'){
				c[i] = 'T';
			}
		}
		return String.valueOf(c);
	}
	/**
	 * ���ַ��������һ���ַ�C����T
	 * @param str
	 * @return
	 */
	public String convertLast(String str){
		char[] c = str.toCharArray();
		int l = c.length;
		c[l-1] = 'T';
		return String.valueOf(c);
	}
	
	public void readAndOutput(String inputFile,String outFile) throws IOException{
		BufferedReader br = FileStreamUtil.getBufferedReader(inputFile);
		BufferedWriter bw = FileStreamUtil.getBufferedWriter(outFile);
		String str = br.readLine();   //��ȡ��һ�е���>��ͷ��ע����Ϣ
		bw.write(str);       //����һ������д�뵽����ļ�
		bw.newLine();       //����
		str = br.readLine();  //��ȡ�ڶ�������
		String prefix = null;  //���ڴ洢ǰһ������     ��ǰһ�����������һ���ַ���Cʱ ���øñ����洢�ַ���
		while(str != null){
			//�����һ�������� �ȴ�����һ��
			if(prefix != null){
				//���ǰһ���ַ��������һ���ַ�ΪC
				if(str.charAt(0)=='G'){  //�����ǰ�е�һ���ַ���G�� ֱ�ӽ���һ���������������ļ�����
					bw.write(prefix);
					bw.newLine();
				}else{   //�����һ���ַ�����G ����һ�����һ���ַ�C ����T �����������ļ�
					prefix = convertLast(prefix);
					bw.write(prefix);
					bw.newLine();
				}
			}
			//����ǰ������
			    str = convert(str); //
				if(str.charAt(str.length()-1)=='C'){ //������һ���ַ���C
					prefix = str;         //����洢������prefix��
				}else{                    //������һ���ַ�����C
					prefix = null;        //������prefiz��Ϊnull
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
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				new Main();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
