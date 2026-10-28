public class Main {
    public static void main(String[] args) {
        Logger logger = new Logger("Test");

        logger.log(LogLevel.TRACE, "trace Message");
        logger.log(LogLevel.INFO, "info message");
        logger.log(LogLevel.WARN, "warn message");
        logger.log(LogLevel.ERROR, "error message");

        Logger.setFormat("%l - %n - %m");
        logger.error("Invalid input");

        Logger.setFormat("[%t] %m 100%");
        logger.info("Valid input");

        Logger.setFormat("%m %x %m");
        logger.info("echo");

        Logger.setFormat("[%t] [%l] %n %m"); // default

        ParenthesesValidator validator = new ParenthesesValidator();
        System.out.println(validator.isValid("()"));
        System.out.println(validator.isValid(")((()))"));
        System.out.println(validator.isValid("("));
        System.out.println(validator.isValid("(())((()())())"));
    }
}