package pack;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class Left_UI extends VBox {
    public Left_UI() {
        setPrefWidth(150); // 최소한의 너비 설정
        setStyle("-fx-background-color: #f0f0f0;"); // 배경색 설정
        Button btn = new Button("새로고침");
        getChildren().add(btn);
    }
}
