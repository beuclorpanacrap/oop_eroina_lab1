public class Main {
     static void main(String[] args) {
        Logger logger = new Logger("Test");
        logger.info("Ratio is 100%n");

        Formatter sharedFormatter = new Formatter();
        ConsoleSink sharedSink = new ConsoleSink();

        Logger logger2 = new Logger("Test2", sharedFormatter, sharedSink);
        Logger logger3 = new Logger("Test3", sharedFormatter, sharedSink);

        sharedFormatter.setFormat("[%n] [%t] %m: %l");

        logger2.info("User entered: Hello %n World");
        logger3.error("User app crashed: Something went wrong");

        Formatter customFormatter = new Formatter();
        customFormatter.setFormat("[%l] %n: %m");

        Logger customLogger = new Logger("Custom", customFormatter, sharedSink);
        customLogger.warn("Check urgently!");

        logger.info("Still using the default format");

        ParenthesesValidator validator = new ParenthesesValidator();
        validator.isValid("(())))");

        System.out.println(customFormatter.formatItMessage("Hello %, welcome to %%%!", "Alex", "Paris"));

         logger.info("Found % items in % boxes", 5, 2);
         logger.warn("Battery at 100%%");
    }
}