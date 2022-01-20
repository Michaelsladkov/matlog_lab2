package parsing;

import java.util.Map;

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
    public boolean match(Expression toMatch, Map<String, Expression> toCheck) {
        if (!toCheck.containsKey(name)) {
            toCheck.put(name, toMatch);
            return true;
        }
        return toCheck.get(name).equals(toMatch);
    }

    @Override
    public Expression useAsPattern(Map<String, Expression> substitutions) {
        if (!substitutions.containsKey(name)) return new Variable(false, name);
        return substitutions.get(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Variable)) return false;
        Variable other = (Variable) obj;
        return other.getName().equals(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}