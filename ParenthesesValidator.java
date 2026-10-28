public class ParenthesesValidator {
    public boolean isValid(String input) {
        int open = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '(') {
                open++;
            } else if (c == ')') {
                if (open == 0) {
                    return false;
                }
                open--;
            }
        }
        return open == 0;
    }
}