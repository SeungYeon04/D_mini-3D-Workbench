package pack;

import java.sql.*;

public class DBManager {
    public static ResultSet getStudentData() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jjack?serverTimezone=UTC";
            String user = "root";
            String password = "1234";

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM student";
            return stmt.executeQuery(sql);  // ⚠️ conn을 닫지 않음 → ResultSet 사용 위해
        } catch (ClassNotFoundException e) {
            System.out.println("❌ 드라이버 로드 실패");
            e.printStackTrace();
            return null;
        }
    }
}
