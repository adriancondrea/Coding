package GUI;

import Model.Expression.*;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntegerType;
import Model.Type.ReferenceType;
import Model.Type.StringType;
import Model.Value.BooleanValue;
import Model.Value.NumberValue;
import Model.Value.StringValue;

import java.io.File;
import java.util.ArrayList;

public class HardcodedPrograms {

    public static ArrayList<Statement> getHardcodedPrograms(){
        ArrayList<Statement> programs = new ArrayList<>();

        Statement ex1 = new CompoundStatement(new VariableDeclarationStatement("v",new IntegerType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new NumberValue(2))),
                        new PrintStatement(new VariableNameExpression("v"))));
        programs.add(ex1);

        Statement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntegerType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new NumberValue(2)),
                                new ArithmeticExpression('*',new ValueExpression(new NumberValue(3)), new ValueExpression(new NumberValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableNameExpression("a"),
                                        new ValueExpression(new NumberValue(1)))), new PrintStatement(new VariableNameExpression("b"))))));
        programs.add(ex2);

        Statement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(new ConditionalStatement(new VariableNameExpression("a"),new AssignmentStatement("v",
                                        new ValueExpression(new NumberValue(2))), new AssignmentStatement("v",
                                        new ValueExpression(new NumberValue(3)))), new PrintStatement(new VariableNameExpression("v"))))));
        programs.add(ex3);

        Statement ex4 = new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()), new AssignmentStatement("a", new ValueExpression(new NumberValue(5))));
        programs.add(ex4);

        Statement ex5 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new AssignmentStatement("a", new ValueExpression(new NumberValue(5))));
        programs.add(ex5);

        Statement ex6 = new CompoundStatement(
                new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()), new AssignmentStatement("a", new ValueExpression(new BooleanValue(false)))),
                new PrintStatement(new LogicExpression('&', new VariableNameExpression("a"), new ValueExpression(new BooleanValue(true)))));
        programs.add(ex6);

        Statement ex7 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(
                new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in")))),
                new OpenReadFileStatement(new VariableNameExpression("varf"))), new VariableDeclarationStatement("varc", new IntegerType())),
                new ReadNumberFromFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                new ReadNumberFromFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                new CloseReadFileStatement(new VariableNameExpression("varf")));
        programs.add(ex7);

        Statement ex8 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(
                new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test2.in")))),
                new OpenReadFileStatement(new VariableNameExpression("varf"))), new VariableDeclarationStatement("varc", new IntegerType())),
                new ReadNumberFromFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                new ReadNumberFromFile(new VariableNameExpression("varf"), "varc")), new PrintStatement(new VariableNameExpression("varc"))),
                new CloseReadFileStatement(new VariableNameExpression("varf")));
        programs.add(ex8);

        Statement ex9 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())), new HeapAllocationStatement("v", new ValueExpression(new NumberValue(20)))),
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType())))), new HeapAllocationStatement("a", new VariableNameExpression("v"))), new PrintStatement(new VariableNameExpression("v"))), new PrintStatement(new VariableNameExpression("a")));
        programs.add(ex9);

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
        programs.add(ex10);

        Statement ex11 = new CompoundStatement(
                new CompoundStatement(
                        new CompoundStatement(
                                new CompoundStatement(
                                        new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())),
                                        new HeapAllocationStatement("v", new ValueExpression(new NumberValue(20)))),
                                new PrintStatement(new ReadHeapExpression(new VariableNameExpression("v")))),
                        new WriteHeapStatement("v", new ValueExpression(new NumberValue(30)))),
                new PrintStatement(new ArithmeticExpression('+', new ReadHeapExpression(new VariableNameExpression("v")), new ValueExpression(new NumberValue(5)))));
        programs.add(ex11);

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
        programs.add(ex12);

        Statement ex13 = new CompoundStatement(
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntegerType()), new AssignmentStatement("v", new ValueExpression(new NumberValue(0)))),
                new WhileStatement(new RelationalExpression("<", new VariableNameExpression("v"), new ValueExpression(new NumberValue(5))), new CompoundStatement(new PrintStatement(new VariableNameExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('+', new VariableNameExpression("v"), new ValueExpression(new NumberValue(1)))))));
        programs.add(ex13);

        Statement ex14 = new CompoundStatement(new VariableDeclarationStatement("v",new IntegerType()),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntegerType())),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new NumberValue(10))),
                                new CompoundStatement(new HeapAllocationStatement("a",new ValueExpression(new NumberValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(
                                                new WriteHeapStatement("a",new ValueExpression(new NumberValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new NumberValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableNameExpression("v")),
                                                                new PrintStatement(new ReadHeapExpression(new VariableNameExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableNameExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableNameExpression("a")))))))));
        programs.add(ex14);

        Statement ex15 = new CompoundStatement(new VariableDeclarationStatement("a", new IntegerType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntegerType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new NumberValue(1))),
                                new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new NumberValue(100))),
                                        new CompoundStatement(
                                                new ForkStatement(new WhileStatement(new RelationalExpression("<", new VariableNameExpression("a"), new ValueExpression(new NumberValue(100))),
                                                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+', new VariableNameExpression("a"), new ValueExpression(new NumberValue(1)))),
                                                                new AssignmentStatement("b", new ArithmeticExpression('-', new VariableNameExpression("b"), new ValueExpression(new NumberValue(1))))))),
                                                new WhileStatement(new RelationalExpression("!=", new VariableNameExpression("a"), new ValueExpression(new NumberValue(0))), new NopStatement())
                                        )))));
        programs.add(ex15);

        return programs;
    }

    public static void deletePreviousLogs() {
        int numberOfPrograms = getHardcodedPrograms().size();
        for(int i = 1; i <= numberOfPrograms; i++) {
            File fileToDelete = new File("log" + i + ".txt");
            if(fileToDelete.delete())
                System.out.println("deleted log file " + fileToDelete.toString());
        }
    }
}
