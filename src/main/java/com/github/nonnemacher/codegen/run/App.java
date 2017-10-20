package com.github.nonnemacher.codegen.run;

import static javafx.application.Application.launch;

import com.github.nonnemacher.codegen.controller.CodeController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Carlos Henrique Nonnemacher
 * @version 1.0.0.0
 */
public class App extends Application {

	private static final String STYLE = "/styles/Styles.css";

	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(CodeController.VIEW));
		Parent root = (Parent) loader.load();

		((CodeController) loader.getController()).setStage(stage);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(STYLE);
		stage.setTitle("JavaFX and Maven");
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}

	/**
	 * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the application can not be launched through deployment artifacts, e.g., in IDEs
	 * with limited FX support. NetBeans ignores main().
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
