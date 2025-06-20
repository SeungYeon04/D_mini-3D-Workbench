package pack;

import javafx.application.Application; // JavaFX 애플리케이션 클래스
import javafx.scene.Scene; // 장면 정의
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane; // 기본 루트 레이아웃
import javafx.stage.Stage; // 윈도우(Stage)
import javafx.scene.text.Font; // 폰트 관련

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        // 🔧 UI 렌더링: UIRenderer 클래스의 정적 메서드를 통해 StackPane UI 구성
        StackPane root = UIRenderer.buildUI();

        // 🔤 사용자 정의 폰트 로딩 (size는 12로 예시이며 실제 UI에서는 무시됨)
        Font.loadFont(getClass().getResource("./font/Bold.ttf").toExternalForm(), 12);

        // 🖼️ 씬(Scene) 생성: 넓이 1100, 높이 700
        Scene scene = new Scene(UIRenderer.buildUI(), 1100, 700);

        // 🎨 외부 CSS 적용
        scene.getStylesheets().add(getClass().getResource("./style.css").toExternalForm());

        /*
        // 💡 사용 가능한 시스템 폰트 목록 출력 (디버깅용)
        for (String fontName : Font.getFamilies()) {
            System.out.println("💡 폰트: " + fontName);
        }
        */

        // 🪟 Stage 설정
        stage.setScene(scene); // 씬 부착
        stage.setTitle("📊 3D 학생 시각화 with DB"); // 타이틀 설정
        stage.show(); // 윈도우 표시
    }

    // 앱 실행 진입점
    public static void main(String[] args) {
        launch(args); // JavaFX 앱 실행
    }
}
