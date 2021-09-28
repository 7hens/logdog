package cn.thens.logdog.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.thens.logdog.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(android.R.id.content).setOnClickListener { testLog() }
        testLog()
    }

    private fun testLog() {
        Logdog { "hello" }
            .error { Throwable() }
            .debug
            .logMemory(this)
            .logCount("hello")
            .logCount("hello")
            .warn
            .logTime("hello")
            .logTime("hello")
            .requires(!BuildConfig.DEBUG)() { "What a Terrible Failure" }

        val customLogger = Logdog
            .priority(LogPriority.WARN)
            .filter(LogFilter.WARN)
            .tag("CustomLogger")
            .logger { priority, tag, message ->
                Logger.logcat().log(
                    priority, tag,
                    PrettyLogger.getStackInfo() + " " + PrettyLogger.stringOf(message)
                )
            }

        customLogger { "hello world" }
    }
}