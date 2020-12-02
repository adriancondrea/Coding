package Model.Expression;

import CustomException.CustomException;
import CustomException.ExpressionException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.AbstractDataTypes.HeapInterface;
import Model.Type.BoolType;
import Model.Value.BooleanValue;
import Model.Value.Value;

public class LogicExpression implements Expression{
    Expression leftExpression, rightExpression;
    int operation;
    /*operations legend:
    1- &
    2- |
    -1 - invalid operation
     */
    public LogicExpression(char operation, Expression leftExpression, Expression rightExpression){
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;

        switch (operation) {
            case '&' -> this.operation = 1;
            case '|' -> this.operation = 2;
            default -> this.operation = -1;
        }
    }

    @Override
    public Value evaluateExpression(DictionaryInterface<String, Value> symbolTable, HeapInterface<Value> heapTable){
        Value leftValue, rightValue;
        leftValue = leftExpression.evaluateExpression(symbolTable, heapTable);
        if(leftValue.getType().equals(new BoolType())){
            rightValue = rightExpression.evaluateExpression(symbolTable, heapTable);
            if(rightValue.getType().equals(new BoolType())){
                BooleanValue val1 = (BooleanValue) leftValue;
                BooleanValue val2 = (BooleanValue) rightValue;
                Boolean v1 = val1.getValue();
                Boolean v2 = val2.getValue();
                return switch (operation) {
                    case 1 -> new BooleanValue(v1 & v2);
                    case 2 -> new BooleanValue(v1 | v2);
                    default -> throw new ExpressionException("Operation unknown!\n");
                };
            }
            else
                throw new ExpressionException("Operand2 is not a boolean!\n");
        }
        else
            throw new ExpressionException("Operand1 is not a boolean!\n");
    }

    @Override
    public String toString() {
        if(operation == -1)
            return leftExpression.toString() + " unknown operation " + rightExpression.toString();
        char[] operationsArray = {'n', '&', '|'};
        return leftExpression.toString() + ' ' + operationsArray[operation] + ' ' + rightExpression.toString();
    }
}
