package GUI;

import Controller.Controller;
import CustomException.CustomException;
import Model.ProgramState;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainProgramController {
    private Controller controller;
    private List<ProgramState> programStateList;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private TableView<Map.Entry<Integer, String>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, Integer> variableAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> valueTableColumn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<String> programStatesListView;

    @FXML
    private TableView<Map.Entry<String, String>> symbolTableView;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> variableNameColumn;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> variableValueColumn;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private Button executeOneStepButton;

    @FXML
    public void initialize(){
     //initialize heapTableView
     variableAddressColumn.setCellValueFactory(feature -> new SimpleIntegerProperty(feature.getValue().getKey()).asObject());
     valueTableColumn.setCellValueFactory(feature -> new SimpleStringProperty(feature.getValue().getValue()));

     //initialize symbolTableView
     variableNameColumn.setCellValueFactory(feature ->
             new SimpleStringProperty(feature.getValue().getKey()));
     variableValueColumn.setCellValueFactory(features -> new SimpleStringProperty(features.getValue().getValue()));

     programStatesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
     programStatesListView.getSelectionModel().select(1);

     executeOneStepButton.setOnAction(actionEvent -> oneStepExecution());
     programStatesListView.setOnMouseClicked(mouseEvent -> populateListsTables());
    }

    public void populateListsTables() {
        populateExecutionStack();
        populateOutput();
        populateFileTable();
        populateProgramStatesList();
        populateSymbolTable();
        populateHeapTable();
    }

    private void populateHeapTable() {
        ProgramState runningProgram = getRunningProgramState();
        List<Map.Entry<Integer, String>> heapString = new ArrayList<>();
        runningProgram.getHeapTable()
                .getContent()
                .forEach((address, value) -> {
                    heapString.add(Map.entry(address, value.toString()));
                });
        heapTableView.setItems(FXCollections.observableArrayList(heapString));
        heapTableView.refresh();
    }

    private void populateSymbolTable() {
        ProgramState runningProgram = getRunningProgramState();
        List<Map.Entry<String, String>> symbolTableString = new ArrayList<>();
        runningProgram.getSymbolTable()
                .getContent()
                .forEach((variableName, variableValue) -> {
                    symbolTableString.add(Map.entry(variableName, variableValue.toString()));
                });
        symbolTableView.setItems(FXCollections.observableArrayList(symbolTableString));
        symbolTableView.refresh();
    }

    @FXML
    private void populateProgramStatesList() {
        List<String> programStatesToBePopulated = controller
                .getProgramStates()
                .stream()
                .map(programState -> programState.id)
                .map(Objects::toString)
                .collect(Collectors.toList());
        programStatesListView.setItems(FXCollections.observableArrayList(programStatesToBePopulated));
        programStatesListView.refresh();
    }

    private void populateFileTable() {
        ProgramState runningProgram = getRunningProgramState();
        List<String> fileTableToBePopulated = runningProgram
                .getFileTable()
                .getContent()
                .keySet()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.toList());
        fileTableListView.setItems(FXCollections.observableArrayList(fileTableToBePopulated));
        fileTableListView.refresh();
    }

    private void populateOutput() {
        ProgramState runningProgram = getRunningProgramState();
        List<String> outputToBePopulated = runningProgram
                .getOutputList()
                .getElements()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.toList());
        outputListView.setItems(FXCollections.observableArrayList(outputToBePopulated));
        outputListView.refresh();
    }

    private void populateExecutionStack() {
        ProgramState runningProgram = getRunningProgramState();
        List<String> stackToBePopulated = runningProgram
                .getExecutionStack()
                .getContent()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.toList());
        executionStackListView.setItems(FXCollections.observableArrayList(stackToBePopulated));
        executionStackListView.refresh();
    }

    private ProgramState getRunningProgramState() {
        if(controller.getProgramStates().isEmpty()) {
            return null;
        }
        int selectedIndex = programStatesListView.getSelectionModel().getSelectedIndex();
        if(selectedIndex < 0) {
            return controller.getProgramStates().get(0);
        }
        return controller.getProgramStates().get(selectedIndex);
    }

    public void oneStepExecution() {
        try{
            if(controller.getProgramStates().get(0).getExecutionStack().isEmpty()) {
                programStatesListView.setItems(null);
                numberOfProgramStatesTextField.clear();
                numberOfProgramStatesTextField.setText(Integer.toString(0));
                raiseAlert("Nothing to execute!");
                closeWindow();
                return;
            }
            controller.oneStepExecution();
            populateListsTables();
            numberOfProgramStatesTextField.clear();
            numberOfProgramStatesTextField.setText(Integer.toString(controller.getProgramStates().size()));
        }catch(CustomException exception){
            raiseAlert(exception.getMessage());
            closeWindow();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) executeOneStepButton.getScene().getWindow();
        stage.close();
    }

    private void raiseAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
        alert.showAndWait();
    }

    public void setController(Controller controller) {
        this.controller = controller;
        this.programStateList = controller.getProgramStates();
    }
}
