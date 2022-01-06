import analysis.Analyzer;
import parsing.BinaryOperation;
import parsing.Expression;
import parsing.Lexer;
import parsing.Parser;

import java.util.*;

public class Deduction {
    public static void main(String[] args){
        Parser parser = new Parser();
        Scanner scanner = new Scanner(System.in);

        String firstLine = Lexer.removeSpaces(scanner.nextLine());
        if (!firstLine.contains("|-")) return;
        String context = firstLine.split("\\|-")[0];
        String betaStr = firstLine.split("\\|-")[1];
        String[] hypothesisesStr = context.split(",");
        String alphaHypothesisStr = hypothesisesStr[hypothesisesStr.length-1];
        Expression alphaHypothesis = parser.parseExpression(Lexer.getLexemes(alphaHypothesisStr));
        List<Expression> hypothesises = new LinkedList<>();
        for (int i = 0; i < hypothesisesStr.length - 1; i++) {
            hypothesises.add(parser.parseExpression(Lexer.getLexemes(hypothesisesStr[i])));
        }
        if (hypothesises.size() > 0) {
            System.out.print(hypothesises.get(0));
            for (Expression e : hypothesises) {
                if (e.equals(hypothesises.get(0))) continue;
                System.out.print(",");
                System.out.print(e);
            }
        }
        System.out.print("|-");
        alphaHypothesis.print();
        System.out.println("->" + betaStr);
        Map<String, Expression> substitutions = new HashMap<>();
        substitutions.put("A", alphaHypothesis);
        List<Expression> formulas = new LinkedList<>();
        while(scanner.hasNextLine()) {
            boolean axiomOrHypothesis = false;
            String line = scanner.nextLine();
            Expression d_i = parser.parseExpression(Lexer.getLexemes(Lexer.removeSpaces(line)));
            formulas.add(d_i);
            substitutions.put("DI", d_i);
            for (Expression h : hypothesises) {
                if (h.equals(d_i)) {
                    axiomOrHypothesis = true;
                    break;
                }
            }
            if (!axiomOrHypothesis) {
                axiomOrHypothesis = Analyzer.isAxiom(d_i, new HashMap<>());
            }
            if (axiomOrHypothesis) {
                System.out.println(d_i);

                System.out.println(Analyzer.createPattern("DI->A->DI").useAsPattern(substitutions));

                System.out.println(Analyzer.createPattern("A->DI").useAsPattern(substitutions));
                continue;
            }
            if (d_i.equals(alphaHypothesis)) {
                System.out.println(Analyzer.createPattern("A->A->A").useAsPattern(substitutions));

                System.out.println(Analyzer.createPattern("A->((A->A)->A)").useAsPattern(substitutions));

                System.out.println(Analyzer.createPattern("(A->(A->A))->(A->(A->A)->A)->(A->A)").
                        useAsPattern(substitutions));

                System.out.println(Analyzer.createPattern("(A->(A->A)->A)->(A->A)").useAsPattern(substitutions));

                System.out.println(Analyzer.createPattern("A->A").useAsPattern(substitutions));
                continue;
            }
            Expression d_j = d_i;
            for (Expression f : formulas) {
                if (f instanceof BinaryOperation) {
                    BinaryOperation b = (BinaryOperation) f;
                    if (b.getType().equals("->")) {
                        if (b.getRight().equals(d_i)) {
                            d_j = b.getLeft();
                        }
                    }
                }
            }
            substitutions.put("DJ", d_j);
            System.out.println(Analyzer.createPattern("(A->DJ)->(A->DJ->DI)->(A->DI)").useAsPattern(substitutions));
            System.out.println(Analyzer.createPattern("(A->DJ->DI)->(A->DI)").useAsPattern(substitutions));
            System.out.println(Analyzer.createPattern("A->DI").useAsPattern(substitutions));
        }
    }
}
