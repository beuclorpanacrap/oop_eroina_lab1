enum LogLevel { TRACE, INFO, WARN, ERROR}

public class Logger {

    private static LogLevel level = LogLevel.INFO;
    private final String name;
    private final Formatter formatter;
    private final ConsoleSink consoleSink;

    public Logger(String name) {
        this.name = name;
        this.formatter = new Formatter();
        this.consoleSink = new ConsoleSink();
    }
    public Logger(String name, Formatter formatter, ConsoleSink consoleSink){
        this.name = name;
        this.formatter = formatter;
        this.consoleSink = consoleSink;
    }

    public static void setLevel(LogLevel newLevel) {
        level = newLevel;
    }

    public void log(LogLevel msgLevel, String message, Object... args) {
        if (msgLevel.compareTo(level) < 0) return;
        consoleSink.write(formatter.formatIt(name, msgLevel, formatter.formatItMessage(message, args)));
    }


    public void trace(String message, Object ... args) { log(LogLevel.TRACE, message, args); }
    public void info(String message, Object ... args)  { log(LogLevel.INFO, message, args); }
    public void warn(String message, Object ... args)  { log(LogLevel.WARN, message, args); }
    public void error(String message, Object ... args) { log(LogLevel.ERROR, message, args); }


}