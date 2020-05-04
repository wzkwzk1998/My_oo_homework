
package com.test;


import java.util.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import personsystem.*;
import coursesystem.*;



interface State{
	public void handle(String[] inputs);
}


/*
 * 初始状态
 */
class LevelOneState implements State {
	private PersonList pList;
	private CourseList cList;
	public LevelOneState(PersonList pList,CourseList cList) {
		this.pList = pList;
		this.cList = cList;
	}
	public void handle(String[] inputs) {
		
		//sudo分支
		if(inputs.length==1&&inputs[0].contentEquals("SUDO")) {
			QTest.setNowState(QTest.sudoState);
		} 
		
		//QUIT分支
		else if(inputs.length==1&&inputs[0].contentEquals("QUIT")) {
			System.exit(0);
		} 
		
		//login分支
		else if(inputs.length==4&&inputs[0].contentEquals("login")) {
			if(inputs[1].contentEquals("-t")) {
				try {
					Person user = pList.teaLoginCheck(inputs[2], inputs[3]);     //如果登录成功则返回一个用户
					System.out.println("Login success.");
					QTest.setUser(user);                           //设置用户
					QTest.setNowState(QTest.TeacherLoginState);              //转换状态
				} catch(PersonException ex) {
					System.out.println(ex.getCodeDescription());
				}
			}
			else if(inputs[1].contentEquals("-s")) {
				try {
					Person user = pList.stuLoginCheck(inputs[2], inputs[3]);     //如果登录成功则返回一个用户
					System.out.println("Login success.");
					QTest.setUser(user);                           //设置用户
					QTest.setNowState(QTest.StudentLoginState);              //转换状态
				} catch(PersonException ex) {
					System.out.println(ex.getCodeDescription());
				}
			}
			else {
				System.out.println("Input illegal.");
			}
		}
		
		else {
			System.out.println("Input illegal.");
		}
	}
}


/*
 * sudo登录状态
 */
class SudoState implements State {
	private PersonList pList;
	private CourseList cList;
	public SudoState(PersonList pList, CourseList cList) {
		this.pList = pList;
		this.cList = cList;
	}
	public void handle(String[] inputs) {
		
		
		//QUIT分支
		if(inputs.length==1&&inputs[0].contentEquals("QUIT")) {
			System.exit(0);
		}
		
		//back分支
		else if(inputs.length==1&&inputs[0].contentEquals("back")) {
			QTest.setNowState(QTest.levelOneState);
		}
		
		
		//np分支
		else if(inputs.length==5&&inputs[0].contentEquals("np")) {
			if(inputs[1].contentEquals("-t")) {
				try {
					pList.addTeacher(inputs[2], inputs[3], inputs[4]);
					System.out.println("Add teacher success.");
				} catch(PersonException ex) {
					System.out.println(ex.getCodeDescription());
				}
			}
			else if(inputs[1].contentEquals("-s")) {
				try {
					pList.addStudent(inputs[2], inputs[3], inputs[4]);
					System.out.println("Add student success.");
				} catch(PersonException ex) {
					System.out.println(ex.getCodeDescription());
				}
			}
			else {
				System.out.println("Input illegal.");
			}
		}
		
		
		//udc分支
		else if(inputs.length==4&&inputs[0].contentEquals("udc")) {
			try {
				this.cList.modCourse(inputs[1], inputs[2], inputs[3]);
				System.out.println("Update success.");
			} catch (CourseException e) {
				System.out.println(e.getCodeDescription());
			}
		}
		
		//nc分支
		else if(inputs.length==6&&inputs[0].contentEquals("nc")) {
			try {
				this.cList.addCourse(inputs[1], inputs[2], inputs[3], inputs[4],inputs[5]);
			} catch (CourseException ex) {
				System.out.println(ex.getCodeDescription());
			}
		}
		
		//clist分支
		else if(inputs.length==4&&inputs[0].contentEquals("clist")) {
			try {
				QTest.getQueryHelper().queryForClist(inputs[1], inputs[2], inputs[3]);
			} catch(CourseException ex) {
				System.out.println(ex.getCodeDescription());
			}
		}
		
		
		//错误分支
		else {
			System.out.println("Input illegal.");
		}
	}
}


