package cn.thens.logdog;

abstract class SingletonProvider<T> {
    private volatile T value;

    public final T get() {
        if (value == null) {
            synchronized (this) {
                if (value == null) {
                    value = onCreate();
                }
            }
        }
        return value;
    }

    protected abstract T onCreate();
}
