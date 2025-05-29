package pack;

import java.sql.*;
import java.util.Scanner;

public class DBManager {

	 	public static final String URL = "jdbc:mysql://localhost:3306/jjack?serverTimezone=UTC";
	    public static final String USER = "root";
	    public static final String PASSWORD = "1234";

    // 📦 학생 정보 반환 (MainApp02 등에서 사용)
    public static ResultSet getStudentData() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM student");
    }

    // 🧪 콘솔에서 명령어 입력받아 실행 (기존 Task06 기능 통합)
    public static void runConsoleQueryMode() {
        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Scanner sc = new Scanner(System.in)
        ) {
            Statement stmt = conn.createStatement();
            System.out.println("✅ DB 연결 성공");

            String msg = """
                (SELECT * FROM student 는 기본출력입니다.)
                1. INSERT INTO student VALUES(1, '테스트값', '10101', 11);
                2. UPDATE student SET id='2' WHERE name='테스트값';
                3. DELETE FROM student WHERE id='2';
                """;

            while (true) {
                System.out.println("*** DB 프로그램 시작 ***");
                System.out.println("ex) " + msg + "\ncmd 명령어를 입력해주세요 (종료: end): ");
                String input = sc.nextLine().trim();

                if (input.equalsIgnoreCase("end")) {
                    System.out.println("시스템을 종료합니다.");
                    break;
                }

                try {
                    stmt.executeUpdate(input); // DML
                } catch (SQLException e) {
                    System.out.println("❌ 쿼리 실행 오류: " + e.getMessage());
                }

                ResultSet rs = stmt.executeQuery("SELECT * FROM student");
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String dept = rs.getString("dept");
                    String score = rs.getString("score");
                    System.out.printf("아이디: %s, 이름: %s, 학과: %s, 점수: %s%n", id, name, dept, score);
                }
            }

        } catch (SQLException e) {
            System.out.println("❗ DB 연결 또는 처리 중 오류 발생: " + e.getMessage());
        }
    }
}
