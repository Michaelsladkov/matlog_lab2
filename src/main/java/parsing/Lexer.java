package parsing;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    static boolean inVariableAlphabet(char c) {
        return (c >= '0' && c <= '9') ||
                (c >= 'A' && c <= 'Z') ||
                (c == '\'');
    }
    public static String removeSpaces(String line) {
        char[] chars = line.toCharArray();
        StringBuilder out = new StringBuilder();
        for (char c : chars) {
            if (c != ' ' && c != '\t' && c != '\r') {
                out.append(c);
            }
        }
        return out.toString();
    }
    public static List<String> getLexemes(String line) {
        List<String> out = new ArrayList<>(line.length());
        char[] chars = line.toCharArray();
        for (int i = 0; i < line.length(); i++) {
            if (chars[i] == '(' || chars[i] == ')' || chars[i] == '!' || chars[i] == '|' || chars[i] == '&') {
                String a = String.valueOf(chars[i]);
                out.add(a);
            }
            if (chars[i] == '-') {
                i++;
                out.add("->");
            }
            if (inVariableAlphabet(chars[i])) {
               StringBuilder a = new StringBuilder();
               String b;
                while (i < line.length() && inVariableAlphabet(chars[i])) {
                    b = String.valueOf(chars[i]);
                    a.append(b);
                    i++;
                }
                out.add(a.toString());
                if (i == line.length()) break;
                i--;
            }
        }
        return out;
    }
}
