package DB;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnect {

	// 교슈님 해커톤 백엔드 누구 ㄱㅊ나요 / 안되면 이부분 잘 보기 
	public static final String dbDriver = "com.mysql.cj.jdbc.Driver"; 
	public static final String dbUrl = "jdbc:mysql://localhost:3306/pos?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF8"; //serverTimezone=UTC : 현재타임
	public static final String dbUser = "root";
	public static final String dbPwd = "1234"; 
	public static Connection conn = null; 
	
	//테스트를 위한 임시 메인 
	public static void main(String[] args) {
		//DB MySQL쓰다가 오라클 바꿔봤음 좋겠다. 
		connect(); 
		close(); 
	}
	
	public static Connection connect() { //Connection으로 리턴해줘야 다른 메소드에서 권한 받아 쿼리날릴 수 있음(셀렉트 등) 
		try {
			Class.forName(dbDriver);
			//conn != null 이어야 권한값이 저장된 거 
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd); //연결 
			
			if(conn != null) {
				System.out.println("DB 연결 성공");
			}
			
		} catch (Exception e) {
			System.out.println("DB 연결 오류");
		}	
		return conn;
	}
	
	public static void close() {
		try {
			if(conn != null) {
				System.out.println("DB연결 해제중...");
				conn.close();
				System.out.println("DB연결 완료");
			}
		}  catch (Exception e) {
			System.out.println("DB연결 해제 시 오류");
		}
	}
}