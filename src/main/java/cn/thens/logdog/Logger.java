package cn.thens.logdog;

/**
 * @author 7hens
 */
public interface Logger {
    void log(int priority, String tag, Object message);
}