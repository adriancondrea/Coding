package Controller;

import CustomException.*;
import Model.AbstractDataTypes.MyDictionary;
import Model.AbstractDataTypes.MyList;
import Model.AbstractDataTypes.MyStack;
import Model.AbstractDataTypes.StackInterface;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntegerType;
import Model.Value.BooleanValue;
import Model.Value.NumberValue;
import Model.Value.Value;
import Repository.Repository;


public class Controller {
    Repository repository;
    //if displayState flag is set to true, display the program state after each execution
    Boolean displayState;
    public Controller(Repository repository){ this.repository = repository; displayState = false; }

    public void setDisplayState(Boolean displayState) { this.displayState = displayState; }

    public ProgramState oneStepExecution(ProgramState state) throws CustomException {
        StackInterface<Statement> executionStack = state.getExecutionStack();
        if(executionStack.isEmpty())
            throw new CustomException("Stack is empty!");
        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(state);
    }

    public void allStepExecution() throws CustomException {
        ProgramState programState = repository.getCurrentProgram();
        if(displayState){
            System.out.println(programState.toString());
        }
        while (!programState.getExecutionStack().isEmpty()){
                oneStepExecution(programState);
                if(displayState)
                    System.out.println(programState.toString());
        }
    }

    public void newProgram(int program){
        ProgramState state;
        StackInterface<Statement> stack = new MyStack<Statement>();
        Statement ex1 = new CompoundStatement(new VariableDeclarationStatement("v",new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new NumberValue(2))),
                              new PrintStatement(new VariableNameExpression("v"))));
        Statement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntegerType()),
                            new CompoundStatement(new VariableDeclarationStatement("b",new IntegerType()),
                                    new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new NumberValue(2)),
                                            new ArithmeticExpression('*',new ValueExpression(new NumberValue(3)), new ValueExpression(new NumberValue(5))))),
                                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableNameExpression("a"),
                                                        new ValueExpression(new NumberValue(1)))), new PrintStatement(new VariableNameExpression("b"))))));
        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                            new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()),
                                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                    new CompoundStatement(new ConditionalStatement(new VariableNameExpression("a"),new AssignmentStatement("v",
                                            new ValueExpression(new NumberValue(2))), new AssignmentStatement("v",
                                                new ValueExpression(new NumberValue(3)))), new PrintStatement(new VariableNameExpression("v"))))));

        Statement ex4 = new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()), new AssignmentStatement("a", new ValueExpression(new NumberValue(5))));

        Statement ex5 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new AssignmentStatement("a", new ValueExpression(new NumberValue(5))));

        Statement ex6 = new CompoundStatement(
                new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new AssignmentStatement("a", new ValueExpression(new BooleanValue(false)))),
                new PrintStatement(new LogicExpression('&', new VariableNameExpression("a"), new ValueExpression(new BooleanValue(true)))));
        /*
        Statement ex7 = new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                            new VariableDeclarationStatement("b", new BoolType())), new CompoundStatement(
                                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                        new AssignmentStatement("b", new ValueExpression(new BooleanValue(false))))),
                        new CompoundStatement(new PrintStatement(new LogicExpression('&', new VariableNameExpression("a"), new VariableNameExpression("b"))),
                                new PrintStatement(new LogicExpression('|', new VariableNameExpression("a"), new VariableNameExpression("b")))));
        */

        if(program == 1)
            stack.push(ex1);
        else if(program == 2)
            stack.push(ex2);
            else if(program == 3)
                stack.push(ex3);
            else if(program == 4)
                stack.push(ex4);
            else if(program == 5)
                stack.push(ex5);
            else if(program == 6)
                stack.push(ex6);

        state = new ProgramState(stack, new MyDictionary<String, Value>(), new MyList<Value>(), null);
        repository.addState(state);
    }
}
