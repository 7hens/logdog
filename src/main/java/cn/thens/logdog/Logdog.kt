package cn.thens.logdog

interface Logdog : PrettyLogger.Ignored {
    val isLoggable: Boolean

    fun log(msg: Any?): Logdog

    fun priority(priority: LogPriority): Logdog

    fun tag(tag: String): Logdog

    fun strategy(strategy: LogStrategy): Logdog

    fun logger(logger: Logger): Logdog

    val verbose get() = priority(LogPriority.VERBOSE)

    val debug get() = priority(LogPriority.DEBUG)

    val info get() = priority(LogPriority.INFO)

    val warn get() = priority(LogPriority.WARN)

    val error get() = priority(LogPriority.ERROR)

    val wtf get() = priority(LogPriority.ASSERT)

    val dummy get() = strategy(LogStrategy.NONE)

    fun requires(sure: Boolean): Logdog {
        return if (sure) dummy else error
    }

    companion object X : Logdog by Impl()

    private class Impl(
        private val priority: LogPriority = LogPriority.DEBUG,
        private val tag: String = "Logdog",
        private val strategy: LogStrategy = LogStrategy.ALL,
        private val logger: Logger = Logger.logcat().pretty(),
    ) : Logdog {

        override val isLoggable: Boolean = strategy.isLoggable(priority, tag)

        override fun priority(priority: LogPriority): Logdog {
            return Impl(priority, tag, strategy, logger)
        }

        override fun tag(tag: String): Logdog {
            return Impl(priority, tag, strategy, logger)
        }

        override fun strategy(strategy: LogStrategy): Logdog {
            return Impl(priority, tag, strategy, logger)
        }

        override fun logger(logger: Logger): Logdog {
            return Impl(priority, tag, strategy, logger)
        }

        override fun log(msg: Any?): Logdog {
            if (isLoggable) {
                logger.log(priority, tag, msg)
            }
            return this
        }
    }
}