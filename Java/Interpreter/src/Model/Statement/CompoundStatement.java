package Model.Statement;

import CustomException.CustomException;
import Model.AbstractDataTypes.StackInterface;
import Model.ProgramState;

public class CompoundStatement implements Statement{
    Statement first, second;

    public CompoundStatement(Statement first, Statement second){
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws CustomException {
        StackInterface<Statement> executionStack = currentState.getExecutionStack();
        executionStack.push(second);
        executionStack.push(first);
        currentState.setExecutionStack(executionStack);
        return null;
    }

    @Override
    public String toString(){
        //return the compound statement in the form (first;second)
        //return "("+first.toString()+";"+second.toString()+")";
        return first.toString() + "; " + second.toString();
    }
}
