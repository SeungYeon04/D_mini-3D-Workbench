package pack;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ✅ DB 학생 데이터를 3D 박스로 시각화하고,
 * 박스 정면에 이름 + 학과 텍스트를 부착
 */
public class MainApp_BackUp extends Application {

    @Override
    public void start(Stage stage) {
        Group group3D = new Group();

        try {
            ResultSet rs = DBManager.getStudentData(); // 반드시 정상 반환되어야 함
            if (rs == null) return;

            int i = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                String dept = rs.getString("dept");

                double y = i * 100;

                // 📦 3D 박스
                Box box = new Box(100, 30, 30);
                box.setTranslateX(0);
                box.setTranslateY(y);
                box.setTranslateZ(0);
                box.setMaterial(new PhongMaterial(Color.LIGHTBLUE));
                group3D.getChildren().add(box);

                // 🏷️ 텍스트를 3D 앞면에 부착
                Text label = new Text(name + " (" + dept + ")");
                label.setFont(Font.font(16));
                label.setFill(Color.DARKBLUE);
                label.setBoundsType(TextBoundsType.VISUAL);

                // 📌 박스 앞면에 위치시키기
                label.getTransforms().addAll(
                    new Translate(-45, y + 5, -20) // X, Y, Z 좌표
                );

                group3D.getChildren().add(label);
                i++;
            }

        } catch (SQLException e) {
            System.out.println("❌ DB 오류!");
            e.printStackTrace();
        }

        // 🎥 카메라 설정
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-600);
        camera.setTranslateY(-100); // 약간 위에서 내려다봄
        camera.setNearClip(0.1);
        camera.setFarClip(2000);
        camera.getTransforms().add(new Rotate(-20, Rotate.X_AXIS));

        // 🪟 3D SubScene
        SubScene subScene = new SubScene(group3D, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setFill(Color.WHITESMOKE);

        // 🧱 전체 Scene
        Group root = new Group(subScene);
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("📊 MySQL 학생 시각화");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
