package parsing;

import java.util.Map;

public interface Expression {
    boolean evaluate();
    void print();
    boolean match(Expression toMatch, Map<String, Expression> toCheck);
}
