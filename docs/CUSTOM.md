# Customizing Content

Jisort! is an informational app, it runs on data and it's designed to teach people skills. The content you see within Jisort! is completely customizable, making it a great multi-purpose tool for any situation where you want to create an app that teaches people something. You can add your own topics, tutorials, and quizzes without any programming experience.

By default Jisort! comes packaged with it's own built-in content, which you can view [here](https://raw.githubusercontent.com/mozilla/Jisort/master/app/src/main/res/raw/bootstrap_data.json).

## Getting Started

Before we start making our own content, we need to understand how this works. All content goes into a special file format called JSON (JavaScript Object Notation) under the file name ```bootstrap_data.json```. Within our content file, we define objects. An object in this case can be a topic, tutorial or quiz.

Each object has attributes and each of these attributes can be modified. When Jisort! first runs, it parses the content file and saves it to database on first execution. We can change the content by changing object attributes.

## Data Format

Below is the documentation about the format of each type of object supported by Jisort!

### Tutorials

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

## Adding Content

It's super simple and easy to add your own content, however here are a few things to keep in mind while doing so.

  - Always increment the id when adding more tutorials, or steps to a tutorial.
      - Having two tutorials or steps with the same id can lead to bugs, crashes or unexpected behavior.
  - If you're adding custom images, make sure you place them in the ```drawable-nodpi``` before referencing them in your content.
      - You can reference images by using the name of the image, minus it's extension (i.e png, gif)
  - Verify that your content is being formatted correctly, and use short tutorial headers.

You can also use HTML and Emojis to help you build your own custom content. Jisort! supports a large variety of HTML tags, and over 140+ emojis.

Happy Customizing! :rocket: