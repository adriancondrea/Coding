package Model;

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
    Statement originalProgram;

    public ProgramState(StackInterface<Statement> executionStack, DictionaryInterface<String, Value> symbolTable, ListInterface<Value> outputList, DictionaryInterface<StringValue, BufferedReader> fileTable ,Statement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram;
        if(originalProgram != null)
            executionStack.push(originalProgram);
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

    @Override
    public String toString() {
        return "\t\t~~Execution Stack~\n" +
                executionStack.toString() +
                "~~~~~~~~~~~~~~~~~~~~~~~~"+
                "\t\t~~Symbol Table~~\n" +
                symbolTable.toString() +
                "~~~~~~~~~~~~~~~~~~~~~~~~"+
                "\t\t~~Output List~~\n" +
                outputList.toString()+
                "\t\t~~File Table~~\n"+
                fileTable.KeysToString()+
                "~~~~~~~~~~END OF STATE~~~~~~~~~~";
    }
}
