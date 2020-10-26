package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class FalseValue extends BooleanValue implements Value{
    public FalseValue(){
        this.value = false;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return "false";
    }
}
