package Model.Statement;

import CustomException.CustomException;
import Model.Expression.Expression;
import Model.ProgramState;

public class readFile implements Statement{
    Expression expression;
    @Override
    public ProgramState execute(ProgramState currentState) throws CustomException {
        return null;
    }
}
