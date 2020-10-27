package Model.Statement;

import CustomException.CollectionException;
import CustomException.CustomException;
import CustomException.ExpressionException;
import CustomException.StatementException;
import Model.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState currentState) throws CustomException;
}
