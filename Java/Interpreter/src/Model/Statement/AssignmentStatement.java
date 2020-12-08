package Model.Statement;

import CustomException.CustomException;
import CustomException.StatementException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.AbstractDataTypes.HeapInterface;
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
        DictionaryInterface<String, Value> symbolTable = currentState.getSymbolTable();
        HeapInterface<Value> heapTable = currentState.getHeapTable();
        if(symbolTable.isDefined(id)){
            Value expressionValue = expression.evaluateExpression(symbolTable, heapTable);
            if(expressionValue.getType().equals(symbolTable.lookup(id).getType()))
                symbolTable.update(id, expressionValue);
            else throw new StatementException("Type of expression and type of variable do not match!\n");
        }
        else throw new StatementException("Variable Id is not declared!\n");
        currentState.setSymbolTable(symbolTable);
        currentState.setHeapTable(heapTable);
        return null;
    }

    @Override
    public String toString() {
        return id + " = " + expression.toString();
    }
}
