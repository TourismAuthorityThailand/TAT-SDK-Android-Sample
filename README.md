# TAT-SDK-Android-Sample app

Welcome to the TAT-SDK-Android Sample application. This application demostrates how to use the TAT-SDK for Android inside an Android app.

## Prerequisites

You can get **API Key** from [TAT Developer Guide and Console](https://developers.tourismthailand.org/console) website.

## Download TAT-SDK-Android
[ ![Download](https://api.bintray.com/packages/nostratat/maven/tat-sdk/images/download.svg) ](https://bintray.com/nostratat/maven/tat-sdk/_latestVersion)

use Gradle:

```gradle
repositories {
    google()
    jcenter()
    mavenCentral()
    maven {
        url 'https://dl.bintray.com/nostratat/maven'
    }
}

dependencies {
    implementation 'org.th.tatsdk:tatsdk:1.0.0'
}
```
## How do I use TAT-SDK-Android ?

Setup API Key for initial environment in application like this:

```java
// Setup API Key in startup class that extend Application.
@Override
public void onCreate() {
    super.onCreate();
    String apiKey = "[API_KEY]"
    TATSDKEnvironment.setEnvironment(apiKey, this);
}
```

 * SDK Reference: [TAT-SDK-Android Reference](https://gdev.geotalent.co.th/tatsdk/android/)

## Samples
1. [Place Search](/app/src/main/java/org/tat/sdksample/placesearch/README.md)
2. [Events and Festivals](/app/src/main/java/org/tat/sdksample/event/README.md)
3. [News](/app/src/main/java/org/tat/sdksample/news/README.md)
4. [Recommended Routes](/app/src/main/java/org/tat/sdksample/recommendedroute/README.md)
<!--5. [ChatBot](/app/src/main/java/org/tat/sdksample/chatbot/README.md)-->

<img src="SDK_Sample_Menu.png" width="30%">

## Compatibility

 * Minimum Android SDK requires a minimum API level of 23.
 * Compile and Target Android SDK requires you to compile against API 28 or later.

## Authors

* Powered by GLOBETECH Co., Ltd - [GlobeTech Co., Ltd. – CDG](https://www.cdg.co.th/website/about-cdg/affiliates-of-cdg/globetech-co-ltd/)
* Developed by GEOTALENT Co., Ltd - [GEOTALENT | GIS Developer](https://www.geotalent.co.th)

## License

* Copyright © <a href="https://www.tourismthailand.org/home" target="_blank">Tourism Authority of Thailand</a>.

## Acknowledgments

* Glide - https://github.com/bumptech/glide
