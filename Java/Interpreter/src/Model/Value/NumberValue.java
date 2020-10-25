package Model.Value;

import Model.Type.IntegerType;
import Model.Type.Type;

public class NumberValue implements Value{
    int value;

    NumberValue(int value){
        this.value = value;
    }

    //default constructor
    NumberValue(){
        value = 0;
    }

    int getValue(){
        return value;
    }

    @Override
    public Type getType() {
        return new IntegerType();
    }

    @Override
    public String toString() {
        return "int";
    }
}
