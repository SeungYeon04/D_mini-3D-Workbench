package pack;

import java.sql.*;
import java.util.ArrayList;

import DB.Item;
import DB.ItemDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
        overlay.getChildren().add(root);

        VBox leftContainer = new VBox(10);
        leftContainer.setPadding(new Insets(10));
        leftContainer.setPrefWidth(300);

        Left_UI leftPanel = new Left_UI();

        TableView<Item> tableView = new TableView<>();
        TableColumn<Item, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Item, String> colName = new TableColumn<>("상품명");
        colName.setCellValueFactory(new PropertyValueFactory<>("item_name"));

        TableColumn<Item, Integer> colStock = new TableColumn<>("재고량");
        colStock.setCellValueFactory(new PropertyValueFactory<>("item_stock"));

        TableColumn<Item, Integer> colPrice = new TableColumn<>("단가");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("item_price"));

        tableView.getColumns().addAll(colId, colName, colStock, colPrice);
        tableView.setPrefHeight(200);

        leftContainer.getChildren().addAll(leftPanel, new Label("\uD83D\uDCCA 상품 목록"), tableView);
        root.setLeft(leftContainer);

        Group group3D = new Group();
        try {
            ResultSet rs = DBManager.getItemData();
            int i = 0;
            while (rs.next()) {
                String name = rs.getString("item_name");
                String dept = rs.getString("item_stock");

                Box box = new Box(100, 30, 30);
                box.setTranslateY(i * 100);
                box.setMaterial(new PhongMaterial(Color.LIGHTBLUE));

                Text label = new Text(name + " (재고: " + dept + ")");
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
        camera.setTranslateY(150);
        camera.setNearClip(0.1);
        camera.setFarClip(2000);

        SubScene subScene = new SubScene(worldGroup, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setFill(Color.WHITESMOKE);

        enableMouseControl(worldGroup, subScene, camera);

        root.setCenter(subScene);

        HBox topRight = new HBox(10);
        topRight.setAlignment(Pos.TOP_RIGHT);
        topRight.setPadding(new Insets(10));
        topRight.setPickOnBounds(false);

        Button helpBtn = new Button("?");
        Button refreshBtn = new Button("\uD83D\uDD04");

        helpBtn.setStyle("-fx-font-size: 14px; -fx-background-radius: 20px;");
        refreshBtn.setStyle("-fx-font-size: 14px; -fx-background-radius: 20px;");
        Tooltip tooltip = new Tooltip("\uD83D\uDCA1 마우스로 회전\nSHIFT+드래그 이동\n휠로 줌인/아웃");
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setHideDelay(Duration.ZERO);
        Tooltip.install(helpBtn, tooltip);

        refreshBtn.setOnAction(e -> {
            System.out.println("\uD83D\uDD04 새로고침 로직 실행");
            try {
                ArrayList<Item> list = ItemDAO.getinstance().getAllItems();
                ObservableList<Item> data = FXCollections.observableArrayList(list);
                tableView.setItems(data);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        topRight.getChildren().addAll(helpBtn, refreshBtn);
        StackPane.setAlignment(topRight, Pos.TOP_RIGHT);
        overlay.getChildren().add(topRight);

        return overlay;
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