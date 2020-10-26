package Model;

import Model.AbstractDataTypes.*;
import Model.Statement.Statement;
import Model.Value.Value;

public class ProgramState {

    StackInterface<Statement> executionStack;
    DictionaryInterface<String, Value> symbolTable;
    ListInterface<Value> outputList;
    Statement originalProgram;

    public ProgramState(StackInterface<Statement> executionStack, DictionaryInterface<String, Value> symbolTable, ListInterface<Value> outputList, Statement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
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

    public DictionaryInterface<String, Value> getSymbolTable() {
        return this.symbolTable;
    }

    public void setOutputList(ListInterface<Value> outputList) {
        this.outputList = outputList;
    }

    public void setSymbolTable(DictionaryInterface<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public String toString() {
        return "\t\t~~Execution Stack~\n" +
                executionStack.toString() +
                "~~~~~~~~~~~~~~~~~~~~~~~~"+
                "\n\t\t~~Symbol Table~~\n" +
                symbolTable.toString() +
                "~~~~~~~~~~~~~~~~~~~~~~~~"+
                "\n\t\t~~Output List~~\n" +
                outputList.toString()+
                "~~~~~~~~~~END OF STATE~~~~~~~~~~\n\n\n\n";
    }
}
