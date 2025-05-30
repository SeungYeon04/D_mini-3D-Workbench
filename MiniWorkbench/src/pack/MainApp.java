package pack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	@Override
	public void start(Stage stage) {
	    StackPane root = UIRenderer.buildUI();
	    Scene scene = new Scene(root, 1100, 700);
	    stage.setScene(scene);
	    stage.setTitle("📊 3D 학생 시각화 with DB");
	    stage.show();
	}


    public static void main(String[] args) {
    	
        launch(args);
       
    }
}
