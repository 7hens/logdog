package cn.thens.logdog;

import android.os.SystemClock;
import android.util.LruCache;

/**
 * @author 7hens
 */
public final class LogMessages {
    private static final int LRU_CACHE_MAX_SIZE = 256;
    private static LruCache<String, Long> timers = new LruCache<>(LRU_CACHE_MAX_SIZE);
    private static LruCache<String, Long> counters = new LruCache<>(LRU_CACHE_MAX_SIZE);

    public static MessageProvider time(final String name) {
        return new MessageProvider() {
            @Override
            public String getMessage() {
                long now = SystemClock.elapsedRealtime();
                Long lastTime = timers.get(name);
                if (lastTime == null) lastTime = 0L;
                timers.put(name, now);
                return "time(" + name + "): " + (now - lastTime) + "ms";
            }
        };
    }

    public static MessageProvider count(final String name) {
        return new MessageProvider() {
            @Override
            public String getMessage() {
                Long lastCount = counters.get(name);
                if (lastCount == null) lastCount = 0L;
                counters.put(name, lastCount + 1);
                return "count(" + name + "): " + (lastCount + 1);
            }
        };
    }

    public static MessageProvider memory() {
        return new MessageProvider() {
            @Override
            public String getMessage() {
                Runtime runtime = Runtime.getRuntime();
                return "total memory: " + (runtime.totalMemory() / 1024) + "KB"
                        + "\nfree memory: " + (runtime.freeMemory() / 1024) + "KB"
                        + "\nmax memory: " + (runtime.maxMemory() / 1024) + "KB";
            }
        };
    }
}
