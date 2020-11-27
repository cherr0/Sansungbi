package VO;

import java.io.Serializable;

public class AcidRain implements Serializable {

	private static final long serialVersionUID = 403131881182796609L;

	private String word;	// 단어
	private int typeidx;	// 단어 타입 idx
	private String typename;	// 단어 타입
	
	private String name;		// 사용자
	private String ip;			// user ip

	
	/* ------ getter, setter ------ */

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getTypeidx() {
		return typeidx;
	}

	public void setTypeidx(int typeidx) {
		this.typeidx = typeidx;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
