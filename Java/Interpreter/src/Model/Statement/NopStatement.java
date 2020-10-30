package Model.Statement;

import CustomException.CustomException;
import Model.ProgramState;

public class NopStatement implements Statement{
    public NopStatement() {}

    @Override
    public ProgramState execute(ProgramState currentState) throws CustomException {
        return currentState;
    }

    @Override
    public String toString() {
        return "nop";
    }
}
