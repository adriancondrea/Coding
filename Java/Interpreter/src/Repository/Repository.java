package Repository;

import Model.ProgramState;

public interface Repository {
    void addState(ProgramState newState);
    ProgramState getCurrentProgram();
}
