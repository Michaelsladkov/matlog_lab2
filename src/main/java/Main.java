import analysis.Analyzer;
import analysis.Options;
import analysis.Pair;
import analysis.axiomchecks.AxiomChecker;
import analysis.axiomchecks.CheckerFactory;
import parsing.Expression;
import parsing.Lexer;
import parsing.Parser;
import parsing.Variable;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        Scanner scanner = new Scanner(System.in);
        CheckerFactory checkerFactory = new CheckerFactory();
        List<AxiomChecker> checkers = checkerFactory.getCheckers();

        String input = scanner.nextLine();
        String withoutSpaces = Lexer.removeSpaces(input);
        List<String> lexemes = Lexer.getLexemes(withoutSpaces);
        Expression tree = parser.parseExpression(lexemes);
        Map<String, Variable> vars = parser.getVariables();
        Pair<Integer, Integer> cases = new Pair<>();
        Options result = Analyzer.analyse(tree, vars, cases);
        tree.print();
        System.out.println();

        System.out.println("Is axiom: " + Analyzer.isAxiom(tree, checkers));

        switch (result) {
            case VALID:
                System.out.println("Valid");
                break;
            case UNSATISFIABLE:
                System.out.println("Unsatisfiable");
                break;
            case SATISFIABLE:
                System.out.println("Satisfiable and invalid, " + cases.getFirst() +
                    " true and " + cases.getSecond() + " false cases");
                break;
        }
    }
}
