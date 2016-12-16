# Contribute

This document serves as a helpful starting place for new project contributors, but also as a reference tool.

Mozilla Foundation currently (as of 2016) uses Mattermost as our communications solution. If at any point you need help, have a question or just want to say hello; join our [chat room](https://chat.mozillafoundation.org).

## Setup

* [Install Android Studio](#install-android-studio)
    * The first step of getting set up for Android development is installing Android Studio.
* [USB Debugging](#usb-debugging)
    * USB debugging allows you to use your phone in order to test your app changes and debug them if necessary.
* [Setup Jisort App](#setup-jisort-app)
    * All the steps needed to get a local version of the Jisort! app repository running locally on your Android phone.
* [Manually install Android SDK](#manually-install-android-sdk)
    * (Optional) Only do this if you want to use Android tools from the command line, or don't want to use Android Studio.

#### Install Android Studio


#### USB Debugging

  * In order to start testing apps on your physical Android device, we need to first activate developer mode by tapping ```Settings > About > Build Number``` seven times

  * Once activated, go to ```Settings > Developer Options > USB Debugging``` and make sure USB Debugging is enabled

  * Connect your phone to your computer via USB and select the green "run" button from the Android Studio tool bar or hit ```Run > Run 'app'``` from the menu bar. Your device should show up under the "Connected Devices" tab. If you've installed Android command line tools, you can also verify your phone is connected by running ```adb devices``` from the command line

  * [This guide](http://www.howtogeek.com/129728/how-to-access-the-developer-options-menu-and-enable-usb-debugging-on-android-4.2/) has additional information for enabling USB Debugging

#### Setup Jisort App

  * Clone the Jisort code repository from [mozilla/Jisort](https://github.com/mozilla/Jisort)

  * In Android Studio, choose "Open an existing Android project"

  * Select the Jisort folder, and press open

  * Connect your device via USB, and press the "run" button:


![run button](img/android-run-btn.png)
#### Manually install Android SDK