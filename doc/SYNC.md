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

# Data format

The format of the JSON file is:

```JSON
    {
       "<entity_collection>": [
           <entity>,
           <entity>,
           <entity>
       ],
       "<entity_collection>": [
           <entity>,
           <entity>,
           <entity>
       ]
    }
```

That is, each file consists of one or more collections of entities,
organized by type. Example entity collections are: tutorials, steps etc.

## Bootstrap data

When the user runs the app for the first time, they expect to see
data. The app ships with preloaded "bootstrap data", which
is essentially a preloaded offline snapshot of the JSON data. This
data is parsed by the app and saved to the database on first execution.

You can find this file in [res/raw/bootstrap_data.json](../android/src/main/res/raw).

## Data format

Below is the documentation about the format of each type of entity
supported by the Mozilla DSO android app.




### Tutorials and steps

```JSON
{
  "tutorials": [
    <tutorial>,
    <tutorial>,
    <tutorial>,
    ...
  ]
}
```
Where each `<tutorial>` has this format:

```JSON
 {
    "id": "1",
    "tag": "wifi",
    "header": "Wi-Fi ni Noma!",
    "photoUrl": "url",
    "steps": [{
        "id": "1",
        "title": "What is Wi-Fi?",
        "gifUrl": "wifi1",
        "description": "Wi-Fi is a technology that helps your phone connect to the internet, similar to airtime or mobile data. However, <b>Wi-Fi doesn't use your airtime or mobile data.</b> Instead, in coffee shops or public spaces, Wi-Fi is often free."
    }, {
        "id": "2",
        "title": "Where do I get it? Who owns it?",
        "gifUrl": "wifi2",
        "description": "Wi-Fi networks are created by \"hotspots\" that connect to the internet. Anyone can buy one and set it up at home, at work, or in a public space. If you can detect a Wi-Fi network on your phone, you might be able to connect to it. To learn more about connecting, check out Connecting to Wi-Fi."
    }, {
        "id": "3",
        "title": "Saving Money",
        "gifUrl": "wifi3",
        "description": "Wi-Fi is great for saving money because it lets you connect to the internet without using mobile data or airtime on your phone. Remember, though, sometimes you have to pay a small fee to use Wi-Fi at a business or cafe, or buy some goods."
    }, {
        "id": "4",
        "title": "Speed and Stability",
        "gifUrl": "wifi4",
        "description": "Wi-Fi networks are generally more stable and faster than mobile data networks. Wi-Fi was designed to support multiple smartphones at once and lots of internet browsing. Downloading files or streaming videos is best over Wi-Fi.\n<b>Be aware:</b> it is not <b>always</b> the case that Wi-Fi is faster and more stable than mobile data. Depending on infrastructure, popularity, and other environmental factors, Wi-Fi may not be as responsive or reliable."
    }]
}
```



