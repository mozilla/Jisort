# Contribute

This document serves as a helpful starting place for new project contributors, but also as a reference tool.

Mozilla Foundation currently (as of 2016) uses Mattermost as our communications solution. If at any point you need help, have a question or just want to say hello; join our [chat room](https://chat.mozillafoundation.org).

## Good Ole' Lingo 

While we try our best to refrain from using obscure terminalogy that isn't widely known, you're likely to come across some things that don't quite make sense.

| Terminalogy | Description |
| --- | --- |
| MoFo | Short for "Mozilla Foundation", MoFo is fueling a broader internet health movement. |
| DSO | Short for "Digital Skills Observatory", the research project this prototype was developed for. |
| R? | Short for "review?", usage of this is no longer encouraged however. |
| R+ | Short for "reviewed", it means that your code passed review and is ready to be merged. |
| R- | Short for "needs work", it means that your code didn't pass review (and needs requested changes) in order to be merged. |

## Setup

This is a Gradle-based project that works best with [Android Studio](http://developer.android.com/sdk/installing/studio.html).

1. Install the following software:
       
       - Android SDK: http://developer.android.com/sdk/index.html
       - Gradle: http://www.gradle.org/downloads
       - Android Studio: http://developer.android.com/sdk/installing/studio.html

2. Run the Android SDK Manager by pressing the SDK Manager toolbar button
   in Android Studio or by running the 'android' command in a terminal
   window.

3. In the Android SDK Manager, ensure that the following are installed, and are updated to the latest available version:

       - Tools > Android SDK Platform-tools
       - Tools > Android SDK Tools
       - Tools > Android SDK Build-tools
       - Android 7.0 > SDK Platform (API 25) // This might be outdated at time of reading, select the newest version of Android & Platform (highest number).
       - Extras > Android Support Repository
       - Extras > Android Support Library
       - Extras > Google Play services
       - Extras > Google Repository

4. Create a file in your working directory called local.properties,
   containing the path to your Android SDK. Use local.properties.example as a
   model.

5. Import the project in Android Studio:

    1. Press File > Import Project
    1. Navigate to and choose the settings.gradle file in this project
    1. Press OK

6. Add your debug keystore to the project (save it as keystore/debug.keystore),
    or modify the build.gradle file to point to your key.

7. Choose Build > Make Project in Android Studio or run the following command in the project root directory:
   ```
    ./gradlew clean assembleDebug
   ```

8. To install on your test device:

   ```
    ./gradlew installDebug
   ```



