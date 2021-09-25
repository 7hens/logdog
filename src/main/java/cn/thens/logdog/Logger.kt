package cn.thens.logdog

import android.util.Log.*
import java.io.OutputStream
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author 7hens
 */
fun interface Logger {
    fun log(priority: LogPriority, tag: String, message: Any?)

    fun pretty(): PrettyLogger {
        val self = this
        return object : PrettyLogger {
            override val logger: Logger = self
        }
    }

    companion object X {
        private const val MARK = "="

        fun empty(): Logger {
            return Empty
        }

        fun logcat(): Logger {
            return Logcat
        }

        fun out(output: OutputStream): Logger {
            return Print(output as? PrintStream ?: PrintStream(output))
        }

        private val OUT by lazy { out(System.out) }
        private val ERR by lazy { out(System.err) }

        fun out(): Logger = OUT

        fun err(): Logger = ERR

        fun sys(): Logger = Sys
    }

    private class Print(private val printer: PrintStream) : Logger {
        private val dateFormat: SimpleDateFormat =
            SimpleDateFormat(DATE_PATTERN, Locale.getDefault())

        override fun log(priority: LogPriority, tag: String, message: Any?) {
            val time = dateFormat.format(Date())
            printer.println("$time ${priority.name.first()}/$MARK$tag: $message")
        }

        companion object {
            private const val DATE_PATTERN = "yyyy.MM.dd HH:mm:ss.SSS"
        }
    }

    private object Sys : Logger {
        override fun log(priority: LogPriority, tag: String, message: Any?) {
            val logger = if (priority >= LogPriority.ERROR) ERR else OUT
            logger.log(priority, tag, message)
        }
    }

    private object Logcat : Logger {
        private val priorities = arrayOf(VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT)
        private var tagFlag = true

        override fun log(priority: LogPriority, tag: String, message: Any?) {
            tagFlag = !tagFlag
            val tagPrefix = if (tagFlag) ":" else ";"
            println(getLogPriority(priority), "$MARK$tagPrefix$tag", "" + message)
        }

        private fun getLogPriority(priority: LogPriority): Int {
            val index = priority.ordinal
            return if (index in priorities.indices) priorities[index] else ASSERT
        }
    }

    private object Empty : Logger {
        override fun log(priority: LogPriority, tag: String, message: Any?) {}
    }
}