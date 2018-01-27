package ���бȶԴ���һ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Future;

import util.LogServer;

public class Main {
	//�洢ID��BeanVo�����ӳ��
	private Map<String,BeanVo> beanMap = new HashMap<String,BeanVo>();
	//�洢����BeanVo����
	private List<BeanVo> beans = new ArrayList<BeanVo>();

	public Main() throws IOException {
		// TODO Auto-generated constructor stub
		String path = "e:/�о�������/B1(14-1)/B1(14-1)/";
		readFiles(path, 23);
		LogServer.log("reads Num"+beans.size()+"\t\t"+beanMap.size());

		//output format 1
		//outputFiles(path+"output/simple.txt", path+"output/detail.txt");
		//output format 2    separate files
		//B1-chr23-out.bsmapper.best
		outputFiles(path+"output/B1-chr","-out.bsmapper.best.filter", 23);
	}

	/**
	 * ������ȡ�ļ���Ϣ
	 * @param path �ļ�·��
	 * @param N  �ļ�����
	 * @throws IOException 
	 */
	public void readFiles(String path,int N) throws IOException{
		for(int i=1 ; i<=N; i++ ){
			System.out.println("Reading..."+i);
			readFile(path+"hsoc-B1-chr"+i+"(14-1)/hsoc-B1-chr"+i+"-out.bsmapper.best", i);
		}
	}
	/**
	 * ��ȡ�����ļ�
	 * @param filename �ļ�����
	 * @throws IOException 
	 */
	public void readFile(String filename,int index) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
		String str = br.readLine();
		Scanner s = null;
		String id = null;
		BeanVo bean = null;
		while(str != null){
			s = new Scanner(str);
			id = s.next();    //��ȡriseID 
			bean = beanMap.get(id);  //�鿴ID ��Ӧ��BeanVo�����Ƿ����
			if(bean == null){           //���������
				bean  = new BeanVo();   //�����ö���
				bean.setId(id);         //����ID����
				bean.setIndex(index);   //����Ⱦɫ����
				try{
				for(int i=0;i<4;i++)s.next();   //�����ĸ�ֵ
				}catch (Exception e) {
					System.out.println(id);
				}
				int a = s.nextInt();           //��ȡƥ�����е���ʼλ��
				int b = s.nextInt();           //��ȡ���н���λ��
				bean.setNum(b-a+1);            //��þ�ȷƥ���������
				bean.getList().add(str);       //��ԭʼ��Ϣ����
				for(int i=0;i<3;i++)bean.getList().add(br.readLine()); //������������ж�Ӧ��ԭʼ��Ϣ
				beans.add(bean);            //����bean����
				beanMap.put(id, bean);      //�洢ID��bean�����ӳ��
			}else{                         //�����ǰID��Ӧ��bean �����Ѿ����� 
				for(int i=0;i<4;i++)s.next();  //���Խ��������ĸ�ֵ
				int a = s.nextInt();          //��ȡƥ�����е���ʼλ��
				int b = s.nextInt();           //��ȡƥ��Ľ���λ��
				b = b-a+1;                     //��ȡ��ȷƥ���������
				if(b>bean.getNum()){           //�����ǰƥ��������� ���� ���滻֮ǰ��bean����
					bean.setIndex(index);  //����Ⱦɫ����
					bean.setNum(b);            //�������¾�ȷƥ���������
					bean.getList().clear();    //��ԭ��bean�����ԭʼ��Ϣ���
					bean.getList().add(str);    //�洢����ԭʼ��Ϣ
					for(int i=0;i<3;i++)bean.getList().add(br.readLine());// �洢���½��������ж�Ӧ��ԭʼ��Ϣ
				}else{
					for(int i=0;i<3;i++)br.readLine();  //�����ǰ��ȷƥ������������� ����Խ���������
				}
			}
			str = br.readLine();
		}
	}
	/**
	 * ���ͳ����Ϣ���ļ�
	 * @param simpleFile �������Ϣ   readsID num Ⱦɫ����
	 * 
	 * @param detailFile �����ϸ��Ϣ ����ԭ��ʽ��������Ϣ
	 * @throws IOException 
	 */
	public void outputFiles(String simpleFile,String detailFile) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(simpleFile)));
		int num = beans.size();
		BeanVo bean = null;
		for(int i=0 ; i<num; i++){
			bean = beans.get(i);
			bw.write(bean.getId()+"\t"+bean.getNum()+"\thchr"+bean.getIndex()+".fa");
			bw.newLine();
		}
		bw.flush();
		bw.close();
		LogServer.log("Output simple file successfully!");
		
		bw = new BufferedWriter(new FileWriter(new File(detailFile)));
		for(int i=0; i<num; i++){
			bean = beans.get(i);
			bw.write(bean.getList().get(0)); //+"\t"+bean.getNum()+"\thchr"+bean.getIndex()+".fa"
			bw.newLine();
			for(int j=1; j<4; j++){
				bw.write(bean.getList().get(j));
				bw.newLine();
			}
		}
		bw.flush();
		bw.close();
		LogServer.log("Output detail file successfully!");
	}
	
	/**
	 * ��beansд�� num���ļ���ȥ��
	 * ÿ�� 
	 * @param filename  �ļ����� 
	 * @param num  �ļ�������Ⱦɫ�������
	 * @throws IOException 
	 * @hsoc-B1-chr23-out.bsmapper.best
	 */
	public void outputFiles(String postfix,String suffix,int num) throws IOException{
		BufferedWriter[] bw = new BufferedWriter[num];
		for(int i=0;i<num;i++){
			bw[i] = new BufferedWriter(new FileWriter(new File(postfix+(i+1)+suffix)));
		}
		int size = beans.size();
		int index = 0;
		BeanVo bean = null;
		for(int i=0; i<size; i++){
			bean = beans.get(i);
			index = bean.getIndex();
			bw[index-1].write(bean.getList().get(0));
			bw[index-1].newLine();
			for(int j=1; j<4; j++){
				bw[index-1].write(bean.getList().get(j));
				bw[index-1].newLine();
			}
		}
		for(int i=0;i<num;i++){
			bw[i].flush();
			bw[i].close();
		}
		LogServer.log("Out put files successfully!");
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
