package Model.AbstractDataTypes;

import CustomException.CustomException;

import java.util.LinkedList;

public class MyList<T> implements ListInterface<T>{
    LinkedList<T> list;

    //default constructor
    public MyList() {list = new LinkedList<T>(); }

    @Override
    public void add(T element) throws CustomException {
        list.add(element);
    }

    @Override
    public T pop() throws CustomException {
        if(list.isEmpty())
            throw new CustomException("list is empty!");
        return list.pop();
    }

    @Override
    public String toString() {
        //StringBuilder - mutable sequence of characters, because strings are immutable in Java.
        StringBuilder listToString = new StringBuilder();
        for(T element: list){
            if(element != null) {
                listToString.append(element.toString());
                listToString.append('\n');
            }
            }
        if(listToString.toString().isEmpty())
            return "EMPTY\n";
        //convert the stringBuilder to string and return it
        return  listToString.toString();
        }
}
