package pack;

import java.sql.*;
import java.util.Scanner;

public class DBManager {

	public static final String URL = "jdbc:mysql://localhost:3306/pos?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF8";

    public static final String USER = "root";
    public static final String PASSWORD = "1234";

    // 📦 item 테이블 정보 반환
    public static ResultSet getItemData() throws SQLException {
    	Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
    	Statement stmt = conn.createStatement();
    	stmt.execute("USE pos");

        return stmt.executeQuery("SELECT * FROM item");
    }

    // 🧪 콘솔 기반 DB 명령어 실행
    public static void runConsoleQueryMode() {
        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Scanner sc = new Scanner(System.in)
        ) {
            Statement stmt = conn.createStatement();
            stmt.execute("USE pos");
            System.out.println("✅ 'pos' DB 연결 성공");

            String msg = """
                (SELECT * FROM item 은 기본출력입니다.)\n
                1. INSERT INTO item VALUES(1, '테스트상품', 50, 10000);\n
                2. UPDATE item SET item_stock=99 WHERE item_name='테스트상품';\n
                3. DELETE FROM item WHERE item_name='테스트상품';\n
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
                    stmt.executeUpdate(input); // DML 수행
                    System.out.println("✅ 쿼리 실행 완료");
                } catch (SQLException e) {
                    System.out.println("❌ 쿼리 실행 오류: " + e.getMessage());
                    continue;
                }

                // 실행 후 item 테이블 다시 출력
                ResultSet rs = stmt.executeQuery("SELECT * FROM item");
                System.out.println("=== 현재 상품 목록 ===");
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("item_name");
                    String stock = rs.getString("item_stock");
                    String price = rs.getString("item_price");
                    System.out.printf("▶ ID: %s | 이름: %s | 재고량: %s | 가격: %s\n", id, name, stock, price);
                }
            }

        } catch (SQLException e) {
            System.out.println("❗ DB 연결 또는 처리 중 오류 발생: " + e.getMessage());
        }
    }
}
