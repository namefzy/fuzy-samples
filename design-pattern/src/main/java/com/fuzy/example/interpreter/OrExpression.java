package com.fuzy.example.interpreter;

/**
 * @ClassName OrExpression
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/30 7:15
 * @Version 1.0.0
 */
public class OrExpression extends Expression{
    private Expression expression1 = null;
    private Expression expression2 = null;

    public OrExpression(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    public boolean interpret(String str) {
        return expression1.interpret(str) || expression2.interpret(str);
    }
}
