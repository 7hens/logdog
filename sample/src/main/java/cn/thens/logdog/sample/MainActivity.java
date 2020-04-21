package cn.thens.logdog.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.thens.logdog.LogMessages;
import cn.thens.logdog.Logdog;
import cn.thens.logdog.Logger;
import cn.thens.logdog.PrettyLogger;

public class MainActivity extends AppCompatActivity {

    private Logdog customLogger = Logdog.create("CustomLogger", new PrettyLogger(Logger.LOGCAT) {
        @Override
        public void log(int priority, String tag, Object message) {
            if (BuildConfig.DEBUG || priority >= Log.WARN) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                int stackOffset = getStackOffset(priority, tag, stackTrace);
                String traceInfo = getStackInfo(stackTrace[stackOffset]);
                super.log(priority, tag, traceInfo + " " + getMessageText(message));
            }
        }

        @Override
        protected Style getStyle(int priority, String tag) {
            return Style.NONE;
        }

        @Override
        protected int getMethodCount(int priority, String tag) {
            return 0;
        }

        @Override
        protected int getStackOffset(int priority, String tag, StackTraceElement[] stackTrace) {
            return super.getStackOffset(priority, tag, stackTrace) + 1;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testLog();
            }
        });
        testLog();
    }

    private void testLog() {
        customLogger.error("hello");

        Logdog.get().debug("hello")
                .error(new Throwable())
                .require(false, "What a Terrible Failure")
                .debug(LogMessages.memory(this))
                .debug(LogMessages.count("hello"))
                .debug(LogMessages.count("hello"))
                .warn(LogMessages.time("hello"))
                .warn(LogMessages.time("hello"));
    }
}
