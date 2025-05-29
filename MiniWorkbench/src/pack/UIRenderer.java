package pack;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UIRenderer {

    public static BorderPane buildUI() {
        // 3D 루트
        Group group3D = new Group();

        try {
            ResultSet rs = DBManager.getStudentData();
            int i = 0;

            while (rs.next()) {
                String name = rs.getString("name");
                String dept = rs.getString("dept");

                double y = i * 100;

                // 📦 박스
                Box box = new Box(100, 30, 30);
                box.setTranslateX(0);
                box.setTranslateY(y);
                box.setTranslateZ(0);
                box.setMaterial(new PhongMaterial(Color.LIGHTBLUE));
                group3D.getChildren().add(box);

                // 🏷️ 텍스트
                Text label = new Text(name + " (" + dept + ")");
                label.setFont(Font.font(16));
                label.setFill(Color.DARKBLUE);
                label.setBoundsType(TextBoundsType.VISUAL);
                label.getTransforms().addAll(new Translate(-45, y + 5, -20));
                group3D.getChildren().add(label);

                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 카메라
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-600);
        camera.setTranslateY(-100);
        camera.setNearClip(0.1);
        camera.setFarClip(2000);
        camera.getTransforms().add(new Rotate(-20, Rotate.X_AXIS));

        // 3D SubScene
        SubScene subScene = new SubScene(group3D, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setFill(Color.WHITESMOKE);

        // 💡 고정 UI
        Button refreshBtn = new Button("🔄 새로고침");
        refreshBtn.setOnAction(e -> System.out.println("버튼 눌림"));

        // 전체 레이아웃
        BorderPane root = new BorderPane();
        root.setCenter(subScene);
        root.setTop(refreshBtn); // 버튼을 고정

        return root;
    }
}
