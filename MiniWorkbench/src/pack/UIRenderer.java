package pack;

import java.sql.ResultSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class UIRenderer {

    private static double mouseOldX, mouseOldY;
    private static boolean isShiftDown = false;
    private static Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private static Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    public static StackPane buildUI() {
        BorderPane root = new BorderPane();
        StackPane overlay = new StackPane();
        overlay.getChildren().add(root); // BorderPane를 아래에 깔기

        // 왼쪽 UI
        Left_UI leftPanel = new Left_UI();
        root.setLeft(leftPanel);

        // 3D 시각화
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

        Group worldGroup = new Group();
        group3D.getTransforms().addAll(rotateX, rotateY);
        worldGroup.getChildren().add(group3D);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(2000);

        SubScene subScene = new SubScene(worldGroup, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setFill(Color.WHITESMOKE);

        enableMouseControl(worldGroup, subScene, camera);

        root.setCenter(subScene);

        // ✅ 오른쪽 상단 버튼 오버레이
        HBox topRight = new HBox(10);
        topRight.setAlignment(Pos.TOP_RIGHT);
        topRight.setPadding(new Insets(10));
        topRight.setPickOnBounds(false);

        Button helpBtn = new Button("?");
        Button refreshBtn = new Button("🔄");

        helpBtn.setStyle("-fx-font-size: 14px; -fx-background-radius: 20px;");
        refreshBtn.setStyle("-fx-font-size: 14px; -fx-background-radius: 20px;");
        Tooltip tooltip = new Tooltip("💡 마우스로 회전\nSHIFT+드래그 이동\n휠로 줌인/아웃");
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setHideDelay(Duration.ZERO);
        Tooltip.install(helpBtn, tooltip);

        refreshBtn.setOnAction(e -> {
            System.out.println("🔄 새로고침 로직 실행");
        });

        topRight.getChildren().addAll(helpBtn, refreshBtn);
        StackPane.setAlignment(topRight, Pos.TOP_RIGHT);
        overlay.getChildren().add(topRight);

        return overlay; // 이제 StackPane 반환
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
                camera.setTranslateX(camera.getTranslateX() - dx);
                camera.setTranslateY(camera.getTranslateY() - dy);
            } else {
                rotateX.setAngle(rotateX.getAngle() - dy * 0.3);
                rotateY.setAngle(rotateY.getAngle() + dx * 0.3);
            }

            mouseOldX = e.getSceneX();
            mouseOldY = e.getSceneY();
        });
    }
}  