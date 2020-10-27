package Model.Expression;

import CustomException.CollectionException;
import CustomException.CustomException;
import CustomException.ExpressionException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.Type.IntegerType;
import Model.Value.NumberValue;
import Model.Value.Value;

public class ArithmeticExpression implements Expression{
    Expression leftExpression, rightExpression;
    int operation;
    /* operations legend:
        1 = *
        2 = /
        3 = +
        4 = -
        -1 = invalid operation
     */

    public ArithmeticExpression(char operation, Expression leftExpression, Expression rightExpression){
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        if(operation == '*')
            this.operation = 1;
        else if(operation == '/')
            this.operation = 2;
        else if(operation == '+')
            this.operation = 3;
        else if(operation =='-')
            this.operation = 4;
        else
            this.operation = -1;
    }

    @Override
    public String toString() {
        if(operation == -1)
            return leftExpression.toString() + " unknown operation " + rightExpression.toString();
        char[] operationsArray = {'n','*','/','+','-'};
        return leftExpression.toString() + ' ' + operationsArray[operation] + ' ' + rightExpression.toString();
    }

    @Override
    public Value evaluateExpression(DictionaryInterface<String, Value> symbolTable) throws CustomException {
        Value leftValue, rightValue;
        leftValue = leftExpression.evaluateExpression(symbolTable);
        if(leftValue.getType().equals(new IntegerType())){
            rightValue = rightExpression.evaluateExpression(symbolTable);
            if(rightValue.getType().equals(new IntegerType())){
                NumberValue val1 = (NumberValue) leftValue;
                NumberValue val2 = (NumberValue) rightValue;
                int leftNumber, rightNumber;
                leftNumber =val1.getValue();
                rightNumber = val2.getValue();
                if(operation == 1)
                    return new NumberValue(leftNumber * rightNumber);
                else if(operation == 2) {
                    if(rightNumber == 0)
                        throw new ExpressionException("Division by zero!\n");
                    return new NumberValue(leftNumber / rightNumber);
                }
                else if(operation == 3)
                    return  new NumberValue(leftNumber + rightNumber);
                else if(operation == 4)
                    return new NumberValue(leftNumber - rightNumber);
                else if(operation == -1)
                    throw new ExpressionException("unknown operation!\n");
            }
            else
                throw new ExpressionException("Operand2 is not an integer!\n");
        }
        else
            throw new ExpressionException("Operand1 is not an integer!\n");
        return null;
    }
}
