package View;

import Controller.Controller;
import Model.Expression.ArithmeticExpression;
import Model.Expression.LogicExpression;
import Model.Expression.ValueExpression;
import Model.Expression.VariableNameExpression;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntegerType;
import Model.Value.BooleanValue;
import Model.Value.NumberValue;
import Repository.Repository;
import Repository.memoryRepository;

import java.util.Scanner;


public class View {


    Controller controller;

    public void hardcodedMenu() {
        //hardcoded menu options for first assignment
        //prints options and lets the user choose which program to load
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

        System.out.println("ex1:");
        System.out.println("int v;\n v=2;\nPrint(v)\n");

        System.out.println("ex2:");
        System.out.println("int a;\nint b;\na=2+3*5;\nb=a+1;\nPrint(b)\n");

        System.out.println("ex3:");
        System.out.println("bool a;\nint v;\na=true;\n(If a Then v=2 Else v=3);\nPrint(v)\n");

        System.out.println("ex4:");
        System.out.println("int a;\na=5\n");

        System.out.println("ex5:");
        System.out.println("Exception testing - assign an integer value to a bool variable\n");

        System.out.println("ex6");
        System.out.println("Logical operator testing\nbool a;\na = false;\nprint(a&true);\n");

        System.out.println("command =");

        Scanner console = new Scanner(System.in);
        int choice = console.nextInt();
        switch (choice) {
            case 1 -> controller.createState(ex1);
            case 2 -> controller.createState(ex2);
            case 3 -> controller.createState(ex3);
            case 4 -> controller.createState(ex4);
            case 5 -> controller.createState(ex5);
            case 6 -> controller.createState(ex6);
        }
    }

    public View() {
        Repository repository = new memoryRepository();
        this.controller = new Controller(repository);
        controller.setDisplayState(true);
        hardcodedMenu();
        try {
            controller.allStepExecution();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
