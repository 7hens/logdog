package cn.thens.logdog.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.thens.logdog.*

class MainActivity : AppCompatActivity() {
    private val customLogger = Logdog
        .priority(LogPriority.WARN)
        .strategy(LogStrategy.WARN)
        .tag("CustomLogger")
        .logger { priority, tag, message ->
            Logger.logcat().log(priority, tag,
                PrettyLogger.getStackInfo() + " " + PrettyLogger.stringOf(message))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(android.R.id.content).setOnClickListener { testLog() }
        testLog()
    }

    private fun testLog() {
        customLogger { "hello world" }

        Logdog { "hello" }
            .error { Throwable() }
            .debug
            .logMemory(this)
            .logCount("hello")
            .logCount("hello")
            .warn
            .logTime("hello")
            .logTime("hello")
            .requires(!BuildConfig.DEBUG) () { "What a Terrible Failure" }
    }
}