package Tests;
import CustomException.ExpressionException;
import Model.AbstractDataTypes.DictionaryInterface;
import Model.AbstractDataTypes.MyDictionary;
import Model.Expression.*;
import Model.Value.*;
import org.junit.Test;
public class TestExpression {

    @Test
    public void testLess(){
        DictionaryInterface<String, Value> dictionary=new MyDictionary<>();
        Expression expression = new RelationalExpression("<", new ValueExpression(new NumberValue(5)), new ValueExpression(new NumberValue(8)));
        Value val = expression.evaluateExpression(dictionary);
        Boolean answer = ((BooleanValue)val).getValue();
        assert(answer);
    }

    @Test
    public void testGreater(){
        DictionaryInterface<String, Value> dictionary=new MyDictionary<>();
        Expression expression = new RelationalExpression(">", new ValueExpression(new NumberValue(5)), new ValueExpression(new NumberValue(8)));
        Value val = expression.evaluateExpression(dictionary);
        Boolean answer = ((BooleanValue)val).getValue();
        assert(!answer);
    }

    @Test
    public void testUnknownRelation(){
        DictionaryInterface<String, Value> dictionary=new MyDictionary<>();
        Expression expression = new RelationalExpression("??", new ValueExpression(new NumberValue(5)), new ValueExpression(new NumberValue(8)));
        try {
            Value val = expression.evaluateExpression(dictionary);
            assert(false);
        }catch (ExpressionException e){
            assert(e.getMessage().equals("unknown comparison relation!"));
        }
    }
    @Test
    public void testAddition(){
        DictionaryInterface<String, Value> dictionary=new MyDictionary<>();
        Expression expression=new ArithmeticExpression('+',new ValueExpression(new NumberValue(2)),new ValueExpression(new NumberValue(3)));
        try
        {
            Value val=expression.evaluateExpression(dictionary);
            int answer=((NumberValue)val).getValue();
            assert(answer==5);
        }catch (Exception ignored){}
    }

    @Test
    public void testAdditionBadRightOperand(){
        DictionaryInterface<String, Value> dictionary=new MyDictionary<>();
        Expression expression=new ArithmeticExpression('+',new ValueExpression(new NumberValue(2)),new ValueExpression(new BooleanValue(true)));
        try
        {
            expression.evaluateExpression(dictionary);
            assert(false);
        }catch (Exception e){ assert(e.getMessage().equals("Operand2 is not an integer!\n")); }
    }

    @Test
    public void testNonexistentOperation(){
        DictionaryInterface<String, Value> dictionary=new MyDictionary<>();
        Expression expression=new ArithmeticExpression('u',new ValueExpression(new NumberValue(2)),new ValueExpression(new NumberValue(3)));
        try
        {
            expression.evaluateExpression(dictionary);
            assert(false);
        }catch (Exception e){ assert(e.getMessage().equals("unknown operation!\n")); }
    }

    @Test
    public void testDivisionByZero(){
        DictionaryInterface<String, Value> dictionary=new MyDictionary<>();
        Expression expression=new ArithmeticExpression('/',new ValueExpression(new NumberValue(2)),new ValueExpression(new NumberValue(0)));
        try {
            expression.evaluateExpression(dictionary);
        }catch (Exception e) {assert(e.getMessage().equals("Division by zero!\n"));}
        }

    @Test
    public void testAdditionBadLeftOperand(){
        DictionaryInterface<String, Value> dictionary=new MyDictionary<>();
        Expression expression=new ArithmeticExpression('+',new ValueExpression(new TrueValue()),new ValueExpression(new NumberValue(3)));
        try
        {
            expression.evaluateExpression(dictionary);
            assert(false);
        }catch (Exception e){ assert(e.getMessage().equals("Operand1 is not an integer!\n")); }
    }

    @Test
    public void testAndOperation(){
        DictionaryInterface<String, Value> dictionary = new MyDictionary<>();
        Expression expression = new LogicExpression('&', new ValueExpression(new TrueValue()), new ValueExpression(new FalseValue()));
        try
        {
            Value val = expression.evaluateExpression(dictionary);
            assert !((BooleanValue) val).getValue();
        }catch (Exception e) { assert false; }
    }

    @Test
    public void testAndOperationBadLeftOperand(){
        DictionaryInterface<String, Value> dictionary = new MyDictionary<>();
        Expression expression = new LogicExpression('&', new ValueExpression(new NumberValue(4)), new ValueExpression(new FalseValue()));
        try
        {
            expression.evaluateExpression(dictionary);
            assert (false);
        }catch (Exception e) { assert e.getMessage().equals("Operand1 is not a boolean!\n"); }
    }

    @Test
    public void testAndOperationBadRightOperand(){
        DictionaryInterface<String, Value> dictionary = new MyDictionary<>();
        Expression expression = new LogicExpression('&', new ValueExpression(new BooleanValue(true)), new ValueExpression(new NumberValue(5)));
        try
        {
            expression.evaluateExpression(dictionary);
            assert (false);
        }catch (Exception e) { assert e.getMessage().equals("Operand2 is not a boolean!\n"); }
    }

    @Test
    public void testUnknownOperation(){
        DictionaryInterface<String, Value> dictionary = new MyDictionary<>();
        Expression expression = new LogicExpression('u', new ValueExpression(new BooleanValue(true)), new ValueExpression(new TrueValue()));
        try
        {
            expression.evaluateExpression(dictionary);
            assert (false);
        }catch (Exception e) { assert e.getMessage().equals("Operation unknown!\n"); }
    }

    @Test
    public void testValueExpression(){
        DictionaryInterface<String, Value> dictionary = new MyDictionary<>();
        Expression expression = new ValueExpression(new NumberValue(42));
        Value val = expression.evaluateExpression(dictionary);
        assert (((NumberValue) val).getValue() == 42);
    }

    @Test
    public void testVariableNameExpression(){
        DictionaryInterface<String, Value> dictionary = new MyDictionary<>();
        dictionary.add("testVar", new NumberValue(42));
        Expression expression = new VariableNameExpression("testVar");
        Value val = expression.evaluateExpression(dictionary);
        assert (((NumberValue) val).getValue() == 42);
    }
}
