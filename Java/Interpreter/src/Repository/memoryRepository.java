package Repository;

import Model.ProgramState;

import java.util.LinkedList;

public class memoryRepository implements Repository{
    LinkedList<ProgramState> programStates;
    public memoryRepository(){
        programStates = new LinkedList<ProgramState>();
    }

    @Override
    public void addState(ProgramState newState) {
        programStates.addLast(newState);
    }

    @Override
    public ProgramState getCurrentProgram() {
        ProgramState currentProgram = programStates.getFirst();
        programStates.removeFirst();
        return currentProgram;
    }
}
