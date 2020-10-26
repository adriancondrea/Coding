package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BooleanValue implements Value{
    Boolean value;
    public BooleanValue(boolean value){ this.value = value; }
    public BooleanValue() {this.value = true; }
    @Override
    public Type getType() {
        return new BoolType();
    }

    public Boolean getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
