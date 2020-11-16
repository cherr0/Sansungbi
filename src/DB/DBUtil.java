package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtil {
	
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String user = "scott";
	private static final String pwd = "tiger";
	
	
	
	public static Connection connDB() {
		try {
			Class.forName(driver);
			System.out.println("Oracle 드라이버 로딩 성공");
			Connection conn = DriverManager.getConnection(url, user, pwd);
			System.out.println("Connection 생성 성공");
			return conn;
		}catch(Exception e) {
			System.out.println("접속에 실패했습니다. ");
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void close(PreparedStatement pstmt, Connection conn) {
		try {
			if(pstmt != null)
				pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			pstmt = null;
		}
		
		try {
			if(conn != null) {
				conn.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			conn = null;
		}
	}
	
	public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if(rs != null)
				rs.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			rs = null;
		}
		
		try {
			if(pstmt != null)
				pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			pstmt = null;
		}
		
		try {
			if(conn != null) {
				conn.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			conn = null;
		}
	}
}
