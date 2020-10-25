package Model.Expression;

import Model.AbstractDataTypes.DictionaryInterface;
import Model.Value.Value;

public interface Expression {
    Value evaluateExpression(DictionaryInterface<String, Value> symbolTable);
}
