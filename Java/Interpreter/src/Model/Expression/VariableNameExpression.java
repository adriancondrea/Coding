package Model.Expression;

import CustomException.CustomException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.Value.Value;

public class VariableNameExpression implements Expression{
    String variableName;
    Value variableValue = null;
    public VariableNameExpression(String variableName){
        this.variableName = variableName;
    }

    @Override
    public Value evaluateExpression(DictionaryInterface<String, Value> symbolTable) throws CustomException {
        variableValue = symbolTable.lookup(variableName);
        return variableValue;
    }

    @Override
    public String toString() {
        return variableValue.toString();
    }
}
