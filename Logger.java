enum LogLevel { TRACE, INFO, WARN, ERROR}

public class Logger {
    private static LogLevel level = LogLevel.INFO;
    private final String name;
    private Formatter formatter = new Formatter();

    public Logger(String name) {
        this.name = name;
    }

    public static void setLevel(LogLevel newLevel) {
        level = newLevel;
    }

    public void log(LogLevel msgLevel, String message){
        if (msgLevel.compareTo(level) < 0) {
            return;
        }
        System.out.println(formatter.formatIt(name, msgLevel, message));
    }
    public void trace(String message) { log(LogLevel.TRACE, message); }
    public void info(String message)  { log(LogLevel.INFO, message); }
    public void warn(String message)  { log(LogLevel.WARN, message); }
    public void error(String message) { log(LogLevel.ERROR, message); }


}