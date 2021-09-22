package cn.thens.logdog;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.util.LruCache;

import java.util.Arrays;


/**
 * @author 7hens
 */
@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public final class LogMessages {
    private static final int LRU_CACHE_MAX_SIZE = 256;
    private static final LruCache<String, Long> timers = new LruCache<>(LRU_CACHE_MAX_SIZE);
    private static final LruCache<String, Long> counters = new LruCache<>(LRU_CACHE_MAX_SIZE);

    public static Object time(final String name) {
        return new Object() {
            @Override
            public String toString() {
                long now = SystemClock.elapsedRealtime();
                Long lastTime = timers.get(name);
                if (lastTime == null) lastTime = 0L;
                timers.put(name, now);
                return "time(" + name + "): " + (now - lastTime) + "ms";
            }
        };
    }

    public static Object count(final String name) {
        return new Object() {
            @Override
            public String toString() {
                Long lastCount = counters.get(name);
                if (lastCount == null) lastCount = 0L;
                counters.put(name, lastCount + 1);
                return "count(" + name + "): " + (lastCount + 1);
            }
        };
    }

    public static Object memory(Context context) {
        final Context app = context.getApplicationContext();
        return new Object() {
            @Override
            public String toString() {
                StringBuilder result = new StringBuilder();
                ActivityManager activityManager = (ActivityManager) app.getSystemService(Context.ACTIVITY_SERVICE);
                ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                activityManager.getMemoryInfo(memoryInfo);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    result.append("total memory: ").append(memoryInfo.totalMem / 1024 / 1024).append(" MB\n");
                }
                result.append("avail memory: ").append(memoryInfo.availMem / 1024 / 1024).append(" MB")
                        .append("\nthreshold: ").append(memoryInfo.threshold / 1024 / 1024).append(" MB")
                        .append("\nlow memory: ").append(memoryInfo.lowMemory);
                return result.toString();
            }
        };
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
