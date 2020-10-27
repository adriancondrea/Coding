package Model.Statement;

import CustomException.CustomException;
import Model.AbstractDataTypes.StackInterface;
import Model.ProgramState;

public class NopStatement implements Statement{
    public NopStatement() {}

    @Override
    public ProgramState execute(ProgramState currentState) throws CustomException {
        StackInterface<Statement> executionStack = currentState.getExecutionStack();
        currentState.setExecutionStack(executionStack);
        return currentState;
    }

    @Override
    public String toString() {
        return "nop";
    }
}
