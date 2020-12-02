package Model.Expression;

import Model.AbstractDataTypes.DictionaryInterface;
import Model.AbstractDataTypes.HeapInterface;
import Model.Value.Value;

public class ValueExpression implements Expression{
    Value result;
    public  ValueExpression(Value value){
        this.result = value;
    }

    @Override
    public Value evaluateExpression(DictionaryInterface<String, Value> symbolTable, HeapInterface<Value> heapTable) {
        return result;
    }

    @Override
    public String toString() {
        return result.toString();
    }
}
