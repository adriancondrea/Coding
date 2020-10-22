package com.company;

public class Main {

    public static void main(String[] args) {
        double sum = 0;
        for(String numbers : args){
            try {
                sum += Double.parseDouble(numbers);
            }
            //catch(Exception e)
            catch (NumberFormatException e) {
                System.out.println("Invalid command line arguments! Please provide integer numbers. Argument " + numbers + " is not a number");
                return;
            }
        }
        System.out.println(sum/args.length);
    }
}
