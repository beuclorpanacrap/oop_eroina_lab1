# Laboratory Report. Logger and Parentheses Validator.

**Student:** Fundureanu Ion  
**Programming language:** Java

## 1. Program Structure

The `Logger` class handles absolutely everything related to displaying data. It takes up message formatting, timestamps, logging levels, and decides which messages should be printed and when.

The `ParenthesesValidator` class on the other hand, is more of a logical class, it's only role is to check whether parentheses in a given string are balanced.

The two classes interact with each other via the Validator calling a `Logger` object in order to share its findings.

```java
private final Logger logger = new Logger("Parentheses");
```

## 2. Classes and Objects

A class in my implementation is just like in any other OOP implementation. It defines the data and behavior of objects, along with a handful of helper methods that handle smaller tasks.

As an example of instantiation, in `Main`, I create these objects:

```java
Logger logger = new Logger("Test");
ParenthesesValidator validator = new ParenthesesValidator();
```

The Logger object stores its name, while the ParenthesesValidator has its own Logger object that it uses to display messages during validation.

The logging level and format are shared between all loggers as they are static.

## 3. Static and Instance Members

In `Logger`, the level, format, TIME_FORMAT, and random fields are static. I.E. they belong to the class.

To understand the `"Why"` behind that decision, just consider the opposite: I want all objects to have the same configuration, as a result - I will make sure that every single object gets it's instance class variable assigned.

Very `counterintuitive`, isn't it? That's why my variables are static, because I only want one copy of each variable.

```java
private static LogLevel level = LogLevel.INFO;
private static String format = "[%t] [%l] %n: %m";
```

The `name` field is an instance member as I want each logger to have a different name. (or at least have the possibility to assign them different names)

```java
private final String name;
```

For example, when I call:

```java
Logger.setLevel(LogLevel.ERROR);
```

The logging level changes for every logger, because as aforementioned, `level` is static. As a result, `ALL` loggers will only print messages with severity `ERROR` until the level changes again. (Which happens a few times in `main()`)

## 4. Methods and Responsibilities

The `log()` method checks whether a message should be displayed based on its severity level. If the message is below the configured level, it is ignored. Otherwise, the method formats and prints it.

```java
if (msgLevel.compareTo(level) < 0) {
    return;
}
System.out.println(formatMessage(msgLevel, message));
```

The validator uses `Logger` instead of printing messages directly because we already have a class designed for logging, and that is `Logger`. Giving the validator the capacity to print logs doesn't make sense from a Class/OOP perspective, and nor does it make sense from an Engineering perspective, since you're just adding more work to your stack, work that doesn't move the needle. 

An example of communication between objects is:

```java
logger.warn("Closing parenthesis at index " + i + " has no open parenthesis pair");
```

Here, the validator calls the `warn()` method of its logger object.

## 5. Testing and Correctness

One valid input is `"(())"` and one invalid input is `"(()"`.

The program uses a counter called `open`. Every opening parenthesis increases it, while every closing parenthesis decreases it.

If a closing parenthesis appears when `open` is already zero, the input is immediately considered invalid. Otherwise, after checking the entire string, the program returns:

```java
return report(input, open == 0);
```

This means the input is valid only if every opening parenthesis has been matched.

One test that helped me find an error was running `Main` with the default format. The output was `20:29:34INFOTestinfo message`, so my placeholders were being replaced correctly, but all of the brackets, spaces, and colons were missing... The cause was a missing line in `formatMessage`: `result.append(c);`. After I added it, the output became `[20:32:18] [INFO] Test: info message`.
(Big mistake looking back)
## 6. Reflection

The most important OOP concept I learned & used, by far, was `the separation of responsibilities between classes`.

This lab also helped me fully comprehend the details & differences of static vs instance members, and the general philosophy behind their usage.

With more time, I'd probably integrate output operations. I could also separate different logs in different file categories such as: (main/network/stats/render/old).