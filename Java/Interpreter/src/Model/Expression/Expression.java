package Model.Expression;

import CustomException.CustomException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.Value.Value;

public interface Expression {
    Value evaluateExpression(DictionaryInterface<String, Value> symbolTable) throws CustomException;
}
