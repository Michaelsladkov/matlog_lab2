package parsing;

import java.util.Map;

public class BinaryOperation implements Expression{
    Expression left;
    Expression right;
    String type;

    public BinaryOperation(){}

    public BinaryOperation(String type, Expression left, Expression right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public String getType() {
        return type;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public boolean evaluate() {
        if (type.equals("|")) {
            return left.evaluate() || right.evaluate();
        }
        if (type.equals("&")) {
            return left.evaluate() && right.evaluate();
        }
        if (type.equals("->")) {
            boolean l = left.evaluate();
            boolean r = right.evaluate();
            return !l || r;
        }
        return false;
    }

    @Override
    public void print() {
        System.out.print("(" + type + ",");
        left.print();
        System.out.print(",");
        right.print();
        System.out.print(")");
    }

    @Override
    public boolean match(Expression toMatch, Map<String, Expression> toCheck) {
        if (!(toMatch instanceof BinaryOperation)) return false;
        BinaryOperation binaryOperation = (BinaryOperation) toMatch;
        if (!type.equals(binaryOperation.type)) return false;
        return left.match(binaryOperation.left, toCheck) && right.match(binaryOperation.right, toCheck);
    }

    @Override
    public Expression useAsPattern(Map<String, Expression> substitutions) {
        return new BinaryOperation(type, left.useAsPattern(substitutions), right.useAsPattern(substitutions));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BinaryOperation)) return false;
        BinaryOperation other = (BinaryOperation) obj;
        return other.type.equals(type) && other.right.equals(right) && other.left.equals(left);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        if (type.equals("->")) {
            ret.append("(");
        }
        ret.append(left.toString());
        ret.append(type);
        ret.append(right.toString());
        if (type.equals("->")) {
            ret.append(")");
        }
        return ret.toString();
    }
}