/*
 * 教师登录状态
 */
class TeacherLoginState implements State {
	private PersonList pList;
	private CourseList cList;
	public TeacherLoginState(PersonList pList,CourseList cList) {
		this.pList = pList;
		this.cList = cList ;
	}
	public void handle(String[] inputs) {
		
		
		//QUIT分支
		if(inputs.length==1&&inputs[0].contentEquals("QUIT")) {
			System.exit(0);
		}
		
		//back分支
		else if(inputs.length==1&&inputs[0].contentEquals("back")) {
			QTest.setNowState(QTest.levelOneState);
		}
		
		//myinfo分支
		else if(inputs.length==1&&inputs[0].contentEquals("myinfo")) {
			System.out.println(QTest.getUser());
		}
		
		//chgpw分支
		else if(inputs.length==3&&inputs[0].contentEquals("chgpw")) {
			try {
				pList.chgPersonPwd(inputs[1], inputs[2], QTest.getUser());
				System.out.println("Password changed successfully.");
			} catch (PersonException e) {
				System.out.println(e.getCodeDescription());
			}
		}
		
		//clist分支
		else if(inputs.length==4&&inputs[0].contentEquals("clist")) {
			try {
				QTest.getQueryHelper().queryForClist(inputs[1], inputs[2], inputs[3]);
			} catch (CourseException ex) {
				System.out.println(ex.getCodeDescription());
			}
		}
		
		
		//myc分支
		else if(inputs.length==3&&inputs[0].contentEquals("myc")) {
			try {
				Teacher teacher = (Teacher)QTest.getUser();
				QTest.getQueryHelper().queryForCoursesOfTeacher(teacher.getTID(),inputs[1], inputs[2]);
			} catch (CourseException e) {
				System.out.println(e.getCodeDescription());
			}
		}
		
		
		//gc分支
		else if(inputs[0].contentEquals("gc")) {
			if(inputs.length==3&&inputs[1].contentEquals("-id")) {
				try {
					QTest.getQueryHelper().queryForCourseById(inputs[2]);
				} catch (CourseException ex) {
					System.out.println(ex.getCodeDescription());
				}
			} else if(inputs.length==5&&inputs[1].contentEquals("-key")) {
				try {
					QTest.getQueryHelper().queryForCoursesByKey(inputs[2], inputs[3], inputs[4]);
				} catch (CourseException ex) {
					System.out.println(ex.getCodeDescription());
				}
			} else if(inputs.length==4&&inputs[1].contentEquals("-all")) {
				try {
					QTest.getQueryHelper().queryForAllCourses(inputs[2], inputs[3]);
				} catch (CourseException ex) {
					System.out.println(ex.getCodeDescription());
				}
			} else {
				System.out.println("Input illegal.");
			}
		}
		
		//错误分支
		else {
			System.out.println("Input illegal.");
		}
	}
}


/*
 * 学生登录状态
 */
