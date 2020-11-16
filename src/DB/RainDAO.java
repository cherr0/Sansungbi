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
	
	
	// 유저 등록
	public void insertUser(AcidRain acidrain) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.connDB();
			pstmt = conn.prepareStatement(insert);
			pstmt.setString(1, acidrain.getUsername());
			pstmt.setString(2, acidrain.getIp());
			
			// execute는 DDL할 때 사용
			// execute query  : select
			// execute update : insert, delete, update
			
			int cnt = pstmt.executeUpdate();
			System.out.println("insert : " + (cnt == 1 ? "성공" : "실패"));
			// insert 성공 시 1, 아니면 0
			
		}catch (SQLException e) {
			System.out.println("insertSQL error : " + e);
		}finally {
			DBUtil.close(pstmt, conn);
		}
	}
	
	// select words
	// 추후 필요하면 메소드 생성
	public Message selectWords(AcidRain acidrain) {
		
		Message msg = null;
		
		
		return msg;
	}
	
	
	// 게임이 끝난 후 유저 스코어 등록
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
	
	// 유저 이름 등록 후 다음에는 이름 업데이트
	public void updateUserName(AcidRain acidrain, String oldName) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.connDB();
			pstmt = conn.prepareStatement(update);
			pstmt.setString(1, acidrain.getUsername());
			pstmt.setString(2, oldName);
			
			int resultCnt = pstmt.executeUpdate();
			System.out.println("nameupdate " + (resultCnt == 1 ? "업데이트 성공" : "업데이트 실패"));
		}catch(SQLException e) {
			System.out.println("updateUserName err : " + e);
		}finally {
			DBUtil.close(pstmt, conn);
		}
	}
	
	
	// 추후 수정
	// 기존 : 서버에서 유저가 나갈 시 유저 delete
	public void deleteUser(AcidRain acidrain) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.connDB();
			pstmt = conn.prepareStatement(delete);
			pstmt.setString(1, acidrain.getUsername());
			
			int cnt = pstmt.executeUpdate();
			
			System.out.println("delete " + (cnt == 1 ? "유저 삭제 성공" : "유저 삭제 실패"));
			
			
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
				list.add(rain);		// 리스트에 추가
				System.out.println("인덱스 이름 : " + rain.getTypeidx());
				System.out.println("타입 이름 : " + rain.getTypename());
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
