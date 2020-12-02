package Model.Expression;

import CustomException.ExpressionException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.AbstractDataTypes.HeapInterface;
import Model.Value.ReferenceValue;
import Model.Value.Value;

public class ReadHeapExpression implements Expression{
    Expression expression;
    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluateExpression(DictionaryInterface<String, Value> symbolTable, HeapInterface<Value> heapTable) {
        Value value = expression.evaluateExpression(symbolTable, heapTable);
        if(value instanceof ReferenceValue) {
            ReferenceValue referenceValue = (ReferenceValue) value;
            int address = referenceValue.getAddress();
            if(heapTable.allocatedAddress(address)){
                return heapTable.getValue(address);
            }
            else {
                throw new ExpressionException("Address not allocated!");
            }
        }
        else {
            throw new ExpressionException("Expression not of reference type!");
        }
    }

    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }
}
