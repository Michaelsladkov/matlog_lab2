import analysis.Analyzer;
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
            hypothesises.get(0).print();
            for (Expression e : hypothesises) {
                if (e.equals(hypothesises.get(0))) continue;
                System.out.print(",");
                e.print();
            }
        }
        System.out.print("|-");
        alphaHypothesis.print();
        System.out.println("->" + betaStr);
        Map<String, Expression> substitutions = new HashMap<>();
        substitutions.put("A", alphaHypothesis);
        while(scanner.hasNextLine()) {
            boolean axiomOrHypothesis = false;
            String line = scanner.nextLine();
            Expression d_i = parser.parseExpression(Lexer.getLexemes(Lexer.removeSpaces(line)));
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
            System.out.println(Analyzer.createPattern("(A->DI)->(A->DI->DI)->(A->DI)").useAsPattern(substitutions));
            System.out.println(Analyzer.createPattern("(A->DI->DI)->(A->DI)").useAsPattern(substitutions));
            System.out.println(Analyzer.createPattern("A->DI").useAsPattern(substitutions));
        }
    }
}
