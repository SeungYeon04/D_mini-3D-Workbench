package pack;

import javafx.geometry.Insets; // 여백 설정을 위한 클래스
import javafx.scene.control.*; // Button, TextArea, Label 등 UI 컴포넌트
import javafx.scene.layout.*; // VBox 등 레이아웃 관련 클래스
import java.sql.*; // JDBC (DB 연결 및 쿼리 실행) 관련 클래스

// VBox를 상속받아 UI를 구성하는 Left_UI 클래스
public class Left_UI extends VBox {

    // SQL 입력 영역
    private TextArea inputArea;
    // 실행 버튼
    private Button runButton;
    // 결과 로그 출력 영역
    private TextArea logArea;

    // 생성자: UI 초기 구성
    public Left_UI() {
        this.getStyleClass().add("left-panel"); // CSS 스타일 클래스 적용

        setPadding(new Insets(10)); // 바깥 여백 10px
        setSpacing(10); // 컴포넌트 간 간격 10px
        setPrefWidth(250); // 왼쪽 패널의 너비 설정

        // SQL 입력창 설정
        inputArea = new TextArea();
        inputArea.setPromptText("SQL 명령어 입력 (예: SELECT * FROM student;)");
        inputArea.setPrefRowCount(3); // 표시 줄 수를 3줄로 제한

        // 실행 버튼 설정
        runButton = new Button("실행");
        runButton.setMaxWidth(Double.MAX_VALUE); // 너비를 VBox에 꽉 차게 설정

        // 로그 출력창 설정
        logArea = new TextArea();
        logArea.setEditable(false); // 사용자 입력 불가능
        logArea.setPromptText("실행 결과 또는 오류 로그"); // 안내 문구
        logArea.setWrapText(true); // 줄바꿈 허용
        logArea.setPrefHeight(160); // 로그창 높이 제한

        // 실행 버튼 클릭 시 SQL 실행 메서드 호출
        runButton.setOnAction(e -> executeSQL());

        // VBox에 컴포넌트 순서대로 추가
        getChildren().addAll(
            new Label("💬 SQL 명령 입력"),
            inputArea,
            runButton,
            new Label("🧾 로그 출력"),
            logArea
        );
    }

    // SQL 명령어 실행 메서드
    private void executeSQL() {
        String sql = inputArea.getText().trim(); // 입력값에서 공백 제거
        if (sql.isEmpty()) {
            logArea.appendText("⚠️ SQL 명령어가 비어 있습니다.\n"); // 비어있으면 경고 로그 출력
            return;
        }

        try (
            // DB 연결
            Connection conn = DriverManager.getConnection(DBManager.URL, DBManager.USER, DBManager.PASSWORD);
            Statement stmt = conn.createStatement()
        ) {
            // SELECT 쿼리 처리
            if (sql.toLowerCase().startsWith("select")) {
                ResultSet rs = stmt.executeQuery(sql); // 결과 받아오기
                logArea.appendText("✅ SELECT 결과:\n");

                // 결과에서 컬럼값 직접 출력
                while (rs.next()) {
                    String id = rs.getString("id");
                    String item_name = rs.getString("item_name");
                    String item_stock = rs.getString("item_stock");
                    String item_price = rs.getString("item_price");

                    logArea.appendText("▶ " + id + " | " + item_name + " | " + item_stock + " | " + item_price + "\n");
                }
            } else {
                // INSERT, UPDATE, DELETE 쿼리 처리
                int updated = stmt.executeUpdate(sql); // 영향을 받은 행 수 반환
                logArea.appendText("✅ 변경된 행 수: " + updated + "\n");
            }

        } catch (SQLException ex) {
            // 예외 발생 시 오류 메시지 출력
            logArea.appendText("❌ 오류: " + ex.getMessage() + "\n");
        }
    }
}
