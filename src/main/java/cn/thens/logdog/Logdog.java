package cn.thens.logdog;

@SuppressWarnings("SpellCheckingInspection")
@PrettyLogger.Ignored
public final class Logdog {
    private static LogdogX get() {
        return LogdogX.getDefaultInstance();
    }

    public static LogdogX onlyIf(boolean condition) {
        return get().onlyIf(condition);
    }

    public static LogdogX tag(String tag) {
        return get().tag(tag);
    }

    public static LogdogX logger(Logger<? super Object> logger) {
        return get().logger(logger);
    }

    public static LogdogX verbose(Object msg) {
        return get().verbose(msg);
    }

    public static LogdogX debug(Object msg) {
        return get().debug(msg);
    }

    public static LogdogX info(Object msg) {
        return get().info(msg);
    }

    public static LogdogX warn(Object msg) {
        return get().warn(msg);
    }

    public static LogdogX error(Object msg) {
        return get().error(msg);
    }

    public static LogdogX wtf(Object msg) {
        return get().wtf(msg);
    }

    public static LogdogX log(int priority, Object msg) {
        return get().log(priority, msg);
    }
}