class StudentLoginState implements State{
	private PersonList pList;
	private CourseList cList;
	public StudentLoginState(PersonList pList,CourseList cList) {
		this.pList = pList;
		this.cList = cList;
	}
	public void handle(String[] inputs) {
		//QUIT分支
		if(inputs.length==1&&inputs[0].contentEquals("QUIT")) {
			System.exit(0);
		}
		
		//back分支
		else if(inputs.length==1&&inputs[0].contentEquals("back")) {
			QTest.setNowState(QTest.levelOneState);
		}
		
		//myinfo分支
		else if(inputs.length==1&&inputs[0].contentEquals("myinfo")) {
			System.out.println(QTest.getUser());
		}
		
		//chgpw分支
		else if(inputs.length==3&&inputs[0].contentEquals("chgpw")) {
			try {
				pList.chgPersonPwd(inputs[1], inputs[2], QTest.getUser());
				System.out.println("Password changed successfully.");
			} catch (PersonException e) {
				System.out.println(e.getCodeDescription());
			}
		}		
		
		//gc分支
		else if(inputs[0].contentEquals("gc")) {
			if(inputs.length==3&&inputs[1].contentEquals("-id")) {
				try {
					QTest.getQueryHelper().queryForCourseById(inputs[2]);
				} catch (CourseException ex) {
					System.out.println(ex.getCodeDescription());
				}
			} else if(inputs.length==5&&inputs[1].contentEquals("-key")) {
				try {
					QTest.getQueryHelper().queryForCoursesByKey(inputs[2], inputs[3], inputs[4]);
				} catch (CourseException ex) {
					System.out.println(ex.getCodeDescription());
				}
			} else if(inputs.length==4&&inputs[1].contentEquals("-all")) {
				try {
					QTest.getQueryHelper().queryForAllCourses(inputs[2], inputs[3]);
				} catch (CourseException ex) {
					System.out.println(ex.getCodeDescription());
				}
			} else {
				System.out.println("Input illegal.");
			}
		}
		
		//getc分支
		else if(inputs.length==2&&inputs[0].contentEquals("getc")) {
			
			Student student = (Student) QTest.getUser();
			try {
				student.chooseCourse(inputs[1]);
				System.out.println("Course chosen success.");
			} catch (CourseException e) {
				System.out.println(e.getCodeDescription());
			}
		}
		
		//dropc分支
		else if(inputs.length==2&&inputs[0].contentEquals("dropc")) {
			
			Student student = (Student) QTest.getUser();
			try {
				student.dropCourse(inputs[1]);
				System.out.println("Drop out successful.");
			} catch (CourseException e) {
				System.out.println(e.getCodeDescription());
			}
			
		}
		
		//myc分支
		else if(inputs.length==3&&inputs[0].contentEquals("myc")) {
			try {
				Student student = (Student) QTest.getUser();
				QTest.getQueryHelper().queryForCourseOfStudent(student.getSID(),inputs[1], inputs[2]);
			} catch (CourseException e) {
				System.out.println(e.getCodeDescription());
			} catch (PersonException ex) {
				System.out.println(ex.getCodeDescription());
			}
		}
		
		
		
		//DROPOUT分支
		else if(inputs.length==3&&inputs[0].contentEquals("DRUPOUT")) {
			try {
				pList.deleteStudent((Student)QTest.getUser(),inputs[1],inputs[2]);
				System.out.println("Congratulations, drop out successfully.");
			} catch (PersonException e) {
				System.out.println(e.getCodeDescription());
			}
		}
		
		
		//错误分支
		else {
			System.out.println("Input illegal.");
		}
	}
}


/*
 * 主要测试类
 */
public class QTest {
	
	
	public static PersonList pList = PersonList.getInstance();
	public static CourseList cList = CourseList.getInstance();
	public static State levelOneState = new LevelOneState(pList,cList);
	public static State sudoState = new SudoState(pList,cList);
	public static State TeacherLoginState = new TeacherLoginState(pList,cList);
	public static State StudentLoginState = new StudentLoginState(pList,cList);
	
	private static State nowState = levelOneState;
	private static QueryHelper queryHelper = new QueryHelper(pList, cList);
	private static Person user;
	
	
	
	public static State getNowState() {
		return nowState;
	}
	public static void setNowState(State nowState) {
		QTest.nowState = nowState;
	}
	
	public static void setUser(Person user) {
		QTest.user =  user;
	}
	public static Person getUser() {
		return QTest.user;
	}

	
	public static QueryHelper getQueryHelper() {
		return queryHelper;
	}
	public static void setQueryHelper(QueryHelper queryHelper) {
		QTest.queryHelper = queryHelper;
	}
	
	
	public static void handle(String[] inputs) {
		QTest.nowState.handle(inputs);
	}
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		String temp = "";
		String[] inputs;
		Logger.getGlobal().setLevel(Level.OFF);
		
		
		while(in.hasNext()) {
			temp = in.nextLine();
			temp = temp.replaceAll("\\s+"," ");
			inputs = temp.split(" ");
			if(inputs.length==0) {
				System.out.println("Input illegal");
				continue;
			}
			QTest.handle(inputs);
		}
		
	}
}
