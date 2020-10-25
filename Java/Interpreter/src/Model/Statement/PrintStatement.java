package Model.Statement;

import CustomException.CustomException;
import Model.AbstractDataTypes.ListInterface;
import Model.AbstractDataTypes.StackInterface;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Value.Value;

public class PrintStatement implements Statement{
    Expression expression;

    public PrintStatement(Expression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws CustomException {
        StackInterface<Statement> executionStack = currentState.getExecutionStack();
        ListInterface<Value> outputList = currentState.getOutputList();
        outputList.add(expression.evaluateExpression(currentState.getSymbolTable()));
        currentState.setExecutionStack(executionStack);
        currentState.setOutputList(outputList);
        return currentState;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
