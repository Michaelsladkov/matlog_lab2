package analysis.axiomchecks;

import parsing.BinaryOperation;
import parsing.Expression;

public class FirstAxiomChecker implements AxiomChecker{
    @Override
    public boolean check(Expression expression) {
        if (!(expression instanceof BinaryOperation)) return false;
        BinaryOperation operation = (BinaryOperation) expression;
        if (!operation.getType().equals("->")) return false;
        Expression a = operation.getLeft();
        if (!(operation.getRight() instanceof BinaryOperation)) return false;
        BinaryOperation ab = (BinaryOperation) operation.getRight();
        if (!ab.getType().equals("->")) return false;
        Expression maybeA = ab.getRight();
        return a.equals(maybeA);
    }
}
