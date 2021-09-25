package cn.thens.logdog;

@SuppressWarnings("SpellCheckingInspection")
@PrettyLogger.Ignored
public final class Logdog {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;

    private static final String DEFAULT_TAG = "Logdog";
    public static Logdog X = new Logdog(DEBUG, DEFAULT_TAG, new PrettyLogger(Loggers.logcat()));
    private static final Logdog EMPTY = new Logdog(DEBUG, DEFAULT_TAG, Loggers.empty());

    private final String tag;
    private final int priority;
    private final Logger logger;

    private Logdog(int priority, String tag, Logger logger) {
        this.priority = priority;
        this.tag = tag;
        this.logger = logger;
    }

    public Logdog priority(int priority) {
        return new Logdog(priority, tag, logger);
    }

    public Logdog tag(String tag) {
        return new Logdog(priority, tag, logger);
    }

    public Logdog logger(Logger logger) {
        return new Logdog(priority, tag, logger);
    }

    public Logdog onlyIf(boolean sure) {
        return sure ? this : EMPTY;
    }

    public Logdog verbose() {
        return priority(VERBOSE);
    }

    public Logdog debug() {
        return priority(DEBUG);
    }

    public Logdog info() {
        return priority(INFO);
    }

    public Logdog warn() {
        return priority(WARN);
    }

    public Logdog error() {
        return priority(ERROR);
    }

    public Logdog wtf() {
        return priority(ASSERT);
    }

    public Logdog log(Object msg) {
        logger.log(priority, tag, msg);
        return this;
    }
}