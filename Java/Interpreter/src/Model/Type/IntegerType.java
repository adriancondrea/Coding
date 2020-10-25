package Model.Type;

public class IntegerType implements Type{

    //check if a type is integer
    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntegerType;
    }

    @Override
    public String toString() {
        return "int";
    }
}
