package org.sufficientlysecure.htmltextview;

public class EmojiUtils {

    public static String convertTag(String str){
        return str
                .replaceAll("<", "&lt;")
                .replaceAll(":)","<img src=\"1f60a\"/>")
                .replaceAll(":)","<img src=\"1f60b\"/>")
                .replaceAll(":)","<img src=\"1f60c\"/>")
                .replaceAll(":)","<img src=\"1f60d\"/>")
                .replaceAll(":)","<img src=\"1f60e\"/>")
                .replaceAll(":)","<img src=\"1f60f\"/>")
                .replaceAll(":)","<img src=\"1f61a\"/>")
                .replaceAll(":)","<img src=\"1f61b\"/>")
                .replaceAll(":)","<img src=\"1f61c\"/>")
                .replaceAll(":)","<img src=\"1f61d\"/>")
                .replaceAll(":)","<img src=\"1f61e\"/>")
                .replaceAll(":)","<img src=\"1f61f\"/>")
                .replaceAll(":)","<img src=\"1f62a\"/>")
                .replaceAll(":)","<img src=\"1f62b\"/>")
                .replaceAll(":)","<img src=\"1f62c\"/>")
                .replaceAll(":)","<img src=\"1f62d\"/>")
                .replaceAll(":)","<img src=\"1f62e\"/>")
                .replaceAll(":)","<img src=\"1f62f\"/>")
                ;
    }
}
