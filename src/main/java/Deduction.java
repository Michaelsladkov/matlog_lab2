
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
        LinkedList<Expression> hypothesises = new LinkedList<>();
        for (int i = 0; i < hypothesisesStr.length - 1; i++) {
            hypothesises.add(parser.parseExpression(Lexer.getLexemes(hypothesisesStr[i])));
        }
        if (hypothesises.size() > 0) {
            System.out.print(hypothesises.get(0));
            for (int i = 1; i < hypothesises.size(); i++) {
                System.out.print(",");
                System.out.print(hypothesises.get(i));
            }
        }
        Expression beta = parser.parseExpression(Lexer.getLexemes(betaStr));
        Map<String, Expression> substitutions = new HashMap<>();
        substitutions.put("A", alphaHypothesis);
        substitutions.put("B", beta);
        System.out.println("|-" + Analyzer.createPattern("A->B").useAsPattern(substitutions));
        List<Expression> formulas = new LinkedList<>();
        List<BinaryOperation> forMP = new LinkedList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Expression d_i = parser.parseExpression(Lexer.getLexemes(Lexer.removeSpaces(line)));
            formulas.add(d_i);
            if (d_i instanceof BinaryOperation) {
                BinaryOperation b = (BinaryOperation) d_i;
                if (b.getType().equals("->")) {
                    forMP.add(b);
                }
            }
            substitutions.put("DI", d_i);
            if (d_i.equals(alphaHypothesis)) {
                System.out.println(Analyzer.createPattern("A->A->A").useAsPattern(substitutions));

                System.out.println(Analyzer.createPattern("(A->(A->A))->(A->(A->A)->A)->(A->A)").
                        useAsPattern(substitutions));

                System.out.println(Analyzer.createPattern("(A->(A->A)->A)->(A->A)").useAsPattern(substitutions));

                System.out.println(Analyzer.createPattern("A->(A->A)->A").useAsPattern(substitutions));

                System.out.println(Analyzer.createPattern("A->A").useAsPattern(substitutions));
                continue;
            }
            boolean axiomOrHypothesis = false;
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
            Expression d_j = d_i;
            for (BinaryOperation b : forMP) {
                if (b.getRight().equals(d_i)) {
                    if (formulas.contains(b.getLeft())) {
                        d_j = b.getLeft();
                    }
                }
            }
            if (d_i != d_j) {
                substitutions.put("DJ", d_j);
                System.out.println(Analyzer.createPattern("(A->DJ)->(A->DJ->DI)->(A->DI)").useAsPattern(substitutions));
                System.out.println(Analyzer.createPattern("(A->DJ->DI)->(A->DI)").useAsPattern(substitutions));
                System.out.println(Analyzer.createPattern("A->DI").useAsPattern(substitutions));
                continue;
            }
            else throw new Error();
        }
    }
}
