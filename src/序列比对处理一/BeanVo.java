package ���бȶԴ���һ;

import java.util.ArrayList;
import java.util.List;

public class BeanVo {
	
	private int index; //Ⱦɫ����
	
	private String id; //reads ID
	
	private int num;  //��ȷƥ��������Ŀ
	
	private List<String> list; //ԭʼ��Ϣ
	
	public BeanVo(){
		list = new ArrayList<String>();
	}
	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	
	
	

}
