    Copyright 2015 Mozilla Foundation. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


# How to build Mozilla DSO app

This is a Gradle-based project that works best with
[Android Studio](http://developer.android.com/sdk/installing/studio.html)

To build the app:

1. Install the following software:
       - Android SDK:
         http://developer.android.com/sdk/index.html
       - Gradle:
         http://www.gradle.org/downloads
       - Android Studio:
         http://developer.android.com/sdk/installing/studio.html

1. Run the Android SDK Manager by pressing the SDK Manager toolbar button
   in Android Studio or by running the 'android' command in a terminal
   window.

1. In the Android SDK Manager, ensure that the following are installed,
   and are updated to the latest available version:
       - Tools > Android SDK Platform-tools
       - Tools > Android SDK Tools
       - Tools > Android SDK Build-tools
       - Tools > Android SDK Build-tools
       - Android 6.0 > SDK Platform (API 23)
       - Extras > Android Support Repository
       - Extras > Android Support Library
       - Extras > Google Play services
       - Extras > Google Repository

1. Create a file in your working directory called local.properties,
   containing the path to your Android SDK. Use local.properties.example as a
   model.

1. Import the project in Android Studio:

    1. Press File > Import Project
    1. Navigate to and choose the settings.gradle file in this project
    1. Press OK

1. Add your debug keystore to the project (save it as keystore/debug.keystore),
    or modify the build.gradle file to point to your key.

1. Choose Build > Make Project in Android Studio or run the following
    command in the project root directory:
   ```
    ./gradlew clean assembleDebug
   ```
1. To install on your test device:

   ```
    ./gradlew installDebug
   ```


Done.

