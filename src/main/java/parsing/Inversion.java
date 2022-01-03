package parsing;

import java.util.Map;

public class Inversion implements Expression{
    Expression operand;
    @Override
    public boolean evaluate() {
        return ! (operand .evaluate());
    }

    @Override
    public void print() {
        System.out.print("(!");
        operand.print();
        System.out.print(")");
    }

    @Override
    public boolean match(Expression toMatch, Map<String, Expression> toCheck) {
        if (!(toMatch instanceof Inversion)) return false;
        Inversion inversion = (Inversion) toMatch;
        return operand.match(inversion.operand, toCheck);
    }

    @Override
    public String toString() {
        return "!" + operand.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Inversion && ((Inversion)obj).operand.equals(this.operand);
    }
}
