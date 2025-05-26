package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Task06 {

	public static void main(String[] args) {
		
		/** 1. DB연결 재료 선언 */
		Connection conn; //DB연결 권한 객체(클래스) *SQL문의 쿼리권한을 가질 것  
		
		String url = "jdbc:mysql://localhost:3306/jjack?severTimezone=UTC"; //DB주소 (언어마다 DB종류마다 형식 다 지정됨. 찾아서 해) https://처럼 
		//서버 지정 안하면 현재 컴 localhost 포트 지정 안하면 기본이 3306 약속 
		//haksa? DB에 들어가고 severTimezone=UTC로 시간옵션 설정
		String id = "root"; //스탠다드 계정. 계정 만들었으면 그걸로 
		String pwd = "1234"; 

		Statement stmt = null; //아래 java.sql.Statement;로 잘 선택! 빈즈X 
		ResultSet rs = null; //결과물에 대한 처리용 
		
		String msg = "(select * from student는 기본출력입니다.)\n1. insert into student values(1, '테스트값', '10101', 11);\n2. update student set id='2' where name='테스트값'\n3. delete from student where id='2'";
		
		/** 처리 */
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, id, pwd); //어나니머스. 익명의 계정일 떄. 이 링크 이 아이디 이 비번으로 액세스해. 커넥션객체로 넘기기 key  
			
			System.out.println("DB 연결 성공");

			Scanner sc = new Scanner(System.in); 
			
			while (true) {
				System.out.println("*** DB 프로그램 시작 ***");
				System.out.println("ex) " + msg + "\ncmd 명령어를 입력해주세요 : ");
				String input = sc.nextLine().trim(); // 앞뒤 공백 제거 
				
				System.out.println("입력된 명령어 : " + input);
				
				String sql = "select * from student"; 
				String sql2 = input; 
				
				stmt = conn.createStatement(); 
				stmt.executeUpdate(sql2);
				rs = stmt.executeQuery(sql); 
			
				if(input.equals("end")) {
					System.out.println("시스템을 종료합니다.");
					rs.close();
					stmt.close();
					conn.close();
					break; 
				}
				
				//기본적으로 select는 출력 
				while(rs.next()) { //next 로 테이블 받은 rs를 행 계속 받아 
					String ids = rs.getString("id"); 
					String name = rs.getString("name"); // "" 부분 필드명 그대로 (대소문자도) 
					String dept = rs.getString("dept"); 
					String score = rs.getString("score"); 
					
					System.out.println("아이디 : " + ids + ", 성명 : " +  name + ", 뎁스 : " + dept + ", 스코어 : " + score);
				}

			}
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 오류 : jar 파일이 있는지 확인해보시오.");
		} catch (SQLException e) {
			System.out.println("DB 연결 && 명령어 오류"); //얘 DriverManager.getConnection가 쿼리 conn로 날릴 건데 안되면 디비 연결안됨. 
		}
	}
}

