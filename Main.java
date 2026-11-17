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
        System.out.println("\n");

        logger.info("Still using the default format");

        ParenthesesValidator validator = new ParenthesesValidator();
        validator.isValid("(())))");

        System.out.println("\n");

        System.out.println(customFormatter.formatItMessage("Hello %, welcome to %%%!", "Alex", "Paris"));

         logger.info("Found % items in % boxes", 5, 2);
         logger.warn("Battery at 100%%");
        System.out.println("\n");

        Display samsung = new Display("Samsung-Odyssey-G5", 2560, 1440, 93.0f);
        Display lg = new Display("LG-27GP850", 2560, 1440, 108.8f);
        Display dell = new Display("Dell-U2723QE", 3840, 2160, 163.0f);

        System.out.println(samsung.compareWithMonitor(lg));
        System.out.println("\n");
        System.out.println(lg.compareWithMonitor(dell));
        System.out.println("\n");
        System.out.println(dell.compareWithMonitor(samsung));
    }
}