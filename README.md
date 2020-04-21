# Logdog

[![jitpack](https://jitpack.io/v/7hens/logdog.svg)](https://jitpack.io/#7hens/logdog)
![travis](https://img.shields.io/travis/7hens/logdog)
[![license](https://img.shields.io/github/license/7hens/logdog.svg)](https://github.com/7hens/logdog/blob/master/LICENSE)
[![stars](https://img.shields.io/github/stars/7hens/logdog.svg?style=social)](https://github.com/7hens/logdog)

Yet another pretty logger for android.

## Setting up the dependency

last_version: [![jitpack](https://jitpack.io/v/7hens/logdog.svg)](https://jitpack.io/#7hens/logdog)

```groovy
implementation 'com.github.7hens:logdog:<last_version>'
```

## Sample usage

```java
Logdog.get().debug("hello")
        .error(new Throwable())
        .wtf(false, "What a Terrible Failure")
        .debug(LogMessages.memory(context))
        .debug(LogMessages.count("hello"))
        .debug(LogMessages.count("hello"))
        .warn(LogMessages.time("hello"))
        .warn(LogMessages.time("hello"));
```

## Advanced

You can customize a logger as you want.

```java
Logger yourLogger = new PrettyLogger(Logger.LOGCAT) {
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
};
```

Then, you can change the behavior of `Logdog.get().xxx()` by reset the default logger.

```java
Logdog.setDefaultLogger(yourLogger);
```

Or you can create your new customized Logdog.

```java
Logdog yourLogdog = Logdog.create(yourLogger).tag("CustomLogger");
```

## Output

You can view all logs about Logdog by filtering `/\d@`.

```plain
D/1@Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/2@Logdog: │ MainActivity.testLog(MainActivity.java:57) on thread: main
D/3@Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/4@Logdog: │ hello
D/5@Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
E/6@Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
E/7@Logdog: ║ MainActivity.testLog(MainActivity.java:58) on thread: main
E/8@Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
E/9@Logdog: ║ java.lang.Throwable
E/0@Logdog: ║ 	at cn.thens.logdog.sample.MainActivity.testLog(MainActivity.java:57)
E/1@Logdog: ║ 	at cn.thens.logdog.sample.MainActivity.onCreate(MainActivity.java:53)
E/2@Logdog: ║ 	at android.app.Activity.performCreate(Activity.java:6278)
E/3@Logdog: ║ 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1107)
E/4@Logdog: ║ 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2396)
E/5@Logdog: ║ 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2503)
E/6@Logdog: ║ 	at android.app.ActivityThread.-wrap11(ActivityThread.java)
E/7@Logdog: ║ 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1353)
E/8@Logdog: ║ 	at android.os.Handler.dispatchMessage(Handler.java:102)
E/9@Logdog: ║ 	at android.os.Looper.loop(Looper.java:148)
E/0@Logdog: ║ 	at android.app.ActivityThread.main(ActivityThread.java:5529)
E/1@Logdog: ║ 	at java.lang.reflect.Method.invoke(Native Method)
E/2@Logdog: ║ 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:745)
E/3@Logdog: ║ 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:635)
E/4@Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
A/5@Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
A/6@Logdog: ║ MainActivity.testLog(MainActivity.java:59) on thread: main
A/7@Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
A/8@Logdog: ║ What a Terrible Failure
A/9@Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
D/0@Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/1@Logdog: │ MainActivity.testLog(MainActivity.java:60) on thread: main
D/2@Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/3@Logdog: │ total memory: 3035 MB
D/4@Logdog: │ avail memory: 2644 MB
D/5@Logdog: │ threshold: 144 MB
D/6@Logdog: │ low memory: false
D/7@Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/8@Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/9@Logdog: │ MainActivity.testLog(MainActivity.java:61) on thread: main
D/0@Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/1@Logdog: │ count(hello): 1
D/2@Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/3@Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/4@Logdog: │ MainActivity.testLog(MainActivity.java:62) on thread: main
D/5@Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/6@Logdog: │ count(hello): 2
D/7@Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
W/8@Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
W/9@Logdog: ║ MainActivity.testLog(MainActivity.java:63) on thread: main
W/0@Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
W/1@Logdog: ║ time(hello): 324167577ms
W/2@Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
W/3@Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
W/4@Logdog: ║ MainActivity.testLog(MainActivity.java:64) on thread: main
W/5@Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
W/6@Logdog: ║ time(hello): 0ms
W/7@Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
E/9@CustomLogger: MainActivity.onCreate(MainActivity.java:53) hello
```
