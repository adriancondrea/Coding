import Controller.Controller;
import Model.AbstractDataTypes.MyDictionary;
import Model.AbstractDataTypes.MyList;
import Model.AbstractDataTypes.MyStack;
import Model.AbstractDataTypes.MyHeap;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntegerType;
import Model.Type.ReferenceType;
import Model.Type.StringType;
import Model.Value.BooleanValue;
import Model.Value.NumberValue;
import Model.Value.ReferenceValue;
import Model.Value.StringValue;
import Repository.Repository;
import Repository.memoryRepository;
import View.Command.ExitCommand;
import View.Command.RunExample;
import View.TextMenu;
import com.sun.jdi.IntegerValue;

import javax.naming.ldap.Control;

public class Main {
    public static void main(String[] args) {

        Statement ex1 = new CompoundStatement(new VariableDeclarationStatement("v",new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new NumberValue(2))),
                        new PrintStatement(new VariableNameExpression("v"))));
        ProgramState program1 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex1);
        Repository repository1 = new memoryRepository(program1, "log1.txt");
        Controller controller1 = new Controller(repository1);

        Statement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntegerType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new NumberValue(2)),
                                new ArithmeticExpression('*',new ValueExpression(new NumberValue(3)), new ValueExpression(new NumberValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableNameExpression("a"),
                                        new ValueExpression(new NumberValue(1)))), new PrintStatement(new VariableNameExpression("b"))))));
        ProgramState program2 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex2);
        Repository repository2 = new memoryRepository(program2, "log2.txt");
        Controller controller2 = new Controller(repository2);

        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(new ConditionalStatement(new VariableNameExpression("a"),new AssignmentStatement("v",
                                        new ValueExpression(new NumberValue(2))), new AssignmentStatement("v",
                                        new ValueExpression(new NumberValue(3)))), new PrintStatement(new VariableNameExpression("v"))))));
        ProgramState program3 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex3);
        Repository repository3 = new memoryRepository(program3, "log3.txt");
        Controller controller3 = new Controller(repository3);

        Statement ex4 = new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()), new AssignmentStatement("a", new ValueExpression(new NumberValue(5))));
        ProgramState program4 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex4);
        Repository repository4 = new memoryRepository(program4, "log4.txt");
        Controller controller4 = new Controller(repository4);

        Statement ex5 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new AssignmentStatement("a", new ValueExpression(new NumberValue(5))));
        ProgramState program5 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex5);
        Repository repository5 = new memoryRepository(program5, "log5.txt");
        Controller controller5 = new Controller(repository5);

        Statement ex6 = new CompoundStatement(
                new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new AssignmentStatement("a", new ValueExpression(new BooleanValue(false)))),
                new PrintStatement(new LogicExpression('&', new VariableNameExpression("a"), new ValueExpression(new BooleanValue(true)))));
        ProgramState program6 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex6);
        Repository repository6 = new memoryRepository(program6, "log6.txt");
        Controller controller6 = new Controller(repository6);

        Statement ex7 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(
                        new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in")))),
                        new OpenReadFileStatement(new VariableNameExpression("varf"))), new VariableDeclarationStatement("varc", new IntegerType())),
                        new ReadNumberFromFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                        new ReadNumberFromFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                        new CloseReadFileStatement(new VariableNameExpression("varf")));
        ProgramState program7 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex7);
        Repository repository7 = new memoryRepository(program7, "log7.txt");
        Controller controller7 = new Controller(repository7);

        Statement ex8 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(
                new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test2.in")))),
                new OpenReadFileStatement(new VariableNameExpression("varf"))), new VariableDeclarationStatement("varc", new IntegerType())),
                new ReadNumberFromFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                new ReadNumberFromFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                new CloseReadFileStatement(new VariableNameExpression("varf")));
        ProgramState program8 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex8);
        Repository repository8 = new memoryRepository(program8, "log8.txt");
        Controller controller8 = new Controller(repository8);

        Statement ex9 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new HeapAllocationStatement("v", new ValueExpression(new NumberValue(20)))),
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType())))), new HeapAllocationStatement("a", new VariableNameExpression("v"))), new PrintStatement(new VariableNameExpression("v"))), new PrintStatement(new VariableNameExpression("a")));
        ProgramState program9 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex9);
        Repository repository9 = new memoryRepository(program9, "log9.txt");
        Controller controller9 = new Controller(repository9);

        Statement ex10 = new CompoundStatement(
                new CompoundStatement(
                        new CompoundStatement(
                                new CompoundStatement(
                                        new CompoundStatement(
                                                new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())),
                new HeapAllocationStatement("v", new ValueExpression(new NumberValue(20)))),
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType())))),
                new HeapAllocationStatement("a", new VariableNameExpression("v"))),
                new PrintStatement(new ReadHeapExpression(new VariableNameExpression("v")))),
                new PrintStatement(new ArithmeticExpression('+',new ReadHeapExpression(new ReadHeapExpression(new VariableNameExpression("a"))), new ValueExpression(new NumberValue(5)))));
        ProgramState program10 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex10);
        Repository repository10 = new memoryRepository(program10, "log10.txt");
        Controller controller10 = new Controller(repository10);

        Statement ex11 = new CompoundStatement(
                new CompoundStatement(
                new CompoundStatement(
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())),
                new HeapAllocationStatement("v", new ValueExpression(new NumberValue(20)))),
                new PrintStatement(new ReadHeapExpression(new VariableNameExpression("v")))),
                new WriteHeapStatement("v", new ValueExpression(new NumberValue(30)))),
                new PrintStatement(new ArithmeticExpression('+', new ReadHeapExpression(new VariableNameExpression("v")), new ValueExpression(new NumberValue(5)))));
        ProgramState program11 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex11);
        Repository repository11 = new memoryRepository(program11, "log11.txt");
        Controller controller11 = new Controller(repository11);

        Statement ex12 = new CompoundStatement(
                new CompoundStatement(
                new CompoundStatement(
                new CompoundStatement(
                new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new HeapAllocationStatement("v", new ValueExpression(new NumberValue(20)))),
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType())))),
                new HeapAllocationStatement("a", new VariableNameExpression("v"))),
                new HeapAllocationStatement("v", new ValueExpression(new NumberValue(30)))),
                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableNameExpression("a")))));
        ProgramState program12 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex12);
        Repository repository12 = new memoryRepository(program12, "log12.txt");
        Controller controller12 = new Controller(repository12);

        Statement ex13 = new CompoundStatement(
                new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()), new AssignmentStatement("v", new ValueExpression(new NumberValue(0)))),
                new WhileStatement(new RelationalExpression("<", new VariableNameExpression("v"), new ValueExpression(new NumberValue(5))), new CompoundStatement(new PrintStatement(new VariableNameExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('+', new VariableNameExpression("v"), new ValueExpression(new NumberValue(1)))))));
        ProgramState program13 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex13);
        Repository repository13 = new memoryRepository(program13, "log13.txt");
        Controller controller13 = new Controller(repository13);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), controller1));
        menu.addCommand(new RunExample("2", ex2.toString(), controller2));
        menu.addCommand(new RunExample("3", ex3.toString(), controller3));
        menu.addCommand(new RunExample("4", ex4.toString(), controller4));
        menu.addCommand(new RunExample("5", ex5.toString(), controller5));
        menu.addCommand(new RunExample("6", ex6.toString(), controller6));
        menu.addCommand(new RunExample("7", ex7.toString(), controller7));
        menu.addCommand(new RunExample("8", ex8.toString(), controller8));
        menu.addCommand(new RunExample("9", ex9.toString(), controller9));
        menu.addCommand(new RunExample("10", ex10.toString(), controller10));
        menu.addCommand(new RunExample("11", ex11.toString(), controller11));
        menu.addCommand(new RunExample("12", ex12.toString(), controller12));
        menu.addCommand(new RunExample("13", ex13.toString(), controller13));
        menu.show();
    }
}
