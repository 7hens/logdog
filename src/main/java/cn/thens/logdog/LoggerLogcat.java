package cn.thens.logdog;

import android.util.Log;

final class LoggerLogcat implements Logger {
    private int tagPrefix = 0;

    @Override
    public void log(int priority, String tag, Object message) {
        tagPrefix = (tagPrefix + 1) % 10;
        Log.println(getLogPriority(priority), tagPrefix + ",," + tag, "" + message);
    }

    private static int getLogPriority(int priority) {
        switch (priority) {
            case Logdog.INFO:
                return Log.INFO;
            case Logdog.ASSERT:
                return Log.ASSERT;
            case Logdog.DEBUG:
                return Log.DEBUG;
            case Logdog.VERBOSE:
                return Log.VERBOSE;
            case Logdog.WARN:
                return Log.WARN;
            default:
                return Log.ERROR;
        }
    }

    static final class Instance extends SingletonProvider<LoggerLogcat> {
        @Override
        protected LoggerLogcat onCreate() {
            return new LoggerLogcat();
        }
    }
}