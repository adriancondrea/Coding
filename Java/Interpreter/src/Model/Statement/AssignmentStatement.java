package Model.Statement;

import CustomException.CustomException;
import CustomException.StatementException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.AbstractDataTypes.StackInterface;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Value.Value;

public class AssignmentStatement implements Statement{
    String id;
    Expression expression;

    public AssignmentStatement(String id, Expression expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws CustomException {
        StackInterface<Statement> executionStack = currentState.getExecutionStack();
        DictionaryInterface<String, Value> symbolTable = currentState.getSymbolTable();
        if(symbolTable.isDefined(id)){
            Value expressionValue = expression.evaluateExpression(symbolTable);
            if(expressionValue.getType().equals(symbolTable.lookup(id).getType()))
                symbolTable.update(id, expressionValue);
            else throw new StatementException("Type of expression and type of variable do not match!\n");
        }
        else throw new StatementException("Variable Id is not declared!\n");
        currentState.setExecutionStack(executionStack);
        currentState.setSymbolTable(symbolTable);
        return currentState;
    }

    @Override
    public String toString() {
        return id + " = " + expression.toString();
    }
}
