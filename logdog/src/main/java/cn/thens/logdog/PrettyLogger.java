package cn.thens.logdog;

import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 7hens
 */
public class PrettyLogger implements Logger<Object> {
    private static final int CHUNK_SIZE = 1024;
    private final Logger<String> lineLogger;

    public PrettyLogger(Logger<String> lineLogger) {
        this.lineLogger = lineLogger;
    }

    @Override
    public void log(int priority, String tag, Object message) {
        Style style = getStyle(priority, tag);
        lineLogger.log(priority, tag, style.top);
        {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int methodOffset = getMethodOffset(priority, tag);
            int methodCount = getMethodCount(priority, tag);
            int stackOffset = getStackOffset(stackTrace) + methodOffset;
            int headerLineCount = Math.min(methodCount, stackTrace.length - 1 - stackOffset);
            for (int i = 0; i < headerLineCount; i++) {
                int stackIndex = i + stackOffset;
                String stackInfo = getStackInfo(stackTrace[stackIndex], i == 0);
                String indent = i == 0 ? "" : "  ";
                lineLogger.log(priority, tag, style.middle + indent + stackInfo);
            }
            if (headerLineCount > 0) {
                lineLogger.log(priority, tag, style.divider);
            }
        }
        {
            String messageText = getMessageText(message);
            if (messageText == null) messageText = "null";
            String[] lines = messageText.split("\n");
            for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
                String line = lines[lineIndex];
                if (line.isEmpty()) {
                    if (lineIndex != lines.length - 1) {
                        lineLogger.log(priority, tag, style.middle);
                    }
                    break;
                }
                int lineLength = line.length();
                int chunkSize = CHUNK_SIZE;
                int pageSize = (lineLength - 1) / chunkSize + 1;
                for (int pageIndex = 0; pageIndex < pageSize; pageIndex++) {
                    int offset = pageIndex * chunkSize;
                    int count = Math.min(lineLength - offset, chunkSize);
                    String chunk = line.substring(offset, offset + count);
                    lineLogger.log(priority, tag, pageIndex == 0 ? style.middle + chunk : chunk);
                }
            }
        }
        lineLogger.log(priority, tag, style.bottom);
    }

    protected Style getStyle(int priority, String tag) {
        return priority > Log.DEBUG ? Style.DOUBLE : Style.SINGLE;
    }

    protected int getMethodCount(int priority, String tag) {
        return 1;
    }

    protected int getMethodOffset(int priority, String tag) {
        return 0;
    }

    protected String getMessageText(Object message) {
        return LogMessages.of(message);
    }

    private String getStackInfo(StackTraceElement element, boolean putsThreadInfo) {
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1);

        StringBuilder result = new StringBuilder()
                .append(className).append(".").append(element.getMethodName())
                .append("(").append(element.getFileName()).append(":")
                .append(element.getLineNumber()).append(")");
        if (putsThreadInfo) {
            result.append(" on thread: ").append(Thread.currentThread().getName());
        }
        return result.toString();
    }

    private int getStackOffset(StackTraceElement[] stackTrace) {
        boolean isInOffsetClass = false;
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement traceElement = stackTrace[i];
            try {
                Class<?> cls = Class.forName(traceElement.getClassName());
                if (cls.isAnnotationPresent(Ignored.class)) {
                    isInOffsetClass = true;
                } else if (isInOffsetClass) {
                    return i;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Ignored {
    }

    public static class Style {
        private static final String EMPTY = "";
        private static final String SINGLE_PART = "────────────────────────────";
        private static final String DASHED_PART = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
        private static final String DOUBLE_PART = "════════════════════════════";
        private static final String SINGLE_LINE = SINGLE_PART + SINGLE_PART + SINGLE_PART + SINGLE_PART;
        private static final String DASHED_LINE = DASHED_PART + DASHED_PART + DASHED_PART + DASHED_PART;
        private static final String DOUBLE_LINE = DOUBLE_PART + DOUBLE_PART + DOUBLE_PART + DOUBLE_PART;
        private static final String SINGLE_TOP = "┌" + SINGLE_LINE;
        private static final String SINGLE_DIV = "├" + DASHED_LINE;
        private static final String SINGLE_MID = "│ ";
        private static final String SINGLE_BOT = "└" + SINGLE_LINE;
        private static final String DOUBLE_TOP = "╔" + DOUBLE_LINE;
        private static final String DOUBLE_DIV = "╟" + DASHED_LINE;
        private static final String DOUBLE_MID = "║ ";
        private static final String DOUBLE_BOT = "╚" + DOUBLE_LINE;
        public static final Style SINGLE = new Style(SINGLE_TOP, SINGLE_DIV, SINGLE_MID, SINGLE_BOT);
        public static final Style DOUBLE = new Style(DOUBLE_TOP, DOUBLE_DIV, DOUBLE_MID, DOUBLE_BOT);
        public static final Style NONE = new Style(EMPTY, EMPTY, EMPTY, EMPTY);

        final String top;
        final String divider;
        final String middle;
        final String bottom;

        public Style(String top, String divider, String middle, String bottom) {
            this.top = top;
            this.divider = divider;
            this.middle = middle;
            this.bottom = bottom;
        }
    }
}
