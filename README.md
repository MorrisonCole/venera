# Venera

Venera is a command-line tool that hooks into any Android test runner and instruments your application test suite.
It adds event-firing probes at instance method entries and logs to a human-readable JSON format. Included is an SDK
with an annotation that gives developers control over the instrumentation policy. Complex probes have a large overhead,
but record a huge amount of program state. Simple probes simply fire events. In the future, probes placement will be
based on historical test run data and a budget.

## Developer Guide

### Requirements

To build the project from source, the following tools are required:

- [Java Development Kit (v.7+)](http://www.oracle.com/technetwork/java/javase/downloads/index.html?ssSourceSiteId=otnjp)
- [Android SDK (with the latest tools)](http://developer.android.com/sdk/index.html?hl=sk)
- [Gradle (v.1.10+)](http://www.gradle.org/downloads)

We recommend using [IntelliJ](http://www.jetbrains.com/idea/) or some other Java-centric IDE, although anything is fine
in principal.

### Running the Project

To try Venera on the included Android application (skeleton-android-app), run from the command line:

```
cd skeleton-android-app
gradle clean connectedAndroidTest -P=venera
```

IntelliJ users can create a skeleton-android-app Gradle build configuration and run the task 'clean connectedAndroidTest'
with script parameters -P=venera.

To run Venera on your own Android application, modify the build.gradle file under venera-instrumentation to point to your
APKs. You'll need to also provide a Gradle task (or equivalent) to pull the results from the device in the same way that
skeleton-android-app's build.gradle already does. Note that integration with other Android applications will be made more
intuitive in the future.
