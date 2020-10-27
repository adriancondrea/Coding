package Model.AbstractDataTypes;

import CustomException.CustomException;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyStack<T> implements StackInterface<T> {
    Deque<T> stack;

    //default constructor
    public MyStack(){
        this.stack = new ArrayDeque<T>();
    }

    @Override
    public T pop() throws CustomException {
        if(stack.isEmpty()){
            throw new CustomException("Stack is empty!");
        }
        return stack.pop();
    }

    @Override
    public void push(T element) {
        stack.push(element);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        if(stack.isEmpty())
            return "EMPTY\n";

        //return stack.toString() + '\n';
        StringBuilder stackToString = new StringBuilder();
        stackToString.append('{');
        for(T element : stack){
            if(element != null)
                stackToString.append(element.toString());
                stackToString.append('|');
        }
        //remove last |
        stackToString.setLength(stackToString.length() -1);
        stackToString.append("}\n");
        return stackToString.toString();
    }
}
