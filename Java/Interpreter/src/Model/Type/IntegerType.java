package Model.Type;

import Model.Value.NumberValue;
import Model.Value.Value;

public class IntegerType implements Type{
    int value;
    public IntegerType(){value = 0; }
    //check if a type is integer
    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntegerType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new NumberValue();
    }
}
