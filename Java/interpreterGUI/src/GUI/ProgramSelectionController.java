package GUI;

import Controller.Controller;
import Model.AbstractDataTypes.MyDictionary;
import Model.AbstractDataTypes.MyHeap;
import Model.AbstractDataTypes.MyList;
import Model.AbstractDataTypes.MyStack;
import Repository.memoryRepository;
import Model.ProgramState;
import Model.Statement.Statement;
import Repository.Repository;
import commandLineView.HardcodedPrograms;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.util.ArrayList;

public class ProgramSelectionController {
   private MainProgramController mainProgramController;

   @FXML
    private ListView<Statement> programsListView;

    @FXML
    private Button programSelectionButton;

    public void setMainProgramController(MainProgramController mainProgramController) {
        this.mainProgramController = mainProgramController;
    }

    @FXML
    public void initialize(){
        ArrayList<Statement> programsList = HardcodedPrograms.getHardcodedPrograms();
        programsListView.setItems(FXCollections.observableArrayList(programsList));
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        programSelectionButton.setOnAction(actionEvent -> {
            try {
                int selectedIndex = programsListView.getSelectionModel().getSelectedIndex();
                if(selectedIndex < 0) {
                    return;
                }
                ProgramState programState = new ProgramState(programsList.get(selectedIndex));
                Repository repository = new memoryRepository(programState, "log" + Integer.toString(selectedIndex));
                Controller controller = new Controller(repository);
                controller.createState(programsList.get(selectedIndex)); //do i need to do this though?

                Rectangle2D screenBounds = Screen.getPrimary().getBounds();
                FXMLLoader mainWindowLoader = new FXMLLoader();
                mainWindowLoader.setLocation(getClass().getResource("mainWindow.fxml"));
                Parent mainWindowRoot = mainWindowLoader.load();
                MainProgramController mainProgramController = mainWindowLoader.getController();

                mainProgramController.setController(controller);
                Stage stage = new Stage();
                stage.setTitle("Main Window");
                stage.setX(screenBounds.getMinX() + screenBounds.getWidth()/3);
                stage.setScene(new Scene(mainWindowRoot, screenBounds.getWidth() * 2 / 3, screenBounds.getHeight()));
                stage.show();

            }
            catch(IOException exception){
                exception.printStackTrace();
            }
        });
    }
}
