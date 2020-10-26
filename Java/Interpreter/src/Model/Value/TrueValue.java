package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class TrueValue extends BooleanValue implements Value{
    public TrueValue(){
        this.value = true;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return "true";
    }
}
