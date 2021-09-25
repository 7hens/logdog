package cn.thens.logdog;

import java.io.OutputStream;

public final class Loggers {
    private static final LoggerLogcat.Instance LOGCAT = new LoggerLogcat.Instance();

    public static Logger logcat() {
        return LOGCAT.get();
    }

    private static final Logger EMPTY = new LoggerEmpty();

    public static Logger empty() {
        return EMPTY;
    }

    public static Logger out(OutputStream output) {
        return new LoggerOut(output);
    }

    private static final LoggerSys.Instance SYS = new LoggerSys.Instance();

    public static Logger sys() {
        return SYS.get();
    }
}