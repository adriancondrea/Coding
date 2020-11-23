package Model.AbstractDataTypes;

import CustomException.CollectionException;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyStack<T> implements StackInterface<T> {
    Deque<T> stack;

    //default constructor
    public MyStack(){
        this.stack = new ArrayDeque<>();
    }

    @Override
    public T pop() throws CollectionException {
        if(stack.isEmpty()){
            throw new CollectionException("Stack is empty!");
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

        StringBuilder stackToString = new StringBuilder();
        for(T element : stack){
            if(element != null)
                stackToString.append(element.toString());
                stackToString.append('\n');
        }
        return stackToString.toString();
    }
}
