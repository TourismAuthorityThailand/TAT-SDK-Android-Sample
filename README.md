# TAT-SDK-Android-Sample app

Welcome to the TAT-SDK-Android Sample application. This application demostrates how to use the TAT-SDK for Android inside an Android app.

## Prerequisites

You can get **API Key** from [TAT Developer Guide and Console](https://developers.tourismthailand.org/console) website.

## Download TAT-SDK-Android

use Gradle:

```gradle
// In Project's build.gradle
repositories {
    google()
    jcenter()
    maven {
        url 'https://dl.bintray.com/nostratat/maven'
    }
}

// In Module's build.gradle
dependencies {
    implementation 'org.th.tatsdk:tatsdk:0.0.6'
}
```
## How do I use TAT-SDK-Android ?

Setup API Key for initial environment in application like this:

```java
// Create BaseApplication class that extend Application,
// then setup API Key in this class.
@Override
public void onCreate() {
    super.onCreate();
    TATSDKEnvironment.setEnvironment([API_KEY], this);
}
```

 * SDK Reference: [TAT-SDK-Android Reference](https://gdev.geotalent.co.th/tatsdk/android/)

## Samples
1. [Place Search](/placesearch/README.md)
2. [Events and Festivals](#EventsAndFestival)
3. [News](#News)
4. [Recommended Route](#RecRoute)

## Compatibility

 * Minimum Android SDK requires a minimum API level of 23.
 * Compile and Target Android SDK requires you to compile against API 28 or later.

## Authors

* Power by GLOBETECH Co., Ltd - [GlobeTech Co., Ltd. – CDG](https://www.cdg.co.th/website/about-cdg/affiliates-of-cdg/globetech-co-ltd/)
* Developed by GEOTALENT Co., Ltd - [GEOTALENT | GIS Developer](https://www.geotalent.co.th)

## License

* Copyright © <a href="https://www.tourismthailand.org/home" target="_blank">Tourism Authority of Thailand</a>.

## Acknowledgments

* Glide - https://github.com/bumptech/glide
