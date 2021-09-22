package cn.thens.logdog;

/**
 * @author 7hens
 */
public interface Logger<T> {
    void log(int priority, String tag, T message);
}
