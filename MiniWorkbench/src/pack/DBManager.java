package pack; // 패키지 선언

import java.sql.*; // JDBC 관련 클래스 임포트
import java.util.Scanner; // 콘솔 입력을 위한 Scanner 임포트

public class DBManager {

    // 데이터베이스 연결을 위한 상수 선언
	public static final String URL = "jdbc:mysql://localhost:3306/pos?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF8";
    public static final String USER = "root"; // DB 사용자 이름
    public static final String PASSWORD = "1234"; // DB 비밀번호

    // 📦 item 테이블 정보 반환 메서드
    public static ResultSet getItemData() throws SQLException {
    	// DB 연결
    	Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
    	Statement stmt = conn.createStatement();

    	// ✅ 사용할 데이터베이스 선택
    	//stmt.execute("USE pos");

        // item 테이블의 모든 데이터를 조회해서 반환
        return stmt.executeQuery("SELECT * FROM item");
    }

    // 🧪 콘솔 기반 DB 명령어 실행 모드
    public static void runConsoleQueryMode() {
        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); // DB 연결
            Scanner scanner = new Scanner(System.in); // 사용자 입력 스캐너
        ) {
            Statement stmt = conn.createStatement(); // SQL 명령을 실행할 Statement 객체 생성

            // pos 데이터베이스 사용
            stmt.execute("USE pos");

            System.out.println("📦 DB 콘솔모드 (종료하려면 'exit')");
            while (true) {
                System.out.print("SQL > "); // 사용자에게 SQL 입력 요청
                String input = scanner.nextLine(); // 입력 받기

                // exit 입력 시 종료
                if (input.equalsIgnoreCase("exit")) break;

                try {
                    // 입력된 SQL이 SELECT문인지 판별
                    if (input.toLowerCase().startsWith("select")) {
                        // SELECT 결과 출력
                        ResultSet rs = stmt.executeQuery(input);
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        // 컬럼 이름 출력
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.print(metaData.getColumnName(i) + "\t");
                        }
                        System.out.println();

                        // 결과 행 출력
                        while (rs.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(rs.getString(i) + "\t");
                            }
                            System.out.println();
                        }
                    } else {
                        // SELECT 외의 쿼리 실행 (INSERT, UPDATE, DELETE 등)
                        int result = stmt.executeUpdate(input);
                        System.out.println("✅ 실행됨, 영향받은 행 수: " + result);
                    }
                } catch (SQLException e) {
                    // SQL 실행 중 예외 처리
                    System.out.println("❌ 오류: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            // DB 연결 시 예외 처리
            System.out.println("❌ DB 연결 오류: " + e.getMessage());
        }
    }
}
