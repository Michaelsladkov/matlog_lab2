package parsing;

public class BinaryOperation implements Expression{
    Expression left;
    Expression right;
    String type;

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
    public boolean equals(Object obj) {
        if (!(obj instanceof BinaryOperation)) return false;
        BinaryOperation other = (BinaryOperation) obj;
        return other.type.equals(type) && other.right.equals(right) && other.left.equals(left);
    }
}
