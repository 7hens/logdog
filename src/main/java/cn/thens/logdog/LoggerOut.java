package cn.thens.logdog;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

final class LoggerOut implements Logger<String> {
    private static final String[] priorityTags = new String[]{"", "", "V", "D", "I", "W", "E", "A"};
    private final SimpleDateFormat dateFormat;
    private final PrintStream printer;

    LoggerOut(OutputStream printer) {
        if (printer instanceof PrintStream) {
            this.printer = (PrintStream) printer;
        } else {
            this.printer = new PrintStream(printer);
        }
        this.dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.getDefault());
    }

    @Override
    public void log(int priority, String tag, String message) {
        String time = dateFormat.format(new Date());
        printer.println(time + " " + getPriorityTag(priority) + ",," + tag + ": " + message);
    }

    private static String getPriorityTag(int priority) {
        return priority >= 0 && priority < priorityTags.length ? priorityTags[priority] : "X";
    }
}
