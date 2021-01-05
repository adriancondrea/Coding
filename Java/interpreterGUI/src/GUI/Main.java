package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("programSelectionWindow.fxml"));
        Parent root = loader.load();
        ProgramSelectionController programSelectionController = loader.getController();
        primaryStage.setTitle("Program Selection Window");
        primaryStage.setScene(new Scene(root, screenBounds.getWidth() / 3, screenBounds.getHeight()));
        primaryStage.setX(0);
        primaryStage.show();

        FXMLLoader mainWindowLoader = new FXMLLoader();
        mainWindowLoader.setLocation(getClass().getResource("mainWindow.fxml"));
        Parent mainWindowRoot = mainWindowLoader.load();
        MainProgramController mainProgramController = mainWindowLoader.getController();

        Stage stage = new Stage();
        stage.setTitle("Main Window");
        stage.setX(screenBounds.getMinX() + screenBounds.getWidth()/3);
        stage.setScene(new Scene(mainWindowRoot, screenBounds.getWidth() * 2 / 3, screenBounds.getHeight()));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
