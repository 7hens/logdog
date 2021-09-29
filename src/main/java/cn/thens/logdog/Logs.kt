@file:JvmSynthetic

package cn.thens.logdog

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.SystemClock
import android.util.LruCache

private const val LOG_CACHE_MAX_SIZE = 256
private val logTimers = LruCache<String, Long>(LOG_CACHE_MAX_SIZE)
private val logCounters = LruCache<String, Long>(LOG_CACHE_MAX_SIZE)

inline operator fun Logdog.invoke(crossinline fn: () -> Any?): Logdog {
    if (isLoggable) {
        log(fn())
    }
    return this
}

inline fun Logdog.require(value: Boolean, crossinline fn: () -> Any?): Logdog {
    return require(value)(fn)
}

fun Logdog.logTime(name: String): Logdog {
    return invoke {
        val now = SystemClock.elapsedRealtime()
        val lastTime = logTimers[name]
        logTimers.put(name, now)
        "time($name): " + (lastTime?.let { "${now - it}ms" } ?: "_")
    }
}

fun Logdog.logCount(name: String): Logdog {
    return invoke {
        var lastCount = logCounters[name]
        if (lastCount == null) lastCount = 0L
        logCounters.put(name, lastCount + 1)
        "count($name): ${lastCount + 1}"
    }
}

fun Logdog.logMemory(context: Context): Logdog {
    return invoke {
        val app = context.applicationContext
        val result = StringBuilder()
        val activityManager =
            app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            result.append("total memory: ").append(memoryInfo.totalMem / 1024 / 1024)
                .append(" MB\n")
        }
        result.append("avail memory: ").append(memoryInfo.availMem / 1024 / 1024)
            .append(" MB")
            .append("\nthreshold: ").append(memoryInfo.threshold / 1024 / 1024)
            .append(" MB")
            .append("\nlow memory: ").append(memoryInfo.lowMemory)
        result.toString()
    }
}