package Repository;

import CustomException.RepositoryException;
import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class memoryRepository implements Repository{
    LinkedList<ProgramState> programStates;
    String logFilePath = "log.txt";

    public memoryRepository(){
        programStates = new LinkedList<ProgramState>();
    }

    public memoryRepository(String logFilePath){
        programStates = new LinkedList<ProgramState>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void addState(ProgramState newState) {
        programStates.addLast(newState);
    }

    @Override
    public ProgramState getCurrentProgram() {
        ProgramState currentProgram = programStates.getFirst();
        programStates.removeFirst();
        return currentProgram;
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e) {
            throw new RepositoryException("Log program state execution failed!");
        }
        logFile.println(programState);
        logFile.close();
    }
}
