package Model.Statement;

import CustomException.CustomException;
import Model.AbstractDataTypes.MyStack;
import Model.AbstractDataTypes.StackInterface;
import Model.ProgramState;

public class ForkStatement implements Statement{
    Statement statement;

    public ForkStatement(Statement statement){
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws CustomException {
        StackInterface<Statement> forkExecutionStack = new MyStack<>();
        forkExecutionStack.push(statement);
        return new ProgramState(forkExecutionStack,
                                currentState.getSymbolTable().deepCopy(),
                                currentState.getOutputList(),
                                currentState.getFileTable(),
                                currentState.getHeapTable(),
                                statement);
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}
