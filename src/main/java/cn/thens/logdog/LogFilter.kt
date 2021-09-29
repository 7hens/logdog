package cn.thens.logdog

fun interface LogFilter {
    fun isLoggable(priority: LogPriority, tag: String): Boolean

    companion object X {
        private fun of(priority: LogPriority) = LogFilter { p, _ -> p >= priority }
        val ALL = LogFilter { _, _ -> true }
        val NONE = LogFilter { _, _ -> false }
        val VERBOSE = of(LogPriority.VERBOSE)
        val DEBUG = of(LogPriority.DEBUG)
        val INFO = of(LogPriority.INFO)
        val WARN = of(LogPriority.WARN)
        val ERROR = of(LogPriority.ERROR)
        val ASSERT = of(LogPriority.ASSERT)
    }
}

@Deprecated("", ReplaceWith("LogFilter"))
typealias LogStrategy = LogFilter