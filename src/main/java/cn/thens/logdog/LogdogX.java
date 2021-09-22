package cn.thens.logdog;

@SuppressWarnings("SpellCheckingInspection")
@PrettyLogger.Ignored
public final class LogdogX {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;

    private static final String DEFAULT_TAG = "Logdog";
    private final String tag;
    private final Logger<? super Object> logger;

    private LogdogX(String tag, Logger<? super Object> logger) {
        this.tag = tag;
        this.logger = logger;
    }

    private static final LogdogX EMPTY = new LogdogX(DEFAULT_TAG, Loggers.empty());

    private static LogdogX DEFAULT;

    private static final SingletonProvider<LogdogX> DEFAULT_PROVIDER = new SingletonProvider<LogdogX>() {
        @Override
        protected LogdogX onCreate() {
            return new LogdogX(DEFAULT_TAG, new PrettyLogger(Loggers.logcat()));
        }
    };

    public static LogdogX getDefaultInstance() {
        return DEFAULT != null ? DEFAULT : DEFAULT_PROVIDER.get();
    }

    public static void setDefaultInstance(LogdogX logdog) {
        DEFAULT = logdog;
    }

    public LogdogX tag(String tag) {
        return new LogdogX(tag, logger);
    }

    public LogdogX logger(Logger<? super Object> logger) {
        return new LogdogX(tag, logger);
    }

    public LogdogX onlyIf(boolean sure) {
        return sure ? this : EMPTY;
    }

    public LogdogX verbose(Object msg) {
        return log(VERBOSE, msg);
    }

    public LogdogX debug(Object msg) {
        return log(DEBUG, msg);
    }

    public LogdogX info(Object msg) {
        return log(INFO, msg);
    }

    public LogdogX warn(Object msg) {
        return log(WARN, msg);
    }

    public LogdogX error(Object msg) {
        return log(ERROR, msg);
    }

    public LogdogX wtf(Object msg) {
        return log(ASSERT, msg);
    }

    public LogdogX log(int priority, Object msg) {
        logger.log(priority, tag, msg);
        return this;
    }
}
