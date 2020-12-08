package Model;

import CustomException.CustomException;
import Model.AbstractDataTypes.*;
import Model.Statement.Statement;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class ProgramState {

    StackInterface<Statement> executionStack;
    DictionaryInterface<String, Value> symbolTable;
    ListInterface<Value> outputList;
    DictionaryInterface<StringValue, BufferedReader> fileTable;
    HeapInterface<Value> heapTable;
    Statement originalProgram;
    public final Integer id;

    public ProgramState(StackInterface<Statement> executionStack, DictionaryInterface<String, Value> symbolTable, ListInterface<Value> outputList, DictionaryInterface<StringValue, BufferedReader> fileTable, HeapInterface<Value> heapTable, Statement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.originalProgram = originalProgram;
        id = newId();
        if(originalProgram != null)
            executionStack.push(originalProgram);
    }

    private Integer newId() {
        return 1;
        //TODO: implement this to return unique id's
    }

    public StackInterface<Statement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(StackInterface<Statement> stack) {
        this.executionStack = stack;
    }


    public ListInterface<Value> getOutputList() {
        return this.outputList;
    }

    public void setOutputList(ListInterface<Value> outputList) {
        this.outputList = outputList;
    }


    public DictionaryInterface<String, Value> getSymbolTable() {
        return this.symbolTable;
    }

    public void setSymbolTable(DictionaryInterface<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }


    public DictionaryInterface<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(DictionaryInterface<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }


    public HeapInterface<Value> getHeapTable() {
        return heapTable;
    }

    public void setHeapTable(HeapInterface<Value> heapTable) {
        this.heapTable = heapTable;
    }

    @Override
    public String toString() {
        return "~~id: " + id.toString()+"~~\n"+
                "\t\t~~Execution Stack~~\n" +
                executionStack.toString() +
                "~~~~~~~~~~~~~~~~~~~~~~~~"+
                "\n\t\t~~Symbol Table~~\n" +
                symbolTable.toString() +
                "~~~~~~~~~~~~~~~~~~~~~~~~"+
                "\n\t\t~~Output List~~\n" +
                outputList.toString()+
                "~~~~~~~~~~~~~~~~~~~~~~~~"+
                "\n\t\t~~File Table~~\n"+
                fileTable.KeysToString()+
                "~~~~~~~~~~~~~~~~~~~~~~~~"+
                "\n\t\t~~Heap Table~~\n"+
                heapTable.toString()+
                "~~~~~~~~~~END OF STATE~~~~~~~~~~";
    }

    public Boolean isNotCompleted() {
        return !this.getExecutionStack().isEmpty();
    }

    public ProgramState oneStepExecution() {
        if(executionStack.isEmpty())
            throw new CustomException("Stack is empty!");
        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }
}
