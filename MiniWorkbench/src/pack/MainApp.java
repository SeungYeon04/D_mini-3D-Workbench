package pack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        // UI 모듈에서 전체 레이아웃 받아오기
        BorderPane root = UIRenderer.buildUI();

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("📊 3D 학생 시각화 with DB");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
