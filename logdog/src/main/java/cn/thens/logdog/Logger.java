package cn.thens.logdog;

import android.util.Log;

/**
 * @author 7hens
 */
public interface Logger<T> {

    void log(int priority, String tag, T message);

    Logger<String> LOGCAT = new Logger<String>() {
        private int tagPrefix = 0;

        @Override
        public void log(int priority, String tag, String message) {
            tagPrefix = (tagPrefix + 1) % 10;
            Log.println(priority, tagPrefix + "@" + tag, message);
        }
    };

    Logger<Object> EMPTY = new Logger<Object>() {
        @Override
        public void log(int priority, String tag, Object message) {
        }
    };
}
