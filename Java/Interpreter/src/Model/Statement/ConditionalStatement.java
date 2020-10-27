package Model.Statement;

import CustomException.CustomException;
import Model.AbstractDataTypes.StackInterface;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Value.BooleanValue;
import Model.Value.Value;

public class ConditionalStatement implements Statement{
    Expression condition;
    Statement trueBranch, falseBranch;

    public ConditionalStatement(Expression condition, Statement trueBranch, Statement falseBranch){
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws CustomException {
        StackInterface<Statement> executionStack = currentState.getExecutionStack();
        Value expressionValue = condition.evaluateExpression(currentState.getSymbolTable());
        if(!expressionValue.getType().equals(new BoolType()))
            throw new CustomException("Conditional expression is not a boolean!\n");
        else {
            if (expressionValue.equals(new BooleanValue(true)))
                executionStack.push(trueBranch);
            else
                executionStack.push(falseBranch);
            currentState.setExecutionStack(executionStack);
            return currentState;
        }
    }

    @Override
    public String toString() {
        return "(IF(" + condition.toString() + ") THEN(" + trueBranch.toString() +")ELSE (" + falseBranch.toString() + "))";
    }
}
