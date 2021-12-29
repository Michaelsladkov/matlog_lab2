package analysis.axiomchecks;

import parsing.Expression;

public interface AxiomChecker {
    boolean check(Expression expression);
}
