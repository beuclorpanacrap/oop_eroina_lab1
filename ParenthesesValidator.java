public class ParenthesesValidator {

    private final Logger logger = new Logger("Parentheses");

    private boolean report(String input, boolean valid){
        logger.info("Validation result: " + valid);
        if (!valid) {
            logger.error("Invalid input \"" + input + "\": the parentheses are not balanced");
        }
        return valid;
    }

    public boolean isValid(String input) {
        int open = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '(') {
                open++;
                logger.trace("Opening parenthesis found at index " + i + ", need: " + open + " closing parenthesis/es");
            } else if (c == ')') {
                if (open == 0) {
                    logger.warn("Closing parenthesis at index " + i + " has no open parenthesis pair");
                    return report(input, false);
                }
                open--;
                logger.info("Matching pair found, closed at index " + i + ", need to close: " + open + " open parenthesis/es");
            }
        }
        return report(input, open == 0);
    }
}