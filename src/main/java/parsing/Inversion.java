package parsing;

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
    public boolean equals(Object obj) {
        return obj instanceof Inversion && ((Inversion)obj).operand.equals(this.operand);
    }
}
