package cn.thens.logdog;

final class LoggerSys implements Logger {
    private final Logger out = Loggers.out(System.out);
    private final Logger err = Loggers.out(System.err);

    @Override
    public void log(int priority, String tag, Object message) {
        Logger logger = priority >= Logdog.ERROR ? err : out;
        logger.log(priority, tag, message);
    }

    static final class Instance extends SingletonProvider<LoggerSys> {
        @Override
        protected LoggerSys onCreate() {
            return new LoggerSys();
        }
    }
}