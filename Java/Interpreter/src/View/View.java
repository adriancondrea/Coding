package View;

import Controller.Controller;
import CustomException.CustomException;
import Repository.*;

import java.util.Scanner;

public class View {
    Controller controller;

    public void printMenu() {
        System.out.println("ex1:");
        System.out.println("int v;\n v=2;\nPrint(v)\n");

        System.out.println("ex2:");
        System.out.println("int a;\nint b;\na=2+3*5;\nb=a+1;\nPrint(b)\n");

        System.out.println("ex3:");
        System.out.println("bool a;\nint v;\na=true;\n(If a Then v=2 Else v=3);\nPrint(v)\n");

        System.out.println("ex4:");
        System.out.println("int a;\na=5\n");

        System.out.println("ex5:");
        System.out.println("test\n");

        System.out.println("command =");

        Scanner console = new Scanner(System.in);
        int choice = console.nextInt();
        controller.newProgram(choice);
    }

    public View() {
        Repository repository = new memoryRepository();
        this.controller = new Controller(repository);
        controller.setDisplayState(true);
        printMenu();
        try {
            controller.allStepExecution();
        } catch (CustomException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
