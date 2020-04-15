package cn.thens.logdog;

import android.util.Log;

import java.util.Arrays;

@SuppressWarnings("SpellCheckingInspection")
@PrettyLogger.Ignored
public class Logdog {
    private static final String DEFAULT_TAG = "@Logdog";
    private final String tag;
    private final Logger logger;

    private Logdog(String tag, Logger logger) {
        this.tag = tag;
        this.logger = logger;
    }

    public static Logdog create(Logger logger) {
        return new Logdog(DEFAULT_TAG, logger);
    }

    private static Logdog INSTANCE = create(new PrettyLogger(Logger.DEFAULT));

    public static Logdog get() {
        return INSTANCE;
    }

    public Logdog tag(String tag) {
        return new Logdog(tag, logger);
    }

    public Logdog verbose(Object msg) {
        return log(Log.VERBOSE, msg);
    }

    public Logdog debug(Object msg) {
        return log(Log.DEBUG, msg);
    }

    public Logdog info(Object msg) {
        return log(Log.INFO, msg);
    }

    public Logdog warn(Object msg) {
        return log(Log.WARN, msg);
    }

    public Logdog error(Object msg) {
        return log(Log.ERROR, msg);
    }

    public Logdog wtf(boolean sure, Object msg) {
        if (sure) return this;
        return log(Log.ASSERT, msg);
    }

    private Logdog log(int priority, Object msg) {
        logger.log(priority, tag, LogMessages.of(msg));
        return this;
    }
}
