package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import VO.AcidRain;
import VO.Message;

public class RainDAO {
	// DAO : Data Access Object
	// VO : Value Object
	/*
	uno number,
	name varchar2(30),
	ip varchar2(30),
	score number,
	*/


	String insertUserSQL = "insert into users(uno, name, ip) values(users_uno_seq.NEXTVAL, ?, ?)";

	String updateUserSQL = "update users set name = ? where name = ?";

	String selectUserSQL = "select name from users";

	String selectRankSQL; // 추후 랭킹 리스트 가져올 때 사용
	
	String updateScoreSQL = "update users set score = score + ? where name = ?";

	String delete = "delete from users where name = ?";

	String selectTypeName = "SELECT typeidx,typename FROM wordtype";
	
	// 유저 등록
	public void insertUser(AcidRain acidrain) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.connDB();
			System.out.println(insertUserSQL);
			pstmt = conn.prepareStatement(insertUserSQL);
			pstmt.setString(1, acidrain.getName());
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

	// 유저 이름 등록 후 다음에는 이름 업데이트
	public void updateUser(AcidRain acidrain,String oldName) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.connDB();
			pstmt = conn.prepareStatement(updateUserSQL);
			pstmt.setString(1, acidrain.getName());
			pstmt.setString(2, oldName);

			int resultCnt = pstmt.executeUpdate();
			System.out.println("nameUpdate " + (resultCnt == 1 ? "업데이트 성공" : "업데이트 실패"));
		}catch(SQLException e) {
			System.out.println("updateUser err : " + e);
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
			pstmt = conn.prepareStatement(updateScoreSQL);
		} catch(SQLException e) {
			System.out.println("UpdateUserScore Err : " + e);
		} finally {
			DBUtil.close(pstmt, conn);
		}
	}
	

	

	// 기존 : 서버에서 유저가 나갈 시 유저 delete
	public void deleteUser(AcidRain acidrain) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.connDB();
			pstmt = conn.prepareStatement(delete);
			pstmt.setString(1, acidrain.getName());
			
			int cnt = pstmt.executeUpdate();
			
			System.out.println("delete " + (cnt == 1 ? "유저 삭제 성공" : "유저 삭제 실패"));
			
			
		} catch(Exception e) {
			System.out.println("delete user err : " + e);
		} finally {
			DBUtil.close(pstmt, conn);
		}
	}
	

	// 선택 가능한 wordType 다 가져옴
	public Message selectWordTypeName() {

		Connection conn = null;
		PreparedStatement pstmt = null;

		ArrayList<AcidRain> list = null;
		AcidRain rain = null;
		Message msg = null;

		try {
			conn = DBUtil.connDB();
			pstmt = conn.prepareStatement(selectTypeName);

			ResultSet rs = pstmt.executeQuery();

			System.out.println("resultSet: " + (rs == null ? "null" : "not null"));
			msg = new Message();
			list = new ArrayList<AcidRain>();

			while(rs.next()) {
				rain = new AcidRain();
				rain.setTypeidx(rs.getInt("typeidx"));
				rain.setTypename(rs.getString("typename"));
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
