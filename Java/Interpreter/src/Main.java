import Controller.Controller;
import Model.AbstractDataTypes.MyDictionary;
import Model.AbstractDataTypes.MyList;
import Model.AbstractDataTypes.MyStack;
import Model.Expression.ArithmeticExpression;
import Model.Expression.LogicExpression;
import Model.Expression.ValueExpression;
import Model.Expression.VariableNameExpression;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntegerType;
import Model.Type.StringType;
import Model.Value.BooleanValue;
import Model.Value.NumberValue;
import Model.Value.StringValue;
import Repository.Repository;
import Repository.memoryRepository;
import View.Command.ExitCommand;
import View.Command.RunExample;
import View.TextMenu;

public class Main {
    public static void main(String[] args) {

        Statement ex1 = new CompoundStatement(new VariableDeclarationStatement("v",new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new NumberValue(2))),
                        new PrintStatement(new VariableNameExpression("v"))));
        ProgramState program1 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),ex1);
        Repository repository1 = new memoryRepository(program1, "log1.txt");
        Controller controller1 = new Controller(repository1);

        Statement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntegerType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new NumberValue(2)),
                                new ArithmeticExpression('*',new ValueExpression(new NumberValue(3)), new ValueExpression(new NumberValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableNameExpression("a"),
                                        new ValueExpression(new NumberValue(1)))), new PrintStatement(new VariableNameExpression("b"))))));
        ProgramState program2 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),ex2);
        Repository repository2 = new memoryRepository(program2, "log2.txt");
        Controller controller2 = new Controller(repository2);

        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(new ConditionalStatement(new VariableNameExpression("a"),new AssignmentStatement("v",
                                        new ValueExpression(new NumberValue(2))), new AssignmentStatement("v",
                                        new ValueExpression(new NumberValue(3)))), new PrintStatement(new VariableNameExpression("v"))))));
        ProgramState program3 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),ex3);
        Repository repository3 = new memoryRepository(program3, "log3.txt");
        Controller controller3 = new Controller(repository3);

        Statement ex4 = new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()), new AssignmentStatement("a", new ValueExpression(new NumberValue(5))));
        ProgramState program4 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),ex4);
        Repository repository4 = new memoryRepository(program4, "log4.txt");
        Controller controller4 = new Controller(repository4);

        Statement ex5 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new AssignmentStatement("a", new ValueExpression(new NumberValue(5))));
        ProgramState program5 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),ex5);
        Repository repository5 = new memoryRepository(program5, "log5.txt");
        Controller controller5 = new Controller(repository5);

        Statement ex6 = new CompoundStatement(
                new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new AssignmentStatement("a", new ValueExpression(new BooleanValue(false)))),
                new PrintStatement(new LogicExpression('&', new VariableNameExpression("a"), new ValueExpression(new BooleanValue(true)))));
        ProgramState program6 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),ex6);
        Repository repository6 = new memoryRepository(program6, "log6.txt");
        Controller controller6 = new Controller(repository6);

        Statement ex7 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(
                        new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in")))),
                        new OpenRFile(new VariableNameExpression("varf"))), new VariableDeclarationStatement("varc", new IntegerType())),
                        new readFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                        new readFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                        new closeRFile(new VariableNameExpression("varf")));
        ProgramState program7 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),ex7);
        Repository repository7 = new memoryRepository(program7, "log7.txt");
        Controller controller7 = new Controller(repository7);

        Statement ex8 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(
                new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test2.in")))),
                new OpenRFile(new VariableNameExpression("varf"))), new VariableDeclarationStatement("varc", new IntegerType())),
                new readFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                new readFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                new closeRFile(new VariableNameExpression("varf")));
        ProgramState program8 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),ex8);
        Repository repository8 = new memoryRepository(program8, "log8.txt");
        Controller controller8 = new Controller(repository8);

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
        menu.show();
    }
}
