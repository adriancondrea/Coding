package Model.Statement;

import CustomException.CustomException;
import Model.ProgramState;
import Model.Type.Type;

import java.util.Map;

public interface Statement {
    ProgramState execute(ProgramState currentState) throws CustomException;
}
