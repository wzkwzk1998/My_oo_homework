package com.person.system;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Logger;

public class Person {
	
	private IDNum id;
	private String name;
	private int sex;     //1为男性，2为女性
	private Date birthday;
	private String passWord;
	
	public Person() {
		setId(new IDNum());
		setName("");
		setSex(0);
		setBirthday(new Date());
		passWord = "a123456";
	}
	
	public IDNum getId() {
		return id;
	}

	public void setId(IDNum id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public void setPassWord(String psw) {
		this.passWord = psw;
	}
	
	public static ArrayList<String> deCode(String inputs){
		ArrayList<String> list = new ArrayList<String>();
		int len = inputs.length();
		int mid = inputs.length()-18;
		if(mid<0) mid = 0;
		String name = inputs.substring(0, mid);
		String id = inputs.substring(mid, len);
		list.add(name);
		list.add(id);
		return list;
	}
	
	public static boolean checkName(String name) {
		return name.matches("[a-zA-Z]+");
	}
	public static boolean checkTID(String TID) {
		return TID.matches("[0-9]{5}");
	}
	public static boolean checkSID(String SID) {
		return SID.matches("[0-9]{8}");
	}
	
	
	/*
	 * @param name 名字
	 * @param id 身份证号
	 * @param stID 教室号或学生号
	 * @param type 学生或者老师
	 * @return Person 实例
	 */
	public static Person newPerson(String name,String id,String stId,String type) throws PersonException{
		
		//检查是否合法
		
		Logger.getGlobal().info("id = "+id);
		if(Person.checkName(name)==false) {
			throw new PersonException(ErrorCodeEnum.Name_Illegal_Error);
		}
		if(IDNum.checkIDNum(id) == false) {
			throw new PersonException(ErrorCodeEnum.ID_Illegal_Error);
		}
		if(type == "teacher") {
			if(checkTID(stId)==false) {
				throw new PersonException(ErrorCodeEnum.TID_Illegal_Error);
			}
		}else if(type == "student") {
			if(checkSID(stId)==false) {
				throw new PersonException(ErrorCodeEnum.SID_Illegal_Error);
			}
		}
		
		
		//若合法,新建一个Person类,设置属性。‘
		Person person = new Person();
		
		IDNum idNum = new IDNum();
		idNum.setIdNum(id);
		
		person.setId(idNum);
		person.setName(name);
		person.setSex(Integer.parseInt(id.substring(14,17))%2);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			sdf.setLenient(false);
			Date dir = sdf.parse(id.substring(6,14));
			person.setBirthday(dir);
		}catch(Exception ex) {
			Logger.getGlobal().info("get data false");
//			System.out.println("false");
			throw new PersonException(ErrorCodeEnum.ID_Illegal_Error);
		}
		
		return person;
	}
	
	
	public static boolean pswCheck(String psw) {
		int hasNum = 0 ;
		int hasLower = 0;
		int hasUpper = 0;
		int hasOther = 0;
		if(psw.length()>18||psw.length()<6) {
			return false;
		}
		for(int i=0;i<psw.length();i++) {
			char c = psw.charAt(i);
			if(c<33||c>126) {
				return false;
			} else if(c>'A'&&c<'Z') {
				hasUpper = 1;
			} else if(c>'a'&&c<'z') {
				hasLower = 1;
			} else if(c>'0'&&c<'9') {
				hasNum=1;
			} else {
				hasOther = 1;
			}
		}
		if((hasNum+hasLower+hasUpper+hasOther)<2) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String temp = "";
		temp = temp+"Name:"+this.getName()+"\n";
		temp = temp+"IDNum:"+this.getId().toString()+"\n";
		temp = temp+"Sex:";
		temp += this.getSex()==0 ? "F":"M";
		temp += "\n";
		temp = temp+"Birthday:"+new SimpleDateFormat("yyyy/MM/dd").format(this.getBirthday());
		return temp;
	}
}
