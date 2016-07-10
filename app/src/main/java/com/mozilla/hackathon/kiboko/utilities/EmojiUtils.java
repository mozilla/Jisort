package com.mozilla.hackathon.kiboko.utilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by secretrobotron on 7/10/16.
 */
public class EmojiUtils {
    public static final Map<String, Integer> emojiMap;

    static {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        map.put(":happy:", 0x1F601);
        map.put(":happy2:", 0x1F602);
        map.put(":happy3:", 0x1F603);
        map.put(":happy4:", 0x1F604);
        map.put(":happy5:", 0x1F605);
        map.put(":happy6:", 0x1F606);
        map.put(":wink:", 0x1F609);
        map.put(":blush:", 0x1F60A);
        map.put(":delicious:", 0x1F60B);
        map.put(":relief:", 0x1F60C);
        map.put(":hearts:", 0x1F60D);
        map.put(":smirk:", 0x1F60F);
        map.put(":unamused:", 0x1F612);
        map.put(":sweating:", 0x1F613);
        map.put(":pensive:", 0x1F614);
        map.put(":confounded:", 0x1F616);
        map.put(":kiss:", 0x1F618);
        map.put(":kiss2:", 0x1F61A);
        map.put(":tongue", 0x1F61C);
        map.put(":tongue2:", 0x1F61D);
        map.put(":disappointed:", 0x1F61E);
        map.put(":angry:", 0x1F620);
        map.put(":pouting:", 0x1F621);
        map.put(":crying:", 0x1F622);
        map.put(":persevering:", 0x1F623);
        map.put(":triumph:", 0x1F624);
        map.put(":relief2:", 0x1F625);
        map.put(":fearful:", 0x1F628);
        map.put(":weary:", 0x1F629);
        map.put(":sleepy:", 0x1F62A);
        map.put(":tired:", 0x1F62B);
        map.put(":crying2:", 0x1F62D);
        map.put(":screaming:", 0x1F631);
        map.put(":astonished:", 0x1F632);
        map.put(":flushed:", 0x1F633);
        map.put(":dizzy:", 0x1F635);
        map.put(":mask:", 0x1F637);
        map.put(":grinning-cat:", 0x1F638);
        map.put(":joy-cat:", 0x1F639);
        map.put(":smiling-cat:", 0x1F63A);
        map.put(":smiling-heart-cat:", 0x1F63B);
        map.put(":wry-cat:", 0x1F63C);
        map.put(":kissing-cat:", 0x1F63D);
        map.put(":pouting-cat:", 0x1F63E);
        map.put(":crying-cat:", 0x1F63F);
        map.put(":weary-cat:", 0x1F640);
        map.put(":no-good:", 0x1F645);
        map.put(":ok:", 0x1F646);
        map.put(":bowing:", 0x1F647);
        map.put(":see-no-evil:", 0x1F648);
        map.put(":hear-no-evil:", 0x1F649);
        map.put(":speak-no-evil:", 0x1F64A);
        map.put(":hand-person:", 0x1F64B);
        map.put(":both-hands-person:", 0x1F64C);
        map.put(":frowning-person:", 0x1F64D);
        map.put(":pouting-person:", 0x1F64E);
        map.put(":folded-hands-person:", 0x1F64F);

        emojiMap = Collections.unmodifiableMap(map);
    }

    public static String parse(String input) {
        Pattern pattern = Pattern.compile("(:[^:]+:)");
        Matcher matcher = pattern.matcher(input);

        String output = "";

        int start = 0;
        int end = 0;

        while (matcher.find()) {
            String match = matcher.group();
            start = matcher.start();

            if (emojiMap.containsKey(match)) {
                output += input.substring(end, start);
                output += new String(Character.toChars(emojiMap.get(match)));
            }

            end = matcher.end();
        }

        if (end < input.length()) {
            output += input.substring(end);
        }

        return output;
    }
}
