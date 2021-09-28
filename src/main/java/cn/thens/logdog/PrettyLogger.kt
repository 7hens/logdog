package cn.thens.logdog

import android.util.Log

/**
 * @author 7hens
 */
class PrettyLogger(
    private val logger: Logger,
    private val adapter: StyleAdapter = StyleAdapter.DEFAULT
) : Logger {
    override fun log(priority: LogPriority, tag: String, message: Any?) {
        val (borders, stackOffset, stackCount) = adapter.getStyle(priority, tag)
        if (borders.top.isNotEmpty()) {
            logger.log(priority, tag, borders.top)
        }
        if (stackCount > 0) {
            val stackTrace = Thread.currentThread().stackTrace
            val stackTraceOffset = getStackTraceOffset(stackTrace) + stackOffset
            val headerLineCount = stackCount.coerceAtMost(stackTrace.size - 1 - stackTraceOffset)
            for (i in 0 until headerLineCount) {
                val stackIndex = i + stackTraceOffset
                var stackInfo = getStackInfo(stackTrace[stackIndex])
                if (i == 0) stackInfo += " on thread: " + Thread.currentThread().name
                val indent = if (i == 0) "" else "  "
                logger.log(priority, tag, borders.middle + indent + stackInfo)
            }
            if (headerLineCount > 0) {
                logger.log(priority, tag, borders.divider)
            }
        }
        val messageText = stringOf(message)
        val lines = messageText.split("\n").toTypedArray()
        for (lineIndex in lines.indices) {
            val line = lines[lineIndex]
            if (line.isEmpty()) {
                if (lineIndex != lines.size - 1) {
                    logger.log(priority, tag, borders.middle)
                }
                continue
            }
            val lineLength = line.length
            val chunkSize = LINE_CHUNK_SIZE
            val pageSize = (lineLength - 1) / chunkSize + 1
            for (pageIndex in 0 until pageSize) {
                val offset = pageIndex * chunkSize
                val count = (lineLength - offset).coerceAtMost(chunkSize)
                val chunk = line.substring(offset, offset + count)
                logger.log(priority, tag, if (pageIndex == 0) borders.middle + chunk else chunk)
            }
        }
        if (borders.bottom.isNotEmpty()) {
            logger.log(priority, tag, borders.bottom)
        }
    }

    data class Style(
        val borders: Borders,
        val stackOffset: Int = 0,
        val stackCount: Int = 1
    )

    fun interface StyleAdapter {
        fun getStyle(priority: LogPriority, tag: String): Style

        companion object {
            val DEFAULT = StyleAdapter { p, _ ->
                Style(if (p >= LogPriority.WARN) Borders.DOUBLE else Borders.SINGLE)
            }
        }
    }

    interface Ignored

    class Borders(val top: String, val divider: String, val middle: String, val bottom: String) {
        companion object {
            private const val EMPTY = ""
            private const val SINGLE_PART = "────────────────────────────"
            private const val DASHED_PART = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄"
            private const val DOUBLE_PART = "════════════════════════════"
            private const val SINGLE_LINE = SINGLE_PART + SINGLE_PART + SINGLE_PART + SINGLE_PART
            private const val DASHED_LINE = DASHED_PART + DASHED_PART + DASHED_PART + DASHED_PART
            private const val DOUBLE_LINE = DOUBLE_PART + DOUBLE_PART + DOUBLE_PART + DOUBLE_PART
            private const val SINGLE_TOP = "┌$SINGLE_LINE"
            private const val SINGLE_DIV = "├$DASHED_LINE"
            private const val SINGLE_MID = "│ "
            private const val SINGLE_BOT = "└$SINGLE_LINE"
            private const val DOUBLE_TOP = "╔$DOUBLE_LINE"
            private const val DOUBLE_DIV = "╟$DASHED_LINE"
            private const val DOUBLE_MID = "║ "
            private const val DOUBLE_BOT = "╚$DOUBLE_LINE"

            val SINGLE = Borders(SINGLE_TOP, SINGLE_DIV, SINGLE_MID, SINGLE_BOT)
            val DOUBLE = Borders(DOUBLE_TOP, DOUBLE_DIV, DOUBLE_MID, DOUBLE_BOT)
            val NONE = Borders(EMPTY, EMPTY, EMPTY, EMPTY)
        }
    }

    companion object {
        private const val LINE_CHUNK_SIZE = 1024

        fun getStackTraceOffset(stackTrace: Array<StackTraceElement>): Int {
            var isInOffsetClass = false
            for (i in stackTrace.indices) {
                val traceElement = stackTrace[i]
                try {
                    val cls = Class.forName(traceElement.className)
                    if (cls.isSynthetic) {
                        continue
                    }
                    if (Ignored::class.java.isAssignableFrom(cls)) {
                        isInOffsetClass = true
                    } else if (isInOffsetClass) {
                        return i
                    }
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            }
            return 0
        }

        fun getStackInfo(element: StackTraceElement): String {
            var className = element.className
            className = className.substring(className.lastIndexOf(".") + 1)
            return className + "." + element.methodName +
                    "(" + element.fileName + ":" +
                    element.lineNumber + ")"
        }

        fun getStackInfo(offset: Int = 0): String {
            val stackTrace = Thread.currentThread().stackTrace
            val stackTraceOffset = getStackTraceOffset(stackTrace) + offset
            return getStackInfo(stackTrace[stackTraceOffset])
        }

        fun stringOf(obj: Any?): String {
            return when (obj) {
                null -> "null"
                is String -> obj
                is Throwable -> Log.getStackTraceString(obj)
                is BooleanArray -> obj.contentToString()
                is ByteArray -> obj.contentToString()
                is CharArray -> obj.contentToString()
                is ShortArray -> obj.contentToString()
                is IntArray -> obj.contentToString()
                is LongArray -> obj.contentToString()
                is FloatArray -> obj.contentToString()
                is DoubleArray -> obj.contentToString()
                is Array<*> -> obj.contentDeepToString()
                else -> obj.toString()
            }
        }
    }
}