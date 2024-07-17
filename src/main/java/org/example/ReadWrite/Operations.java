package org.example.ReadWrite;


public enum Operations {
    AND("*"),
    OR("+"),
    GREATER(">"),
    GREATEREQUAL(">="),
    LESS("<"),
    LESSEQUAL("<="),
    EQUAL("="),
    PARENTHESESSTART("("),
    PARENTHESESEND(")");

    private String symbol;

    Operations(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static int getPrecedence(String symbol) {
        switch (symbol) {
            case "*":
                return 2;
            case "+":
                return 1;
            case ">":
            case ">=":
            case "<":
            case "<=":
            case "=":
                return 3;
            case "(":
            case ")":
                return -1;
        }
        return 0;
    }

    public static Boolean containOperation(String s) {
        for (Operations op : Operations.values()) {
            if (op.getSymbol().equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static Operations getOperationBySymbol(String symbol) {
        for (Operations op : Operations.values()) {
            if (op.getSymbol().equals(symbol)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Invalid operation symbol: " + symbol);
    }

}
