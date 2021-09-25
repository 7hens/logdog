package cn.thens.logdog;

final class LoggerEmpty implements Logger {
    @Override
    public void log(int priority, String tag, Object message) {
    }
}