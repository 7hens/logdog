package cn.thens.logdog

interface Logdog : PrettyLogger.Ignored {
    val isLoggable: Boolean

    fun log(msg: Any?): Logdog

    fun priority(priority: LogPriority): Logdog

    fun tag(tag: String): Logdog

    fun filter(filter: LogFilter): Logdog

    fun logger(logger: Logger): Logdog

    val verbose get() = priority(LogPriority.VERBOSE)

    val debug get() = priority(LogPriority.DEBUG)

    val info get() = priority(LogPriority.INFO)

    val warn get() = priority(LogPriority.WARN)

    val error get() = priority(LogPriority.ERROR)

    val wtf get() = priority(LogPriority.ASSERT)

    val dummy get() = filter(LogFilter.NONE)

    fun requires(sure: Boolean): Logdog {
        return if (sure) dummy else error
    }

    companion object X : Logdog by Impl()

    private class Impl(
        private val priority: LogPriority = LogPriority.DEBUG,
        private val tag: String = "Logdog",
        private val filter: LogFilter = LogFilter.ALL,
        private val logger: Logger = PrettyLogger(Logger.logcat()),
    ) : Logdog {

        override val isLoggable: Boolean = filter.isLoggable(priority, tag)

        override fun priority(priority: LogPriority): Logdog {
            return Impl(priority, tag, filter, logger)
        }

        override fun tag(tag: String): Logdog {
            return Impl(priority, tag, filter, logger)
        }

        override fun filter(filter: LogFilter): Logdog {
            return Impl(priority, tag, filter, logger)
        }

        override fun logger(logger: Logger): Logdog {
            return Impl(priority, tag, filter, logger)
        }

        override fun log(msg: Any?): Logdog {
            if (isLoggable) {
                logger.log(priority, tag, msg)
            }
            return this
        }
    }
}