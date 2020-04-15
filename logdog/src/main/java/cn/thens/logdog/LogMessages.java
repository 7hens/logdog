package cn.thens.logdog;

import android.os.SystemClock;
import android.util.Log;
import android.util.LruCache;

import java.util.Arrays;


/**
 * @author 7hens
 */
public final class LogMessages {
    private static final int LRU_CACHE_MAX_SIZE = 256;
    private static LruCache<String, Long> timers = new LruCache<>(LRU_CACHE_MAX_SIZE);
    private static LruCache<String, Long> counters = new LruCache<>(LRU_CACHE_MAX_SIZE);

    public static String time(final String name) {
        long now = SystemClock.elapsedRealtime();
        Long lastTime = timers.get(name);
        if (lastTime == null) lastTime = 0L;
        timers.put(name, now);
        return "time(" + name + "): " + (now - lastTime) + "ms";
    }

    public static String count(final String name) {
        Long lastCount = counters.get(name);
        if (lastCount == null) lastCount = 0L;
        counters.put(name, lastCount + 1);
        return "count(" + name + "): " + (lastCount + 1);
    }

    public static String memory() {
        Runtime runtime = Runtime.getRuntime();
        return "total memory: " + (runtime.totalMemory() / 1024) + "KB"
                + "\nfree memory: " + (runtime.freeMemory() / 1024) + "KB"
                + "\nmax memory: " + (runtime.maxMemory() / 1024) + "KB";
    }

    public static String of(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof String) return (String) obj;
        if (obj instanceof Throwable) return Log.getStackTraceString((Throwable) obj);
        if (!obj.getClass().isArray()) return obj.toString();
        if (obj instanceof boolean[]) return Arrays.toString((boolean[]) obj);
        if (obj instanceof byte[]) return Arrays.toString((byte[]) obj);
        if (obj instanceof char[]) return Arrays.toString((char[]) obj);
        if (obj instanceof short[]) return Arrays.toString((short[]) obj);
        if (obj instanceof int[]) return Arrays.toString((int[]) obj);
        if (obj instanceof long[]) return Arrays.toString((long[]) obj);
        if (obj instanceof float[]) return Arrays.toString((float[]) obj);
        if (obj instanceof double[]) return Arrays.toString((double[]) obj);
        if (obj instanceof Object[]) return Arrays.deepToString((Object[]) obj);
        return obj.toString();
    }
}
