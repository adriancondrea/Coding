package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class TrueValue implements Value{
    public static boolean value;

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return "true";
    }
}
