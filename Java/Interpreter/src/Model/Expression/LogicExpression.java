package Model.Expression;

import CustomException.CustomException;
import Model.AbstractDataTypes.DictionaryInterface;
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
        if(operation == '&')
            this.operation = 1;
        else if(operation == '|')
            this.operation = 2;
        else
            this.operation = -1;
    }

    @Override
    public Value evaluateExpression(DictionaryInterface<String, Value> symbolTable) throws CustomException {
        Value leftValue, rightValue;
        leftValue = leftExpression.evaluateExpression(symbolTable);
        if(leftValue.getType().equals(new BoolType())){
            rightValue = rightExpression.evaluateExpression(symbolTable);
            if(rightValue.getType().equals(new BoolType())){
                BooleanValue val1 = (BooleanValue) leftValue;
                BooleanValue val2 = (BooleanValue) rightValue;
                Boolean v1 = val1.getValue();
                Boolean v2 = val2.getValue();
                if(operation == 1)
                    return new BooleanValue(v1 & v2);
                else if(operation == 2)
                    return new BooleanValue(v1 | v2);
            }
            else
                throw new CustomException("Operand2 is not a boolean!\n");
        }
        else
            throw new CustomException("Operand1 is not a boolean!\n");
        return null;
    }

    @Override
    public String toString() {
        char[] operationsArray = {'n', '&', '|'};
        return leftExpression.toString() + ' ' + operationsArray[operation] + ' ' + rightExpression.toString();
    }
}
