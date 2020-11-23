package Model.Expression;

import CustomException.CustomException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.Value.Value;

public class VariableNameExpression implements Expression{
    String variableName;

    public VariableNameExpression(String variableName){
        this.variableName = variableName;
    }

    @Override
    public Value evaluateExpression(DictionaryInterface<String, Value> symbolTable) throws CustomException {
        return symbolTable.lookup(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}
