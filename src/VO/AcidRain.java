package VO;

import java.io.Serializable;

public class AcidRain implements Serializable {

	private static final long serialVersionUID = 403131881182796609L;
	
	private int wordidx;	// �ܾ��ε���
	private String word;	// �ܾ� ����
	private int typeidx;	// �ܾ� Ÿ�� idx
	private boolean wordflag;	// �ܾ� ���� flag
	private String typename;	// �ܾ� Ÿ��
	
	private String username;	// �����
	private String ip;			// ����� ip

	
	/* ------ getter, setter ------ */
	
	public int getWordidx() {
		return wordidx;
	}

	public void setWordidx(int wordidx) {
		this.wordidx = wordidx;
	}

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

	public boolean isWordflag() {
		return wordflag;
	}

	public void setWordflag(boolean wordflag) {
		this.wordflag = wordflag;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
