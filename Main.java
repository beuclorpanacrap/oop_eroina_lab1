public class Main {
    public static void main(String[] args) {
        Logger logger = new Logger("Test");
        Logger logger2 = new Logger("Test2");

        logger.info("Ratio is 100%n");

        Formatter formatter = new Formatter();

        System.out.println(formatter.formatIt("Test",LogLevel.ERROR, "Something went wrong!"));

        Formatter.setFormat("[%n] [%t] %m: %l");

        Formatter formatter2 = new Formatter();

        System.out.println(formatter2.formatIt("Test2",LogLevel.WARN, "Check urgently!"));

        System.out.println(formatter.formatIt("Test",LogLevel.ERROR, "Something went wrong!"));

        logger2.info("User entered: Hello %n World");
    }
}