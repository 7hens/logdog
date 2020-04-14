package cn.thens.logdog.sample;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.thens.logdog.LogMessages;
import cn.thens.logdog.Logdog;
import cn.thens.logdog.Logger;
import cn.thens.logdog.PrettyLogger;

public class MainActivity extends AppCompatActivity {

    private Logdog customLogger = Logdog.create(new PrettyLogger(Logger.DEFAULT) {
        @Override
        public void log(int priority, String tag, String message) {
            if (!BuildConfig.DEBUG) return;
            super.log(priority, tag, message);
        }

        @Override
        protected Style getStyle(int priority, String tag) {
            return Style.SINGLE;
        }

        @Override
        protected int getMethodCount(int priority, String tag) {
            return 2;
        }

        @Override
        protected int getMethodOffset(int priority, String tag) {
            return 1;
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
        Logdog.get().debug("hello")
                .error(new Throwable())
                .wtf(false, "What a Terrible Failure")
                .debug(LogMessages.memory())
                .debug(LogMessages.count("hello"))
                .debug(LogMessages.count("hello"))
                .warn(LogMessages.time("hello"))
                .warn(LogMessages.time("hello"));
        customLogger.tag("CustomLogger").error("hello");
    }
}
