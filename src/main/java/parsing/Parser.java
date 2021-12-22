package parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private final Map<String, Variable> variables;

    public Parser() {
        variables = new HashMap<>();
    }

    public Expression parseExpression(List<String> lexemes) {
        lexemes = eliminateExtraBrackets(lexemes);
        if (lexemes.size() == 1) {
            Variable ret = null;
            if (variables.get(lexemes.get(0)) == null) {
                ret = new Variable(false, lexemes.get(0));
                variables.put(lexemes.get(0), ret);
            }
            else {
                ret = variables.get(lexemes.get(0));
            }
            return ret;
        }
        int lowest_index = findLowestPriority(lexemes);
        List<String> left = new ArrayList<>();
        for (int i = 0; i < lowest_index; i++) {
            left.add(lexemes.get(i));
        }
        List<String> right = new ArrayList<>();
        for (int i = lowest_index + 1; i < lexemes.size(); i++) {
            right.add(lexemes.get(i));
        }
        if (lexemes.get(lowest_index).equals("!")) {
            Inversion ret = new Inversion();
            ret.operand = parseExpression(right);
            return ret;
        }
        String opType = lexemes.get(lowest_index);
        BinaryOperation ret = new BinaryOperation();
        ret.left = parseExpression(left);
        ret.right = parseExpression(right);
        ret.type = opType;
        return ret;
    }

    private static boolean checkBrackets (List<String> line) {
        int level = 0;
        for (int i = 0; i < line.size(); i++) {
            if(line.get(i).equals("(")) level ++;
            if (line.get(i).equals(")")) level --;
            if (level < 0) return false;
        }
        return level == 0;
    }

    private static List<String> eliminateExtraBrackets(List<String> lex) {
        if (!lex.get(0).equals("(")) return lex;
        List<String> newList = new ArrayList<>(lex);
        newList.remove(0);
        newList.remove(newList.size() - 1);
        if (checkBrackets(newList)) return eliminateExtraBrackets(newList);
        else return lex;
    }

    private static int findLowestPriority(List<String> lexemes) {
        int lowest_priority = 1000000;
        int priority;
        char brackets_level = 0;
        int i = 0;
        int res = 0;
        for (String lex: lexemes) {
            if (lex.equals("(")){
                brackets_level+=10;
                i++;
                continue;
            }
            if (lex.equals(")")) {
                brackets_level-=10;
                i++;
                continue;
            }
            if (lex.equals("!")) {
                priority = 4 + brackets_level;
            }
            else if (lex.equals("&")) {
                priority = 3 + brackets_level;
            }
            else if (lex.equals("|")) {
                priority = 2 + brackets_level;
            }
            else if (lex.equals("->")) {
                priority = 1 + brackets_level;
            }
            else {
                i++;
                continue;
            }
            if (priority < lowest_priority) {
                lowest_priority = priority;
                res = i;
            }
            if (priority == lowest_priority) {
                if (!lex.equals("->") && !lex.equals("!")) {
                    lowest_priority = priority;
                    res = i;
                }
            }
            i++;
        }
        return res;
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }
}
