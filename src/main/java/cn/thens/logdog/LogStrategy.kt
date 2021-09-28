package cn.thens.logdog

// FIXME rename to LogFilter
fun interface LogStrategy {
    fun isLoggable(priority: LogPriority, tag: String): Boolean

    companion object X {
        private fun of(priority: LogPriority) = LogStrategy { p, _ -> p >= priority }
        val ALL = LogStrategy { _, _ -> true }
        val NONE = LogStrategy { _, _ -> false }
        val VERBOSE = of(LogPriority.VERBOSE)
        val DEBUG = of(LogPriority.DEBUG)
        val INFO = of(LogPriority.INFO)
        val WARN = of(LogPriority.WARN)
        val ERROR = of(LogPriority.ERROR)
        val ASSERT = of(LogPriority.ASSERT)
    }
}