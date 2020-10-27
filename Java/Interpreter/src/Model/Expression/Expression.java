package Model.Expression;

import CustomException.CollectionException;
import CustomException.CustomException;
import CustomException.ExpressionException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.Value.Value;

public interface Expression {
    Value evaluateExpression(DictionaryInterface<String, Value> symbolTable) throws CustomException;
}
