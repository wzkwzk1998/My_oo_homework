package Elevator2;

public class FloorItem {
	private int upButton;
	private int downButton;                //为楼层添加按钮，1为按下，0为没按
	
	
	public FloorItem() {
		this.upButton = 0;
		this.downButton = 0;
	}
	
	public void setUpButton(int state) {
		this.upButton = state;
	}
	public int getUpButton(){
		return this.upButton;
	}
	
	
	public void setDownButton(int state) {
		this.downButton = state;
	}
	public int getDownButton() {
		return this.downButton;
	}
	
	public boolean noPress() {            //没有楼层摁下电梯
		if(upButton==0&&downButton==0) {
			return true;
		}
		else return false;
	}
	
}
