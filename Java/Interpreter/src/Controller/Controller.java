package Controller;

import CustomException.CustomException;
import Model.AbstractDataTypes.MyDictionary;
import Model.AbstractDataTypes.MyList;
import Model.AbstractDataTypes.MyStack;
import Model.AbstractDataTypes.StackInterface;
import Model.ProgramState;
import Model.Statement.Statement;
import Repository.Repository;


public class Controller {
    Repository repository;
    //if displayState flag is set to true, display the program state after each execution
    Boolean displayState;
    public Controller(Repository repository){ this.repository = repository; displayState = false; }

    public void setDisplayState(Boolean displayState) { this.displayState = displayState; }

    public ProgramState oneStepExecution(ProgramState state) throws CustomException {
        StackInterface<Statement> executionStack = state.getExecutionStack();
        if(executionStack.isEmpty())
            throw new CustomException("Stack is empty!");
        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(state);
    }

    public void allStepExecution(){
        ProgramState programState = repository.getCurrentProgram();
        repository.logProgramStateExecution(programState);
        if(displayState){
            System.out.println(programState.toString());
        }
        while (!programState.getExecutionStack().isEmpty()){
                oneStepExecution(programState);
                if(displayState)
                    System.out.println(programState.toString());
                repository.logProgramStateExecution(programState);
        }
    }

    public void createState(Statement program){
        //create a new state from the program given as parameter
        ProgramState state;
        StackInterface<Statement> stack = new MyStack<>();
        stack.push(program);
        state = new ProgramState(stack, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),null);
        repository.addState(state);
    }
}
