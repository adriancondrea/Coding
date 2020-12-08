package Controller;

import CustomException.CustomException;
import Model.AbstractDataTypes.*;
import Model.ProgramState;
import Model.Statement.Statement;
import Model.Value.ReferenceValue;
import Model.Value.Value;
import Repository.Repository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Controller {
    Repository repository;
    //if displayState flag is set to true, display the program state after each execution
    Boolean displayState;
    ExecutorService executor;
    public Controller(Repository repository){ this.repository = repository; displayState = false; }


    void oneStepForAllPrograms(List<ProgramState> programStates){
        //before the execution, print the programState list into the log file
        programStates.forEach(program -> repository.logProgramStateExecution(program));

        //run concurrently one step for each of the existing programStates

        //prepare the list of callables
        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStepExecution)) .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        List<ProgramState> newProgramStates = null;
        try {
            newProgramStates = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (CustomException | InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                            System.exit(1);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        catch (InterruptedException e){
            throw new CustomException(e.getMessage());
        }
        programStates.addAll(newProgramStates);
        programStates.forEach(program -> repository.logProgramStateExecution(program));
        repository.setProgramStates(programStates);
        }
        
    private Map<Integer, Value> garbageCollector(List<Integer> referredAddresses, Map<Integer, Value> heapTable) {
        return heapTable.entrySet().stream()
                .filter(e -> referredAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Set<Integer> getReferredAddresses(Collection<Value> symbolTableValues, Collection<Value> heapTableValues) {
        return Stream.concat(
                symbolTableValues.stream()
                        .filter(v -> v instanceof ReferenceValue)
                        .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();}),
                heapTableValues.stream()
                        .filter(v -> v instanceof ReferenceValue)
                        .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();}))
                .collect(Collectors.toSet());
    }

    public void setDisplayState(Boolean displayState) { this.displayState = displayState; }


    public void allStepExecution(){
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> programStates = removeCompletedPrograms(repository.getProgramStates());
        while(!programStates.isEmpty()){
            ProgramState state = programStates.get(0);
            state.getHeapTable().setContent(
                    garbageCollector(
                    getReferredAddresses(
                            programStates.stream().map(programState -> programState.getSymbolTable().getContent().values()).collect(Collectors.toList()),
                            state.getHeapTable().getContent()
                    ),
                            state.getHeapTable().getContent()
                    )
            );
            oneStepForAllPrograms(programStates);
            programStates = removeCompletedPrograms(repository.getProgramStates());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed program
        //and its List<ProgramState> is not empty. We have to eupdate the repository state
        repository.setProgramStates(programStates);
        //OLD
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

    List<ProgramState> removeCompletedPrograms(List<ProgramState> ProgramStates) {
        return  ProgramStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }
}
