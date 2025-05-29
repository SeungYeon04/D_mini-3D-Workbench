package pack;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.sql.*;

public class Left_UI extends VBox {

    private TextArea inputArea;
    private Button runButton;
    private TextArea logArea;

    public Left_UI() {
        setPadding(new Insets(10));
        setSpacing(10);
        setPrefWidth(250); // 고정 너비

        inputArea = new TextArea();
        inputArea.setPromptText("SQL 명령어 입력 (예: SELECT * FROM student;)");
        inputArea.setPrefRowCount(4);

        runButton = new Button("실행");
        runButton.setMaxWidth(Double.MAX_VALUE);

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPromptText("실행 결과 또는 오류 로그");
        logArea.setWrapText(true);
        logArea.setPrefHeight(400);

        runButton.setOnAction(e -> executeSQL());

        getChildren().addAll(new Label("💬 SQL 명령 입력"), inputArea, runButton, new Label("🧾 로그 출력"), logArea);
    }

    private void executeSQL() {
        String sql = inputArea.getText().trim();
        if (sql.isEmpty()) {
            logArea.appendText("⚠️ SQL 명령어가 비어 있습니다.\n");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DBManager.URL, DBManager.USER, DBManager.PASSWORD);
             Statement stmt = conn.createStatement()) {

            if (sql.toLowerCase().startsWith("select")) {
                ResultSet rs = stmt.executeQuery(sql);
                logArea.appendText("✅ SELECT 결과:\n");
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String dept = rs.getString("dept");
                    String score = rs.getString("score");
                    logArea.appendText("▶ " + id + " | " + name + " | " + dept + " | " + score + "\n");
                }
            } else {
                int updated = stmt.executeUpdate(sql);
                logArea.appendText("✅ 변경된 행 수: " + updated + "\n");
            }

        } catch (SQLException ex) {
            logArea.appendText("❌ 오류: " + ex.getMessage() + "\n");
        }
    }
}
