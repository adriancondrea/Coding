package Controller;

import CustomException.CustomException;
import Model.AbstractDataTypes.MyDictionary;
import Model.AbstractDataTypes.MyList;
import Model.AbstractDataTypes.MyStack;
import Model.AbstractDataTypes.MyHeap;
import Model.AbstractDataTypes.StackInterface;
import Model.ProgramState;
import Model.Statement.Statement;
import Model.Value.ReferenceValue;
import Model.Value.Value;
import Repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Controller {
    Repository repository;
    //if displayState flag is set to true, display the program state after each execution
    Boolean displayState;
    public Controller(Repository repository){ this.repository = repository; displayState = false; }


    private Map<Integer, Value> garbageCollector(List<Integer> referredAddresses, Map<Integer, Value> heapTable) {
        return heapTable.entrySet().stream()
                .filter(e -> referredAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getReferredAddresses(Collection<Value> symbolTableValues, Collection<Value> heapTableValues) {
        return Stream.concat(
                symbolTableValues.stream()
                        .filter(v -> v instanceof ReferenceValue)
                        .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();}),
                heapTableValues.stream()
                        .filter(v -> v instanceof ReferenceValue)
                        .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();}))
                .collect(Collectors.toList());
    }

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
                programState.getHeapTable().setContent(garbageCollector(
                        getReferredAddresses(programState.getSymbolTable().getContent().values(), programState.getHeapTable().getContent().values()),
                        programState.getHeapTable().getContent()));
                repository.logProgramStateExecution(programState);
        }
    }

    public void createState(Statement program){
        //create a new state from the program given as parameter
        ProgramState state;
        StackInterface<Statement> stack = new MyStack<>();
        stack.push(program);
        state = new ProgramState(stack, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), null);
        repository.addState(state);
    }
}
