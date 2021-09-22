package cn.thens.logdog;

final class LoggerSys implements Logger<String> {
    private final Logger<String> out = Loggers.out(System.out);
    private final Logger<String> err = Loggers.out(System.err);

    @Override
    public void log(int priority, String tag, String message) {
        Logger<String> logger = priority >= LogdogX.ERROR ? err : out;
        logger.log(priority, tag, message);
    }

    static final class Instance extends SingletonProvider<LoggerSys> {
        @Override
        protected LoggerSys onCreate() {
            return new LoggerSys();
        }
    }
}
