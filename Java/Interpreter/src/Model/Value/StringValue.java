package Model.Value;

import Model.Type.Type;

public class StringValue implements Value{
    String value;

    public StringValue(String s){
        this.value = s;
    }

    public StringValue(){
        this.value = "";
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StringValue){
            String value = ((StringValue) obj).getValue();
            return value == this.getValue();
        }
        return false;
    }
}
