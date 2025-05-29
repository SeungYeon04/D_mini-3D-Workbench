package pack;

import java.sql.ResultSet;

import javafx.scene.Group; // ✅ JavaFX용 Group
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;


public class UIRenderer {
    public static BorderPane buildUI() {
        BorderPane root = new BorderPane();

        // 왼쪽 UI
        Left_UI leftPanel = new Left_UI();
        root.setLeft(leftPanel);

        // 중앙 3D 시각화
        Group group3D = new Group();

        // DB에서 박스 생성
        try {
            ResultSet rs = DBManager.getStudentData();
            int i = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                String dept = rs.getString("dept");

                Box box = new Box(100, 30, 30);
                box.setTranslateY(i * 80);
                box.setMaterial(new PhongMaterial(Color.LIGHTBLUE));
                group3D.getChildren().add(box);

                Text label = new Text(name + " (" + dept + ")");
                label.setFont(Font.font(14));
                label.setFill(Color.DARKBLUE);
                label.getTransforms().add(new Translate(-45, i * 80 + 5, -20));
                group3D.getChildren().add(label);

                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 카메라
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-600);
        camera.setTranslateY(-100);
        camera.getTransforms().add(new Rotate(-20, Rotate.X_AXIS));
        camera.setNearClip(0.1);
        camera.setFarClip(2000);

        SubScene subScene = new SubScene(group3D, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setFill(Color.WHITESMOKE);

        root.setCenter(subScene); // ✅ 이게 핵심

        return root;
    }
}
