package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import VO.AcidRain;
import VO.Message;

public class RainDAO {
	// DAO : Data Access Object
	// VO : Value Object
	
	String insert;
	
	String select;
	
	String update;
	
	String updateName;
	
	String delete;
	
	String selectTypeName;
	
	
	// ���� ���
	public void insertUser(AcidRain acidrain) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.connDB();
			pstmt = conn.prepareStatement(insert);
			pstmt.setString(1, acidrain.getUsername());
			pstmt.setString(2, acidrain.getIp());
			
			// execute�� DDL�� �� ���
			// execute query  : select
			// execute update : insert, delete, update
			
			int cnt = pstmt.executeUpdate();
			System.out.println("insert : " + (cnt == 1 ? "����" : "����"));
			// insert ���� �� 1, �ƴϸ� 0
			
		}catch (SQLException e) {
			System.out.println("insertSQL error : " + e);
		}finally {
			DBUtil.close(pstmt, conn);
		}
	}
	
	// select words
	// ���� �ʿ��ϸ� �޼ҵ� ����
	public Message selectWords(AcidRain acidrain) {
		
		Message msg = null;
		
		
		return msg;
	}
	
	
	// ������ ���� �� ���� ���ھ� ���
	public void updateUserScore(AcidRain acidrain) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int score = 0;
		
		try {
			conn = DBUtil.connDB();
			pstmt = conn.prepareStatement(update);
		} catch(SQLException e) {
			System.out.println("UpdateUserScore Err : " + e);
		} finally {
			DBUtil.close(pstmt, conn);
		}
	}
	
	// ���� �̸� ��� �� �������� �̸� ������Ʈ
	public void updateUserName(AcidRain acidrain, String oldName) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.connDB();
			pstmt = conn.prepareStatement(update);
			pstmt.setString(1, acidrain.getUsername());
			pstmt.setString(2, oldName);
			
			int resultCnt = pstmt.executeUpdate();
			System.out.println("nameupdate " + (resultCnt == 1 ? "������Ʈ ����" : "������Ʈ ����"));
		}catch(SQLException e) {
			System.out.println("updateUserName err : " + e);
		}finally {
			DBUtil.close(pstmt, conn);
		}
	}
	
	
	// ���� ����
	// ���� : �������� ������ ���� �� ���� delete
	public void deleteUser(AcidRain acidrain) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.connDB();
			pstmt = conn.prepareStatement(delete);
			pstmt.setString(1, acidrain.getUsername());
			
			int cnt = pstmt.executeUpdate();
			
			System.out.println("delete " + (cnt == 1 ? "���� ���� ����" : "���� ���� ����"));
			
			
		} catch(Exception e) {
			System.out.println("delete user err : " + e);
		} finally {
			DBUtil.close(pstmt, conn);
		}
	}
	
	
	public Message selectWordTypeName(AcidRain acidrain) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ArrayList<AcidRain> list = null;
		AcidRain rain = null;
		Message msg = null;
		
		try {
			conn = DBUtil.connDB();
			pstmt = conn.prepareStatement(selectTypeName);
			pstmt.setString(1, "1");
			
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("resultset: " + (rs == null ? "null" : "not null"));
			
			msg = new Message();
			list = new ArrayList<AcidRain>();
			
			while(rs.next()) {
				rain = new AcidRain();
				rain.setTypename(rs.getString(1));
				list.add(rain);		// ����Ʈ�� �߰�
				System.out.println("�ε��� �̸� : " + rain.getTypeidx());
				System.out.println("Ÿ�� �̸� : " + rain.getTypename());
			}
			
			msg.setList(list);
			
		} catch(SQLException e) {
			System.out.println("selectWordTypeName err : " + e);
		}finally {
			DBUtil.close(pstmt, conn);
		}
		
		return msg;
	}
	
}
