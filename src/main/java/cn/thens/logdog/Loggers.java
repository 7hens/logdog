package cn.thens.logdog;

import java.io.OutputStream;

public final class Loggers {
    private static final LoggerLogcat.Instance LOGCAT = new LoggerLogcat.Instance();

    public static Logger<String> logcat() {
        return LOGCAT.get();
    }

    private static final Logger<Object> EMPTY = new LoggerEmpty();

    public static Logger<Object> empty() {
        return EMPTY;
    }

    public static Logger<String> out(OutputStream output) {
        return new LoggerOut(output);
    }

    private static final LoggerSys.Instance SYS = new LoggerSys.Instance();

    public static Logger<String> sys() {
        return SYS.get();
    }
}
