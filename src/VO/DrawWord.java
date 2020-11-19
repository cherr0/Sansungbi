package VO;

import java.io.Serializable;

public class DrawWord implements Serializable {
	// 각 단어의 x, y, 내용, 표시유무를 가지는 객체

	private static final long serialVersionUID = 355351511455294186L;
	
	private int x;
	private int y;
	private String text;
	private boolean visible = true;
	
	// delta y
	private int dy;
	
	public DrawWord() {}
	
	public DrawWord(int x,int y, String text, int dy) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.dy = dy;
	}
	
	/* ------ getter, setter ------ */
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	// 단계에 따라 떨어지는 y 속도값 추가
	public void yMover() {
		y += dy;
	}
	
	
}
