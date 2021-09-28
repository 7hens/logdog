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

```kotlin
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
```

## Advanced

You can customize a logger as you want.

```kotlin
val customLogger = Logdog
    .priority(LogPriority.WARN)
    .strategy(LogStrategy.WARN)
    .tag("CustomLogger")
    .logger { priority, tag, message ->
        Logger.logcat().log(priority, tag,
            PrettyLogger.getStackInfo() + " " + PrettyLogger.stringOf(message))
    }

customLogger { "hello world" }
```

## Output

You can view all logs about Logdog by filtering `/=`.

```plain
E/=:CustomLogger: MainActivity.onCreate(MainActivity.java:53) hello world
D/=:Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/=:Logdog: │ MainActivity.testLog(MainActivity.java:59) on thread: main
D/=:Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/=:Logdog: │ hello
D/=:Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
E/=:Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
E/=:Logdog: ║ MainActivity.testLog(MainActivity.java:60) on thread: main
E/=:Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
E/=:Logdog: ║ java.lang.Throwable
E/=:Logdog: ║ 	at cn.thens.logdog.sample.MainActivity.testLog(MainActivity.java:59)
E/=:Logdog: ║ 	at cn.thens.logdog.sample.MainActivity.onCreate(MainActivity.java:53)
E/=:Logdog: ║ 	at android.app.Activity.performCreate(Activity.java:6278)
E/=:Logdog: ║ 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1107)
E/=:Logdog: ║ 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2396)
E/=:Logdog: ║ 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2503)
E/=:Logdog: ║ 	at android.app.ActivityThread.-wrap11(ActivityThread.java)
E/=:Logdog: ║ 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1353)
E/=:Logdog: ║ 	at android.os.Handler.dispatchMessage(Handler.java:102)
E/=:Logdog: ║ 	at android.os.Looper.loop(Looper.java:148)
E/=:Logdog: ║ 	at android.app.ActivityThread.main(ActivityThread.java:5529)
E/=:Logdog: ║ 	at java.lang.reflect.Method.invoke(Native Method)
E/=:Logdog: ║ 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:745)
E/=:Logdog: ║ 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:635)
E/=:Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
D/=:Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/=:Logdog: │ MainActivity.testLog(MainActivity.java:61) on thread: main
D/=:Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/=:Logdog: │ total memory: 3035 MB
D/=:Logdog: │ avail memory: 2617 MB
D/=:Logdog: │ threshold: 144 MB
D/=:Logdog: │ low memory: false
D/=:Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/=:Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/=:Logdog: │ MainActivity.testLog(MainActivity.java:62) on thread: main
D/=:Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/=:Logdog: │ count(hello): 1
D/=:Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/=:Logdog: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
D/=:Logdog: │ MainActivity.testLog(MainActivity.java:63) on thread: main
D/=:Logdog: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
D/=:Logdog: │ count(hello): 2
D/=:Logdog: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
W/=:Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
W/=:Logdog: ║ MainActivity.testLog(MainActivity.java:64) on thread: main
W/=:Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
W/=:Logdog: ║ time(hello): _
W/=:Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
W/=:Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
W/=:Logdog: ║ MainActivity.testLog(MainActivity.java:65) on thread: main
W/=:Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
W/=:Logdog: ║ time(hello): 1ms
W/=:Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
A/=:Logdog: ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════
A/=:Logdog: ║ MainActivity.testLog(MainActivity.java:66) on thread: main
A/=:Logdog: ╟┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
A/=:Logdog: ║ What a Terrible Failure
A/=:Logdog: ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════
```