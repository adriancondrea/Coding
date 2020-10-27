package Model.Expression;

import CustomException.CustomException;
import CustomException.ExpressionException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.Value.Value;

public class ValueExpression implements Expression{
    Value result;
    public  ValueExpression(Value value){
        this.result = value;
    }

    @Override
    public Value evaluateExpression(DictionaryInterface<String, Value> symbolTable) throws CustomException {
        return result;
    }

    @Override
    public String toString() {
        return result.toString();
    }
}
