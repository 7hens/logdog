package cn.thens.logdog;

import android.util.Log;

/**
 * @author 7hens
 */
public interface Logger {

    void log(int priority, String tag, String message);

    Logger DEFAULT = new Logger() {
        private int tagPrefix = 0;

        @Override
        public void log(int priority, String tag, String message) {
            tagPrefix = (tagPrefix + 1) % 10;
            Log.println(priority, tagPrefix + "." + tag, message);
        }
    };
}
