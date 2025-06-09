package pack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;



public class MainApp extends Application {

	@Override
	public void start(Stage stage) {
	    StackPane root = UIRenderer.buildUI();
	    
	    Font.loadFont(getClass().getResource("./font/Bold.ttf").toExternalForm(), 12);

	    
	    Scene scene = new Scene(UIRenderer.buildUI(), 1100, 700);
	    scene.getStylesheets().add(getClass().getResource("./style.css").toExternalForm());
	    
	    /*for (String fontName : Font.getFamilies()) {
	        System.out.println("💡 폰트: " + fontName);
	    }*/


	    stage.setScene(scene);
	    stage.setTitle("📊 3D 학생 시각화 with DB");
	    stage.show();
	}


    public static void main(String[] args) {
    	
        launch(args);
       
    }
}
