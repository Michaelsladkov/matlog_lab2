package analysis;

import parsing.Expression;
import parsing.Lexer;
import parsing.Parser;
import parsing.Variable;

import java.util.*;

import static analysis.Options.*;

public class Analyzer {
    private static final Parser parser = new Parser();
    private static List<Expression> patterns = null;

    private static Variable[] createArrayFromMap(Map<String, Variable> map) {
        Variable[] out = new Variable[map.size()];
        List<Variable> ret = new ArrayList<>(map.size());
        ret.addAll(map.values());
        return ret.toArray(out);
    }

    private static void nextCombination(Variable[] array) {
        boolean value = array[0].getValue();
        array[0].setValue(!value);
        boolean carry = value;
        for (int i = 1; i < array.length; i++) {
            value = array[i].getValue();
            array[i].setValue(value^carry);
            carry = value && carry;
        }
    }

    public static Options analyse(Expression tree, Map<String, Variable> variables_map, Pair<Integer, Integer> cases) {
        Variable[] variables_array = createArrayFromMap(variables_map);
        int array_size = variables_array.length;
        boolean satisfied = tree.evaluate();
        boolean unsatisfied = !(tree.evaluate());
        boolean value;
        int true_cases = satisfied ? 1 : 0;
        int false_cases = unsatisfied ? 1 : 0;
        for (int c = 0; c < Math.pow(2, array_size) - 1; c++)
        {
            nextCombination(variables_array);
            value = tree.evaluate();
            satisfied &= value;
            unsatisfied &= !value;
            true_cases += value ? 1 : 0;
            false_cases += value ? 0 : 1;
        }
        cases.setFirst(true_cases);
        cases.setSecond(false_cases);
        if (unsatisfied) return UNSATISFIABLE;
        else if (satisfied) return VALID;
        else return SATISFIABLE;
    }

    public static boolean isAxiom(Expression toAnalyse, Map<String, Expression> parts) {
        initPatterns();
        for (Expression pattern : patterns) {
            if (pattern.match(toAnalyse, parts)) return true;
            parts.clear();
        }
        return false;
    }

    static void initPatterns() {
        if (patterns == null) {
            patterns = new LinkedList<>();
            patterns.add(createPattern("A->B->A"));
            patterns.add(createPattern("(A->B)->(A->B->C)->(A->C)"));
            patterns.add(createPattern("A->B->A&B"));
            patterns.add(createPattern("A&B->A"));
            patterns.add(createPattern("A&B->B"));
            patterns.add(createPattern("A->A|B"));
            patterns.add(createPattern("B->A|B"));
            patterns.add(createPattern("(A->C)->(B->C)->(A|B->C)"));
            patterns.add(createPattern("(A->B)->(A->!B)->!A"));
            patterns.add(createPattern("!!A->A"));
        }
    }

    public static Expression createPattern(String pattern) {
        return parser.parseExpression(Lexer.getLexemes(pattern));
    }
}
