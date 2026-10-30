import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

enum LogLevel { TRACE, INFO, WARN, ERROR}

public class Logger {
    private static LogLevel level = LogLevel.INFO;
    private static String format = "[%t] [%l] %n: %m";

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    //self - convention for variables marked as static final i.e constants

    private static final Random random = new Random();

    private final String name;

    private String placeholderValue(char placeholder, LogLevel msgLevel, String message){
        return switch (placeholder) {
            case 't' -> LocalTime.now().format(TIME_FORMAT);
            case 'm' -> message;
            case 'n' -> name;
            case 'l' -> msgLevel.name();
            case 'r' -> randomMessage(msgLevel);
            default -> null;
        };
    }
    private String formatMessage(LogLevel msgLevel, String message){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < format.length(); i++){
            char c = format.charAt(i);

            if (c == '%' && i + 1 < format.length()){
                String replacement = placeholderValue(format.charAt(i + 1), msgLevel, message);
                if (replacement != null) {
                    result.append(replacement);
                    i++;
                    continue;
                }
            }
            result.append(c);
        }
        return result.toString();
    }
// Java strings can't be modified
    private String randomMessage(LogLevel msgLevel){
        String[] options = switch (msgLevel){
            case TRACE -> new String[] {"Howdy", "Nothing important here", "Quite windy outside"};
            case INFO  -> new String[] {"Operation completed", "Everything looks good", "Here is your result"};
            case WARN  -> new String[] {"Something is off", "Double check", "This looks wrong"};
            case ERROR -> new String[] {"URGENT", "Something went wrong", "You messed up"};
        };
        return options[random.nextInt(options.length)];
    }

    public Logger(String name) {
        this.name = name;
    }
    //self - these are "setters"
    public static void setLevel(LogLevel newLevel) {
        level = newLevel;
    }
    public static void setFormat(String newFormat){
        format = newFormat;
    }
    public void log(LogLevel msgLevel, String message){
        if (msgLevel.compareTo(level) < 0) {
            return;
        }
        System.out.println(formatMessage(msgLevel, message));
    }
    public void trace(String message) { log(LogLevel.TRACE, message); }
    public void info(String message)  { log(LogLevel.INFO, message); }
    public void warn(String message)  { log(LogLevel.WARN, message); }
    public void error(String message) { log(LogLevel.ERROR, message); }


}