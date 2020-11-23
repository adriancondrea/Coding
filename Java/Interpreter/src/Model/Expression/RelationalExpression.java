package Model.Expression;

import CustomException.CustomException;
import CustomException.ExpressionException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.Type.IntegerType;
import Model.Value.BooleanValue;
import Model.Value.NumberValue;
import Model.Value.Value;

public class RelationalExpression implements Expression{
    Expression leftExpression, rightExpression;
    int relation;
    public RelationalExpression(String relationString, Expression left, Expression right){
        this.leftExpression = left;
        this.rightExpression = right;
        switch(relationString){
            case "<" ->     relation = 1;
            case "<=" ->    relation = 2;
            case "==" ->    relation = 3;
            case "!=" ->    relation = 4;
            case ">" ->     relation = 5;
            case ">=" ->    relation = 6;
            default ->      relation = -1;
        }
    }
    @Override
    public Value evaluateExpression(DictionaryInterface<String, Value> symbolTable) throws CustomException {
        Value leftValue, rightValue;
        leftValue = leftExpression.evaluateExpression(symbolTable);
        if(leftValue.getType().equals(new IntegerType())){
            rightValue = rightExpression.evaluateExpression(symbolTable);
            if(rightValue.getType().equals(new IntegerType())){
                int leftNumber, rightNumber;
                leftNumber = ((NumberValue) leftValue).getValue();
                rightNumber =((NumberValue) rightValue).getValue();
                return switch (relation) {
                    case 1 -> new BooleanValue(leftNumber < rightNumber);
                    case 2 -> new BooleanValue(leftNumber <= rightNumber);
                    case 3 -> new BooleanValue(leftNumber == rightNumber);
                    case 4 -> new BooleanValue(leftNumber != rightNumber);
                    case 5 -> new BooleanValue(leftNumber > rightNumber);
                    case 6 -> new BooleanValue(leftNumber >= rightNumber);
                    default -> throw new ExpressionException("unknown comparison relation!");
                };
            }
            else{
                throw new ExpressionException("Right side of relational expression not integer!");
            }
        }
        else {
            throw new ExpressionException("Left side of relational expression not integer!");
        }
    }

    @Override
    public String toString() {
        String[] relations = {"","<", "<=", "==", "!=", ">", ">="};
        return leftExpression.toString() + ' ' + relations[relation] + ' ' + rightExpression.toString();
    }
}
