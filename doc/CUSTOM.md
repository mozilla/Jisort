    Copyright 2014 Mozilla Foundation. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

# Customizing the app for your own data

To customize the app for your own data, you will need to:

1. Set up the build  as described
in [BUILDING.md](BUILDING.md)

5. Edit Config.java settings as appropriate for your data

6. Generate bootstrap JSON data (which should be a recent snapshot of the
tutorial data JSON files) and save it to
res/raw/bootstrap_data.json. More information about format in
[SYNC.md](SYNC.md)

7. Modify the app to add your own icons, colors etc.

Here are a few more details about each step.


## Set up Config.java
The Config.java file is located at:

   [android/src/main/java/com/mozilla/hackathon/kiboko/Config.java](../android/src/main/java/com/mozilla/hackathon/kiboko/Config.java)

This file controls most of the event-specific aspects of IOSched.
Make sure to edit this file to reflect the configuration you need
for your event. Read the comments on the file for details about
what each configuration parameter means.

