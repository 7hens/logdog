package cn.thens.logdog;

import android.util.Log;

final class LoggerLogcat implements Logger<String> {
    private int tagPrefix = 0;

    @Override
    public void log(int priority, String tag, String message) {
        tagPrefix = (tagPrefix + 1) % 10;
        Log.println(getLogPriority(priority), tagPrefix + ",," + tag, message);
    }

    private static int getLogPriority(int priority) {
        switch (priority) {
            case LogdogX.INFO:
                return Log.INFO;
            case LogdogX.ASSERT:
                return Log.ASSERT;
            case LogdogX.DEBUG:
                return Log.DEBUG;
            case LogdogX.VERBOSE:
                return Log.VERBOSE;
            case LogdogX.WARN:
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
