# Logdog

[![jitpack](https://jitpack.io/v/7hens/logdog.svg)](https://jitpack.io/#7hens/logdog)
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
        .debug(LogMessages.memory(this))
        .debug(LogMessages.count("hello"))
        .debug(LogMessages.count("hello"))
        .warn(LogMessages.time("hello"))
        .warn(LogMessages.time("hello"))
        .onlyIf(BuildConfig.DEBUG).wtf("What a Terrible Failure");
```

## Advanced

You can customize a logger as you want.

```java
Logdog yourLogdog = Logdog.create("CustomLogger", new PrettyLogger(Logger.LOGCAT) {
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
```

You can also change the behavior of `Logdog.get().xxx()` by reset the default Logdog instance.

```java
Logdog.setDefaultInstance(yourLogdog);
```

## Output

You can view all logs about Logdog by filtering `/\d@`.

```plain
E/1@CustomLogger: MainActivity.onCreate(MainActivity.java:53) hello
D/2@Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/3@Logdog: │ MainActivity.testLog(MainActivity.java:59) on thread: main
D/4@Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/5@Logdog: │ hello
D/6@Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
E/7@Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
E/8@Logdog: ║ MainActivity.testLog(MainActivity.java:60) on thread: main
E/9@Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
E/0@Logdog: ║ java.lang.Throwable
E/1@Logdog: ║ 	at cn.thens.logdog.sample.MainActivity.testLog(MainActivity.java:59)
E/2@Logdog: ║ 	at cn.thens.logdog.sample.MainActivity.onCreate(MainActivity.java:53)
E/3@Logdog: ║ 	at android.app.Activity.performCreate(Activity.java:6278)
E/4@Logdog: ║ 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1107)
E/5@Logdog: ║ 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2396)
E/6@Logdog: ║ 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2503)
E/7@Logdog: ║ 	at android.app.ActivityThread.-wrap11(ActivityThread.java)
E/8@Logdog: ║ 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1353)
E/9@Logdog: ║ 	at android.os.Handler.dispatchMessage(Handler.java:102)
E/0@Logdog: ║ 	at android.os.Looper.loop(Looper.java:148)
E/1@Logdog: ║ 	at android.app.ActivityThread.main(ActivityThread.java:5529)
E/2@Logdog: ║ 	at java.lang.reflect.Method.invoke(Native Method)
E/3@Logdog: ║ 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:745)
E/4@Logdog: ║ 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:635)
E/5@Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
D/6@Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/7@Logdog: │ MainActivity.testLog(MainActivity.java:61) on thread: main
D/8@Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/9@Logdog: │ total memory: 3035 MB
D/0@Logdog: │ avail memory: 2617 MB
D/1@Logdog: │ threshold: 144 MB
D/2@Logdog: │ low memory: false
D/3@Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/4@Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/5@Logdog: │ MainActivity.testLog(MainActivity.java:62) on thread: main
D/6@Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/7@Logdog: │ count(hello): 1
D/8@Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/9@Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/0@Logdog: │ MainActivity.testLog(MainActivity.java:63) on thread: main
D/1@Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/2@Logdog: │ count(hello): 2
D/3@Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
W/4@Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
W/5@Logdog: ║ MainActivity.testLog(MainActivity.java:64) on thread: main
W/6@Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
W/7@Logdog: ║ time(hello): 360946482ms
W/8@Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
W/9@Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
W/0@Logdog: ║ MainActivity.testLog(MainActivity.java:65) on thread: main
W/1@Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
W/2@Logdog: ║ time(hello): 1ms
W/3@Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
A/4@Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
A/5@Logdog: ║ MainActivity.testLog(MainActivity.java:66) on thread: main
A/6@Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
A/7@Logdog: ║ What a Terrible Failure
A/8@Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
```
