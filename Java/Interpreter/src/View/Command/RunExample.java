package View.Command;

import Controller.Controller;
import CustomException.CustomException;

public class RunExample extends  Command{
    private Controller controller;
    public RunExample(String key, String description, Controller controller){
        super(key, description);
        this.controller = controller;
    }
    @Override
    public void execute() {
        try {
            controller.allStepExecution();
        }
        catch (Exception exception){
            System.out.println("ERROR! " + exception.getMessage());
        }
    }
}
