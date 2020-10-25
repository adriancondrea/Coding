package Model.Statement;

import CustomException.CustomException;
import Model.Expression.Expression;
import Model.ProgramState;

public class AssignmentStatement implements Statement{
    String id;
    Expression expression;

    AssignmentStatement(String id, Expression expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws CustomException {
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
