package cn.thens.logdog;

import android.util.Log;

@SuppressWarnings("SpellCheckingInspection")
@PrettyLogger.Ignored
public final class Logdog {
    private static final String DEFAULT_TAG = "Logdog";
    private final String tag;
    private final Logger<? super Object> logger;

    private Logdog(String tag, Logger<? super Object> logger) {
        this.tag = tag;
        this.logger = logger;
    }

    public static Logdog create(String tag, Logger<? super Object> logger) {
        return new Logdog(tag, logger);
    }

    private static Logdog INSTANCE = create(DEFAULT_TAG, new PrettyLogger(Logger.LOGCAT));

    public static Logdog get() {
        return INSTANCE;
    }

    public static void setDefaultInstance(Logdog logdog) {
        INSTANCE = logdog;
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

    public Logdog require(boolean sure, Object msg) {
        if (sure) return this;
        return log(Log.ASSERT, msg);
    }

    public Logdog log(int priority, Object msg) {
        logger.log(priority, tag, msg);
        return this;
    }
}
