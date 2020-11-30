package com.fuzy.example.interpreter;

import java.util.StringTokenizer;

/**
 * @ClassName TerminalExpression
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/30 7:14
 * @Version 1.0.0
 */
public class TerminalExpression extends Expression{
    private String literal = null;

    public TerminalExpression(String str) {
        literal = str;
    }

    @Override
    public boolean interpret(String str) {
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            String test = st.nextToken();
            if (test.equals(literal)) {
                return true;
            }
        }
        return false;
    }
}
