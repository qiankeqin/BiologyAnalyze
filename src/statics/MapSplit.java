package statics;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;

import util.FileStreamUtil;

/**
 * ͳ��map�ʵ͵�reads��ֺ�����ƥ���reads����
 * @author wuxuehong
 * 2012-1-16
 */
public class MapSplit {
	
	int total = 0;
	Set<String> reads = new HashSet<String>();
	
	public void readMappbelReads(String filename){
//		reads.clear();
		try{
			BufferedReader br = FileStreamUtil.getBufferedReader(filename);
			String str = br.readLine();
			str = br.readLine();
			while(str != null){
				reads.add(str.substring(0,16));
				str = br.readLine();
			}
			br.close();
//			System.out.println("read:"+filename);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public MapSplit() {
		// TODO Auto-generated constructor stub
		for(int i=1;i<=22;i++){
			readMappbelReads("E:/�о�������/personal_data/2010.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/unmappble/bwa/Chr"+i+"_watson_CT");
			readMappbelReads("E:/�о�������/personal_data/2010.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/unmappble/bwa/Chr"+i+"_watson_GA");
			readMappbelReads("E:/�о�������/personal_data/2010.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/unmappble/bwa/Chr"+i+"_crick_CT");
			readMappbelReads("E:/�о�������/personal_data/2010.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/unmappble/bwa/Chr"+i+"_crick_GA");
		}
		readMappbelReads("E:/�о�������/personal_data/2010.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/unmappble/bwa/ChrX_watson_CT");
		readMappbelReads("E:/�о�������/personal_data/2010.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/unmappble/bwa/ChrX_watson_GA");
		readMappbelReads("E:/�о�������/personal_data/2010.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/unmappble/bwa/ChrX_crick_CT");
		readMappbelReads("E:/�о�������/personal_data/2010.GAC.454Reads/bwaƥ����/FormatConvert/Fusion/unmappble/bwa/ChrX_crick_GA");
		System.out.println("total:"+reads.size());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new MapSplit();
	}

}
