package zmuda.filip.javafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		Stage window = arg0;
		
		VBox layout = new VBox();
		layout.setAlignment(Pos.CENTER);
		Button button = new Button("Click me");
		Label label = new Label("something");
		button.setOnAction(e -> label.setText("changed"));
		
		layout.getChildren().addAll(label, button);
		Scene scene = new Scene(layout, 400, 400);
		
		window.setScene(scene);
		window.setTitle("My first window");
		window.show();
	}

}
