package Model.Value;

import Model.Type.ReferenceType;
import Model.Type.Type;

public class ReferenceValue implements Value{
    Integer address;
    Type locationType;

    public ReferenceValue(int address, Type type){
        this.address = address;
        this.locationType = type;
    }

    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }

    public int getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }
}
