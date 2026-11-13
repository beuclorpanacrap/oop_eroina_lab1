import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Formatter {

    private String format = "[%t] [%l] %n: %m";

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final Random random = new Random();

    private static final String[] traceMessages =  {"Howdy", "Nothing important here", "Quite windy outside"};

    private static final String[] infoMessages = {"Operation completed", "Everything looks good", "Here is your result"};

    private static final String[] warnMessages = {"Something is off", "Double check", "This looks wrong"};

    private static final String[] errorMessages = {"URGENT", "Something went wrong", "You messed up"};

    private String randomMessage(LogLevel msgLevel){
        return switch (msgLevel){
            case TRACE -> traceMessages[random.nextInt(traceMessages.length)];
            case INFO -> infoMessages[random.nextInt(infoMessages.length)];
            case WARN  -> warnMessages[random.nextInt(warnMessages.length)];
            case ERROR -> errorMessages[random.nextInt(errorMessages.length)];
        };
    }
    public String formatIt(String name, LogLevel msgLevel, String message){
    StringBuilder result = new StringBuilder();

            for (int i = 0; i < format.length(); i++){
                char c = format.charAt(i);

                if (c == '%' && i + 1 < format.length()){
                    String replacement = placeholderValue(format.charAt(i + 1), name, msgLevel, message);
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

    private String placeholderValue(char placeholder, String name, LogLevel msgLevel, String message) {
        return switch (placeholder) {
            case 't' -> LocalTime.now().format(TIME_FORMAT);
            case 'm' -> message;
            case 'n' -> name;
            case 'l' -> msgLevel.name();
            case 'r' -> randomMessage(msgLevel);
            default -> null;
        };
    }
    public void setFormat(String newFormat){
        format = newFormat;
    }
}
