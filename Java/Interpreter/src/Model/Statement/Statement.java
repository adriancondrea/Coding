package Model.Statement;

import CustomException.CustomException;
import Model.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState currentState) throws CustomException;
}
