package pack;

import java.sql.ResultSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;


public class UIRenderer {

    private static double mouseOldX, mouseOldY;
    private static boolean isShiftDown = false;
    private static Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private static Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    public static BorderPane buildUI() {
        BorderPane root = new BorderPane();

        // 왼쪽 UI
        Left_UI leftPanel = new Left_UI();
        root.setLeft(leftPanel);

        // 3D 박스 그룹
        Group group3D = new Group();

        try {
            ResultSet rs = DBManager.getStudentData();
            int i = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                String dept = rs.getString("dept");

                Box box = new Box(100, 30, 30);
                box.setTranslateY(i * 100);
                box.setMaterial(new PhongMaterial(Color.LIGHTBLUE));

                Text label = new Text(name + " (" + dept + ")");
                label.setFont(Font.font(14));
                label.setFill(Color.DARKBLUE);
                label.getTransforms().add(new Translate(-45, i * 100 + 5, -20));

                group3D.getChildren().addAll(box, label);

                // 예시 의존선
                if (i > 0) {
                    Line line = new Line(0, (i - 1) * 100, 0, i * 100);
                    line.setStroke(Color.RED);
                    line.setTranslateX(50);
                    group3D.getChildren().add(line);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ group3D는 회전만 담당하고, 위치 이동은 worldGroup으로
        Group worldGroup = new Group();
        group3D.getTransforms().addAll(rotateX, rotateY);
        worldGroup.getChildren().add(group3D);

        // 카메라
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(2000);

        // SubScene
        SubScene subScene = new SubScene(worldGroup, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setFill(Color.WHITESMOKE);

        // 🖱 마우스 컨트롤
        enableMouseControl(worldGroup, subScene, camera);

        root.setCenter(subScene);
        
  

           
           return root;
    }

    private static void enableMouseControl(Group worldGroup, SubScene scene, PerspectiveCamera camera) {
        scene.setOnMousePressed(e -> {
            mouseOldX = e.getSceneX();
            mouseOldY = e.getSceneY();
            isShiftDown = e.isShiftDown();
        });

        scene.setOnMouseDragged(e -> {
            double dx = e.getSceneX() - mouseOldX;
            double dy = e.getSceneY() - mouseOldY;

            if (isShiftDown) {
                // 🖐 Shift 누른 경우 = "두 손"
                camera.setTranslateX(camera.getTranslateX() - dx);
                camera.setTranslateY(camera.getTranslateY() - dy);
            } else {
                // 👆 일반 드래그는 회전
                rotateX.setAngle(rotateX.getAngle() - dy * 0.3);
                rotateY.setAngle(rotateY.getAngle() + dx * 0.3);
            }

            mouseOldX = e.getSceneX();
            mouseOldY = e.getSceneY();
        });
    

    }
}
