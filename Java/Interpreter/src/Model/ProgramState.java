package Model;

import Model.AbstractDataTypes.*;
import Model.Statement.Statement;
import Model.Value.Value;

public class ProgramState {

    StackInterface<Statement> executionStack;
    DictionaryInterface<String, Value> symbolTable;
    ListInterface<Value> outputList;

    public StackInterface<Statement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(StackInterface<Statement> stack) {
        this.executionStack = stack;
    }

    public ListInterface<Value> getOutputList() {
        return this.outputList;
    }

    public DictionaryInterface<String, Value> getSymbolTable() {
        return this.symbolTable;
    }

    public void setOutputList(ListInterface<Value> outputList) {
        this.outputList = outputList;
    }
}
