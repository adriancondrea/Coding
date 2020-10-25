package Model.Type;

import Model.Value.TrueValue;
import Model.Value.Value;

public class BoolType implements Type{
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new TrueValue();
    }
}
