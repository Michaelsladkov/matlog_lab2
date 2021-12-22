package parsing;

public class Variable implements Expression {
    private boolean value;
    private String name;

    public Variable(boolean value, String name) {
        this.name = name;
        this.value = value;
    }

    public void setValue(boolean newValue) {
        value = newValue;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean evaluate() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public void print() {
        System.out.print(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Variable)) return false;
        Variable other = (Variable) obj;
        return other.getName().equals(this.name);
    }
}