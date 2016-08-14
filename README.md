## Taponium
![Preview](http://i.imgur.com/LV7yGxq.png)

This is a simple reflex-oriented arcade game for Android. It was written with usage of Android SDK and the minimal API version of 10 corresponding to at least Android 2.3.3 and newer. It uses Canvas to draw things on the display and it's run by a terrible engine-like thing which is pretty much useless for anything else. It was designed to be resolution independent but wasn't really tested extensively for that case.

Taponium was inspired by a game called Wacko which had been shipped with old Siemens phones with monochromatic displays many years ago. The game mechanics are simple. Player has to tap as many hamsters randomly appearing on a board as he can. For each tapped hamster he receives 10 points. For each missed hamster he loses 100 points. Additionally, tapping a bunny causes a loss of 1000 points, so watch out for these ones! Difficulty gradually rises over time as everything gets surprisingly faster.

### Building
Open this project with Android Studio and choose debug build variant. Release variant requires signing the application to make it properly installable on a device, so it's not feasible. Depending on the needs you can run and debug the game on your device directly with Android Studio (if you have ADB drivers) or just build the .apk file. These options are accessible within Build menu item.

### Usage
You have to allow installing applications from unknown sources on your device. This is usually a checkbox placed somewhere inside system's security subsettings page. You have to check it. After that, put the .apk file into the device's memory and with some kind of a file manager go to its location and install it. You can launch the game directly after successful installation.

### Credits
Taponium uses Indie Flower font created by Kimberly Geswein and published under Open Font License.