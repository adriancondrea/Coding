package Model.Expression;

import Model.AbstractDataTypes.DictionaryInterface;
import Model.AbstractDataTypes.HeapInterface;
import Model.Value.Value;

public class VariableNameExpression implements Expression{
    String variableName;

    public VariableNameExpression(String variableName){
        this.variableName = variableName;
    }

    @Override
    public Value evaluateExpression(DictionaryInterface<String, Value> symbolTable, HeapInterface<Value> heapTable) {
        return symbolTable.lookup(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}
