import gui.ChooseCardDialog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LuhnCheck extends Application {
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new ChooseCardDialog(primaryStage), 420, 160);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Card check");
        primaryStage.show();
    }
}
